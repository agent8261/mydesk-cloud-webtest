package edu.umich.imlc.mydesk.cloud.frontend.app.obj;

import com.google.gwt.core.shared.GWT;

import edu.umich.imlc.mydesk.cloud.frontend.AppGinInjector;
import edu.umich.imlc.mydesk.cloud.frontend.app.drawables.DrawableObject;
import edu.umich.imlc.mydesk.cloud.frontend.app.drawables.DrawableObjectFactory;


public class GWT_Node
{
  // Allows for on demand dependency injection
  protected static final AppGinInjector appInjector = GWT.create(AppGinInjector.class);
  
  protected static final DrawableObjectFactory 
    factory = appInjector.getDrawableObjectFactory();
  
  final DrawableObject drawableObject;
  String note = null;
  
  // ---------------------------------------------------------------------------
  // ---------------------------------------------------------------------------
  
  protected GWT_Node(DrawableObject obj, String note)
  {
    if(obj == null)
      throw new IllegalArgumentException();
    drawableObject = obj;
    this.note = note;
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
  
  public final void setPosition(double x, double y)
  {
    drawableObject.setPosition(x, y);
  }
  
  // ---------------------------------------------------------------------------
  
  public final String getTitle()
  {
    return drawableObject.getTitle();
  }

  // ---------------------------------------------------------------------------
  
  public final void setTitle(String title)
  {
    drawableObject.setTitle(title);
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
  
  public final String getNote()
  {
    return note;
  }

  // ---------------------------------------------------------------------------
  
  public final void setNote(String note)
  {
    this.note = note;
  }
  
  // --------------------------------------------------------------------------- 
}
