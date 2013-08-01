package edu.umich.imlc.mydesk.cloud.frontend;

import com.google.gwt.inject.client.GinModules;
import com.google.gwt.inject.client.Ginjector;

import edu.umich.imlc.mydesk.cloud.frontend.app.drawables.DrawableObjectFactory;


/*
 * Used in dependency injection
 * Exposes the available injected methods. 
 */
@GinModules(AppGinModule.class)
public interface AppGinInjector extends Ginjector
{
  // ---------------------------------------------------------------------------
  
  public DrawableObjectFactory getDrawableObjectFactory();
  
  // ---------------------------------------------------------------------------
}
