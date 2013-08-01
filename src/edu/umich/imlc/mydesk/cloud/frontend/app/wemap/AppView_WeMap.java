package edu.umich.imlc.mydesk.cloud.frontend.app.wemap;

import com.emitrom.lienzo.client.core.event.NodeDragEndEvent;
import com.emitrom.lienzo.client.core.event.NodeDragEndHandler;
import com.emitrom.lienzo.client.core.event.NodeDragStartEvent;
import com.emitrom.lienzo.client.core.event.NodeDragStartHandler;
import com.emitrom.lienzo.client.core.event.NodeMouseUpEvent;
import com.emitrom.lienzo.client.core.mediator.IMediator;
import com.emitrom.lienzo.client.core.mediator.MousePanMediator;
import com.emitrom.lienzo.client.core.mediator.MouseWheelZoomMediator;
import com.emitrom.lienzo.client.core.shape.GridLayer;
import com.emitrom.lienzo.client.core.shape.IPrimitive;
import com.emitrom.lienzo.client.core.shape.Line;
import com.emitrom.lienzo.client.core.shape.Node;
import com.emitrom.lienzo.client.core.shape.Viewport;
import com.emitrom.lienzo.shared.core.types.ColorName;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.ResizeComposite;
import com.google.gwt.user.client.ui.SimpleLayoutPanel;

import edu.umich.imlc.mydesk.cloud.frontend.BasicPresenter;
import edu.umich.imlc.mydesk.cloud.frontend.PresentersView;
import edu.umich.imlc.mydesk.cloud.frontend.app.drawables.CanvasPanel;
import edu.umich.imlc.mydesk.cloud.frontend.app.drawables.LienzoLayer;

/**
 * 
 */
