package edu.umich.imlc.mydesk.cloud.frontend.app.drawables;

import edu.umich.imlc.mydesk.cloud.frontend.app.drawables.DrawableObject.DrawingSurface;


/*
 * Lienzo library based implementation of DrawableObjectFactory
 */
public class LienzoObjectFactory implements DrawableObjectFactory
{
  // ---------------------------------------------------------------------------
  // ---------------------------------------------------------------------------
  
  @Override
  public DrawableObject createOvalNode
    (double x, double y, String color, String title, String objID, DrawingSurface drawingSurface)
  {
    return new LienzoOvalNode(x, y, color, title, objID, drawingSurface);
  }
  
  // ---------------------------------------------------------------------------
}
