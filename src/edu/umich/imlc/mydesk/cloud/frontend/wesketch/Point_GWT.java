package edu.umich.imlc.mydesk.cloud.frontend.wesketch;

import java.io.Serializable;

public class Point_GWT implements Serializable
{
  private static final long serialVersionUID = -3981697331009868342L;
  public int x;
  public int y;

  // ---------------------------------------------------------------------------
  // ---------------------------------------------------------------------------

  public Point_GWT(int x_, int y_)
  {
    x = x_;
    y = y_;
  }// ctor

  // ---------------------------------------------------------------------------

  public Point_GWT(double x_, double y_)
  {
    x = (int) x_;
    y = (int) y_;
  }// ctor

  // ---------------------------------------------------------------------------

  // No-arg constructor for serialization
  public Point_GWT()
  {
  }// ctor

  // ---------------------------------------------------------------------------

}// class
