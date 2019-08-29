package com.gilcu2.balltree

object Ball {
  def apply[T](center: T, radio: Double): Ball[T] = new Ball(center, radio)

}

class Ball[T](val center: T, val radio: Double) {

  def isInside(b: Ball[T])(implicit distance: (T, T) => Double): Boolean =
    distance(this.center, b.center) + b.radio <= this.radio

  def isIntercepting(b: Ball[T])(implicit distance: (T, T) => Double): Boolean =
    distance(this.center, b.center) <= this.radio + b.radio

  override def toString: String = s"Ball($center,$radio)"
}

object Point {
  def apply[T](center: T): Point[T] = new Point(center)

}

class Point[T](center: T) extends Ball[T](center, 0.0)