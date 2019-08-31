package interfaces

import com.gilcu2.balltree.Ball
import com.gilcu2.spaces.{EuclideanSpace, RNDensePoint}
import org.scalatest.{FlatSpec, GivenWhenThen, Matchers}

class PlotTest extends FlatSpec with Matchers with GivenWhenThen {

  behavior of "Plot"

  implicit val space = EuclideanSpace

  it should "paint the ball projection" in {
    val balls = Seq(Ball(RNDensePoint(0, 0), 1))

    Plot.paint(balls)
  }

}
