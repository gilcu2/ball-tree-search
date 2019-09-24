package com.gilcu2.balltree

import com.gilcu2.spaces.Space

object BallTree {

  def apply[T](space: Space[T], balls: Ball[T]*): BallTree[T] =
    new BallTree(space, balls: _*)

}

class BallTree[T](space: Space[T], balls: Ball[T]*) {

  assert(balls.nonEmpty)

  implicit val s = space

  val leafs = balls.map(b => {
    Node(idGenerator, b)
  })


  //  def getInside(b: Ball[T]): Seq[Ball[T]] = root.getInside(b)

  var idGenerator = 0

  private def newId: Int = {
    idGenerator += 1
    idGenerator
  }

}
