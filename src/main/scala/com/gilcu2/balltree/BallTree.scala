package com.gilcu2.balltree

import com.cibo.evilplot.numeric.Datum2d
import com.gilcu2.balltree.BallsOperations._
import com.gilcu2.spaces.Space

import scala.collection.mutable

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

  def printLevel[T](node: Node[T], level: Int): Unit =
    if (node.level == level)
      println(node)
    else if (node.left.nonEmpty) {
      printLevel(node.left.get, level)
      printLevel(node.right.get, level)
    }

  def render[T](node: Node[T])(implicit space: Space[T]): Seq[BallTreeDraw] = {
    val r2 = space.r2Projection(node.ball.center)
    val current =
      Seq(BallTreeDraw(r2.x, r2.y, node.id, node.ball.radio, node.level))
    val children = if (node.left.nonEmpty)
      render(node.left.get) ++ render(node.right.get)
    else Seq()
    current ++ children
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

  def kNearestMaximumDistance(b: Ball[T], k: Int): Seq[Ball[T]] = {
    val queue = new BoundedPriorityQueue[BallDistance[T]](k)
    root.kNearestMaximumDistance(b, k, queue)
    val sorted = queue.getSortedAndClear()
    sorted.map(_.ball)
  }

  def print(): Unit = {
    for (i <- height - 1 to 0 by -1) {
      println(s"Level $i")
      printLevel(root, i)
    }
  }

  def height: Int = root.level + 1

  def render: Seq[BallTreeDraw] = BallTree.render(root).reverse

  private def createTreeBottomUp: Node[T] = {

    def createNotIncluded: mutable.HashMap[Int, Node[T]] = {
      val nodesNotIncluded = mutable.HashMap[Int, Node[T]]()
      balls.foreach(b => {
        val id: Int = getNewId
        nodesNotIncluded(id) = Node(id, b, 0)
      })
      nodesNotIncluded
    }

    def findPairAndUpdate(nodesNotIncluded: mutable.HashMap[Int, Node[T]]): Unit = {
      val id = getNewId
      val minimumPair = findMinimumVolumePair(nodesNotIncluded)
      val nodeLeft = nodesNotIncluded(minimumPair.id1)
      val nodeRight = nodesNotIncluded(minimumPair.id2)
      val level = Math.max(nodeLeft.level, nodeRight.level) + 1
      val newNode = Node(id, minimumPair.boundBall, level,
        left = Some(nodeLeft),
        right = Some(nodeRight)
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

case class BallDistance[T](ball: Ball[T], distance: Double) extends Ordered[BallDistance[T]] {

  override def compare(that: BallDistance[T]): Int = (that.distance - this.distance).toInt

}

case class BallTreeDraw(x: Double, y: Double, id: Int, radio: Double, level: Int) extends Datum2d[BallTreeDraw] {
  def withXY(x: Double, y: Double): BallTreeDraw = this.copy(x, y)

  override def toString: String = {
    f"BallTreeEvil($id,$x%1.2f,$y%1.2f,$radio%1.2f,$level)"
  }
}
