package edu.umich.imlc.mydesk.cloud.frontend.app.drawables;

import com.emitrom.lienzo.client.core.shape.Ellipse;
import com.emitrom.lienzo.client.core.shape.Group;
import com.emitrom.lienzo.client.core.shape.Layer;
import com.emitrom.lienzo.client.core.shape.Text;
import com.emitrom.lienzo.client.core.types.TextMetrics;
import com.emitrom.lienzo.shared.core.types.ColorName;
import com.emitrom.lienzo.shared.core.types.TextAlign;
import com.emitrom.lienzo.shared.core.types.TextBaseLine;


/*
 * Width and height of the Oval is based on the size of the title
 */
public class LienzoOvalNode implements DrawableObject
{
  static final String DEFAULT_COLOR = "black";
  static final double DEFAULT_NODE_WIDTH = 50;
  static final double DEFAULT_NODE_HEIGHT = 30;
  static final double TEXT_BOX_PADDING = 5.0;
  static final int MAX_TITLE_SIZE = 40;
  
  static final String DEFAULT_FONT = "Helvetica";
  static final double DEFAULT_FONT_SIZE = 35;
  
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
    System.out.println("Oval Location - x: " + x + " y: " + y);
    this.title = title;
    groupShape = new Group();
    
    titleShape = new Text(" ", DEFAULT_FONT, DEFAULT_FONT_SIZE);
    titleShape.setX(x).setY(y).setFillColor(ColorName.BLACK)
      .setTextAlign(TextAlign.CENTER).setTextBaseLine(TextBaseLine.MIDDLE);
    
    groupShape.add(titleShape);
    Layer layer = (Layer)drawingSurface;
    layer.add(groupShape);
    Size s = calcEllipseSize(x, y);
    ellipseShape = new Ellipse(s.w, s.h);
    ellipseShape.setStrokeColor(color).setX(x).setY(y);
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
      double urX = (tm.getWidth() * 0.5) + TEXT_BOX_PADDING;
      double urY = (tm.getHeight() * 0.5) + TEXT_BOX_PADDING;
      size.w = urY + dist(centerX, centerY, urX, urY);
      double a = size.w * 0.5, f = urX * 0.5;
      size.h = Math.sqrt(a * a - f * f);
    }    
    System.out.println("Oval Node Size w: " + size.w + " h: " + size.h);
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
    double dx = p2x -p1x, dy = p2y - p1y;
    return Math.sqrt(dx * dx + dy * dy);
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
