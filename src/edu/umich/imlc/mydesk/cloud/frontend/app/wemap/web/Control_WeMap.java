package edu.umich.imlc.mydesk.cloud.frontend.app.wemap.web;

import com.google.gwt.user.client.ui.FlowPanel;

import edu.umich.imlc.mydesk.cloud.frontend.BasicPresenterClass;
import edu.umich.imlc.mydesk.cloud.frontend.BasicView;
import edu.umich.imlc.mydesk.cloud.frontend.app.wemap.WeMapFile_GWT;
import edu.umich.imlc.mydesk.cloud.frontend.app.wesketch.WeSketchFile_GWT;
import edu.umich.imlc.mydesk.cloud.frontend.app.wesketch.web.WeSketchView;
import edu.umich.imlc.mydesk.cloud.frontend.canvas.CanvasStyle;
import edu.umich.imlc.mydesk.cloud.frontend.canvas.DataCache;

public class Control_WeMap extends BasicPresenterClass
{
  static final String FILE_NAME = "dummyFile";
  
  WeMapFile_GWT mapfile = null;
  WeSketchFile_GWT skfile = null;
  
  WeMapView mapView = null;
  WeSketchView skView = null;
    
  FlowView corePanel = new FlowView();
  
  // ---------------------------------------------------------------------------
  // ---------------------------------------------------------------------------
  
  public Control_WeMap()
  {
    CanvasStyle css = DataCache.IMPL.canvasStyle();
    corePanel.setStyleName(css.outlineOff());
    mapView = new WeMapView();
    switchToMap();
  }

  // ---------------------------------------------------------------------------
  
  public void doCanvasTest()
  {
    switchToMap();
    mapView.drawFile(null);
  }

  // ---------------------------------------------------------------------------
  
  public void doWeMapTest()
  {    
    assert(mapfile != null);
    switchToMap();
    mapView.drawFile(mapfile);
  }
  
  // ---------------------------------------------------------------------------
  
  public void doSketchTest()
  {
    assert(skfile != null);
    switchToSketch();
    skView.drawFile(skfile);
  }
  
  // ---------------------------------------------------------------------------
  
  @Override
  public BasicView getView()
  {
    return corePanel;
  }
  
  // ---------------------------------------------------------------------------
  
  public void loadSketchFile()
  {
    if(skView == null)
      skView = new WeSketchView();
      
    if(skfile == null)
      skfile = WeSketchFile_GWT.makeDummyFile(FILE_NAME);
  }
  
  // ---------------------------------------------------------------------------
  
  public void loadWeMapFile()
  {
    if(mapView == null)
      mapView = new WeMapView();
    
    if(mapfile == null)
    {
      mapfile = WeMapFile_GWT.makeDummyFile(FILE_NAME);
      mapfile.setEdgeFile();
    }
  }
  
  // ---------------------------------------------------------------------------
  
  void switchToMap()
  {
    corePanel.clear();
    corePanel.add(mapView.asWidget());
  }
  
  // ---------------------------------------------------------------------------
  
  void switchToSketch()
  {
    corePanel.clear();
    corePanel.add(skView.asWidget());
  }
  
  // ---------------------------------------------------------------------------
  // ---------------------------------------------------------------------------
  
  public static class FlowView extends FlowPanel implements BasicView
  {
    
  }
  
  // ---------------------------------------------------------------------------
}
