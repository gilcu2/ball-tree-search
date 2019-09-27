package com.gilcu2.balltree

import com.gilcu2.spaces.RNDensePoint.toBall
import com.gilcu2.spaces._
import org.scalatest.{FlatSpec, GivenWhenThen, Matchers}

import scala.util.Random

class BallTreeSimplePruningQueryRNDensePointTest extends FlatSpec with Matchers with GivenWhenThen {

  behavior of "BallTree"

  implicit val space = EuclideanSpace(2)

  it should "return the balls inside a Ball. Case 1 point and exist" in {
    val ballTree = new BallTree(RNDensePoint(0, 0))

    ballTree.areContained(Ball(RNDensePoint(0, 0), 1)) shouldBe Seq(Ball(RNDensePoint(0, 0)))
  }

  it should "return the balls inside a Ball. Case 1 point and doesn't exist" in {
    val ballTree = new BallTree(RNDensePoint(0, 0))

    ballTree.areContained(Ball(RNDensePoint(2, 0), 1)) shouldBe Seq()
  }

  it should "return the balls inside a Ball. Case 2 point and 1 satisfy query" in {
    val ballTree = new BallTree(RNDensePoint(0, 0), RNDensePoint(2, 0))


    ballTree.areContained(Ball(RNDensePoint(3, 0), 1)) shouldBe Seq(Ball(RNDensePoint(2, 0)))
  }

  it should "return the balls inside a Ball. Case 3 balls and 1 satisfies the query" in {
    val ball1 = Ball(RNDensePoint(0, 0), 1)
    val ball2 = Ball(RNDensePoint(0, 1), 1)
    val ball3 = Ball(RNDensePoint(1, 1), 1)
    val balls = Seq(ball1, ball2, ball3)
    val ballTree = BallTree(balls)

    ballTree.areContained(Ball(RNDensePoint(0, 0), 1.5)) shouldBe Seq(ball1)

  }

  it should "return the balls inside a Ball. Case 3 balls and 2 satisfy the query" in {
    val ball1 = Ball(RNDensePoint(0, 0), 1)
    val ball2 = Ball(RNDensePoint(1, 0), 1)
    val ball3 = Ball(RNDensePoint(1, 1), 1)
    val balls = Seq(ball1, ball2, ball3)
    val ballTree = BallTree(balls)

    ballTree.areContained(Ball(RNDensePoint(1.5, 0.5), 2)) shouldBe Seq(ball2, ball3)

  }

  it should "return the balls that contain a given. Case 3 balls and 1 satisfy the query" in {
    val ball1 = Ball(RNDensePoint(0, 0), 1)
    val ball2 = Ball(RNDensePoint(1, 0), 1)
    val ball3 = Ball(RNDensePoint(1, 1), 1)
    val balls = Seq(ball1, ball2, ball3)
    val ballTree = BallTree(balls)

    ballTree.contain(Ball(RNDensePoint(0, 0.0), 0.8)) shouldBe Seq(ball1)

  }

  it should "return the balls that contain a given. Case 3 balls and 2 satisfy the query" in {
    val ball1 = Ball(RNDensePoint(0, 0), 1)
    val ball2 = Ball(RNDensePoint(1, 0), 1)
    val ball3 = Ball(RNDensePoint(1, 1), 1)
    val balls = Seq(ball1, ball2, ball3)
    val ballTree = BallTree(balls)

    ballTree.contain(Ball(RNDensePoint(0.5, 0.0), 0.4)).toSet shouldBe Set(ball1, ball2)

  }

  it should "return the balls that contain a given. Case 3 balls and 3 satisfy the query" in {
    val ball1 = Ball(RNDensePoint(0, 0), 1)
    val ball2 = Ball(RNDensePoint(1, 0), 1)
    val ball3 = Ball(RNDensePoint(1, 1), 1)
    val balls = Seq(ball1, ball2, ball3)
    val ballTree = BallTree(balls)

    ballTree.contain(Ball(RNDensePoint(0.5, 0.5), 0.25)).toSet shouldBe Set(ball1, ball2, ball3)

  }

  it should "return the balls that contain a given. Case 3 balls and no one satisfy the query" in {
    val ball1 = Ball(RNDensePoint(0, 0), 1)
    val ball2 = Ball(RNDensePoint(1, 0), 1)
    val ball3 = Ball(RNDensePoint(1, 1), 1)
    val balls = Seq(ball1, ball2, ball3)
    val ballTree = BallTree(balls)

    ballTree.contain(Ball(RNDensePoint(0.5, 0.5), 1.5)).toSet shouldBe Set()

  }

  it should "areContained ball query should return the same balls than the brute force algorithm" in {
    val generator = new Random(100)
    val balls = (1 to 10).map(i => Ball.random(generator, dim = 2))
    val query = Ball.random(generator, dim = 2)

    val bruteResults = balls.filter(b => query.contain(b)).toSet

    val tree = BallTree(balls)
    val treeResults = tree.areContained(query).toSet

    treeResults shouldBe bruteResults

  }

  it should "contain ball query should return the same balls than the brute force algorithm" in {
    val generator = new Random(100)
    val balls = (1 to 10).map(i => Ball.random(generator, dim = 2))
    val query = Ball.random(generator, dim = 2)

    val bruteResults = balls.filter(b => b.contain(query)).toSet

    val tree = BallTree(balls)
    val treeResults = tree.contain(query).toSet

    treeResults shouldBe bruteResults

  }

}
