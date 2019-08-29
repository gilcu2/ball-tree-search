package com.gilcu2.balltree

case class Ball[T](center: T, radio: Double = 0) {

  def contains(b: Ball[T])(implicit distance: (T, T) => Double): Boolean =
    distance(this.center, b.center) + b.radio <= this.radio

  def isIntercepting(b: Ball[T])(implicit distance: (T, T) => Double): Boolean =
    distance(this.center, b.center) <= this.radio + b.radio

}
