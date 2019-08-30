package com.gilcu2.balltree

import com.gilcu2.spaces.Space

object BallTree {

  def apply[T](space: Space[T], balls: Ball[T]*): BallTree[T] =
    new BallTree(space, balls: _*)

}

class BallTree[T](space: Space[T], balls: Ball[T]*) {

  assert(balls.nonEmpty)

  implicit val s = space

  private var nElements = 1
  private var root = Node(balls(0))

  def insert(b: Ball[T]): Unit = {
    nElements += 1
    root = root.insert(b)
  }

  def getInside(b: Ball[T]): Seq[Ball[T]] = root.getInside(b)

  def size: Int = nElements

  def getRoot: Node[T] = this.root

  def computeBoundingBall(balls: Seq[Ball[T]]): Ball[T] = {

    val b1 = findFarthestFrom(balls(0), balls)
    val b2 = findFarthestFrom(b1, balls)

    val center = midPoint(b1, b2)
    val radio = space.distance(center, b2.center) + b2.radio
    var boundingBall = Ball(center, radio)

    var ballsOutside = balls.filter(!boundingBall.contains(_))
    while (balls.nonEmpty) {
      val farthest =
    }

  }

  def findFarthestFrom(b: Ball[T], balls: Seq[Ball[T]]): Ball[T] =
    balls.map(b1 => (b1, b.minimumDistance(b1))).maxBy(_._2)._1

  def midPoint(b1: Ball[T], b2: Ball[T]): T = {
    val distance = b1.minimumDistance(b2)
    val b1NearestPoint = space.moveToward(b1.center, b2.center, b1.radio)
    val b2NearestPoint = space.moveToward(b2.center, b1.center, b2.radio)
    space.moveToward(b1NearestPoint, b2NearestPoint, distance / 2)
  }


}
