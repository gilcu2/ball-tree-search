package com.gilcu2.plotting

case class R2Range(xMin: Double, yMin: Double, xMax: Double, yMax: Double) {

  def update(other: R2Range): R2Range = R2Range(
    math.min(this.xMin, other.xMin),
    math.min(this.yMin, other.yMin),
    math.max(this.xMax, other.xMax),
    math.max(this.yMax, other.yMax)
  )

}