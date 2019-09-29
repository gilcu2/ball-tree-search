package com.gilcu2.plotting

import java.awt.Image.SCALE_SMOOTH

import com.cibo.evilplot.colors._
import com.cibo.evilplot.geometry.{Disc, Drawable}
import scalaview.Utils._
import com.cibo.evilplot.numeric.{Datum2d, Point, Point3d}
import com.cibo.evilplot.plot._
import com.cibo.evilplot.plot.renderers.PointRenderer
import com.cibo.evilplot.plot.aesthetics.DefaultTheme._

import scala.util.Random


object EvilPlotDemo {

  def main(args: Array[String]): Unit = {

    //    val plot=ScatterPlotDemo
    //    val plot = PointRendererDemo
    val plot = CircleRendererDemo

    scalaview.SfxImageViewer(biResize(plot.asBufferedImage, 1000, 800, SCALE_SMOOTH))

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

    val visibleColor = HSLA(100, 100, 100, 1)
    val transparentColor = HSLA(210, 100, 56, 0.1)
    Random.setSeed(666L) //evil seed
    val data = Seq.fill(10)(
      Point3d(1000 * Random.nextDouble(), 1000 * Random.nextDouble(), Random.nextInt(100)))

    ScatterPlot(
      data,
      pointRenderer = Some(PointRenderer.custom({ x: Point3d[Int] =>
        Disc.centered(x.z).filled(transparentColor).colored(visibleColor).weighted(2)
      }))
    ).xAxis()
      .yAxis()
      .frame()
      .rightLegend()
      .render()

  }


}
