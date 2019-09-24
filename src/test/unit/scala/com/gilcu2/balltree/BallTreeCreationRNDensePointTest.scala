package com.gilcu2.balltree

import com.gilcu2.spaces._
import org.scalatest.{FlatSpec, GivenWhenThen, Matchers}

class BallTreeCreationRNDensePointTest extends FlatSpec with Matchers with GivenWhenThen {

  behavior of "BallTree"

  it should "create a BallTree with one object" in {
    val ballTree = BallTree(EuclideanSpace(2), RNDensePoint(0, 0))

  }

  it should "create a BallTree with 2 balls" in {
    val ballTree = BallTree(EuclideanSpace(2), RNDensePoint(0, 0))

  }

}
