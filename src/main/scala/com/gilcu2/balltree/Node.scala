package com.gilcu2.balltree


object Node {

  def apply[T](ball: Ball[T], parent: Option[Node[T]] = None,
               left: Option[Node[T]] = None, right: Option[Node[T]] = None): Node[T] =
    new Node(ball, parent, left, right)

}

class Node[T](var ball: Ball[T], var parent: Option[Node[T]] = None,
              var left: Option[Node[T]] = None, var right: Option[Node[T]] = None) {

  def insert(ball: Ball[T])(implicit distance: (T, T) => Double): Node[T] = {
    (left, right) match {

      case (None, _) =>
        left = Some(Node(ball))
        this

      case (_, None) =>
        right = Some(Node(ball))
        this

      case _ =>
        this
    }
  }

  def getInside(ball: Ball[T])(implicit distance: (T, T) => Double): Seq[Ball[T]] = {
    val isInside = ball.isInside(this.ball)
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
