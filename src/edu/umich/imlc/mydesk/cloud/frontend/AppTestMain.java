package edu.umich.imlc.mydesk.cloud.frontend;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import edu.umich.imlc.mydesk.cloud.frontend.app.canvas.AppStyle;
import edu.umich.imlc.mydesk.cloud.frontend.app.canvas.DataCache;
import edu.umich.imlc.mydesk.cloud.frontend.app.web.AppView_WeMap;
import edu.umich.imlc.mydesk.cloud.frontend.flexi.CPicker;

public class AppTestMain implements EntryPoint, ResizeHandler
{
  static 
  {
    DataCache.IMPL.canvasStyle().ensureInjected();
  }
  
  static final String BASIC_TYPE = "Basic";
  static final String WEMAP_TYPE = "WeMap";
  static final String WESKETCH_TYPE = "WeSketch";
  static final String WEKWL_TYPE = "WeKWL";
  
  Label typeLabel = new Label("Select Type:");  
  ListBox typeBox = new ListBox();
  Button btnLoadFile = new Button("Test File");
  Button btnLogin = new Button("Login");
  Button btnLogout = new Button("Logout");
  
  HorizontalPanel typeSelectionPanel = new HorizontalPanel();
  VerticalPanel  westPanel = new VerticalPanel();
  DockLayoutPanel corePanel = new DockLayoutPanel(Unit.PX);
  FlowPanel westContentPanel = new FlowPanel();
  
  AppTestControl ctrl = null;
  
  int windowWidth = 0;
  int windowHeight = 0;
  
  AppView_WeMap appMap = null;
  
  // ---------------------------------------------------------------------------
  // ---------------------------------------------------------------------------
  
  @Override
  public void onModuleLoad()
  {
    
    AppStyle css = DataCache.IMPL.canvasStyle();
    corePanel.setStyleName(css.ctrlMain());
    corePanel.addStyleName(css.outlineOn());
    
    RootLayoutPanel.get().add(corePanel);
    typeBox.addItem(BASIC_TYPE, BASIC_TYPE);
    typeBox.addItem(WEMAP_TYPE, WEMAP_TYPE);
    typeBox.addItem(WESKETCH_TYPE, WESKETCH_TYPE);
    typeBox.addItem(WEKWL_TYPE, WEKWL_TYPE);
    typeBox.setVisibleItemCount(1);

    westPanel.setStyleName(css.ctrlWest());
    westPanel.addStyleName(css.outlineOff());
    
    //btnLoadFile.addClickHandler(new SwitchTestHandler());
    typeSelectionPanel.add(typeBox);
    typeSelectionPanel.add(btnLoadFile);
    
    westPanel.add(typeLabel);        
    westPanel.add(typeSelectionPanel);
    
    
    corePanel.addWest(westPanel, 300);
    // appMap = new AppView_WeMap();
    // corePanel.add(appMap);
    CPicker cp = new CPicker();
    corePanel.add(cp);
    //CpPanel cpPanel = new CpPanel();
    //corePanel.add(cpPanel);
    
    /*
    ctrl = new AppTestControl();        
    // doBasicTest();
    // doMapTest();
    // doSketchTest();    
    corePanel.add(ctrl.getView().asWidget());
    */
  }
  
  // ---------------------------------------------------------------------------
  
  void doBasicTest()
  {
    typeBox.setSelectedIndex(0);
    ctrl.doCanvasTest();
  }
  
  // ---------------------------------------------------------------------------
  
  void doMapTest()
  {
    typeBox.setSelectedIndex(1);
    ctrl.loadWeMapFile();
    ctrl.doWeMapTest();
  }
  
  // ---------------------------------------------------------------------------
  
  void doSketchTest()
  {
    typeBox.setSelectedIndex(2);
    ctrl.loadSketchFile();
    ctrl.doSketchTest();
  }
  
  // ---------------------------------------------------------------------------
  
  void doKWLTest()
  {
    typeBox.setSelectedIndex(3);
    ctrl.doWeKwlTest();
  }
  
  // ---------------------------------------------------------------------------

  @Override
  public void onResize(ResizeEvent event)
  {
    setSize(event.getWidth(), event.getHeight());
  }
  
  // ---------------------------------------------------------------------------
  
  void setSize(int width, int height)
  {
    windowWidth = width;
    windowHeight = height;
    RootLayoutPanel.get().setPixelSize(windowWidth, windowHeight);
  }
  
  // ---------------------------------------------------------------------------
  // ---------------------------------------------------------------------------
  
  class SwitchTestHandler implements ClickHandler
  {
    @Override
    public void onClick(ClickEvent event)
    {
      int i = typeBox.getSelectedIndex();
      switch(i)
      {
        case 0: doBasicTest(); break;
        case 1: doMapTest(); break;
        case 2: doSketchTest();break;
        case 3: doKWLTest(); break;
        default: break;
      }
    }
  }
  // ---------------------------------------------------------------------------
}
