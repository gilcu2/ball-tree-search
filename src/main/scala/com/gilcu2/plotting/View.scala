package com.gilcu2.plotting

import com.gilcu2.spaces.R2Point

class View(width: Double = 1000, range: R2Range) {

  val mx = width / (range.xMax - range.xMin)
  val my = width / (range.yMax - range.yMin)
  val m = math.min(mx, my)
  val x0 = -m * range.xMin
  val y0 = -m * range.yMin

  def project(p: R2Point): R2Point = R2Point(x0 + m * p.x, y0 + m * p.y)

  def project(b: R2Ball): R2Ball = R2Ball(this.project(b.center), m * b.radio)

}
