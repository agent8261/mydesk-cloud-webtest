
package com.emitrom.lienzo.client.core.animation.positioning;

import com.emitrom.lienzo.client.core.types.Point2D;

public abstract class AbstractRadialPositioningCalculator implements IPositioningCalculator
{
    @Override
    public final Point2D calculate(final double percent)
    {
        final double r = getRadius();

        final double a = ((Math.PI * 2) * percent);

        return new Point2D(getX() + Math.cos(a) * r, getY() + Math.sin(a) * r);
    }

    public abstract double getX();

    public abstract double getY();

    public abstract double getRadius();
}
