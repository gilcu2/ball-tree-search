package com.gilcu2.spaces

case class R2Point(x: Double, y: Double)

trait Space[T] {

  val dimension: Int

  def distance(p1: T, p2: T): Double

  def moveToward(from: T, to: T, distance: Double): T

  def r2Projection(p: T): R2Point

}

case class EuclideanSpace(dimension: Int) extends Space[RNDensePoint] {

  override def distance(p1: RNDensePoint, p2: RNDensePoint): Double =
    (p1 - p2).norm

  override def moveToward(from: RNDensePoint, to: RNDensePoint, distance: Double) = {
    val norm = (to - from).norm
    from + (to - from) * (distance / norm)
  }

  override def r2Projection(p: RNDensePoint): R2Point = R2Point(p.coordinates(0), p.coordinates(1))

}