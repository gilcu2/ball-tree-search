package com.gilcu2.spaces

trait Space[T] {

  def distance(p1: T, p2: T): Double

  def moveToward(from: T, to: T, distance: Double): T
}

object EuclideanSpace extends Space[RNDensePoint] {

  override def distance(p1: RNDensePoint, p2: RNDensePoint): Double =
    (p1 - p2).norm

  override def moveToward(from: RNDensePoint, to: RNDensePoint, distance: Double) = {
    val norm = (to - from).norm
    from + (to - from) * (distance / norm)
  }

}