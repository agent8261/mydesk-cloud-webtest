
package com.emitrom.lienzo.client.core.shape.path;

import com.emitrom.lienzo.client.core.types.Point2DArray;

public class QuadraticCurveToPathPart extends PathPart
{
    public QuadraticCurveToPathPart(Point2DArray points, boolean absolute)
    {
        super(PathPartJSO.makePathPartPoints(absolute ? PathPartType.Q : PathPartType.q, points));
    }
}
