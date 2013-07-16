package edu.umich.imlc.mydesk.cloud.frontend.app.web;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;

public interface WeMapDataCache extends ClientBundle
{
  public WeMapDataCache IMPL = GWT.create(WeMapDataCache.class);
  
  @CssResource.NotStrict
  @Source("edu/umich/imlc/mydesk/cloud/frontend/app/wemap/res/wemap.css")
  WeMapCssStyle weMapStyle();
  
  // ---------------------------------------------------------------------------
  // ---------------------------------------------------------------------------
  
  public static interface WeMapCssStyle extends CssResource
  {
    @ClassName("node-dialog-selected-color")
    String selectedColorPanel();
  }
  
  // ---------------------------------------------------------------------------
}
