//package com.gilcu2.balltree
//
//import com.gilcu2.points.RNDensePoint.toBall
//import com.gilcu2.points.{RNDensePoint, RNDistances}
//import com.gilcu2.spaces.RNDistances
//import org.scalatest.{FlatSpec, GivenWhenThen, Matchers}
//
//class BallTreeQueryRNDensePointTest extends FlatSpec with Matchers with GivenWhenThen {
//
//  behavior of "BallTree"
//
//  it should "return the balls inside a Ball. Case 1 point and exist" in {
//    val ballTree = BallTree(RNDistances.euclidean, RNDensePoint(0, 0))
//
//    ballTree.getInside(Ball(RNDensePoint(0, 0), 1)) shouldBe Seq(Ball(RNDensePoint(0, 0)))
//  }
//
//  it should "return the balls inside a Ball. Case 1 point and don't exist" in {
//    val ballTree = BallTree(RNDistances.euclidean, RNDensePoint(0, 0))
//
//    ballTree.getInside(Ball(RNDensePoint(2, 0), 1)) shouldBe Seq()
//  }
//
//  it should "return the balls inside a Ball. Case 2 point and 1 satisfy query" in {
//    val ballTree = BallTree(RNDistances.euclidean, RNDensePoint(0, 0))
//    ballTree.insert(RNDensePoint(2, 0))
//
//    ballTree.getInside(Ball(RNDensePoint(3, 0), 1)) shouldBe Seq(Ball(RNDensePoint(2, 0)))
//  }
//
//}
