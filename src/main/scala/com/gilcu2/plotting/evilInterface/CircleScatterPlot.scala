package com.gilcu2.plotting.evilInterface

import com.cibo.evilplot.colors.Color
import com.cibo.evilplot.geometry.{Drawable, Extent, Style, Text}
import com.cibo.evilplot.numeric.{Datum2d, Point}
import com.cibo.evilplot.plot.ScatterPlot.transformDatumsToWorld
import com.cibo.evilplot.plot.{LegendContext, Plot, PlotContext, PlotUtils, ScatterPlot, TransformWorldToScreen}
import com.cibo.evilplot.plot.aesthetics.Theme
import com.cibo.evilplot.plot.renderers.{PlotRenderer, PointRenderer}

object CircleScatterPlot extends TransformWorldToScreen {

  case class CircleScatterPlotRenderer[T <: Datum2d[T]](data: Seq[T], circleRenderer: CircleRenderer[T])
    extends PlotRenderer {

    override def legendContext: LegendContext = circleRenderer.legendContext

    def render(plot: Plot, plotExtent: Extent)(implicit theme: Theme): Drawable = {

      val plotContext = PlotContext(plot, plotExtent)
      val xformedPoints: Seq[T] = transformDatumsToWorld(
        data,
        plotContext.xCartesianTransform,
        plotContext.yCartesianTransform)
      val points = xformedPoints
        //        .filter(p => plotExtent.contains(p))
        .flatMap {
          case point =>
            val r = circleRenderer.render(plot, plotExtent, point)
            Some(r.translate(x = point.x, y = point.y))
        }
        .group

      points
    }
  }

  /** Create a scatter plot from some data.
   *
   * @param data           The points to plot.
   * @param circleRenderer A function to create a Drawable for each point to plot.
   * @param boundBuffer    Extra padding to add to the bounds as a fraction.
   * @return A Plot representing a scatter plot.
   */
  def apply[T <: Datum2d[T]](
                              data: Seq[T],
                              circleRenderer: Option[CircleRenderer[T]] = None,
                              xBoundBuffer: Option[Double] = None,
                              yBoundBuffer: Option[Double] = None
                            )(implicit theme: Theme): Plot = {
    require(xBoundBuffer.getOrElse(0.0) >= 0.0)
    require(yBoundBuffer.getOrElse(0.0) >= 0.0)
    val (xbounds, ybounds) =
      PlotUtils.bounds(data, theme.elements.boundBuffer, xBoundBuffer, yBoundBuffer)
    Plot(
      xbounds,
      ybounds,
      CircleScatterPlotRenderer(
        data,
        circleRenderer.getOrElse(CircleRenderer.default())
      )
    )
  }

  /** Create a scatter plot with the specified name and color.
   *
   * @param data        The points to plot.
   * @param name        The name of this series.
   * @param color       The color of the points in this series.
   * @param pointSize   The size of points in this series.
   * @param boundBuffer Extra padding to add to bounds as a fraction.
   */
  def series(
              data: Seq[Point],
              name: String,
              color: Color,
              pointSize: Option[Double] = None,
              boundBuffer: Option[Double] = None
            )(implicit theme: Theme): Plot =
    series(
      data,
      Style(
        Text(name, theme.fonts.legendLabelSize, theme.fonts.fontFace),
        theme.colors.legendLabel),
      color,
      pointSize,
      boundBuffer
    )

  /** Create a scatter plot with the specified name and color.
   *
   * @param data        The points to plot.
   * @param name        The name of this series.
   * @param color       The color of the points in this series.
   * @param pointSize   The size of points in this series.
   * @param boundBuffer Extra padding to add to bounds as a fraction.
   */
  def series(
              data: Seq[Point],
              name: Drawable,
              color: Color,
              pointSize: Option[Double],
              boundBuffer: Option[Double]
            )(implicit theme: Theme): Plot = {
    val pointRenderer = PointRenderer.default[Point](Some(color), pointSize, name)
    ScatterPlot(data, Some(pointRenderer), boundBuffer, boundBuffer)
  }
}

