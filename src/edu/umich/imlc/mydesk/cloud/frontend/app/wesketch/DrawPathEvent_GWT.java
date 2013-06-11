package edu.umich.imlc.mydesk.cloud.frontend.app.wesketch;

import java.io.Serializable;
import java.util.List;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.Context2d.LineCap;
import com.google.gwt.canvas.dom.client.CssColor;

import edu.umich.imlc.mydesk.cloud.frontend.utilities.ColorUtil;

public class DrawPathEvent_GWT extends WeSketchBaseEvent_GWT implements Serializable
{
  /**
   * 
   */
  private static final long serialVersionUID = -5231627000312015237L;
  public Path_GWT path = new Path_GWT();
  private int color;  
  private float strokeWidth;
  
  CssColor cssColor = null;
  int red;
  int green;
  int blue;
  int alpha;

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
  
  public final int getColor()  
  {
    return color;
  }
  
  // ---------------------------------------------------------------------------
  
  public final void setColor(int color)
  {
    this.color = color;
    this.alpha = ColorUtil.getAlphaComponent(color);
    this.red = ColorUtil.getRedComponent(color);
    this.green = ColorUtil.getGreenComponent(color);
    this.blue = ColorUtil.getBlueComponent(color);
    cssColor = CssColor.make(red, green, blue);
  }
  
  // ---------------------------------------------------------------------------
  
  public final float getStrokeWidth()
  {
    return strokeWidth;
  }
  
  // ---------------------------------------------------------------------------
  
  public final void setStrokeWidth(float strokeWidth)
  {
    this.strokeWidth = strokeWidth;
  }
  
  // ---------------------------------------------------------------------------

  @Override
  public void draw(Context2d context)
  {    
    assert (path.points.size() >= 2);       
    if( strokeWidth == 0 )
      strokeWidth = 1;
    
    context.save();
    context.setLineWidth(strokeWidth);
    context.setLineCap(LineCap.ROUND);    
    context.setGlobalAlpha(alpha);
    context.setStrokeStyle(cssColor);
    
    List<Point_GWT> points = path.points;    
    int end = points.size() - 1;
    context.beginPath();
    for(int i = 0; i < end; i++)
    {
      Point_GWT p1 = points.get(i);
      Point_GWT p2 = points.get(i + 1);
      context.moveTo(p1.x, p1.y);
      context.lineTo(p2.x, p2.y);
    }// for
    context.stroke();
    context.restore();
  }// draw

}// class
