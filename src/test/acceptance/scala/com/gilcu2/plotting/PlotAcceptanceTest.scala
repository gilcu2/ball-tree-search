package com.gilcu2.plotting

import com.gilcu2.balltree.Ball
import com.gilcu2.spaces.{EuclideanSpace, RNDensePoint}
import doodle.core.Color
import org.scalatest.{FlatSpec, GivenWhenThen, Matchers}

class PlotAcceptanceTest extends FlatSpec with Matchers with GivenWhenThen {

  behavior of "Plot"

  implicit val space = EuclideanSpace

  ignore should "paint the ball projection" in {
    val balls = Seq(Ball(RNDensePoint(0, 0), 1), Ball(RNDensePoint(2, 0), 1))
    val board = new Board

    board.draw(balls)
    board.show(Seq(Color.black, Color.red), wait = true)

    balls.size shouldBe 2
  }

}
