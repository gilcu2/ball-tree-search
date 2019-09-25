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

case class NodePair[T](id1: Int, id2: Int, boundBall: Ball[T], volume: Double) {
  override def toString = {
    val volumeS = f"$volume%1.3f"
    s"NodePair($id1,$id2,$boundBall,$volumeS)"
  }
}

object BallTree {

  def apply[T](balls: Seq[Ball[T]])(implicit space: Space[T]): BallTree[T] =
    new BallTree(balls: _*)

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
      else if (newPair.volume == pair.volume && (newPair.id1 < pair.id1 || newPair.id2 < pair.id2))
        pair = newPair
    }
    pair
  }

  def height[T](node: Node[T]): Int = node match {
    case Node(_, _, _, None, None) => 1
    case Node(_, _, _, left, right) =>
      val leftHeight = height(left.get)
      val rightHeight = height(right.get)
      math.max(leftHeight, rightHeight) + 1
  }

  def printLevel[T](node: Node[T], level: Int): Unit =
    if (level == 1)
      println(node)
    else if (node.left.nonEmpty) {
      printLevel(node.left.get, level - 1)
      printLevel(node.right.get, level - 1)
    }

}

class BallTree[T](balls: Ball[T]*)(implicit space: Space[T]) {

  import BallTree._

  assert(balls.nonEmpty)

  val root = createTreeBottomUp
  private var nodeIdGenerator = -1

  def areContained(b: Ball[T]): Seq[Ball[T]] = root.areContained(b)

  def contain(b: Ball[T]): Seq[Ball[T]] = root.contain(b)

  def nearestMaximumDistance(b: Ball[T]): Ball[T] =
    root.nearestMaximumDistance(b, Double.MaxValue)._1.get

  def print(): Unit = {
    for (i <- 1 to height) {
      println(s"Level $i")
      printLevel(root, i)
    }
  }

  def height: Int = BallTree.height(root)

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

}
