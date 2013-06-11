package edu.umich.imlc.mydesk.cloud.frontend.canvas;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;

public interface DataCache extends ClientBundle
{
  public DataCache IMPL = (DataCache) GWT.create(DataCache.class);

  @Source("edu/umich/imlc/mydesk/cloud/frontend/canvas/res/CanvasStyle.css")
  @CssResource.NotStrict
  CanvasStyle canvasStyle();

}
