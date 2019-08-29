package com.gilcu2.balltree

import com.gilcu2.spaces.{RNDensePoint, RNDistances}
import org.scalatest.{FlatSpec, GivenWhenThen, Matchers}

class BallTreeTest extends FlatSpec with Matchers with GivenWhenThen {

  behavior of "BallTree"

  it should "create a BallTree with one object" in {
    val ballTree = BallTree(RNDistances.euclidean, Point(RNDensePoint(0, 0)))

    ballTree.size shouldBe 1
  }

  it should "return the balls inside a Ball" in {
    val ballTree = BallTree(RNDistances.euclidean, Point(RNDensePoint(0, 0)))

    ballTree.getInside(Ball(RNDensePoint(0, 0), 1)) shouldBe Seq(Point(RNDensePoint(0, 0)))
  }

}
