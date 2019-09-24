package com.gilcu2.plotting

import com.gilcu2.balltree.Ball
import com.gilcu2.spaces.{EuclideanSpace, RNDensePoint, Space}

import scala.collection.mutable
import doodle.core._
import doodle.image._
import doodle.image.syntax._
import doodle.java2d._
import scala.io.StdIn.readChar
import cats.implicits._

class Board {

  private var (range, figures) = reset

  def reset: (R2Range, mutable.ListBuffer[R2Ball]) = (R2Range(0, 0, 1, 1), mutable.ListBuffer[R2Ball]())

  def draw[T](balls: Seq[Ball[T]])(implicit space: Space[T]): Unit = {

    balls.foreach(b => {
      val projection = b.r2Projection
      val ballRange = projection.r2Range
      updateRange(ballRange)
      figures += projection
    })

  }

  def updateRange(newRange: R2Range): Unit = {
    range = range.update(newRange)
  }

  def show(colors: Seq[Color], width: Double = 1000, wait: Boolean = false): Unit = {

    implicit val view = new View(width, range)

    val images = figures.zip(colors).map { case (f, c) => f.toImage.strokeColor(c) }.toList

    images.allOn.draw()
    if (wait)
      readChar()

  }

}

object Board {

  def main(args: Array[String]): Unit = {

    implicit val space = EuclideanSpace(2)
    val balls = Seq(Ball(RNDensePoint(0, 0), 1), Ball(RNDensePoint(2, 0), 1))
    val board = new Board

    board.draw(balls)
    board.show(Seq(Color.black, Color.red))

  }

}

