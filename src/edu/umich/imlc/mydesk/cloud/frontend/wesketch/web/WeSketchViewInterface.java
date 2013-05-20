package edu.umich.imlc.mydesk.cloud.frontend.wesketch.web;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.Label;

import edu.umich.imlc.mydesk.cloud.frontend.BasicView;

interface WeSketchViewInterface extends BasicView
{
  HasClickHandlers getBackButton();

  HasClickHandlers getCancelButton();
  
  Button getPreviousSlideButton();
  
  Button getNextSlideButton();

  HasValue<String> getFileNameBox();

  Context2d getFrontBufferContext();

  Context2d getBackBufferContext();

  Canvas getFrontCanvas();

  Canvas getBackCanvas();
  
  Label getTitleLabel();
  
  Label getCurrentSlideLabel();

}
