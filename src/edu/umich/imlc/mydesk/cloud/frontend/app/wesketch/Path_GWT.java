package edu.umich.imlc.mydesk.cloud.frontend.app.wesketch;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class Path_GWT implements Serializable
{
  private static final long serialVersionUID = 6800737129186711393L;
  public List<Point_GWT> points = new ArrayList<Point_GWT>();

  // ---------------------------------------------------------------------------
  // ---------------------------------------------------------------------------

  public Path_GWT(List<Point_GWT> points_)
  {
    points.addAll(points_);
  }// ctor

  // ---------------------------------------------------------------------------

  // No-arg construtor for serialization
  public Path_GWT()
  {
  }// ctor

  // ---------------------------------------------------------------------------

}// class
