package com.gilcu2.balltree

import com.gilcu2.spaces.Space

object BallsOperations {

  def computeBoundingBall[T](balls: Seq[Ball[T]])(implicit space: Space[T]): Ball[T] = {

    val (b1, b2) = approximateFarthestPair(balls)

    var center = midPoint(b1, b2)
    var radio = space.distance(center, b2.center) + b2.radio
    var boundingBall = Ball(center, radio)

    var ballsOutside = balls.filter(!boundingBall.contains(_))
    while (ballsOutside.nonEmpty) {
      val farthest = findLargestMaximumDistanceFrom(center, balls)
      val distance = farthest.maximumDistance(center)
      val delta = distance - radio
      center = space.moveToward(center, farthest.center, delta)
      radio = radio * 1.01
      boundingBall = Ball(center, radio)
      ballsOutside = balls.filter(!boundingBall.contains(_))
    }

    boundingBall
  }

  def computePartition[T](balls: Seq[Ball[T]])(implicit space: Space[T]): (Seq[Ball[T]], Seq[Ball[T]]) = {

    val (b1, b2) = approximateFarthestPair(balls)

    val nearestPointPerBall = balls.map(b => {
      val db1 = b1.maximumDistance(b)
      val db2 = b2.maximumDistance(b)
      if (db1 > db2) (b, 1) else (b, 2)
    })

    val (part1, part2) = nearestPointPerBall.partition(_._2 == 1)
    (part1.map(_._1), part2.map(_._1))
  }

  def approximateFarthestPair[T](balls: Seq[Ball[T]])(implicit space: Space[T]): (Ball[T], Ball[T]) = {
    val b1 = findLargestMinimumDistanceFrom(balls(0), balls)
    val b2 = findLargestMinimumDistanceFrom(b1, balls)
    (b1, b2)
  }

  def findLargestMinimumDistanceFrom[T](b: Ball[T], balls: Seq[Ball[T]])(implicit space: Space[T]): Ball[T] = {
    val tmp = balls.map(b1 => (b1, b.minimumDistance(b1)))
    tmp.maxBy(_._2)._1
  }

  def findLargestMaximumDistanceFrom[T](p: T, balls: Seq[Ball[T]])(implicit space: Space[T]): Ball[T] =
    balls.map(b1 => (b1, b1.maximumDistance(p))).maxBy(_._2)._1

  def midPoint[T](b1: Ball[T], b2: Ball[T])(implicit space: Space[T]): T = {
    val distance = b1.minimumDistance(b2)
    val b1NearestPoint = space.moveToward(b1.center, b2.center, b1.radio)
    val b2NearestPoint = space.moveToward(b2.center, b1.center, b2.radio)
    space.moveToward(b1NearestPoint, b2NearestPoint, distance / 2)
  }

}
