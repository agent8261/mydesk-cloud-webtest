
package com.emitrom.lienzo.client.core.shape.path;

import com.emitrom.lienzo.client.core.types.Point2DArray;

public class MoveToPathPart extends PathPart
{
    public MoveToPathPart(Point2DArray points, boolean absolute)
    {
        super(PathPartJSO.makePathPartPoints(absolute ? PathPartType.M : PathPartType.m, points));
    }
}
