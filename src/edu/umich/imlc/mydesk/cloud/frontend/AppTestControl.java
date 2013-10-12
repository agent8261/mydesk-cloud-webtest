package edu.umich.imlc.mydesk.cloud.frontend;

import com.google.gwt.user.client.ui.SimpleLayoutPanel;

import edu.umich.imlc.mydesk.cloud.frontend.BasicView;
import edu.umich.imlc.mydesk.cloud.frontend.app.wemap.App_WeMap;

public class AppTestControl extends BasicPresenterClass
{
  static final String FILE_NAME = "dummyFile";
  
  App_WeMap ctrlWeMap = null;
  
  FlowView corePanel = new FlowView();
  
  // ---------------------------------------------------------------------------
  // ---------------------------------------------------------------------------
  
  public AppTestControl()
  {
    ctrlWeMap = new App_WeMap();
    switchToMap();
  }

  // ---------------------------------------------------------------------------
  
  public void doCanvasTest()
  {
    switchToMap();
  }

  // ---------------------------------------------------------------------------
  
  public void doWeMapTest()
  {
    switchToMap();
  }
  
  // ---------------------------------------------------------------------------
  
  @Override
  public BasicView getView()
  {
    return corePanel;
  }
  
  // ---------------------------------------------------------------------------
  
  void switchToMap()
  {
    corePanel.clear();
    ctrlWeMap.go(corePanel);
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
