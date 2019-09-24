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
    val keys = nodes.keys.toArray
    var pair = NodePair(nodes(keys(0)), nodes(keys(1)))
    for (i <- 0 until size; j <- i + 1 until size) {
      val newPair = NodePair(nodes(keys(i)), nodes(keys(j)))
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
  private var nodeIdGenerator = -1

  val root = createTreeBottomUp


  //  def getInside(b: Ball[T]): Seq[Ball[T]] = root.getInside(b)

  private def createTreeBottomUp: Node[T] = {

    def createNotIncluded: mutable.HashMap[Int, Node[T]] = {
      val nodesNotIncluded = mutable.HashMap[Int, Node[T]]()
      balls.foreach(b => {
        val id: Int = getNewId
        nodesNotIncluded(id) = Node(id, b)
      })
      nodesNotIncluded
    }

    def findPairAndUpdate(nodesNotIncluded: mutable.HashMap[Int, Node[T]]): Unit = {
      val id = getNewId
      val minimumPair = findMinimumVolumePair(nodesNotIncluded)
      val newNode = Node(id, minimumPair.boundBall,
        left = Some(nodesNotIncluded(minimumPair.id1)),
        right = Some(nodesNotIncluded(minimumPair.id2))
      )
      newNode.left.get.parent = Some(newNode)
      newNode.right.get.parent = Some(newNode)

      nodesNotIncluded.remove(minimumPair.id1)
      nodesNotIncluded.remove(minimumPair.id2)

      nodesNotIncluded(id) = newNode
    }

    val nodesNotIncluded = createNotIncluded
    while (nodesNotIncluded.size > 1)
      findPairAndUpdate(nodesNotIncluded)

    nodesNotIncluded.head._2
  }

  private def getNewId: Int = {
    nodeIdGenerator += 1
    nodeIdGenerator
  }

  def height(node: Node[T]): Int = node match {
    case Node(_, _, _, None, None)
  }

  def printLevel

}
