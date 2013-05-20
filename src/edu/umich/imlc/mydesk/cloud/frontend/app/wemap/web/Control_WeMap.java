package edu.umich.imlc.mydesk.cloud.frontend.app.wemap.web;

import edu.umich.imlc.mydesk.cloud.frontend.BasicPresenterClass;
import edu.umich.imlc.mydesk.cloud.frontend.BasicView;
import edu.umich.imlc.mydesk.cloud.frontend.app.wemap.WeMapFile_GWT;
import edu.umich.imlc.mydesk.cloud.frontend.canvas.Pan_Zoom_Canvas;
import edu.umich.imlc.mydesk.cloud.frontend.wesketch.WeSketchFile_GWT;

public class Control_WeMap extends BasicPresenterClass
{
  static final String FILE_NAME = "dummyFile";
  
  static final int WEMAP_WIDTH = 400;
  static final int WEMAP_HEIGHT = 400;
  static final double WEMAP_SCALE = 0.1;
  
  static final int WESKETCH_WIDTH = 0;
  static final int WESKETCH_HEIGHT = 0;  
  static final double WESKETCH_SCALE = 0.0;
  
  WeMapFile_GWT mapfile = null;
  WeSketchFile_GWT skfile = null;
  
  Pan_Zoom_Canvas mapcanvas = null;
  Pan_Zoom_Canvas skcanvas = null;
  
  // ---------------------------------------------------------------------------
  // ---------------------------------------------------------------------------
  
  public Control_WeMap()
  {
    loadWeMapFile();
  }  

  // ---------------------------------------------------------------------------
  
  public void doCanvasTest()
  {
    mapcanvas.drawFile(null);
  }

  // ---------------------------------------------------------------------------
  
  public void doWeMapTest()
  {    
    assert(mapfile != null);
    mapcanvas.drawFile(mapfile);
  }
  
  // ---------------------------------------------------------------------------
  
  public void doSketchTest()
  {
    assert(skfile != null);
    skcanvas.drawFile(skfile);
  }
  
  // ---------------------------------------------------------------------------
  
  @Override
  public BasicView getView()
  {
    return mapcanvas;
  }
  
  // ---------------------------------------------------------------------------
  
  public void loadSketchFile()
  {
    if(skcanvas == null)
      skcanvas = new Pan_Zoom_Canvas(WESKETCH_WIDTH, WESKETCH_HEIGHT, WESKETCH_SCALE);
      
    if(skfile == null)
      skfile = WeSketchFile_GWT.makeDummyFile(FILE_NAME);
  }
  
  // ---------------------------------------------------------------------------
  
  public void loadWeMapFile()
  {
    if(mapcanvas == null)
      mapcanvas = new Pan_Zoom_Canvas(WEMAP_WIDTH, WEMAP_HEIGHT, WEMAP_SCALE);
    
    if(mapfile == null)
      mapfile = WeMapFile_GWT.makeDummyFile(FILE_NAME);
  }
  
  // ---------------------------------------------------------------------------
}
