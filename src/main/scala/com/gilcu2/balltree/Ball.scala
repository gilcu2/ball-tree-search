package com.gilcu2.balltree

import com.gilcu2.spaces.Space

case class Ball[T](center: T, radio: Double = 0) {

  def contains(b: Ball[T])(implicit space: Space[T]): Boolean =
    space.distance(this.center, b.center) + b.radio <= this.radio

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


}
