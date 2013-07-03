
package com.emitrom.lienzo.client.core.shape.path;

import com.emitrom.lienzo.client.core.types.Point2DArray;

public class LineToPathPart extends PathPart
{
    public LineToPathPart(Point2DArray points, boolean absolute)
    {
        super(PathPartJSO.makePathPartPoints(absolute ? PathPartType.L : PathPartType.l, points));
    }
}
