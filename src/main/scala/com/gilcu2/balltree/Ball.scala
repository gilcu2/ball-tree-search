package com.gilcu2.balltree

import com.gilcu2.plotting.R2Ball
import com.gilcu2.spaces.{R2Point, RNDensePoint, Space}

import scala.util.Random

object Ball {

  def random(generator: Random, dim: Int): Ball[RNDensePoint] = {
    val coordinates = (1 to dim).map(i => generator.nextDouble()).toVector
    Ball(RNDensePoint(coordinates), generator.nextDouble())
  }

}

case class Ball[T](center: T, radio: Double = 0) {

  def contain(b: Ball[T])(implicit space: Space[T]): Boolean =
    space.distance(this.center, b.center) + b.radio <= this.radio

  def volume(implicit space: Space[T]): Double = Math.PI * Math.pow(radio, space.dimension)

  def isIntercepting(b: Ball[T])(implicit space: Space[T]): Boolean =
    space.distance(this.center, b.center) <= this.radio + b.radio

  def minimumDistance(b: Ball[T])(implicit space: Space[T]): Double =
    if (!this.isIntercepting(b))
      space.distance(this.center, b.center) - this.radio - b.radio
    else
      0

  def maximumDistance(b: Ball[T])(implicit space: Space[T]): Double =
    space.distance(this.center, b.center) + this.radio + b.radio

  def maximumDistance(p: T)(implicit space: Space[T]): Double =
    space.distance(this.center, p) + this.radio

  def r2Projection(implicit space: Space[T]): R2Ball = R2Ball(space.r2Projection(center), radio)

  override def toString: String = {
    val radioS = f"$radio%1.2f"
    s"Ball($center,$radioS)"
  }
}
