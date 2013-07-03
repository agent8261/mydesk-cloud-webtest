/*
   Copyright (c) 2012 Emitrom LLC. All rights reserved. 
   For licensing questions, please contact us at licensing@emitrom.com

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
 */

package com.emitrom.lienzo.client.core.shape;

import com.emitrom.lienzo.client.core.Attribute;
import com.emitrom.lienzo.client.core.Context2D;
import com.emitrom.lienzo.client.core.shape.PolyLine.LastState;
import com.emitrom.lienzo.client.core.shape.json.IFactory;
import com.emitrom.lienzo.client.core.shape.json.ShapeFactory;
import com.emitrom.lienzo.client.core.shape.json.validators.ValidationContext;
import com.emitrom.lienzo.client.core.types.DashArray;
import com.emitrom.lienzo.client.core.types.Point2D;
import com.emitrom.lienzo.client.core.types.Point2DArray;
import com.emitrom.lienzo.shared.core.types.ShapeType;
import com.google.gwt.json.client.JSONObject;

/**
 * Line is a line segment between two points.
 * The class can be used to draw regular lines as well as dashed lines.
 * To create a dashed line, use one of the setDashArray() methods.
 */
public class Line extends Shape<Line>
{
    /**
     * Constructor.  Creates an instance of a line of 0-pixel length, at the 0,0
     * coordinates.
     */
    public Line()
    {
        this(0, 0, 0, 0);
    }
    
    /**
     * Constructor. Creates an instance of a line.
     * 
     * @param x1 first point X coordinate
     * @param y1 first point Y coordinate
     * @param x2 second point X coordinate
     * @param y2 second point Y coordinate
     */
    public Line(double x1, double y1, double x2, double y2)
    {
        super(ShapeType.LINE);

        setPoints(new Point2DArray(new Point2D(x1, y1), new Point2D(x2, y2)));
    }

    protected Line(JSONObject node)
    {
        super(ShapeType.LINE, node);
    }

    /**
     * Draws this line
     * 
     * @param context
     */
    @Override
    public void draw(Context2D context)
    {
        Attributes attr = getAttributes();

        Point2DArray list = attr.getPoints();

        if ((null != list) && (list.getLength() == 2))
        {
            if ((false == context.isSelection()) && (attr.isDefined(Attribute.DASH_ARRAY)))
            {
                DashArray dash = attr.getDashArray();

                if (dash != null)
                {
                    double[] data = dash.getNormalizedArray();

                    if (data.length > 0)
                    {
                        if (setStrokeParams(context, attr))
                        {
                            Point2D p0 = list.getPoint(0);

                            Point2D p1 = list.getPoint(1);

                            context.beginPath();

                            drawDashedLine(context, p0.getX(), p0.getY(), p1.getX(), p1.getY(), data, new LastState(), attr.getStrokeWidth() / 2);
                        }
                        return;
                    }
                }
            }
            Point2D point = list.getPoint(0);

            context.beginPath();

            context.moveTo(point.getX(), point.getY());

            point = list.getPoint(1);

            context.lineTo(point.getX(), point.getY());
        }
    }

    /**
     * Gets the {@link DashArray}. If this is a solid line, the dash array is empty.
     * 
     * @return {@link DashArray} if this line is not dashed, there will be no elements in the {@link DashArray}
     */
    public DashArray getDashArray()
    {
        return getAttributes().getDashArray();
    }

    /**
     * Sets the dash array. 
     * 
     * @param array contains dash lengths
     * @return this Line
     */
    public Line setDashArray(DashArray array)
    {
        getAttributes().setDashArray(array);

        return this;
    }

    /**
     * Sets the dash array with individual dash lengths.
     * 
     * @param dash length of dash
     * @param dashes if specified, length of remaining dashes
     * @return this Line
     */
    public Line setDashArray(double dash, double... dashes)
    {
        getAttributes().setDashArray(new DashArray(dash, dashes));

        return this;
    }

    /**
     * Gets the end-points of this line.
     * 
     * @return Point2DArray
     */
    public Point2DArray getPoints()
    {
        return getAttributes().getPoints();
    }

    /**
     * Sets the end-points of this line.  
     * The points should be a 2-element {@link Point2DArray}
     * 
     * @param points
     * @return this Line
     */
    public Line setPoints(Point2DArray points)
    {
        getAttributes().setPoints(points);

        return this;
    }

    /**
     * Empty implementation since we multi-purpose this class for regular and dashed lines.
     */
    @Override
    public void fill(Context2D context, Attributes attr)
    {

    }

    @Override
    public IFactory<?> getFactory()
    {
        return new LineFactory();
    }

    public static class LineFactory extends ShapeFactory<Line>
    {
        public LineFactory()
        {
            super(ShapeType.LINE);

            addAttribute(Attribute.POINTS, true);

            addAttribute(Attribute.DASH_ARRAY);
        }

        @Override
        public Line create(JSONObject node, ValidationContext ctx)
        {
            return new Line(node);
        }
    }
}
