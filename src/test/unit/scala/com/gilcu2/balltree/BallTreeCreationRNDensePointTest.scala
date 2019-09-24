package com.gilcu2.balltree

import com.gilcu2.spaces._
import org.scalatest.{FlatSpec, GivenWhenThen, Matchers}

import scala.collection.mutable

class BallTreeCreationRNDensePointTest extends FlatSpec with Matchers with GivenWhenThen {

  behavior of "BallTree"

  implicit val space = EuclideanSpace(2)

  it should "find the pair with minimum volume bounding" in {
    val nodes = mutable.HashMap(
      0 -> Node(0, Ball(RNDensePoint(0, 0), 1)),
      1 -> Node(1, Ball(RNDensePoint(1, 1), 1)),
      2 -> Node(2, Ball(RNDensePoint(0, 1), 1))
    )

    val minimumPair = BallTree.findMinimumVolumePair(nodes)

    minimumPair.id1 shouldBe 0
    minimumPair.id2 shouldBe 2

  }

  it should "create a BallTree with 2 balls" in {
    val ball1 = Ball(RNDensePoint(0, 0), 1)
    val ball2 = Ball(RNDensePoint(0, 1), 2)
    val balls = Seq(ball1, ball2)
    val ballTree = BallTree(EuclideanSpace(2), balls)

    Set(ballTree.root.left.get.ball, ballTree.root.right.get.ball) shouldBe Set(ball1, ball2)

  }

  it should "create a BallTree with 3 balls" in {
    val ball1 = Ball(RNDensePoint(0, 0), 1)
    val ball2 = Ball(RNDensePoint(0, 1), 1)
    val ball3 = Ball(RNDensePoint(1, 1), 1)
    val balls = Seq(ball1, ball2, ball3)
    val ballTree = BallTree(EuclideanSpace(2), balls)

    Set(ballTree.root.left.get.ball, ballTree.root.right.get.ball).contains(ball3) shouldBe true

  }

  it should "compute the height of a BallTree with 3 balls" in {
    val ball1 = Ball(RNDensePoint(0, 0), 1)
    val ball2 = Ball(RNDensePoint(0, 1), 1)
    val ball3 = Ball(RNDensePoint(1, 1), 1)
    val balls = Seq(ball1, ball2, ball3)
    val ballTree = BallTree(EuclideanSpace(2), balls)
    val height = ballTree.height

    height shouldBe 3

  }

  it should "print a BallTree with 3 balls" in {
    val ball1 = Ball(RNDensePoint(0, 0), 1)
    val ball2 = Ball(RNDensePoint(0, 1), 1)
    val ball3 = Ball(RNDensePoint(1, 1), 1)
    val balls = Seq(ball1, ball2, ball3)
    val ballTree = BallTree(EuclideanSpace(2), balls)

    ballTree.print()

  }

}
