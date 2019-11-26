package com.gilcu2.plotting

import java.awt.Image.SCALE_SMOOTH

import com.cibo.evilplot.colors._
import com.cibo.evilplot.geometry.{Disc, Drawable, Extent}
import com.cibo.evilplot.numeric.{Point, Point3d}
import com.cibo.evilplot.plot._
import com.cibo.evilplot.plot.aesthetics.DefaultTheme._
import com.cibo.evilplot.plot.renderers.PointRenderer
import com.gilcu2.balltree.{Ball, BallTree, BallTreeDraw}
import com.gilcu2.plotting.evilInterface.{CircleRenderer, CircleScatterPlot}
import com.gilcu2.spaces.EuclideanSpace
import scalaview.Utils._

import scala.util.Random


object EvilPlotDemo {

  val visibleColor = HSLA(100, 100, 100, 1)
  val transparentColor = HSLA(210, 100, 56, 0.1)

  def main(args: Array[String]): Unit = {

    //    val plot=ScatterPlotDemo
    //    val plot = PointRendererDemo
    val plot = CircleRendererDemo
    //    val plot = BallTreeRendererDemo

    scalaview.SfxImageViewer(biResize(plot.asBufferedImage, 1000, 800, SCALE_SMOOTH))

  }

  private def BallTreeRendererDemo = {

    implicit val generator = new Random(666L) //evil seed
    implicit val space = EuclideanSpace(2)

    val n = 3
    val k = 2
    val balls = (1 to n).map(i => Ball.random(dim = 2, factor = 100))
    val query = Ball.random(dim = 2)

    val tree = BallTree(balls)
    val data = tree.render

    CircleScatterPlot(
      data,
      circleRenderer = Some(CircleRenderer.custom({ (plot: Plot, extent: Extent, x: BallTreeDraw) => {
        val scale = (y: Double) =>
          plot.ytransform(plot, extent)(y + plot.ybounds.min) - extent.height
        Disc.centered(scale(x.radio)).filled(transparentColor).colored(visibleColor).weighted(2)
      }
      }))
    ).xAxis()
      .yAxis()
      .frame()
      .rightLegend()
      .render()

  }

  private def ScatterPlotDemo: Drawable = {
    val data = Seq.tabulate(100) { i =>
      Point(i.toDouble, scala.util.Random.nextDouble())
    }
    ScatterPlot.series(data, "Line graph", HSL(210, 100, 56)).
      xAxis().yAxis().frame().
      xLabel("x").yLabel("y").render()


  }

  private def PointRendererDemo = {

    Random.setSeed(666L) //evil seed
    val allYears = (2007 to 2013).toVector
    val data = Seq.fill(150)(
      Point3d(Random.nextDouble(), Random.nextDouble(), allYears(Random.nextInt(allYears.length))))

    ScatterPlot(
      data,
      pointRenderer = Some(PointRenderer.colorByCategory(data, { x: Point3d[Int] =>
        x.z
      }))
    ).xAxis()
      .yAxis()
      .frame()
      .rightLegend()
      .render()

  }

  private def CircleRendererDemo = {

    Random.setSeed(666L) //evil seed
    //    val data = Seq.fill(10)(
    //      Point3d(1000 * Random.nextDouble(), 1000 * Random.nextDouble(), Random.nextInt(100)))
    val data = Seq(
      BallTreeDraw(-1, 0, 0, 1, 0),
      BallTreeDraw(1, 0, 1, 1, 0)
    )

    CircleScatterPlot(
      data,
      circleRenderer = Some(CircleRenderer.custom({ (plot: Plot, extent: Extent, x: BallTreeDraw) => {
        val scale = (x: Double) =>
          extent.width / (plot.xbounds.max - plot.xbounds.min) * x
        val scaledRadio = scale(x.radio)
        Disc.centered(scaledRadio).filled(transparentColor).colored(visibleColor).weighted(2)
      }
      }))
    ).xAxis()
      .yAxis()
      .frame()
      .rightLegend()
      .render()

  }

}
