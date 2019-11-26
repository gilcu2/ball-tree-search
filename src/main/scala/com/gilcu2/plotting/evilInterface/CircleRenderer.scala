package com.gilcu2.plotting.evilInterface

import com.cibo.evilplot.colors.Color
import com.cibo.evilplot.geometry.{Disc, Drawable, EmptyDrawable, Extent}
import com.cibo.evilplot.numeric.Datum2d
import com.cibo.evilplot.plot.aesthetics.Theme
import com.cibo.evilplot.plot.{LegendContext, Plot}
import com.cibo.evilplot.plot.renderers.{PlotElementRenderer, PointRenderer}

trait CircleRenderer[T <: Datum2d[T]] extends PlotElementRenderer[T] {
  def legendContext: LegendContext = LegendContext()

}

object CircleRenderer {

  def custom[T <: Datum2d[T]](renderFn: (Plot, Extent, T) => Drawable,
                              legendCtx: Option[LegendContext] = None)
  : CircleRenderer[T] = new CircleRenderer[T] {

    def render(plot: Plot, extent: Extent, datum: T): Drawable = {
      renderFn(plot, extent, datum)
    }

    override def legendContext: LegendContext = legendCtx.getOrElse(super.legendContext)
  }

  def default[T <: Datum2d[T]](
                                color: Option[Color] = None,
                                pointSize: Option[Double] = None,
                                label: Drawable = EmptyDrawable()
                              )(implicit theme: Theme): CircleRenderer[T] = new CircleRenderer[T] {
    override def legendContext: LegendContext = label match {
      case _: EmptyDrawable => LegendContext.empty
      case d =>
        val size = pointSize.getOrElse(theme.elements.pointSize)
        LegendContext.single(Disc.centered(size).filled(color.getOrElse(theme.colors.point)), d)
    }

    def render(plot: Plot, extent: Extent, index: T): Drawable = {
      val size = pointSize.getOrElse(theme.elements.pointSize)
      Disc.centered(size).filled(color.getOrElse(theme.colors.point))
    }
  }

}
