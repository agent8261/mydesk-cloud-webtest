package edu.umich.imlc.mydesk.cloud.frontend.app.wemap;

import com.google.gwt.touch.client.Point;
import com.google.gwt.user.client.rpc.IsSerializable;

public class Point_GWT implements IsSerializable
{
  public static final Point_GWT ZERO = new Point_GWT(0, 0);

  public int x;
  public int y;

  // ---------------------------------------------------------------------------
  // ---------------------------------------------------------------------------

  public Point_GWT()
  {
    this(0, 0);
  }

  // ---------------------------------------------------------------------------

  public Point_GWT(int x_, int y_)
  {
    x = x_;
    y = y_;
  }

  // ---------------------------------------------------------------------------

  public Point_GWT(Point_GWT original)
  {
    this(original.x, original.y);
  }

  // ---------------------------------------------------------------------------

  public Point_GWT(Point original)
  {
    this((int) original.getX(), (int) original.getY());
  }

  // ---------------------------------------------------------------------------

  /**
   * Sets coordinates to zero
   */
  public void zero()
  {
    x = 0;
    y = 0;
  }

  // ---------------------------------------------------------------------------

  public Point toPoint()
  {
    return new Point(x, y);
  }

  // ---------------------------------------------------------------------------

  public int getX()
  {
    return x;
  }

  // ---------------------------------------------------------------------------

  public int getY()
  {
    return y;
  }

  // ---------------------------------------------------------------------------

  public void divide(Point_GWT point)
  {
    x /= point.x;
    y /= point.y;
  }

  // ---------------------------------------------------------------------------

  public static Point_GWT divide(Point_GWT p1, Point_GWT p2)
  {
    Point_GWT newPoint = new Point_GWT(p1);
    newPoint.divide(p2);
    return newPoint;
  }

  // ---------------------------------------------------------------------------

  public void divide(int value)
  {
    x /= value;
    y /= value;
  }

  // ---------------------------------------------------------------------------

  public void divide(double value)
  {
    x = (int) (x / value);
    y = (int) (y / value);
  }

  // ---------------------------------------------------------------------------

  public void multiply(Point_GWT point)
  {
    x *= point.x;
    y *= point.y;
  }

  // ---------------------------------------------------------------------------

  public static Point_GWT multiply(Point_GWT p1, Point_GWT p2)
  {
    Point_GWT newPoint = new Point_GWT(p1);
    newPoint.multiply(p2);
    return newPoint;
  }

  // ---------------------------------------------------------------------------

  public void multiply(int value)
  {
    x *= value;
    y *= value;
  }

  // ---------------------------------------------------------------------------

  public void multiply(double value)
  {
    x = (int) (x * value);
    y = (int) (y * value);
  }

  // ---------------------------------------------------------------------------

  public void plus(Point_GWT point)
  {
    plus(point.x, point.y);
  }

  // ---------------------------------------------------------------------------

  public static Point_GWT plus(Point_GWT p1, Point_GWT p2)
  {
    Point_GWT newPoint = new Point_GWT(p1);
    newPoint.plus(p2);
    return newPoint;
  }

  // ---------------------------------------------------------------------------

  public void plus(int x_, int y_)
  {
    x += x_;
    y += y_;
  }

  // ---------------------------------------------------------------------------

  public void plus(double x_, double y_)
  {
    x = (int) (x + x_);
    y = (int) (y + y_);
  }

  // ---------------------------------------------------------------------------

  public void minus(Point_GWT point)
  {
    minus(point.x, point.y);
  }

  // ---------------------------------------------------------------------------

  public static Point_GWT minus(Point_GWT p1, Point_GWT p2)
  {
    Point_GWT newPoint = new Point_GWT(p1);
    newPoint.minus(p2);
    return newPoint;
  }

  // ---------------------------------------------------------------------------

  public void minus(int x_, int y_)
  {
    x -= x_;
    y -= y_;
  }

  // ---------------------------------------------------------------------------

  public void minus(double x_, double y_)
  {
    x = (int) (x - x_);
    y = (int) (y - y_);
  }

  // ---------------------------------------------------------------------------

  public String toString()
  {
    return "(" + x + "," + y + ")";
  }

  // ---------------------------------------------------------------------------

  public boolean equals(Object obj)
  {
    if( !(obj instanceof Point_GWT) )
    {
      return false;
    }
    Point_GWT other = (Point_GWT) obj;
    return (x == other.x) && (y == other.y);
  }// equals

  // ---------------------------------------------------------------------------

  /**
   * Uses bitwise XOR of x and y.
   */
  @Override
  public int hashCode()
  {
    return (int) x ^ (int) y;
  }

  // ---------------------------------------------------------------------------
  // ---------------------------------------------------------------------------
  // ---------------------------------------------------------------------------

}// class
