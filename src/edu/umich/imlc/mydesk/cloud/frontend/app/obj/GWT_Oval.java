package edu.umich.imlc.mydesk.cloud.frontend.app.obj;

import edu.umich.imlc.mydesk.cloud.frontend.app.drawables.DrawableObject.DrawingSurface;

public class GWT_Oval extends GWT_Node
{
  public GWT_Oval(String objID, double x, double y, String title, 
      String note, String color, DrawingSurface surface)
  {
    super(factory.createOvalNode(x, y, color, title, objID, surface), note);
  }

}
