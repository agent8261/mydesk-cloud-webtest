package edu.umich.imlc.mydesk.cloud.frontend.app.drawables;

import com.emitrom.lienzo.client.core.shape.Ellipse;
import com.emitrom.lienzo.client.core.shape.Group;
import com.emitrom.lienzo.client.core.shape.Layer;
import com.emitrom.lienzo.client.core.shape.Text;
import com.emitrom.lienzo.client.core.types.TextMetrics;
import com.emitrom.lienzo.shared.core.types.ColorName;
import com.emitrom.lienzo.shared.core.types.TextAlign;
import com.emitrom.lienzo.shared.core.types.TextBaseLine;
import com.google.gwt.core.shared.GWT;


/*
 * Width and height of the Oval is based on the size of the title
 */
public class LienzoOvalNode implements DrawableObject
{
  static final DrawableObjectInjector appInjector = GWT.create(DrawableObjectInjector.class);
  
  static final String DEFAULT_COLOR = "black";
  static final double DEFAULT_NODE_WIDTH = 50;
  static final double DEFAULT_NODE_HEIGHT = 30;
  static final double TEXT_BOX_PADDING_W = 4.0;
  static final double TEXT_BOX_PADDING_H = 6.0;
  static final int MAX_TITLE_SIZE = 40;
  static final double LINE_WIDTH = 3;
  
  static final String DEFAULT_FONT = "Helvetica";
  //static final double DEFAULT_FONT_SIZE = 35;
  static final double DEFAULT_FONT_SIZE = 12;
  
  Group groupShape = null;
  Ellipse ellipseShape = null;
  Text titleShape = null;
  String title = null;
  
  static class Size{ double w, h; };
  
  // ---------------------------------------------------------------------------
  // ---------------------------------------------------------------------------
  
  public LienzoOvalNode
    (double x, double y, String color, String title, DrawingSurface drawingSurface)
  {
    assert(drawingSurface instanceof Layer);
    this.title = title;
    groupShape = new Group();
    groupShape.setDraggable(true);
    
    titleShape = new Text(" ", DEFAULT_FONT, DEFAULT_FONT_SIZE);
    titleShape.setX(x).setY(y).setFillColor(ColorName.BLACK)
      .setTextAlign(TextAlign.CENTER).setTextBaseLine(TextBaseLine.MIDDLE);
    
    groupShape.add(titleShape);
    Layer layer = (Layer)drawingSurface;
    layer.add(groupShape);
    Size s = calcEllipseSize(x, y);
    ellipseShape = new Ellipse(s.w, s.h);
    ellipseShape.setStrokeColor(color).setX(x).setY(y).setStrokeWidth(LINE_WIDTH);
    groupShape.add(ellipseShape);
  }
  
  // ---------------------------------------------------------------------------
  
  Size calcEllipseSize(double centerX, double centerY)
  {
    Size size = new Size();
    size.w = DEFAULT_NODE_WIDTH; size.h = DEFAULT_NODE_HEIGHT;
    if((title != null) && !title.isEmpty())
    {
      String displayedText = getDisplayText();
      titleShape.setText(displayedText);
      TextMetrics tm = titleShape.measure(groupShape.getLayer().getContext());
      
      double h = (tm.getHeight() * 0.5) + TEXT_BOX_PADDING_H;
      double w = (tm.getWidth() * 0.5) + TEXT_BOX_PADDING_W;
      double disA = dist(w, 0, w, h);
      double disB = dist(-1 * w, 0, w, h);
      size.w = disA + disB;
      double a = size.w * 0.5;
      size.h = 2 * Math.sqrt(Math.abs(a * a - w * w));
    }    
    return size;
  }
  
  // ---------------------------------------------------------------------------
  
  String getDisplayText()
  {
    assert(title != null);
    if(title.length() > MAX_TITLE_SIZE)
      return title.substring(0, MAX_TITLE_SIZE) + "...";
    return title;
  }
  
  // ---------------------------------------------------------------------------
  
  static double dist(double p1x, double p1y, double p2x, double p2y)
  {
    double dx = p2x - p1x, dy = p2y - p1y;
    return Math.sqrt( Math.abs((dx * dx) + (dy * dy)) );
  }
  
  // ---------------------------------------------------------------------------
  
  @Override
  public void setPosition(double x, double y)
  {
  }

  // ---------------------------------------------------------------------------
  
  @Override
  public double getX()
  {
    return 0;
  }

  // ---------------------------------------------------------------------------
  
  @Override
  public double getY()
  {
    return 0;
  }

  // ---------------------------------------------------------------------------
  
  @Override
  public void addTo(DrawingSurface drawingSurface)
  {
    if(drawingSurface instanceof Layer)
    {
      Layer layer = (Layer)drawingSurface;
      layer.add(groupShape);
    }
  }

  // ---------------------------------------------------------------------------
}
