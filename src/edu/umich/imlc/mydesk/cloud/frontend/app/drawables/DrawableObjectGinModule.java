package edu.umich.imlc.mydesk.cloud.frontend.app.drawables;

import javax.inject.Singleton;

import com.google.gwt.inject.client.AbstractGinModule;


/*
 * Used in dependency injection. 
 * Binds the interface for DrawableObjectFactory to it's implementation   
 */
public class DrawableObjectGinModule extends AbstractGinModule
{
  // ---------------------------------------------------------------------------
  // ---------------------------------------------------------------------------
  @Override
  protected void configure()
  {
    bind(DrawableObjectFactory.class)
      .to(LienzoObjectFactory.class).in(Singleton.class);
  }
  
  // ---------------------------------------------------------------------------
}
