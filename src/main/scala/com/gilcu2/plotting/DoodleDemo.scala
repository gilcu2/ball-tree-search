package com.gilcu2.plotting

import doodle.core._
import doodle.image._
import doodle.image.syntax._
import doodle.java2d._


object DoodleDemo {

  def main(args: Array[String]): Unit = {

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


}
