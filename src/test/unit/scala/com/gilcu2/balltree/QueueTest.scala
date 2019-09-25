package com.gilcu2.balltree

import com.gilcu2.spaces.RNDensePoint
import org.scalatest.{FlatSpec, GivenWhenThen, Matchers}

class PriorityQueueMaximumSizeTest extends FlatSpec with Matchers with GivenWhenThen {

  behavior of "PriorityQueueMaximumSize"

  it should "return the farthest element in the queue" in {

    val elements = Seq(
      Element(Ball(RNDensePoint(0, 0), 1), 1),
      Element(Ball(RNDensePoint(0, 2), 1), 3),
      Element(Ball(RNDensePoint(0, 1), 1), 2)
    )

    val queue = new PriorityQueueMaximumSize(3, elements)
    queue.getFarthest.distance shouldBe 3

  }

  it should "keep in the queue the n smaller elements" in {

    val elements = Seq(
      Element(Ball(RNDensePoint(0, 3), 1), 4),
      Element(Ball(RNDensePoint(0, 0), 1), 1),
      Element(Ball(RNDensePoint(0, 2), 1), 3),
      Element(Ball(RNDensePoint(0, 1), 1), 2)
    )

    val queue = new PriorityQueueMaximumSize(3, elements)
    queue.getFarthest.distance shouldBe 3

  }

}
