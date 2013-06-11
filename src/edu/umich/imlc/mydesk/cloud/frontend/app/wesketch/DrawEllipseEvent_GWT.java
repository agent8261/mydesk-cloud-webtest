package edu.umich.imlc.mydesk.cloud.frontend.app.wesketch;

import java.io.Serializable;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.CssColor;

import edu.umich.imlc.mydesk.cloud.frontend.utilities.ColorUtil;

public class DrawEllipseEvent_GWT extends WeSketchBaseEvent_GWT implements Serializable
{
  private static final long serialVersionUID = -5201455174742013230L;

  private int color;
  private Point_GWT boundingRectangleTopLeft;
  private Point_GWT boundingRectangleBottomRight;
  private Point_GWT center;
  private Integer height;
  private Integer width;

  // ---------------------------------------------------------------------------
  // ---------------------------------------------------------------------------

  public DrawEllipseEvent_GWT(int eventPointer_, String slideID_)
  {
    super(eventPointer_, slideID_);
  }// ctor

  // ---------------------------------------------------------------------------
  public DrawEllipseEvent_GWT(int eventPointer_)
  {
    this(eventPointer_, null);
  }// ctor

  // ---------------------------------------------------------------------------

  /**
   * No-arg constructor for serialization
   */
  public DrawEllipseEvent_GWT()
  {
  }

  // ---------------------------------------------------------------------------

  public void setColor(int color_)
  {
    color = color_;
  }

  // ---------------------------------------------------------------------------

  public int getColor()
  {
    return color;
  }

  // ---------------------------------------------------------------------------

  public void setBoundingRectangleTopLeft(Point_GWT point)
  {
    boundingRectangleTopLeft = point;
    recalculateValues();
  }

  // ---------------------------------------------------------------------------

  public void setBoundingRectangleBottomRight(Point_GWT point)
  {
    boundingRectangleBottomRight = point;
    recalculateValues();
  }

  // ---------------------------------------------------------------------------

  private void recalculateValues()
  {
    if( boundingRectangleTopLeft != null
        && boundingRectangleBottomRight != null )
    {
      height = Math.abs(boundingRectangleBottomRight.y
          - boundingRectangleTopLeft.y);
      width = Math.abs(boundingRectangleBottomRight.x
          - boundingRectangleTopLeft.x);
      center = new Point_GWT(
          (boundingRectangleTopLeft.x + boundingRectangleBottomRight.x) / 2,
          (boundingRectangleTopLeft.y + boundingRectangleBottomRight.y) / 2);
    }// if

  }// recalculateValues

  // ---------------------------------------------------------------------------

  public Point_GWT getBoundingRectangleTopLeft()
  {
    return boundingRectangleTopLeft;
  }

  // ---------------------------------------------------------------------------

  public Point_GWT getBoundingRectangleBottomRight()
  {
    return boundingRectangleBottomRight;
  }

  // ---------------------------------------------------------------------------

  @Override
  public void draw(Context2d context)
  {
    context.save();
    context.beginPath();
    context.setGlobalAlpha(ColorUtil.getAlphaComponent(color));
    context.moveTo(center.x, center.y - height / 2);
    context.bezierCurveTo(center.x + width / 2, center.y - height / 2, center.x
        + width / 2, center.y + height / 2, center.x, center.y + height / 2);

    context.bezierCurveTo(center.x - width / 2, center.y + height / 2, center.x
        - width / 2, center.y - height / 2, center.x, center.y - height / 2);

    context.setFillStyle(CssColor.make(ColorUtil.getRedComponent(color),
        ColorUtil.getGreenComponent(color), ColorUtil.getBlueComponent(color)));
    context.fill();
    context.closePath();
    context.restore();
  }// draw
}// class
