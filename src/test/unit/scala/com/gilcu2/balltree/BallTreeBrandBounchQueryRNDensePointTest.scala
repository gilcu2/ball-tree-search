package com.gilcu2.balltree

import com.gilcu2.spaces.RNDensePoint.toBall
import com.gilcu2.spaces._
import org.scalatest.{FlatSpec, GivenWhenThen, Matchers}

class BallTreeBrandBounchQueryRNDensePointTest extends FlatSpec with Matchers with GivenWhenThen {

  behavior of "BallTree"

  implicit val space = EuclideanSpace(2)

  it should "find the nearest ball using the maximum distance between balls, query on left" in {
    val ball1 = Ball(RNDensePoint(0, 0), 1)
    val ball2 = Ball(RNDensePoint(1, 0), 1)
    val ball3 = Ball(RNDensePoint(1, 1), 1)
    val balls = Seq(ball1, ball2, ball3)
    val ballTree = BallTree(balls)

    ballTree.nearestMaximumDistance(Ball(RNDensePoint(-1, 0), 1)) shouldBe ball1

  }

  it should "find the nearest ball using the maximum distance between balls, query on top" in {
    val ball1 = Ball(RNDensePoint(0, 0), 1)
    val ball2 = Ball(RNDensePoint(1, 0), 1)
    val ball3 = Ball(RNDensePoint(1, 1), 1)
    val balls = Seq(ball1, ball2, ball3)
    val ballTree = BallTree(balls)

    ballTree.nearestMaximumDistance(Ball(RNDensePoint(0, 2, 0), 1)) shouldBe ball3

  }

  it should "nearest point using the maximum distance between balls, query on top" in {
    val p1 = Ball(RNDensePoint(0, 0), 1)
    val p2 = Ball(RNDensePoint(1, 0), 1)
    val p3 = Ball(RNDensePoint(1, 1), 1)
    val points = Seq(p1, p2, p3)
    val ballTree = BallTree(points)

    ballTree.nearestMaximumDistance(RNDensePoint(0, 2, 0)) shouldBe p3

  }

}
