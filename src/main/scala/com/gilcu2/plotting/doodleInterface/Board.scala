package com.gilcu2.plotting.doodleInterface

import cats.implicits._
import com.gilcu2.balltree.{Ball, BallTree}
import com.gilcu2.plotting.{R2Ball, R2Range, View}
import com.gilcu2.spaces.{EuclideanSpace, RNDensePoint, Space}
import doodle.core._
import doodle.image._
import doodle.image.syntax._
import doodle.java2d._

import scala.collection.mutable
import scala.io.StdIn.readChar

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

  def draw[T](tree: BallTree[T], query: Ball[T])(implicit space: Space[T]): Unit = {
    val colors = Array(Color.black, Color.yellow, Color.blue, Color.green)
    var board = Image.empty

    val data = tree.render

    val circlesBall = data.map(b => Image.circle(2 * b.radio).at(b.x, b.y).strokeColor(colors(b.level)))
    val r2Query = space.r2Projection(query.center)
    val circles = circlesBall ++ Seq(Image.circle(2 * query.radio).at(r2Query.x, r2Query.y).strokeColor(Color.red))

    circles.foreach(c => {
      board = c.on(board)
    })

    board.draw()
  }

}

