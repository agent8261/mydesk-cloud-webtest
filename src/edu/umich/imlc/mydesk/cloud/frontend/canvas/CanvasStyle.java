package edu.umich.imlc.mydesk.cloud.frontend.canvas;

import com.google.gwt.resources.client.CssResource;

public interface CanvasStyle extends CssResource
{
  @ClassName("ctrl-Main")
  String ctrlMain();
  
  @ClassName("ctrl-West")
  String ctrlWest();  
  
  @ClassName("outlineOn")
  String outlineOn();
  
  @ClassName("outlineOff")
  String outlineOff();

  @ClassName("alignCenter")
  String alignCenter();
  
  @ClassName("WeSketch-SlideCtrl")
  String WeSketchSlideCtrl();
  
  @ClassName("pzCanvas-border")
  String pzCanvasBorder();

  @ClassName("pzCanvas-title")
  String pzCanvasTitle();

  @ClassName("pzCanvas-header")
  String pzCanvasHeader();

  @ClassName("pzCanvas-context")
  String pzCanvasContext();
  
  @ClassName("contentPane")
  String contentPane();
  
  @ClassName("hiddenElement")
  String hiddenElement();
}
