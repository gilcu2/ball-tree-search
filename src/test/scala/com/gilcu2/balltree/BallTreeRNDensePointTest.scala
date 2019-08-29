package com.gilcu2.balltree

import com.gilcu2.spaces.{RNDensePoint, RNDistances}
import org.scalatest.{FlatSpec, GivenWhenThen, Matchers}
import RNDensePoint.toBall

class BallTreeRNDensePointTest extends FlatSpec with Matchers with GivenWhenThen {

  behavior of "BallTree"

  it should "create a BallTree with one object" in {
    val ballTree = BallTree(RNDistances.euclidean, RNDensePoint(0, 0))

    ballTree.size shouldBe 1
  }

  it should "return the balls inside a Ball. Case 1 point and exist" in {
    val ballTree = BallTree(RNDistances.euclidean, RNDensePoint(0, 0))

    ballTree.getInside(Ball(RNDensePoint(0, 0), 1)) shouldBe Seq(RNDensePoint(0, 0))
  }

  it should "return the balls inside a Ball. Case 1 point and don't exist" in {
    val ballTree = BallTree(RNDistances.euclidean, RNDensePoint(0, 0))

    ballTree.getInside(Ball(RNDensePoint(2, 0), 1)) shouldBe Seq()
  }

  it should "return the balls inside a Ball. Case 2 point and 1 exist" in {
    val ballTree = BallTree(RNDistances.euclidean, RNDensePoint(0, 0))
    ballTree.insert(RNDensePoint(2, 0))

    ballTree.getInside(Ball(RNDensePoint(3, 0), 1)) shouldBe Seq(RNDensePoint(2, 0))
  }

}
