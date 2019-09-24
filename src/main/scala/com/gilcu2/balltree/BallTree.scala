package com.gilcu2.balltree

import com.gilcu2.balltree.BallsOperations._
import com.gilcu2.spaces.Space

import scala.collection.mutable

object NodePair {
  def apply[T](node1: Node[T], node2: Node[T])(implicit space: Space[T]): NodePair[T] = {
    val boundingBall = computeBoundingBall(node1.ball, node2.ball)
    val volume = boundingBall.volume
    new NodePair(node1.id, node2.id, boundingBall, volume)
  }
}

case class NodePair[T](id1: Int, id2: Int, boundBall: Ball[T], volume: Double)

object BallTree {

  def apply[T](space: Space[T], balls: Seq[Ball[T]]): BallTree[T] =
    new BallTree(space, balls: _*)

  def findMinimumVolumePair[T](nodes: mutable.HashMap[Int, Node[T]])(implicit space: Space[T])
  : NodePair[T] = {
    val size = nodes.size
    assert(size > 1)

    var pair = NodePair(nodes(0), nodes(1))
    for (i <- 0 until size; j <- i + 1 until size) {
      val newPair = NodePair(nodes(i), nodes(j))
      if (newPair.volume < pair.volume)
        pair = newPair
    }
    pair
  }

}

class BallTree[T](space: Space[T], balls: Ball[T]*) {

  import BallTree._

  assert(balls.nonEmpty)

  implicit val s: Space[T] = space

  val root = createTreeBottomUp
  var idGenerator = 0

  //  def getInside(b: Ball[T]): Seq[Ball[T]] = root.getInside(b)

  private def createTreeBottomUp: Node[T] = {

    def createNotIncluded: mutable.HashMap[Int, Node[T]] = {
      val nodesNotIncluded = mutable.HashMap[Int, Node[T]]()
      balls.foreach(b => {
        val id = idGenerator
        nodesNotIncluded(id) = Node(id, b)
      })
      nodesNotIncluded
    }

    def findPairAndUpdate(nodesNotIncluded: mutable.HashMap[Int, Node[T]]): Unit = {
      val minimumPair = findMinimumVolumePair(nodesNotIncluded)
      val newNode = Node(idGenerator, minimumPair.boundBall,
        left = Some(nodesNotIncluded(minimumPair.id1)),
        right = Some(nodesNotIncluded(minimumPair.id2))
      )
      newNode.left.get.parent = Some(newNode)
      newNode.right.get.parent = Some(newNode)

      nodesNotIncluded.remove(minimumPair.id1)
      nodesNotIncluded.remove(minimumPair.id2)
    }

    val nodesNotIncluded = createNotIncluded
    while (nodesNotIncluded.size > 1)
      findPairAndUpdate(nodesNotIncluded)

    nodesNotIncluded.head._2
  }

  private def newId: Int = {
    idGenerator += 1
    idGenerator
  }

}
