package com.gilcu2.balltree

import com.gilcu2.spaces.RNDensePoint
import org.scalatest.{FlatSpec, GivenWhenThen, Matchers}

class BoundedPriorityQueueTest extends FlatSpec with Matchers with GivenWhenThen {

  behavior of "BoundedPriorityQueue"

  it should "return the farthest element in the queue without removing it" in {

    val elements = Seq(
      BallDistance(Ball(RNDensePoint(0, 0), 1), 1),
      BallDistance(Ball(RNDensePoint(0, 2), 1), 3),
      BallDistance(Ball(RNDensePoint(0, 1), 1), 2)
    )

    val queue = new BoundedPriorityQueue[BallDistance[RNDensePoint]](3)
    queue ++= elements
    queue.peek.distance shouldBe 3
    queue.peek.distance shouldBe 3

  }

  it should "keep in the queue the n nearer elements" in {

    val elements = Seq(
      BallDistance(Ball(RNDensePoint(0, 0), 1), 1),
      BallDistance(Ball(RNDensePoint(0, 2), 1), 3),
      BallDistance(Ball(RNDensePoint(0, 3), 1), 4)
    )

    val queue = new BoundedPriorityQueue[BallDistance[RNDensePoint]](3)
    queue ++= elements
    queue.peek.distance shouldBe 4

    queue += BallDistance(Ball(RNDensePoint(0, 1), 1), 2)
    queue.peek.distance shouldBe 3

    queue.getSortedAndClear() shouldBe Seq(
      BallDistance(Ball(RNDensePoint(0, 0), 1), 1),
      BallDistance(Ball(RNDensePoint(0, 1), 1), 2),
      BallDistance(Ball(RNDensePoint(0, 2), 1), 3)
    )

  }

}
