package com.gilcu2.balltree

import scala.collection.mutable

case class Element[T](ball: Ball[T], distance: Double) extends Ordered[Element[T]] {

  override def compare(that: Element[T]): Int = (this.distance - that.distance).toInt

}

class PriorityQueueMaximumSize[T](val n: Int, elements: Seq[Element[T]]) {

  val queue = mutable.PriorityQueue[Element[T]](elements: _*)
  fixLen

  def fixLen: Unit = {
    val len = queue.length
    if (len > n)
      queue.drop(len - n)
  }

  def insert(e: Element[T]): Unit = {
    queue += e
    if (queue.length > n)
      queue.drop(1)
  }

  def getFarthest: Element[T] =
    queue.head

  def getElements: Seq[Element[T]] = queue.dequeueAll
}
