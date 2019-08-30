package com.gilcu2.balltree

import com.gilcu2.spaces.Space

object BallTree {

  def apply[T](space: Space[T], balls: Ball[T]*): BallTree[T] =
    new BallTree(space, balls: _*)

}

class BallTree[T](space: Space[T], balls: Ball[T]*) {

  assert(balls.nonEmpty)

  implicit val s = space

  private var nElements = 1
  private var root = Node(balls(0))

  def insert(b: Ball[T]): Unit = {
    nElements += 1
    root = root.insert(b)
  }

  def getInside(b: Ball[T]): Seq[Ball[T]] = root.getInside(b)

  def size: Int = nElements

  def getRoot: Node[T] = this.root



}
