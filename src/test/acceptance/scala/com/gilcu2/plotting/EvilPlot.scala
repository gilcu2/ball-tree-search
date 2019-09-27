package com.gilcu2.plotting

import java.awt.Image.SCALE_SMOOTH

import com.cibo.evilplot.colors._
import com.cibo.evilplot.numeric.Point
import com.cibo.evilplot.plot._
import com.cibo.evilplot.plot.aesthetics.DefaultTheme._
import org.scalatest.{FlatSpec, GivenWhenThen, Matchers}
import scalaview.Utils._

class EvilPlotTest extends FlatSpec with Matchers with GivenWhenThen {

  behavior of "EvilPlot"

  it should "paint xy plot" in {

    val data = Seq.tabulate(100) { i =>
      Point(i.toDouble, scala.util.Random.nextDouble())
    }
    val plot = LinePlot.series(data, "Line graph", HSL(210, 100, 56)).
      xAxis().yAxis().frame().
      xLabel("x").yLabel("y").render()

    scalaview.SfxImageViewer(biResize(plot.asBufferedImage, 1000, 800, SCALE_SMOOTH))
    println("Done")
  }

}
