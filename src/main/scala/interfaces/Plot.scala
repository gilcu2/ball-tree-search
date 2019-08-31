package interfaces

import com.gilcu2.balltree.Ball
import com.gilcu2.spaces.Space
import doodle.image._
// Colors and other useful stuff
import doodle.core._
import doodle.image.syntax._
// Render to a window using Java2D (must be running in the JVM)
import doodle.java2d._

object Plot {

  def paint[T](balls: Seq[Ball[T]])(implicit space: Space[T]): Unit = {

    val circle = Image.circle(balls(0).radio * 100).fillColor(Color.black)
    val pos = space.r2Projection(balls(0).center)
    val paint = circle.at(200 + pos.x, 200 + pos.y)

    paint.draw()
    println("pass")
  }

}
