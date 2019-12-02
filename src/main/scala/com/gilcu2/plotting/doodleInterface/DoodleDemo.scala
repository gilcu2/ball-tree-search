package com.gilcu2.plotting.doodleInterface

import com.gilcu2.balltree.{Ball, BallTree}
import com.gilcu2.spaces.EuclideanSpace
import doodle.core._
import doodle.image._
import doodle.image.syntax._
import doodle.java2d._

import scala.util.Random

object DoodleDemo {

  def main(args: Array[String]): Unit = {

    //    circlesDemo
    ballTreeDemo
  }

  def circlesDemo = {
    var board = Image.empty

    val circles = Seq(
      Image.circle(200).at(200, 200).strokeColor(Color.black),
      Image.circle(200).at(200, 400).strokeColor(Color.red),
      Image.circle(200).at(400, 200).strokeColor(Color.blue)
    )

    circles.foreach(c => {
      board = c.on(board)
    })

    board.draw()
  }

  def ballTreeDemo = {


    implicit val generator = new Random(100) //evil seed
    implicit val space = EuclideanSpace(2)

    val n = 3
    val k = 2
    val balls = (1 to n).map(i => Ball.random(dim = 2, factor = 300))
    val query = Ball.randomFixedRadio(dim = 2, radio = 0, factor = 300)

    val tree = BallTree(balls)
    tree.print

    println(s"Query: $query")


    Board.draw(tree, query)
    println("End")
  }


}
