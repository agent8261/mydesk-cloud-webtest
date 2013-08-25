package edu.umich.imlc.mydesk.cloud.frontend.app.obj;

import com.google.gwt.core.shared.GWT;

import edu.umich.imlc.mydesk.cloud.frontend.AppGinInjector;
import edu.umich.imlc.mydesk.cloud.frontend.app.drawables.DrawableObject;
import edu.umich.imlc.mydesk.cloud.frontend.app.drawables.DrawableObjectFactory;

public class GWT_WeMapObject
{
  // Allows for on demand dependency injection
  protected static final AppGinInjector appInjector = GWT.create(AppGinInjector.class);
  
  protected static final DrawableObjectFactory 
  factory = appInjector.getDrawableObjectFactory();
  
  protected final DrawableObject drawableObject;
  
  // ---------------------------------------------------------------------------
  // ---------------------------------------------------------------------------
  
  public GWT_WeMapObject(DrawableObject obj)
  {
    if(obj == null)
      throw new IllegalArgumentException();
    drawableObject = obj;
  }

  // ---------------------------------------------------------------------------
  
  public final void setPosition(double x, double y)
  {
    drawableObject.setPosition(x, y);
  }
  
  // ---------------------------------------------------------------------------
  
  public final String getObjectID()
  {
    return drawableObject.getObjectID();
  }
  
  // ---------------------------------------------------------------------------
  
  public final DrawableObject getDrawnObject()
  {
    return drawableObject;
  }
  
  // ---------------------------------------------------------------------------
  
  public final String getColor()
  {
    return drawableObject.getColor();
  }
  
  // ---------------------------------------------------------------------------
  
  public final void setColor(String color)
  {
    drawableObject.setColor(color);
  }
  
  // ---------------------------------------------------------------------------
}
