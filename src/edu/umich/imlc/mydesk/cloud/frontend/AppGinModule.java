package edu.umich.imlc.mydesk.cloud.frontend;

import javax.inject.Singleton;

import com.google.gwt.inject.client.AbstractGinModule;

import edu.umich.imlc.mydesk.cloud.frontend.app.drawables.DrawableObjectFactory;
import edu.umich.imlc.mydesk.cloud.frontend.app.drawables.LienzoObjectFactory;


/*
 * Used in dependency injection. 
 * Binds an interface to some implementation   
 */
public class AppGinModule extends AbstractGinModule
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
