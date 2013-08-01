package edu.umich.imlc.mydesk.cloud.frontend.app.obj;

import com.google.gwt.core.shared.GWT;

import edu.umich.imlc.mydesk.cloud.frontend.AppGinInjector;
import edu.umich.imlc.mydesk.cloud.frontend.app.drawables.DrawableObject;
import edu.umich.imlc.mydesk.cloud.frontend.app.drawables.DrawableObjectFactory;


public class GWT_Node
{
  // Allows for on demand dependency injection
  protected static final AppGinInjector appInjector = GWT.create(AppGinInjector.class);
  
  protected final DrawableObjectFactory factory;
  
  DrawableObject drawableObject = null;
  String title = null;
  String note = null;
  
  // ---------------------------------------------------------------------------
  // ---------------------------------------------------------------------------
  
  public GWT_Node()
  {
    this(null, null);
  }
  
  // ---------------------------------------------------------------------------
  
  public GWT_Node(String title, String note)
  {
    factory = appInjector.getDrawableObjectFactory();
    this.title = title; this.note = note;
  }
  
  // ---------------------------------------------------------------------------
  
  public final String getObjectID()
  {
    checkDrawnObject();
    return drawableObject.getObjectID();
  }
  
  // ---------------------------------------------------------------------------
  
  public final DrawableObject getDrawnObject()
  {
    checkDrawnObject();
    return drawableObject;
  }
  
  // ---------------------------------------------------------------------------
  
  public final void setPosition(double x, double y)
  {
    checkDrawnObject();
    drawableObject.setPosition(x, y);
  }
  
  // ---------------------------------------------------------------------------
  
  protected final void setDrawnObject(DrawableObject drawableObject)
  {
    this.drawableObject = drawableObject;
    checkDrawnObject();
  }
  
  
  // ---------------------------------------------------------------------------
  
  protected final String getTitle()
  {
    return title;
  }

  // ---------------------------------------------------------------------------
  
  protected final void setTitle(String title)
  {
    this.title = title;
  }

  // ---------------------------------------------------------------------------
  
  protected final String getNote()
  {
    return note;
  }

  // ---------------------------------------------------------------------------
  
  protected final void setNote(String note)
  {
    this.note = note;
  }
  
  // ---------------------------------------------------------------------------
  
  protected final void checkDrawnObject()
  {
    if(drawableObject == null)
      throw new IllegalStateException();
  }
  
  // --------------------------------------------------------------------------- 
}
