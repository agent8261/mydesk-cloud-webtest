package edu.umich.imlc.mydesk.cloud.frontend.canvas;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.DataResource;

import com.google.gwt.resources.client.CssResource;

public interface MyDeskResources extends ClientBundle
{
  public MyDeskResources IMPL = (MyDeskResources) GWT.create(MyDeskResources.class);
  
  @Source("res/image.jpg")
  DataResource image();

  @Source("res/CanvasStyle.css")
  @CssResource.NotStrict
  CanvasCssResource canvasCss();
}
