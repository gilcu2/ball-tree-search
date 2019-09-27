package com.gilcu2.balltree

import com.gilcu2.spaces.Space


object Node {

  def apply[T](id: Int, b: Ball[T], parent: Option[Node[T]] = None,
               left: Option[Node[T]] = None, right: Option[Node[T]] = None): Node[T] =
    new Node(id, b, parent, left, right)

  //  def bestBall[T](b1: Ball[T], b2: Ball[T])(implicit space: Space[T]): Node[T] = {
  //    if (b1.contains(b2)) Node(b1)
  //    else if (b2.contains(b1)) Node(b2)
  //    Node(Ball(b1.center, space.distance(b1.center, b2.center) + Math.min(b1.radio, b2.radio)))
  //  }

  //  def bestChild[T](newBall: Ball[T], leftBall: Ball[T], rightBall: Ball[T])(implicit distance: (T, T) => Double)
  //  : (Boolean, Ball[T]) = {
  //    val posibleLeftRadio = distance(newBall.center, leftBall.center)
  //  }

  def relatedId[T](possibleNode: Option[Node[T]]): String = {
    if (possibleNode.nonEmpty) possibleNode.get.id.toString else ""
  }

}

case class Node[T](id: Int, ball: Ball[T], var parent: Option[Node[T]] = None,
                   var left: Option[Node[T]] = None, var right: Option[Node[T]] = None) {

  import Node._

  //  def insert(ball: Ball[T])(implicit space: Space[T]): Node[T] = {
  //    (left, right) match {
  //
  //      case (None, None) =>
  //        val newRoot = Node.bestBall(this.ball, ball)
  //        val newLeft = this
  //        newLeft.parent = Some(newRoot)
  //        newRoot.left = Some(newLeft)
  //
  //        val newRight = Node(ball, parent = Some(newRoot))
  //        newRoot.right = Some(newRight)
  //        newRoot
  //
  //      case _ =>
  //        this
  //    }
  //  }

  def areContained(b: Ball[T])(implicit space: Space[T]): Seq[Ball[T]] = {
    val intercept = b.isIntercepting(this.ball)
    (intercept, left, right) match {
      case (false, _, _) =>
        Seq()
      case (true, None, None) =>
        if (b.contain(this.ball))
          Seq(this.ball)
        else
          Seq()
      case _ =>
        val insideLeft = left.fold(Seq[Ball[T]]())(_.areContained(b))
        val insideRight = right.fold(Seq[Ball[T]]())(_.areContained(b))
        insideLeft ++ insideRight
    }
  }

  def contain(b: Ball[T])(implicit space: Space[T]): Seq[Ball[T]] = {
    val contained = this.ball.contain(b)
    (contained, left, right) match {
      case (false, _, _) =>
        Seq()
      case (true, None, None) =>
        Seq(this.ball)
      case _ =>
        val insideLeft = left.fold(Seq[Ball[T]]())(_.contain(b))
        val insideRight = right.fold(Seq[Ball[T]]())(_.contain(b))
        insideLeft ++ insideRight
    }
  }

  def nearestMaximumDistance(b: Ball[T], bestDistance: Double)(implicit space: Space[T]): (Option[Ball[T]], Double) = {

    val minimumDistance = b.minimumDistance(this.ball)
    (minimumDistance, left, right) match {
      case _ if minimumDistance > bestDistance =>
        (None, 0)
      case (_, None, None) =>
        val distance = b.maximumDistance(this.ball)
        if (distance < bestDistance)
          (Some(this.ball), distance)
        else
          (None, 0)
      case _ =>
        val (bestLeftBall, distanceLeft) = left.get.nearestMaximumDistance(b, bestDistance)
        val bestDistance1 = if (bestLeftBall.nonEmpty && distanceLeft < bestDistance) distanceLeft else bestDistance

        val (bestRightBall, distanceRight) = right.get.nearestMaximumDistance(b, bestDistance1)
        (bestLeftBall, bestRightBall) match {
          case (None, None) =>
            (None, 0)
          case (_, bestRight) if bestRight.nonEmpty =>
            (bestRightBall, distanceRight)
          case (_, None) =>
            (bestLeftBall, distanceLeft)
        }
    }

  }

  def kNearestMaximumDistance(b: Ball[T], k: Int,
                              priorityQueue: BoundedPriorityQueue[BallDistance[T]]
                             )(implicit space: Space[T]): Unit = {

    val minimumDistance = b.minimumDistance(this.ball)

    def getMaximumDistanceInQueue: Double =
      if (priorityQueue.size == k) priorityQueue.peek.distance else Double.MaxValue

    val maximumDistanceInQueue = getMaximumDistanceInQueue
    (minimumDistance, left, right) match {

      case _ if minimumDistance > maximumDistanceInQueue =>

      case (_, None, None) =>
        val distance = b.maximumDistance(this.ball)
        if (distance < maximumDistanceInQueue)
          priorityQueue += BallDistance(this.ball, distance)
      case _ =>
        left.get.kNearestMaximumDistance(b, k, priorityQueue)
        right.get.kNearestMaximumDistance(b, k, priorityQueue)
    }

  }


  override def toString: String =
    s"Node(${this.id}: ${this.ball} Parent: ${relatedId(this.parent)} Left: ${relatedId(this.left)} Right: ${relatedId(this.right)} )"

}
