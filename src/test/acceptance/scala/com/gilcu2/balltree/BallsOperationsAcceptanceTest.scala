package com.gilcu2.balltree

import com.gilcu2.balltree.BallsOperations._
import com.gilcu2.plotting.doodleInterface.Board
import com.gilcu2.spaces.{EuclideanSpace, RNDensePoint}
import doodle.core.Color
import org.scalatest.{FlatSpec, GivenWhenThen, Matchers}
import scala.io.StdIn

class BallsOperationsAcceptanceTest extends FlatSpec with Matchers with GivenWhenThen {

  behavior of "BallOperations"

  val showFigure = true

  implicit val space = EuclideanSpace(2)

  it should "partition a list of balls" in {
    val balls = Seq(
      Ball(RNDensePoint(-1, 0), 1),
      Ball(RNDensePoint(4, 0), 1),
      Ball(RNDensePoint(-2, 0), 1),
      Ball(RNDensePoint(3, 0), 1)
    )

    if (showFigure) {
      val board = new Board()
      board.draw(balls)
      board.show(Seq(Color.black, Color.blue, Color.red, Color.orange))
    }

    val (part1, part2) = computePartition(balls)

    Set(part1.toSet, part2.toSet) shouldBe Set(
      Set(Ball(RNDensePoint(-1, 0), 1), Ball(RNDensePoint(-2, 0), 1)),
      Set(Ball(RNDensePoint(3, 0), 1), Ball(RNDensePoint(4, 0), 1))
    )

    StdIn.readChar()

  }

}
