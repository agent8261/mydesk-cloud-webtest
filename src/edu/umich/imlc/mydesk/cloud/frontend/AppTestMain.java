package edu.umich.imlc.mydesk.cloud.frontend;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.SimpleLayoutPanel;

import edu.umich.imlc.mydesk.cloud.frontend.app.wemap.App_WeMap;

public class AppTestMain extends BasicPresenterClass 
  implements EntryPoint, ResizeHandler
{
  static final String FILE_NAME = "dummyFile";
  App_WeMap ctrlWeMap = null;
  FlowView corePanel = new FlowView();
  
  // ---------------------------------------------------------------------------
  // ---------------------------------------------------------------------------
  
  @Override
  public void onResize(ResizeEvent event)
  { }

  // ---------------------------------------------------------------------------
  @Override
  public void onModuleLoad()
  {
    RootLayoutPanel.get().add(corePanel);
    switchToWeMapApp();
  }
  
  // ---------------------------------------------------------------------------
  
  @Override
  public BasicView getView()
  {
    return corePanel;
  }
  
  // ---------------------------------------------------------------------------
  
  void switchToWeMapApp()
  {
    if(ctrlWeMap == null)
    { ctrlWeMap = new App_WeMap(); }
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
}
