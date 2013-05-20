package edu.umich.imlc.mydesk.cloud.frontend.wesketch;

import java.io.Serializable;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.Context2d.LineCap;
import com.google.gwt.canvas.dom.client.CssColor;

import edu.umich.imlc.mydesk.cloud.frontend.ColorUtil;

public class DrawPathEvent_GWT extends WeSketchBaseEvent_GWT implements Serializable
{
  /**
   * 
   */
  private static final long serialVersionUID = -5231627000312015237L;
  public Path_GWT path = new Path_GWT();
  public int color;
  public float strokeWidth;

  // ---------------------------------------------------------------------------
  // ---------------------------------------------------------------------------

  public DrawPathEvent_GWT(int eventPointer_, String slideID_)
  {
    super(eventPointer_, slideID_);
  }// ctor

  // ---------------------------------------------------------------------------
  public DrawPathEvent_GWT(int eventPointer_)
  {
    this(eventPointer_, null);
  }// ctor

  // ---------------------------------------------------------------------------

  /**
   * No-arg constructor for serialization
   */
  public DrawPathEvent_GWT()
  {
  }

  // ---------------------------------------------------------------------------

  @Override
  public void draw(Context2d context)
  {
    assert (path.points.size() >= 2);

    if( strokeWidth == 0 )
    {
      strokeWidth = 1;
    }
    context.setLineWidth(strokeWidth);
    context.setLineCap(LineCap.ROUND);
    context.beginPath();
    context.setGlobalAlpha(ColorUtil.getAlphaComponent(color));
    Point_GWT p1 = path.points.get(0);

    for( Point_GWT p2 : path.points.subList(1, path.points.size()) )
    {
      // Draw line segment
      context.moveTo(p1.x, p1.y);
      context.lineTo(p2.x, p2.y);
      context.setStrokeStyle(CssColor.make(ColorUtil.getRedComponent(color),
          ColorUtil.getGreenComponent(color), ColorUtil.getBlueComponent(color)));
      context.stroke();
      context.fill();
      context.closePath();

      // NOTE: This is no longer needed. Lines have round caps now.
      // // Draw circle at gaps.
      // context.beginPath();
      // context.setLineWidth(.1);
      // context.setFillStyle(CssColor.make(getRedColor(color),
      // getGreenColor(color), getBlueColor(color)));
      // context.arc(p1.x, p1.y, strokeWidth / 4.0, 0, 2 * Math.PI);
      // context.fill();
      // context.closePath();

      // Set current end point as next start point.
      p1 = p2;
    }// for

  }// draw

}// class
