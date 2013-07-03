
package com.emitrom.lienzo.client.core.shape;

import java.util.List;

import com.emitrom.lienzo.client.core.Attribute;
import com.emitrom.lienzo.client.core.Context2D;
import com.emitrom.lienzo.client.core.shape.json.IFactory;
import com.emitrom.lienzo.client.core.shape.json.ShapeFactory;
import com.emitrom.lienzo.client.core.shape.json.validators.ValidationContext;
import com.emitrom.lienzo.client.core.shape.path.IPathPartBuilder;
import com.emitrom.lienzo.client.core.shape.path.PathPartArray;
import com.emitrom.lienzo.client.core.types.Point2D;
import com.emitrom.lienzo.client.core.types.Point2DArray;
import com.emitrom.lienzo.shared.core.types.ShapeType;
import com.google.gwt.json.client.JSONObject;

public class Path extends Shape<Path> implements IPathPartBuilder<Path>
{
    public Path()
    {
        super(ShapeType.PATH);

        if (null == getAttributes().getPathParts())
        {
            getAttributes().setPathParts(new PathPartArray());
        }
    }

    protected Path(JSONObject node)
    {
        super(ShapeType.PATH, node);

        if (null == getAttributes().getPathParts())
        {
            getAttributes().setPathParts(new PathPartArray());
        }
    }

    @Override
    public Path m(double x, double y)
    {
        getAttributes().getPathParts().m(x, y);

        return this;
    }

    @Override
    public Path m(Point2D point)
    {
        getAttributes().getPathParts().m(point);

        return this;
    }

    @Override
    public Path m(Point2DArray points)
    {
        getAttributes().getPathParts().m(points);

        return this;
    }

    @Override
    public Path m(Point2D point, Point2D... points)
    {
        getAttributes().getPathParts().m(point, points);

        return this;
    }

    @Override
    public Path m(Point2D[] points)
    {
        getAttributes().getPathParts().m(points);

        return this;
    }

    @Override
    public Path m(List<Point2D> points)
    {
        getAttributes().getPathParts().m(points);

        return this;
    }

    @Override
    public Path M(double x, double y)
    {
        getAttributes().getPathParts().M(x, y);

        return this;
    }

    @Override
    public Path M(Point2D point)
    {
        getAttributes().getPathParts().M(point);

        return this;
    }

    @Override
    public Path M(Point2DArray points)
    {
        getAttributes().getPathParts().M(points);

        return this;
    }

    @Override
    public Path M(Point2D point, Point2D... points)
    {
        getAttributes().getPathParts().M(point, points);

        return this;
    }

    @Override
    public Path M(Point2D[] points)
    {
        getAttributes().getPathParts().M(points);

        return this;
    }

    @Override
    public Path M(List<Point2D> points)
    {
        getAttributes().getPathParts().M(points);

        return this;
    }

    @Override
    public Path l(double x, double y)
    {
        getAttributes().getPathParts().l(x, y);

        return this;
    }

    @Override
    public Path l(Point2D point)
    {
        getAttributes().getPathParts().l(point);

        return this;
    }

    @Override
    public Path l(Point2DArray points)
    {
        getAttributes().getPathParts().l(points);

        return this;
    }

    @Override
    public Path l(Point2D point, Point2D... points)
    {
        getAttributes().getPathParts().l(point, points);

        return this;
    }

    @Override
    public Path l(Point2D[] points)
    {
        getAttributes().getPathParts().l(points);

        return this;
    }

    @Override
    public Path l(List<Point2D> points)
    {
        getAttributes().getPathParts().l(points);

        return this;
    }

    @Override
    public Path L(double x, double y)
    {
        getAttributes().getPathParts().L(x, y);

        return this;
    }

    @Override
    public Path L(Point2D point)
    {
        getAttributes().getPathParts().L(point);

        return this;
    }

    @Override
    public Path L(Point2DArray points)
    {
        getAttributes().getPathParts().L(points);

        return this;
    }

    @Override
    public Path L(Point2D point, Point2D... points)
    {
        getAttributes().getPathParts().L(point, points);

        return this;
    }

    @Override
    public Path L(Point2D[] points)
    {
        getAttributes().getPathParts().L(points);

        return this;
    }

    @Override
    public Path L(List<Point2D> points)
    {
        getAttributes().getPathParts().L(points);

        return this;
    }

    @Override
    public Path z()
    {
        getAttributes().getPathParts().z();

        return this;
    }

    @Override
    public IFactory<?> getFactory()
    {
        return new PathFactory();
    }

    @Override
    protected boolean prepare(Context2D context, Attributes attr, double alpha)
    {
        context.beginPath();

        context.drawPath(attr.getPathParts());

        return true;
    }

    public static class PathFactory extends ShapeFactory<Path>
    {
        public PathFactory()
        {
            super(ShapeType.PATH);

            addAttribute(Attribute.PATH_PARTS, true);
        }

        @Override
        public Path create(JSONObject node, ValidationContext ctx)
        {
            return new Path(node);
        }
    }

    @Override
    public Path q(double cx, double cy, double ex, double ey)
    {
        getAttributes().getPathParts().q(cx, cy, ex, ey);

        return this;
    }

    @Override
    public Path q(Point2D cp, Point2D ep)
    {
        getAttributes().getPathParts().q(cp, ep);

        return this;
    }

    @Override
    public Path Q(double cx, double cy, double ex, double ey)
    {
        getAttributes().getPathParts().Q(cx, cy, ex, ey);

        return this;
    }

    @Override
    public Path Q(Point2D cp, Point2D ep)
    {
        getAttributes().getPathParts().Q(cp, ep);

        return this;
    }
}
