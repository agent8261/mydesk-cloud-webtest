package edu.umich.imlc.mydesk.cloud.frontend.app.obj;

import com.google.gwt.core.shared.GWT;

import edu.umich.imlc.mydesk.cloud.frontend.app.drawables.DrawableObjectInjector;
import edu.umich.imlc.mydesk.cloud.frontend.app.drawables.DrawableObject;
import edu.umich.imlc.mydesk.cloud.frontend.app.drawables.DrawableObjectFactory;


public class GWT_Node
{
  // Allows for on demand dependency injection
  protected static final DrawableObjectInjector appInjector = GWT.create(DrawableObjectInjector.class);
  
  protected final DrawableObjectFactory factory;
  
  DrawableObject drawableObject = null;
  String title = null;
  String note = null;
  
  // ---------------------------------------------------------------------------
  // ---------------------------------------------------------------------------
  
  public GWT_Node()
  {
    factory = appInjector.getDrawableObjectFactory();
  }
  
  // ---------------------------------------------------------------------------
  
  public GWT_Node(String title, String note)
  {
    this(); this.title = title; this.note = note;
  }
  
  // ---------------------------------------------------------------------------
  
  public final DrawableObject getDrawnObject()
  {
    return drawableObject;
  }
  
  // ---------------------------------------------------------------------------
  
  protected final void setDrawnObject(DrawableObject drawableObject)
  {
    this.drawableObject = drawableObject;
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
}
