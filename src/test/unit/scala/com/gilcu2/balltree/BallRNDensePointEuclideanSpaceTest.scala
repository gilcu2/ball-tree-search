package com.gilcu2.balltree

import com.gilcu2.spaces._
import org.scalatest.{FlatSpec, GivenWhenThen, Matchers}

class BallRNDensePointEuclideanSpaceTest extends FlatSpec with Matchers with GivenWhenThen {

  behavior of "Ball with RNDensePoint in Euclidean space"

  implicit val space = EuclideanSpace

  it should "return that ball is contained" in {
    val ball1 = Ball(RNDensePoint(0, 0), 3)
    val ball2 = Ball(RNDensePoint(0, 1), 1)

    ball1.contains(ball2) shouldBe true
  }

  it should "return that ball is not contained when is the inverse relation" in {
    val ball1 = Ball(RNDensePoint(0, 0), 3)
    val ball2 = Ball(RNDensePoint(0, 1), 1)

    ball2.contains(ball1) shouldBe false
  }

  it should "return that ball is not contained when are separated" in {
    val ball1 = Ball(RNDensePoint(0, 0), 1)
    val ball2 = Ball(RNDensePoint(0, 3), 1)

    ball2.contains(ball1) shouldBe false
  }

  it should "return that ball intercept" in {
    val ball1 = Ball(RNDensePoint(0, 0), 1)
    val ball2 = Ball(RNDensePoint(0, 0.5), 1)

    ball2.isIntercepting(ball1) shouldBe true
  }

  it should "return that ball are not intercepting when they are separated" in {
    val ball1 = Ball(RNDensePoint(0, 0), 1)
    val ball2 = Ball(RNDensePoint(0, 3), 1)

    ball2.isIntercepting(ball1) shouldBe false
  }

  it should "compute the minimum distance between balls separated" in {
    val ball1 = Ball(RNDensePoint(0, 0), 1)
    val ball2 = Ball(RNDensePoint(0, 3), 1)

    ball1.minimumDistance(ball2) shouldBe 1
  }

  it should "compute the minimum distance between balls overlapped" in {
    val ball1 = Ball(RNDensePoint(0, 0), 1)
    val ball2 = Ball(RNDensePoint(0, 2), 1)

    ball1.minimumDistance(ball2) shouldBe 0
  }

  it should "compute the maximum distance between balls separated" in {
    val ball1 = Ball(RNDensePoint(0, 0), 1)
    val ball2 = Ball(RNDensePoint(0, 3), 1)

    ball1.maximumDistance(ball2) shouldBe 5
  }

  it should "compute the maximum distance between balls overlapped" in {
    val ball1 = Ball(RNDensePoint(0, 0), 1)
    val ball2 = Ball(RNDensePoint(2, 0), 1)

    ball1.maximumDistance(ball2) shouldBe 4
  }

  it should "compute the maximum distance between balls contained" in {
    val ball1 = Ball(RNDensePoint(0, 0), 3)
    val ball2 = Ball(RNDensePoint(1, 0), 1)

    ball1.maximumDistance(ball2) shouldBe 5
  }

}
