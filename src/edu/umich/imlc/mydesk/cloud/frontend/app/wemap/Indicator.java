package edu.umich.imlc.mydesk.cloud.frontend.app.wemap;

import com.google.gwt.canvas.dom.client.Context2d;

/*
 */
public class Indicator
{
  public static final double GAGE_LINE_WIDTH = 1.0;
  public static final double HALF_GAGE_LINE_WIDTH = 1.0;
  static final String LINE_JOIN = "round";
  static final String INNER_COLOR = "white";
  static final String OUTER_COLOR = "black";

  final double width, height, innerW, innerH, halfW, halfH;
  final double maxX, maxY, minX, minY;

  double cenX = 0.0, cenY = 0.0;
  double mouseX = 0.0, mouseY = 0.0;
  double deltaX = 0.0, deltaY = 0.0;
  
  boolean isMoving = false;

  // -------------------------------------------------------------------------
  // -------------------------------------------------------------------------
  
  public Indicator(double width, double height, double canvasW, double canvasH)
  {
    this.width = width; this.height = height;
    innerW = width - 2 * HALF_GAGE_LINE_WIDTH;
    innerH = height - 2 * HALF_GAGE_LINE_WIDTH;
    
    halfW = 0.5 * width; halfH = 0.5 * height;
    maxX = canvasW; maxY = canvasH; minX = 0; minY = 0;
  }
  
  // -------------------------------------------------------------------------

  public void draw(Context2d context)
  {
    double x = getX() - halfW, y = getY() - halfH;
    double innerUL_X = x + HALF_GAGE_LINE_WIDTH;
    double innerUL_Y = y + HALF_GAGE_LINE_WIDTH;

    context.setLineJoin(LINE_JOIN);
    context.setLineWidth(GAGE_LINE_WIDTH);
    context.setStrokeStyle(OUTER_COLOR);
    context.strokeRect(x, y, width, height);
    context.setStrokeStyle(INNER_COLOR);
    context.strokeRect(innerUL_X, innerUL_Y, innerW, innerH);
  }

  // -------------------------------------------------------------------------
  
  // x coordinate of the center point
  public double getX()
  {
    return cenX + deltaX;
  }

  // -------------------------------------------------------------------------

  // y coordinate of the center point
  public double getY()
  {
    return cenY + deltaY;
  }
  
  // -------------------------------------------------------------------------
  
  public Indicator setX(double x)
  {
    if( (x > maxX) || (x < minX) )
    { return this; }
    cenX = x; 
    return this;
  }
  
  // -------------------------------------------------------------------------
  
  public Indicator setY(double y)
  {
    if( (y > maxY) || (y < minY) )
    { return this; }
    cenY = y; 
    return this;
  }
  
  // -------------------------------------------------------------------------

  public Indicator setCenter(double x, double y)
  {
    return setX(x).setY(y);
  }

  // -------------------------------------------------------------------------
  
  public Indicator incrementX(double increment)
  {
    double newX = cenX + increment;
    return setX(newX);
  }
  
  // -------------------------------------------------------------------------
  
  public Indicator incrementY(double increment)
  {
    double newY = cenY + increment;
    return setY(newY);
  }
  
  // -------------------------------------------------------------------------

  Indicator doStartX(double x)
  {
    if( (x > maxX) || (x < minX) )
    { return this; }
    isMoving = true; cenX = x; mouseX = x; return this;
  }

  // -------------------------------------------------------------------------
  
  Indicator doStartY(double y)
  {
    if( (y > maxY) || (y < minY) )
    { return this; }
    isMoving = true; cenY = y; mouseY = y; return this;
  }

  // -------------------------------------------------------------------------
  
  Indicator doMoveY(double y)
  {
    if( isMoving )
    {
      double dy = y - mouseY, newY = cenY + dy;
      if( (newY > maxY) || (newY < minY) )
      { return this; }
      deltaY = dy;
    }
    return this;
  }

  // -------------------------------------------------------------------------
  
  Indicator doMoveX(double x)
  {
    if( isMoving )
    {
      double dx = x - mouseX, newX = cenX + dx;
      if( (newX > maxX) || (newX < minX) )
      { return this; }
      deltaX = dx;
    }
    return this;
  }

  // -------------------------------------------------------------------------
  
  Indicator doFinish()
  {
    cenX += deltaX; deltaX = 0.0; cenY += deltaY; deltaY = 0.0;
    isMoving = false; return this;
  }

  // -------------------------------------------------------------------------
  
  public void printCenter()
  {
    System.out.println("cX: " + cenX + " cY: " + cenY);
  }
} // Class Indicator
