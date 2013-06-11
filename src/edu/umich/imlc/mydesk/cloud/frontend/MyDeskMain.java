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
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.VerticalPanel;

import edu.umich.imlc.mydesk.cloud.frontend.app.wemap.web.Control_WeMap;
import edu.umich.imlc.mydesk.cloud.frontend.canvas.CanvasStyle;
import edu.umich.imlc.mydesk.cloud.frontend.canvas.DataCache;

public class MyDeskMain implements EntryPoint, ResizeHandler
{
  static 
  {
    DataCache.IMPL.canvasStyle().ensureInjected();
  }
  
  static final String BASIC_TYPE = "Basic";
  static final String WEMAP_TYPE = "WeMap";
  static final String WESKETCH_TYPE = "WeSketch";
  
  Label idLabel = new Label("Input file ID:");
  Label typeLabel = new Label("Select Type:");  
  TextArea fileIDTextInput = new TextArea();
  ListBox typeBox = new ListBox();
  Button btnLoadFile = new Button("Test File");
  
  HorizontalPanel typeSelectionPanel = new HorizontalPanel();
  VerticalPanel  westPanel = new VerticalPanel();
  DockLayoutPanel corePanel = new DockLayoutPanel(Unit.PX);
  FlowPanel westContentPanel = new FlowPanel();
  
  Control_WeMap ctrl = null;
  
  int windowWidth = 0;
  int windowHeight = 0;
  
  // ---------------------------------------------------------------------------
  // ---------------------------------------------------------------------------
  
  @Override
  public void onModuleLoad()
  {    
    CanvasStyle css = DataCache.IMPL.canvasStyle();
    corePanel.setStyleName(css.ctrlMain());
    corePanel.addStyleName(css.outlineOn());
    
    RootLayoutPanel.get().add(corePanel);
    typeBox.addItem(BASIC_TYPE, BASIC_TYPE);
    typeBox.addItem(WEMAP_TYPE, WEMAP_TYPE);
    typeBox.addItem(WESKETCH_TYPE, WESKETCH_TYPE);
    typeBox.setVisibleItemCount(1);
    
    westPanel.setStyleName(css.ctrlWest());
    westPanel.addStyleName(css.outlineOff());
    fileIDTextInput.setCharacterWidth(20);
    fileIDTextInput.setVisibleLines(2);

    btnLoadFile.addClickHandler(new SwitchTestHandler());
    typeSelectionPanel.add(typeBox);
    typeSelectionPanel.add(btnLoadFile);
    
    westPanel.add(idLabel);
    westPanel.add(fileIDTextInput);
    westPanel.add(typeLabel);        
    westPanel.add(typeSelectionPanel);    
    corePanel.addWest(westPanel, 300);
    
    ctrl = new Control_WeMap();        
    doBasicTest();
    //doSketchTest();    
    corePanel.add(ctrl.getView().asWidget());
  }
  
  // ---------------------------------------------------------------------------
  
  void doBasicTest()
  {
    ctrl.doCanvasTest();
  }
  
  // ---------------------------------------------------------------------------
  
  void doMapTest()
  {
    ctrl.loadWeMapFile();
    ctrl.doWeMapTest();
  }
  
  // ---------------------------------------------------------------------------
  
  void doSketchTest()
  {
    ctrl.loadSketchFile();
    ctrl.doSketchTest();
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
        default: break;
      }
    }
  }
  // ---------------------------------------------------------------------------
}
