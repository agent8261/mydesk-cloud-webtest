package edu.umich.imlc.mydesk.cloud.frontend.app.web;

import com.emitrom.lienzo.client.core.event.NodeMouseUpEvent;
import com.emitrom.lienzo.client.core.mediator.IMediator;
import com.emitrom.lienzo.client.core.mediator.MousePanMediator;
import com.emitrom.lienzo.client.core.mediator.MouseWheelZoomMediator;
import com.emitrom.lienzo.client.core.shape.GridLayer;
import com.emitrom.lienzo.client.core.shape.Line;
import com.emitrom.lienzo.client.core.shape.Text;
import com.emitrom.lienzo.client.core.shape.Viewport;
import com.emitrom.lienzo.shared.core.types.ColorName;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.ResizeComposite;
import com.google.gwt.user.client.ui.SimpleLayoutPanel;

import edu.umich.imlc.mydesk.cloud.frontend.BasicView;
import edu.umich.imlc.mydesk.cloud.frontend.app.drawables.CanvasPanel;
import edu.umich.imlc.mydesk.cloud.frontend.app.drawables.LienzoLayer;
import edu.umich.imlc.mydesk.cloud.frontend.app.drawables.LieznoAppView;
import edu.umich.imlc.mydesk.cloud.frontend.app.obj.GWT_Oval;

/**
 * 
 */
