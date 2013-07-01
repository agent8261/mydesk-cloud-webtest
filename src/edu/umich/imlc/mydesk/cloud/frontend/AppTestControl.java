package edu.umich.imlc.mydesk.cloud.frontend;

import com.google.gwt.user.client.ui.SimpleLayoutPanel;

import edu.umich.imlc.mydesk.cloud.frontend.BasicView;
import edu.umich.imlc.mydesk.cloud.frontend.app.canvas.AppStyle;
import edu.umich.imlc.mydesk.cloud.frontend.app.canvas.DataCache;
import edu.umich.imlc.mydesk.cloud.frontend.app.wekwl.WeKWLFile_GWT;
import edu.umich.imlc.mydesk.cloud.frontend.app.wekwl.web.WeKWLView;
import edu.umich.imlc.mydesk.cloud.frontend.app.wemap.WeMapFile_GWT;
import edu.umich.imlc.mydesk.cloud.frontend.app.wemap.web.WeMapView;
import edu.umich.imlc.mydesk.cloud.frontend.app.wesketch.WeSketchFile_GWT;
import edu.umich.imlc.mydesk.cloud.frontend.app.wesketch.web.WeSketchView;

public class AppTestControl extends BasicPresenterClass
{
  static final String FILE_NAME = "dummyFile";
  
  WeMapFile_GWT mapfile = null;
  WeSketchFile_GWT skfile = null;
  WeKWLFile_GWT kwlfile = null;
  
  WeMapView mapView = null;
  WeSketchView skView = null;
  WeKWLView kwlView = null;
    
  FlowView corePanel = new FlowView();
  
  // ---------------------------------------------------------------------------
  // ---------------------------------------------------------------------------
  
  public AppTestControl()
  {
    AppStyle css = DataCache.IMPL.canvasStyle();
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
  
  public void doWeKwlTest()
  {
   if(kwlfile == null)
     kwlfile = WeKWLFile_GWT.makeDummyFile("KwlTestFile", "WeKWL", "kwlTestID", "format");
   if(kwlView == null)
   {
     kwlView = new WeKWLView();
     kwlView.loadFile(kwlfile);
   }
   switchToKwl();
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
  
  void switchToKwl()
  {
    corePanel.clear();
    corePanel.add(kwlView.asWidget());
  }
  
  // ---------------------------------------------------------------------------
  // ---------------------------------------------------------------------------
  
  public static class FlowView extends SimpleLayoutPanel implements BasicView
  {/*
    @Override
    public void onResize()
    {
    }
    */
  }
  
  // ---------------------------------------------------------------------------
}
