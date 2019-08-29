package com.gilcu2.balltree

object BallTree {

  def apply[T](distance: (T, T) => Double, b: Ball[T]): BallTree[T] =
    new BallTree(distance, b)

}

class BallTree[T](distance: (T, T) => Double, b: Ball[T]) {

  implicit val d = distance

  private var nElements = 1
  private var root = Node(b)

  def insert(b: Ball[T]): Unit = {
    nElements += 1
    println(root)
    root = root.insert(b)
    println(root)
  }

  def getInside(b: Ball[T]): Seq[Ball[T]] = root.getInside(b)

  def size: Int = nElements

  def getRoot: Node[T] = this.root

}
