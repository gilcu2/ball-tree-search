package com.gilcu2.balltree

import com.gilcu2.spaces._
import org.scalatest.{FlatSpec, GivenWhenThen, Matchers}

class BallTreeCreationRNDensePointTest extends FlatSpec with Matchers with GivenWhenThen {

  behavior of "BallTree"

  it should "create a BallTree with one object" in {
    val ballTree = BallTree(EuclideanSpace, RNDensePoint(0, 0))

    ballTree.size shouldBe 1
  }

  it should "create a BallTree with 2 balls" in {
    val ballTree = BallTree(EuclideanSpace, RNDensePoint(0, 0))
    ballTree.insert(RNDensePoint(1, 0))

    val root = ballTree.getRoot
    root.ball shouldBe Ball(RNDensePoint(0, 0), 1)
    root.getLeft.get.ball shouldBe Ball(RNDensePoint(0, 0))
    root.getRight.get.ball shouldBe Ball(RNDensePoint(1, 0))
  }

}
