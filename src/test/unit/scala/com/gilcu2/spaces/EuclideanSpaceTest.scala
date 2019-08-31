package com.gilcu2.spaces

import com.gilcu2.spaces.RNDensePoint
import org.scalatest.{FlatSpec, GivenWhenThen, Matchers}
import com.gilcu2.spaces.EuclideanSpace._

class EuclideanSpaceTest extends FlatSpec with Matchers with GivenWhenThen {

  behavior of "EuclideanSpace"

  it should "compute the distance between points in Euclidean space" in {
    val p1 = RNDensePoint(0, 0)
    val p2 = RNDensePoint(1, 0)

    distance(p1, p2) shouldBe 1
  }

  it should "compute the point from one point to another at distance given" in {
    val p1 = RNDensePoint(0, 0)
    val p2 = RNDensePoint(2, 0)

    moveToward(p1, p2, distance = 1.0) shouldBe RNDensePoint(1, 0)
  }

}
