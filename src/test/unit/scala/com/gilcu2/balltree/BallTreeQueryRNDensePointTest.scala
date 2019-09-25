package com.gilcu2.balltree

import com.gilcu2.spaces.RNDensePoint.toBall
import com.gilcu2.spaces._
import org.scalatest.{FlatSpec, GivenWhenThen, Matchers}

class BallTreeQueryRNDensePointTest extends FlatSpec with Matchers with GivenWhenThen {

  behavior of "BallTree"

  implicit val space = EuclideanSpace(2)

  it should "return the balls inside a Ball. Case 1 point and exist" in {
    val ballTree = new BallTree(RNDensePoint(0, 0))

    ballTree.getInside(Ball(RNDensePoint(0, 0), 1)) shouldBe Seq(Ball(RNDensePoint(0, 0)))
  }

  it should "return the balls inside a Ball. Case 1 point and doesn't exist" in {
    val ballTree = new BallTree(RNDensePoint(0, 0))

    ballTree.getInside(Ball(RNDensePoint(2, 0), 1)) shouldBe Seq()
  }

  it should "return the balls inside a Ball. Case 2 point and 1 satisfy query" in {
    val ballTree = new BallTree(RNDensePoint(0, 0), RNDensePoint(2, 0))


    ballTree.getInside(Ball(RNDensePoint(3, 0), 1)) shouldBe Seq(Ball(RNDensePoint(2, 0)))
  }

  it should "return the balls inside a Ball. Case 3 balls and 1 satisfies the query" in {
    val ball1 = Ball(RNDensePoint(0, 0), 1)
    val ball2 = Ball(RNDensePoint(0, 1), 1)
    val ball3 = Ball(RNDensePoint(1, 1), 1)
    val balls = Seq(ball1, ball2, ball3)
    val ballTree = BallTree(balls)

    ballTree.getInside(Ball(RNDensePoint(0, 0), 1.5)) shouldBe Seq(ball1)

  }

  it should "return the balls inside a Ball. Case 3 balls and 2 satisfy the query" in {
    val ball1 = Ball(RNDensePoint(0, 0), 1)
    val ball2 = Ball(RNDensePoint(1, 0), 1)
    val ball3 = Ball(RNDensePoint(1, 1), 1)
    val balls = Seq(ball1, ball2, ball3)
    val ballTree = BallTree(balls)

    ballTree.getInside(Ball(RNDensePoint(1.5, 0.5), 2)) shouldBe Seq(ball2, ball3)

  }

}
