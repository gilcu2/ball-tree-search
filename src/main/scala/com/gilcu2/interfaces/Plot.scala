package com.gilcu2.interfaces

import com.gilcu2.balltree.Ball
import com.gilcu2.spaces.Space

import doodle.image._
import doodle.core._
import doodle.image.syntax._
import doodle.java2d._

object Plot {

  def paint[T](balls: Seq[Ball[T]])(implicit space: Space[T], scale: Double = 100, ini: Double = 200): Unit = {

    val circles = balls.map(b => {
      val pos = space.r2Projection(b.center)
      Image.circle(b.radio * scale).at(ini + pos.x * scale, ini + pos.y * scale)
    })

    val emptyImage = Image.empty
    val compoundImage = circles.fold(emptyImage)((prevImg, img) => prevImg.on(img))
    compoundImage.draw()

  }

  def main(args: Array[String]): Unit = {

    val circle = Image.circle(100).fillColor(Color.black)
    val paint = circle.at(200, 200)

    paint.draw()
  }


}
