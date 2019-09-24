package com.gilcu2.balltree

import com.gilcu2.spaces.{EuclideanSpace, RNDensePoint}
import org.scalatest.{FlatSpec, GivenWhenThen, Matchers}

class BallEuclideanTest extends FlatSpec with Matchers with GivenWhenThen {

  behavior of "Ball"

  implicit val space = EuclideanSpace

  it should "confirm that the ball is contained" in {
    val b1 = Ball(RNDensePoint(0, 0), 3)
    val b2 = Ball(RNDensePoint(0, 2), 1)
    b1.contains(b2) shouldBe true
  }

  it should "confirm that the ball is not contained" in {
    val b1 = Ball(RNDensePoint(0, 0), 3)
    val b2 = Ball(RNDensePoint(0, 2), 2)
    b1.contains(b2) shouldBe false
  }


}
