package com.gilcu2.plotting

import com.gilcu2.spaces.R2Point
import doodle.image._


case class R2Ball(center: R2Point, radio: Double) {
  def r2Range: R2Range = R2Range(center.x - radio, center.y - radio, center.x + radio, center.y + radio)

  def toImage(implicit view: View) = {
    val projection = view.project(this)
    Image.circle(2 * projection.radio).at(projection.center.x, projection.center.y)
  }
}
