package com.gilcu2.spaces

import com.gilcu2.balltree._

import scala.math.sqrt

object RNDensePoint {

  def apply(coordinates: Vector[Double]): RNDensePoint = new RNDensePoint(coordinates)

  def apply(coordinates: Double*): RNDensePoint = new RNDensePoint(coordinates.toVector)

  implicit def toBall(rnp: RNDensePoint): Ball[RNDensePoint] = Ball(rnp)

}

case class RNDensePoint(val coordinates: Vector[Double]) {

  def +(other: RNDensePoint): RNDensePoint =
    new RNDensePoint(this.coordinates.zip(other.coordinates).map { case (c1, c2) => c1 + c2 })

  def -(other: RNDensePoint): RNDensePoint =
    new RNDensePoint(this.coordinates.zip(other.coordinates).map { case (c1, c2) => c1 - c2 })

  def *(scalar: Double): RNDensePoint =
    new RNDensePoint(this.coordinates.map(scalar * _))

  def unary_- : RNDensePoint =
    RNDensePoint(this.coordinates.map(-_))

  def norm: Double =
    sqrt(this.coordinates.map(c => c * c).sum)

  override def toString: String = {
    val valuesS = coordinates.map(f => f"$f%1.2f").mkString(",")
    s"RN($valuesS)"
  }

}