package com.gilcu2.balltree


object Node {

  def apply[T](b: Ball[T], parent: Option[Node[T]] = None,
               left: Option[Node[T]] = None, right: Option[Node[T]] = None): Node[T] =
    new Node(b, parent, left, right)

  def bestBall[T](b1: Ball[T], b2: Ball[T])(implicit distance: (T, T) => Double): Node[T] = {
    if (b1.contains(b2)) Node(b1)
    else if (b2.contains(b1)) Node(b2)
    Node(Ball(b1.center, distance(b1.center, b2.center) + Math.min(b1.radio, b2.radio)))
  }

}

case class Node[T](ball: Ball[T], parent: Option[Node[T]] = None,
                   left: Option[Node[T]] = None, right: Option[Node[T]] = None) {

  def insert(ball: Ball[T])(implicit distance: (T, T) => Double): Node[T] = {
    (left, right) match {

      case (None, None) =>
        val newLeft = Some(Node(ball))
        val newRight = Some(this)
        Node.bestBall(ball, this.ball)

      case (None, _) =>
        val newLeft = Some(Node(ball))
        val newRight = Some(this)
        this

      case (_, None) =>
        this.copy(right = Some(Node(ball)))

      case _ =>
        this
    }


  }

  def getInside(ball: Ball[T])(implicit distance: (T, T) => Double): Seq[Ball[T]] = {
    val isInside = ball.contains(this.ball)
    (isInside, left, right) match {
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

}
