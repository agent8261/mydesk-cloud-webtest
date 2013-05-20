package edu.umich.imlc.mydesk.cloud.frontend;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootLayoutPanel;

import edu.umich.imlc.mydesk.cloud.frontend.app.wemap.web.Control_WeMap;

public class MyDeskMain implements EntryPoint
{
  Control_WeMap ctrl = null;
  
  @Override
  public void onModuleLoad()
  {
    ctrl = new Control_WeMap();
    ctrl.go(RootLayoutPanel.get());
    //doBasicTest();
    doMapTest();
  }
  
  void doBasicTest()
  {
    ctrl.doCanvasTest();
  }
  
  void doMapTest()
  {
    ctrl.loadWeMapFile();
    ctrl.doWeMapTest();
  }
  
  void doSketchTest()
  {
    ctrl.loadSketchFile();
    ctrl.doSketchTest();
  }
  
}
