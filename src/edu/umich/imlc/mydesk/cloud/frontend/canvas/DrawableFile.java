package edu.umich.imlc.mydesk.cloud.frontend.canvas;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.user.client.rpc.IsSerializable;

public interface DrawableFile extends IsSerializable
{
  public void draw(Context2d context);
}