public class AppView_WeMap extends ResizeComposite 
  implements PresentersView
{
  private static ViewUiBinder uiBinder = GWT
      .create(ViewUiBinder.class);
  
  interface ViewUiBinder extends UiBinder<SimpleLayoutPanel, AppView_WeMap> {}

  // ---------------------------------------------------------------------------
  // ---------------------------------------------------------------------------
  
  static final int CANVAS_WIDTH = 400;
  static final int CANVAS_HEIGHT = 400;
  static final int WORLD_WIDTH = 4000;
  static final int WORLD_HEIGHT = 4000;
  static final int LINE_WIDTH = 3;
  static final String MODE_SELECT_GROUP = "Mode Selection";
  
  @UiField
  SimpleLayoutPanel corePanel;
  
  @UiField
  HorizontalPanel modeSelectionPanel;  
  
  @UiField
  Button btnUndo, btnRedo;
  
  @UiField(provided=true)
  FlowPanel canvasDiv = new FlowPanel();
  
  @UiField(provided=true)
  CanvasPanel canvas = new CanvasPanel(CANVAS_WIDTH, CANVAS_HEIGHT);
  
  @UiField(provided=true)
  RadioButton rbAdd = new RadioButton(MODE_SELECT_GROUP, "Add"),
              rbDelete = new RadioButton(MODE_SELECT_GROUP, "Delete"),
              rbMove = new RadioButton(MODE_SELECT_GROUP, "Move"),
              rbSelect = new RadioButton(MODE_SELECT_GROUP, "Select");
  
  // ---------------------------------------------------------------------------
  
  Line line1 = null, line2 = null;
  GridLayer gridLayer = null;
  LienzoLayer baseLayer = null;
  App_WeMap presenter = null;
  
  int centerX = 0, centerY = 0;
  ModeType_e modeType = ModeType_e.NONE;
  
  final LienzoMediator mediator = new LienzoMediator();
  final EditorOptionHandler editOptionHandler = new EditorOptionHandler();
  final EditNodeDialog nodeDialogBox = new EditNodeDialog(editOptionHandler);  
  public final DragListener dragListener = new DragListener();
  
  // ---------------------------------------------------------------------------
  // ---------------------------------------------------------------------------
  
  public AppView_WeMap()
  {
    canvasDiv.setWidth(Integer.toString(CANVAS_WIDTH) + "px");
    canvasDiv.setHeight(Integer.toString(CANVAS_HEIGHT) + "px");
    initWidget(uiBinder.createAndBindUi(this));    
    initModeSelectionPanel();    
    corePanel.getElement().getStyle().setBackgroundColor("white");
    doInitDraw();
    /*    
    Scheduler.get().scheduleDeferred( new Scheduler.ScheduledCommand()
    {
      @Override
      public void execute()
      { showNodeDialogBox(0, 0); }
    });
    */
  }
  
  // ---------------------------------------------------------------------------
  
  void initModeSelectionPanel()
  {
    setMode(ModeType_e.ADD);
    rbAdd.setValue(true);
    rbMove.setValue(false);
    rbDelete.setEnabled(false);
  }
  
  // ---------------------------------------------------------------------------
  
  public void doInitDraw()
  {
    baseLayer = new LienzoLayer();
    baseLayer.setNodeDragStartHandler(dragListener);
    baseLayer.setNodeDragEndHandler(dragListener);
    canvas.add(baseLayer);
    //canvas.getViewport().viewGlobalArea(0, 0, WORLD_WIDTH, WORLD_HEIGHT);
    
    line1 = new Line(0, 0, 0, 0)
      .setStrokeColor(ColorName.BLUE).setStrokeWidth(LINE_WIDTH);
    line2 = new Line(0, 0, 0, 0)
      .setStrokeColor(ColorName.GREEN).setStrokeWidth(LINE_WIDTH);
    line2.setDashArray(2, 2); // the secondary lines are dashed lines
    gridLayer = new GridLayer(200, line1, 50, line2);
    canvas.setBackgroundLayer(gridLayer);
    
    baseLayer.draw();
  }
  
  // ---------------------------------------------------------------------------
  
  void checkPresenter()
  {
    if(presenter == null)
    {
      throw new IllegalStateException();
    }
  }
  
  // ---------------------------------------------------------------------------
  
  void updateUndoRedoButtons()
  {
    checkPresenter();
    btnRedo.setEnabled(presenter.isRedoable());
    btnUndo.setEnabled(presenter.isUndoable());
  }
  
  // ---------------------------------------------------------------------------
  
  public void setPresenter(BasicPresenter p)
  {
    presenter = (App_WeMap) p;
    updateUndoRedoButtons();
    presenter.setDrawingSurface(baseLayer);
  }
  
  // ---------------------------------------------------------------------------
  
  void showNodeDialogBox(int x, int y)
  {
    centerX = x;
    centerY = y;
    nodeDialogBox.show();
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
  
  // ---------------------------------------------------------------------------
  
  @UiHandler("btnUndo")
  void doUndo(ClickEvent event)
  {
    checkPresenter();
    presenter.undo();
    updateUndoRedoButtons();
  }
  
  // ---------------------------------------------------------------------------
  
  @UiHandler("btnRedo")
  void doRedo(ClickEvent event)
  {
    checkPresenter();
    presenter.redo();
    updateUndoRedoButtons();
  }
  
  // ---------------------------------------------------------------------------
  
  @UiHandler({"rbAdd", "rbDelete", "rbMove", "rbSelect"})
  void doModeSelection(ClickEvent event)
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
  
  // ===========================================================================
  // ===========================================================================
  
  static enum ModeType_e
  {
    NONE, ADD, MOVE, DELETE;
  }

  // ===========================================================================
  // ===========================================================================
  
  class EditorOptionHandler implements EditNodeDialog.OptionHandler
  {
    @Override
    public void onConfirm(String title, String note, String color)
    {
      checkPresenter();
      presenter.createNewNode(centerX, centerY, title, note, color);
      baseLayer.draw();
      updateUndoRedoButtons();
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
  
  class DragListener implements NodeDragEndHandler, NodeDragStartHandler
  {
    String objID = null;
    double startX, startY, endX, endY;
    
    @Override
    public void onNodeDragStart(NodeDragStartEvent event)
    {
      if(objID != null)
        throw new IllegalStateException();
      IPrimitive<?> node = event.getDragContext().getNode();
      objID = ((Node<?>) node).getID();
      startX = node.getX();
      startY = node.getY();
    }

    @Override
    public void onNodeDragEnd(NodeDragEndEvent event)
    {
      checkPresenter();
      IPrimitive<?> node = event.getDragContext().getNode();
      String EndID = ((Node<?>) node).getID();
      if((objID == null) || (EndID == null) || !objID.equals(EndID))
        throw new IllegalStateException();
      
      endX = node.getX();
      endY = node.getY();
      presenter.updateNodeMove(objID, startX, startY, endX, endY);
      objID = null;      
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
    { return enabled; }

    // ---------------------------------------------------------------------------
    
    public void setEnabled(boolean enabled)
    { this.enabled = enabled; }

    // ---------------------------------------------------------------------------
    
    public String getName()
    { return name; }

    // ---------------------------------------------------------------------------
    
    public void setName(String name)
    { this.name = name; }
  } // LienzoMediator

  // ---------------------------------------------------------------------------
}
