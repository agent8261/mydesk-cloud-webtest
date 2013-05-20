package edu.umich.imlc.mydesk.cloud.frontend.wesketch;

import java.io.Serializable;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.CssColor;

import edu.umich.imlc.mydesk.cloud.frontend.ColorUtil;

public class DrawTextEvent_GWT extends WeSketchBaseEvent_GWT implements Serializable
{
  /**
   * 
   */
  private static final long serialVersionUID = -5328478697675832807L;

  public String text;
  public Point_GWT topLeft;
  public int color;
  public int size;
  public String font;

  // ---------------------------------------------------------------------------
  // ---------------------------------------------------------------------------

  public DrawTextEvent_GWT(int eventPointer_, String slideID_)
  {
    super(eventPointer_, slideID_);
  }// ctor

  // ---------------------------------------------------------------------------

  public DrawTextEvent_GWT(int eventPointer_)
  {
    this(eventPointer_, null);
  }// ctor

  // ---------------------------------------------------------------------------

  /**
   * No-arg constructor for serialization
   */
  public DrawTextEvent_GWT()
  {
  }

  // ---------------------------------------------------------------------------

  @Override
  public void draw(Context2d context)
  {
    context.beginPath();
    // font = "Helvetica";
    context.setFillStyle(CssColor.make(ColorUtil.getRedComponent(color),
        ColorUtil.getGreenComponent(color), ColorUtil.getBlueComponent(color)));
    String temp = size + "px " + font;
    context.setFont(temp);
    context.fillText(text, topLeft.x, topLeft.y);
    context.closePath();
  }// draw

}// class
