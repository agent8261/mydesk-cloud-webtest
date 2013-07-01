package edu.umich.imlc.mydesk.cloud.frontend.app.drawables;

import com.google.gwt.inject.client.GinModules;
import com.google.gwt.inject.client.Ginjector;


/*
 * Used in dependency injection
 * Exposes the available injected methods. 
 */
@GinModules(DrawableObjectGinModule.class)
public interface DrawableObjectInjector extends Ginjector
{
  // ---------------------------------------------------------------------------
  
  public DrawableObjectFactory getDrawableObjectFactory();
  
  // ---------------------------------------------------------------------------
}
