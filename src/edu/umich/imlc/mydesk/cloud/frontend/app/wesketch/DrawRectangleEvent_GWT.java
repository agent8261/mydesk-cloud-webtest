package edu.umich.imlc.mydesk.cloud.frontend.app.wesketch;

import java.io.Serializable;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.CssColor;

import edu.umich.imlc.mydesk.cloud.frontend.utilities.ColorUtil;

public class DrawRectangleEvent_GWT extends WeSketchBaseEvent_GWT implements
    Serializable
{
  /**
   * 
   */
  private static final long serialVersionUID = -6978883119894281001L;

  public int color;
  public Point_GWT topLeft;
  public Point_GWT bottomRight;

  // ---------------------------------------------------------------------------
  // ---------------------------------------------------------------------------

  public DrawRectangleEvent_GWT(int eventPointer_, String slideID_)
  {
    super(eventPointer_, slideID_);
  }// ctor

  // ---------------------------------------------------------------------------

  public DrawRectangleEvent_GWT(int eventPointer_)
  {
    this(eventPointer_, null);
  }// ctor

  // ---------------------------------------------------------------------------

  /**
   * No-arg constructor for serialization
   */
  public DrawRectangleEvent_GWT()
  {
  }

  // ---------------------------------------------------------------------------

  @Override
  public void draw(Context2d context)
  {
    context.save();
    context.beginPath();
    context.setGlobalAlpha(ColorUtil.getAlphaComponent(color));
    context.setFillStyle(CssColor.make(ColorUtil.getRedComponent(color),
        ColorUtil.getGreenComponent(color), ColorUtil.getBlueComponent(color)));
    context.fillRect(topLeft.x, topLeft.y, Math.abs(topLeft.x - bottomRight.x),
        Math.abs(topLeft.y - bottomRight.y));
    context.closePath();
    context.restore();
  }// draw

}// class
