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

import edu.umich.imlc.mydesk.cloud.frontend.AppGinInjector;


/*
 * Width and height of the Oval is based on the size of the title
 */
public class LienzoOvalNode implements DrawableObject
{
  static final AppGinInjector appInjector = GWT.create(AppGinInjector.class);
  
  static final String DEFAULT_COLOR = "black";
  static final double DEFAULT_NODE_WIDTH = 50;
  static final double DEFAULT_NODE_HEIGHT = 30;
  static final double TEXT_BOX_PADDING_W = 4.0;
  static final double TEXT_BOX_PADDING_H = 6.0;
  static final int MAX_TITLE_SIZE = 40;
  static final double LINE_WIDTH = 3;
  
  static final String DEFAULT_FONT = "Helvetica";
  static final double DEFAULT_FONT_SIZE = 12;
  
  Group groupShape = null;
  Ellipse ellipseShape = null;
  Text titleShape = null;
  String title = null;
  String color = null;
  
  static class Size{ double w, h; };
  
  // ---------------------------------------------------------------------------
  // ---------------------------------------------------------------------------
  
  public LienzoOvalNode
    (double x, double y, String color, String title, String objID, DrawingSurface drawingSurface)
  {
    assert(drawingSurface instanceof LienzoLayer);
    if(!(drawingSurface instanceof LienzoLayer) || (objID == null))
      throw new IllegalArgumentException();
    
    this.color = ((color == null) || color.isEmpty()) ? DEFAULT_COLOR : color;
    this.title = title;
    
    groupShape = new Group();
    String oID = new String(objID);
    groupShape.setID(oID);
    groupShape.setDraggable(true);
    
    titleShape = new Text(" ", DEFAULT_FONT, DEFAULT_FONT_SIZE);
    titleShape.setX(x).setY(y).setFillColor(ColorName.BLACK)
      .setTextAlign(TextAlign.CENTER).setTextBaseLine(TextBaseLine.MIDDLE);
    groupShape.add(titleShape);
    titleShape.setID(oID);
    
    LienzoLayer layer = (LienzoLayer)drawingSurface;
    groupShape.addNodeMouseEnterHandler(layer.getNodeMouseEnterHandler());
    groupShape.addNodeMouseExitHandler(layer.getNodeMouseExitHandler());
    titleShape.addNodeMouseEnterHandler(layer.getNodeMouseEnterHandler());
    titleShape.addNodeMouseExitHandler(layer.getNodeMouseExitHandler());
    
    layer.add(groupShape);
    Size s = calcEllipseSize(x, y);
    ellipseShape = new Ellipse(s.w, s.h);
    ellipseShape.setStrokeColor(color).setX(x).setY(y).setStrokeWidth(LINE_WIDTH);
    
    groupShape.add(ellipseShape);    
    ellipseShape.setID(oID);
    ellipseShape.addNodeMouseEnterHandler(layer.getNodeMouseEnterHandler());
    ellipseShape.addNodeMouseExitHandler(layer.getNodeMouseExitHandler());
  }
  
  // ---------------------------------------------------------------------------
  
  Size calcEllipseSize(double centerX, double centerY)
  {
    Size size = new Size();
    size.w = DEFAULT_NODE_WIDTH; size.h = DEFAULT_NODE_HEIGHT;
    if((title == null) || title.isEmpty())
    {
      titleShape.setText("");
    }
    else if((title != null) && !title.isEmpty())
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
  public void addTo(DrawingSurface drawingSurface)
  {
    if(drawingSurface instanceof Layer)
    {
      Layer layer = (Layer)drawingSurface;
      layer.add(groupShape);
    }
  }

  // ---------------------------------------------------------------------------
  
  @Override
  public void removeFrom(DrawingSurface drawingSurface)
  {
    if(drawingSurface instanceof Layer)
    {
      Layer layer = (Layer)drawingSurface;
      layer.remove(groupShape);
    }
  }

  // ---------------------------------------------------------------------------
  
  @Override
  public String getObjectID()
  {
    return groupShape.getID();
  }

  // ---------------------------------------------------------------------------
  
  @Override
  public void setPosition(double x, double y)
  {
    groupShape.setX(x);
    groupShape.setY(y);
  }

  // ---------------------------------------------------------------------------
  
  @Override
  public void setColor(String color)
  {
    if((color == null) || (color.isEmpty()))
      throw new IllegalArgumentException();
    this.color = color;
    ellipseShape.setStrokeColor(color);
  }

  // ---------------------------------------------------------------------------
  
  @Override
  public String getColor()
  {
    return color;
  }

  // ---------------------------------------------------------------------------
  
  @Override
  public void setTitle(String title)
  {
    this.title = title;
    double x = ellipseShape.getX(), y = ellipseShape.getY();
    Size s = calcEllipseSize(x, y);
    ellipseShape.setHeight(s.h).setWidth(s.w);
  }

  // ---------------------------------------------------------------------------
  
  @Override
  public String getTitle()
  {
    return title;
  }

  // ---------------------------------------------------------------------------
}
