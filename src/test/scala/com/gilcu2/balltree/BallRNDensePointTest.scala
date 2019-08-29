package com.gilcu2.balltree

import com.gilcu2.spaces.{RNDensePoint, RNDistances}
import org.scalatest.{FlatSpec, GivenWhenThen, Matchers}

class BallRNDensePointTest extends FlatSpec with Matchers with GivenWhenThen {

  implicit val d = RNDistances.euclidean _

  behavior of "Ball"

  it should "return that ball is contained" in {
    val ball1 = Ball(RNDensePoint(0, 0), 3)
    val ball2 = Ball(RNDensePoint(0, 1), 1)

    ball1.contains(ball2) shouldBe true
  }

  it should "return that ball is not contained" in {
    val ball1 = Ball(RNDensePoint(0, 0), 3)
    val ball2 = Ball(RNDensePoint(0, 1), 1)

    ball2.contains(ball1) shouldBe false
  }

}
