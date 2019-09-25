package com.gilcu2.balltree

import com.gilcu2.balltree.BallsOperations._
import com.gilcu2.plotting.Board
import com.gilcu2.spaces.{EuclideanSpace, RNDensePoint}
import doodle.core.Color
import org.scalatest.{FlatSpec, GivenWhenThen, Matchers}

class BallsOperationsTest extends FlatSpec with Matchers with GivenWhenThen {

  behavior of "BallOperations"

  implicit val space = EuclideanSpace(2)

  it should "compute the minimum bounding ball of two balls when one is contained" in {
    val b1 = Ball(RNDensePoint(0, 0), 3)
    val b2 = Ball(RNDensePoint(0, 2), 1)

    computeBoundingBall(b1, b2) shouldBe b1
  }

  it should "compute the minimum bounding ball of two balls when no one is contained" in {
    val b1 = Ball(RNDensePoint(0, 0), 3)
    val b2 = Ball(RNDensePoint(0, 2), 2)

    val boundBall = computeBoundingBall(b1, b2)
    boundBall.radio shouldBe 3.5
    boundBall.contains(b1) shouldBe true
    boundBall.contains(b2) shouldBe true
  }

  it should "compute the minimum bounding ball of two balls when no coordinate is equal" in {
    val b1 = Ball(RNDensePoint(0, 0), 1)
    val b2 = Ball(RNDensePoint(1, 1), 1)

    val boundBall = computeBoundingBall(b1, b2)
    boundBall.radio shouldBe (math.sqrt(2) + 2) / 2
    boundBall.contains(b1) shouldBe true
    boundBall.contains(b2) shouldBe true
  }

  it should "find the ball with bigger minimum distance to ball" in {
    val balls = Seq(Ball(RNDensePoint(1, 0), 1), Ball(RNDensePoint(3, 0), 1))
    val ball = Ball(RNDensePoint(-1, 0), 1)
    val farthest = findLargestMinimumDistanceFrom(ball, balls)

    farthest shouldBe Ball(RNDensePoint(3, 0), 1)
  }

  it should "find the ball with bigger maximum distance to point" in {
    val balls = Seq(Ball(RNDensePoint(1, 0), 5), Ball(RNDensePoint(3, 0), 1))
    val point = RNDensePoint(-1, 0)
    val farthest = findLargestMaximumDistanceFrom(point, balls)

    farthest shouldBe Ball(RNDensePoint(1, 0), 5)
  }

  it should "compute the mid point between two balls" in {
    val ball1 = Ball(RNDensePoint(1, 0), 2)
    val ball2 = Ball(RNDensePoint(8, 0), 1)

    val r = midPoint(ball1, ball2)

    r shouldBe RNDensePoint(5, 0)
  }

  it should "compute the bounding sphere of a list of balls not overlapped" in {
    val balls = Seq(Ball(RNDensePoint(1, 0), 1), Ball(RNDensePoint(4, 0), 1), Ball(RNDensePoint(0, 3), 1))

    val boundingBall = computeBoundingBall(balls)

    balls.foreach(b =>
      boundingBall.contains(b) shouldBe true
    )

  }

  it should "partition a list of balls" in {
    val balls = Seq(
      Ball(RNDensePoint(-1, 0), 1),
      Ball(RNDensePoint(4, 0), 1),
      Ball(RNDensePoint(-2, 0), 1),
      Ball(RNDensePoint(3, 0), 1)
    )

    val (part1, part2) = computePartition(balls)

    part1 shouldBe Seq(Ball(RNDensePoint(-1, 0), 1), Ball(RNDensePoint(-2, 0), 1))
    part2 shouldBe Seq(Ball(RNDensePoint(0, 3), 1), Ball(RNDensePoint(4, 0), 1))

  }

}