public class AppView_WeMap extends ResizeComposite 
  implements BasicView
{
  static final int CANVAS_WIDTH = 400;
  static final int CANVAS_HEIGHT = 400;
  static final int WORLD_WIDTH = 4000;
  static final int WORLD_HEIGHT = 4000;
  static final int LINE_WIDTH = 3;
  static final String MODE_SELECT_GROUP = "Mode Selection";
  
  SimpleLayoutPanel corePanel = new SimpleLayoutPanel();
  FlowPanel contentPanel = new FlowPanel();
  FlowPanel canvasDiv = new FlowPanel();
  HorizontalPanel modeSelectionPanel = new HorizontalPanel();  
  CanvasPanel canvas = null;
  
  InlineLabel ilblMode = new InlineLabel("Mode: ");
  RadioButton rbAdd = new RadioButton(MODE_SELECT_GROUP, "Add");
  RadioButton rbDelete = new RadioButton(MODE_SELECT_GROUP, "Delete");
  RadioButton rbMove = new RadioButton(MODE_SELECT_GROUP, "Move");
  RadioButton rbSelect = new RadioButton(MODE_SELECT_GROUP, "Select");
  
  Text text = null;
  Line line1 = null;
  Line line2 = null;
  GridLayer gridLayer = null;
  LienzoLayer baseLayer = null;
  
  ModeType_e modeType = ModeType_e.NONE;
  EditNodeDialog nodeDialogBox = null;
  
  LieznoAppView appView = LieznoAppView.getInstance();
  int centerX = 0, centerY = 0;
  GWT_Oval node;
  GWT_Oval ulNode, uRNode;
  
  LienzoMediator mediator = null;
  EditorOptionHandler editOptionHandler = new EditorOptionHandler();
  RadioBtnClickHandler radioBtnHandler = new RadioBtnClickHandler();
  
  // ---------------------------------------------------------------------------
  // ---------------------------------------------------------------------------
  
  public AppView_WeMap()
  {
    canvasDiv.setWidth(Integer.toString(CANVAS_WIDTH) + "px");
    canvasDiv.setHeight(Integer.toString(CANVAS_HEIGHT) + "px");
    canvas = new CanvasPanel(CANVAS_WIDTH, CANVAS_HEIGHT);
    mediator = new LienzoMediator();    
    initModeSelectionPanel();
    
    canvasDiv.add(canvas);
    contentPanel.add(canvasDiv);
    corePanel.add(contentPanel);
    corePanel.getElement().getStyle().setBackgroundColor("white");
    initWidget(corePanel);    
    doInitDraw();
  }
  
  // ---------------------------------------------------------------------------
  
  void initModeSelectionPanel()
  {
    modeSelectionPanel.add(ilblMode);
    modeSelectionPanel.add(rbAdd);
    modeSelectionPanel.add(rbDelete);
    modeSelectionPanel.add(rbMove);
    
    rbAdd.addClickHandler(radioBtnHandler);
    rbDelete.addClickHandler(radioBtnHandler);
    rbMove.addClickHandler(radioBtnHandler);
    
    setMode(ModeType_e.ADD);
    rbAdd.setValue(true);
    rbMove.setValue(false);
    rbDelete.setEnabled(false);
    contentPanel.add(modeSelectionPanel);    
  }
  
  // ---------------------------------------------------------------------------
  
  public void doInitDraw()
  {
    baseLayer = new LienzoLayer();
    canvas.add(baseLayer);
    //canvas.getViewport().viewGlobalArea(0, 0, WORLD_WIDTH, WORLD_HEIGHT);
    
    line1 = new Line(0, 0, 0, 0)
      .setStrokeColor(ColorName.BLUE).setStrokeWidth(LINE_WIDTH);
    line2 = new Line(0, 0, 0, 0)
      .setStrokeColor(ColorName.GREEN).setStrokeWidth(LINE_WIDTH);
    line2.setDashArray(2, 2); // the secondary lines are dashed lines
    gridLayer = new GridLayer(200, line1, 50, line2);
    canvas.setBackgroundLayer(gridLayer);
    
    ulNode = new GWT_Oval(40, 40, null, null, "#000000", baseLayer);
    uRNode  = new GWT_Oval(185, 174, "Foo", null, "#f8b40d", baseLayer);
    baseLayer.draw();
    
    Scheduler.get().scheduleDeferred( new Scheduler.ScheduledCommand()
    {
      @Override
      public void execute()
      { showNodeDialogBox(0, 0); }
    });
    
  }
  
  // ---------------------------------------------------------------------------
  
  void showNodeDialogBox(int x, int y)
  {
    if(nodeDialogBox == null)
      nodeDialogBox = new EditNodeDialog(editOptionHandler);
    
    centerX = x;
    centerY = y;
    nodeDialogBox.show();
  } 
  
  // ---------------------------------------------------------------------------
  
  void createNewNode(String title, String note, String color)
  {
    node = new GWT_Oval(centerX, centerY, title, note, color, baseLayer);
    baseLayer.draw();
  }
  
  // ---------------------------------------------------------------------------
  
  void setMode(ModeType_e mode)
  {
    assert(canvas != null);
    modeType = mode;
    switch(mode)
    {
      case ADD: 
        canvas.setEnableDragging(false);
        if(!rbAdd.getValue())
        { rbAdd.setValue(true); }
        break;
      case MOVE: 
        canvas.setEnableDragging(true);
        if(!rbMove.getValue())
        { rbMove.setValue(true); }
        break;
      case DELETE: break;
      default: break;
    }
  }
  
  // ===========================================================================
  // ===========================================================================
  
  static enum ModeType_e
  {
    NONE, ADD, MOVE, DELETE;
  }

  // ===========================================================================
  // ===========================================================================
  
  class RadioBtnClickHandler implements ClickHandler
  {
    @Override
    public void onClick(ClickEvent event)
    {
      Object src = event.getSource();
      if(src == rbAdd)
      {
        setMode(ModeType_e.ADD);
      }
      else if(src == rbMove)
      {
        setMode(ModeType_e.MOVE);
      }
    }
  }
  
  // ===========================================================================
  // ===========================================================================
  
  class EditorOptionHandler implements EditNodeDialog.OptionHandler
  {
    @Override
    public void onConfirm(String title, String note, String color)
    {
      createNewNode(title, note, color);
      nodeDialogBox.hide();
    }

    @Override
    public void onCancel()
    {
      nodeDialogBox.hide();
    }
  }
  
  // ===========================================================================
  // ===========================================================================
  
  class LienzoMediator implements IMediator
  {
    String name;
    boolean enabled = true;
    
    MouseWheelZoomMediator zoomHndlr = new MouseWheelZoomMediator();
    MousePanMediator panHndlr = new MousePanMediator();
    
    // ---------------------------------------------------------------------------
    // ---------------------------------------------------------------------------
    
    private LienzoMediator()
    {
      Viewport vp = canvas.getViewport();
      panHndlr.setViewport(vp);
      zoomHndlr.setViewport(vp);
      canvas.pushMediator(this);
    }
    
    // ---------------------------------------------------------------------------
    
    @Override
    public boolean handleEvent(GwtEvent<?> event)
    {
      switch(modeType)
      {
        case ADD:
          if(event instanceof NodeMouseUpEvent)
          {
            NodeMouseUpEvent e = (NodeMouseUpEvent) event;
            showNodeDialogBox(e.getX(), e.getY());
            return true;
          }
          break;
        case MOVE:
        {
          if(panHndlr.handleEvent(event))
            return true;
          else if(zoomHndlr.handleEvent(event))
            return true;
          break;
        }
        case DELETE: case NONE: default: 
          break;
      }    
      return false;
    }

    // ---------------------------------------------------------------------------
    
    public void cancel()
    {}

    // ---------------------------------------------------------------------------
    
    public boolean isEnabled()
    {
      return enabled;
    }

    // ---------------------------------------------------------------------------
    
    public void setEnabled(boolean enabled)
    {
      this.enabled = enabled;
    }

    // ---------------------------------------------------------------------------
    
    public String getName()
    {
      return name;
    }

    // ---------------------------------------------------------------------------
    
    public void setName(String name)
    {
      this.name = name;
    }
  } // LienzoMediator

  // ---------------------------------------------------------------------------
}
