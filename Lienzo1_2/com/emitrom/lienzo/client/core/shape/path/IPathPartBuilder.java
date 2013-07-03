
package com.emitrom.lienzo.client.core.shape.path;

import java.util.List;

import com.emitrom.lienzo.client.core.types.Point2D;
import com.emitrom.lienzo.client.core.types.Point2DArray;

public interface IPathPartBuilder<T extends IPathPartBuilder<T>>
{
    public T m(double x, double y);

    public T m(Point2D point);

    public T m(Point2DArray points);

    public T m(Point2D point, Point2D... points);

    public T m(Point2D[] points);

    public T m(List<Point2D> points);

    public T M(double x, double y);

    public T M(Point2D point);

    public T M(Point2DArray points);

    public T M(Point2D point, Point2D... points);

    public T M(Point2D[] points);

    public T M(List<Point2D> points);

    public T l(double x, double y);

    public T l(Point2D point);

    public T l(Point2DArray points);

    public T l(Point2D point, Point2D... points);

    public T l(Point2D[] points);

    public T l(List<Point2D> points);

    public T L(double x, double y);

    public T L(Point2D point);

    public T L(Point2DArray points);

    public T L(Point2D point, Point2D... points);

    public T L(Point2D[] points);

    public T L(List<Point2D> points);

    public T q(double cx, double cy, double ex, double ey);

    public T q(Point2D cp, Point2D ep);

    public T Q(double cx, double cy, double ex, double ey);

    public T Q(Point2D cp, Point2D ep);

    public T z();
}
