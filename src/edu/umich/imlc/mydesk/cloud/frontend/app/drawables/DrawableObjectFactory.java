package edu.umich.imlc.mydesk.cloud.frontend.app.drawables;

import edu.umich.imlc.mydesk.cloud.frontend.app.drawables.DrawableObject.DrawingSurface;

public interface DrawableObjectFactory
{
  // ---------------------------------------------------------------------------
  
  public DrawableObject createOvalNode
    (double x, double y, String color, String title, DrawingSurface drawingSurface);
  
  // ---------------------------------------------------------------------------
}
