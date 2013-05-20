package edu.umich.imlc.mydesk.cloud.frontend.app.wemap.web;


import com.google.gwt.user.client.ui.Widget;

import edu.umich.imlc.mydesk.cloud.frontend.BasicView;
import edu.umich.imlc.mydesk.cloud.frontend.app.wemap.WeMapFile_GWT;
import edu.umich.imlc.mydesk.cloud.frontend.canvas.Pan_Zoom_Canvas;

public class WeMapView implements BasicView
{
  static final int CANVAS_WIDTH = 400;
  static final int CANVAS_HEIGHT = 400;
  
  Pan_Zoom_Canvas pzCanvas = null;
  
  // ---------------------------------------------------------------------------
  // ---------------------------------------------------------------------------
  
  public WeMapView()
  {
    pzCanvas = new Pan_Zoom_Canvas(CANVAS_WIDTH, CANVAS_HEIGHT, 0.1);
  }

  // ---------------------------------------------------------------------------
  
  public void drawFile(WeMapFile_GWT file)
  {
    pzCanvas.drawFile(file);
  }
  
  // ---------------------------------------------------------------------------
  
  @Override
  public void onResize()
  {
    pzCanvas.onResize();
  }

  // ---------------------------------------------------------------------------
  
  @Override
  public Widget asWidget()
  {
    return pzCanvas.asWidget();
  }
  
  // ---------------------------------------------------------------------------
}
