package com.gilcu2.balltree

import com.gilcu2.spaces.Space


object Node {

  def apply[T](b: Ball[T], parent: Option[Node[T]] = None,
               left: Option[Node[T]] = None, right: Option[Node[T]] = None): Node[T] =
    new Node(b, parent, left, right)

  def bestBall[T](b1: Ball[T], b2: Ball[T])(implicit space: Space[T]): Node[T] = {
    if (b1.contains(b2)) Node(b1)
    else if (b2.contains(b1)) Node(b2)
    Node(Ball(b1.center, space.distance(b1.center, b2.center) + Math.min(b1.radio, b2.radio)))
  }

  //  def bestChild[T](newBall: Ball[T], leftBall: Ball[T], rightBall: Ball[T])(implicit distance: (T, T) => Double)
  //  : (Boolean, Ball[T]) = {
  //    val posibleLeftRadio = distance(newBall.center, leftBall.center)
  //  }

  def childToString[T](possibleNode: Option[Node[T]]): String = {
    if (possibleNode.nonEmpty) possibleNode.get.ball.toString else ""
  }

}

case class Node[T](ball: Ball[T], private var parent: Option[Node[T]] = None,
                   private var left: Option[Node[T]] = None, private var right: Option[Node[T]] = None) {

  import Node._

  def insert(ball: Ball[T])(implicit space: Space[T]): Node[T] = {
    (left, right) match {

      case (None, None) =>
        val newRoot = Node.bestBall(this.ball, ball)
        val newLeft = this
        newLeft.parent = Some(newRoot)
        newRoot.left = Some(newLeft)

        val newRight = Node(ball, parent = Some(newRoot))
        newRoot.right = Some(newRight)
        newRoot

      case _ =>
        this
    }

  }

  def getInside(ball: Ball[T])(implicit space: Space[T]): Seq[Ball[T]] = {
    val intercept = ball.isIntercepting(this.ball)
    (intercept, left, right) match {
      case (false, _, _) =>
        Seq()
      case (true, None, None) =>
        Seq(this.ball)
      case _ =>
        val insideLeft = if (left.nonEmpty) left.get.getInside(ball) else Seq()
        val insideRight = if (right.nonEmpty) right.get.getInside(ball) else Seq()
        insideLeft ++ insideRight
    }
  }

  def getParent: Option[Node[T]] = this.parent

  def getLeft: Option[Node[T]] = this.left

  def getRight: Option[Node[T]] = this.right

  override def toString: String = s"Node(${this.ball},${childToString(this.left)},${childToString(this.right)} )"

}
