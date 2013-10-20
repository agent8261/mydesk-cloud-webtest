package edu.umich.imlc.mydesk.cloud.frontend.app.wemap;

import com.emitrom.lienzo.client.core.event.AbstractNodeMouseEvent;
import com.emitrom.lienzo.client.core.event.NodeDragEndEvent;
import com.emitrom.lienzo.client.core.event.NodeDragEndHandler;
import com.emitrom.lienzo.client.core.event.NodeDragStartEvent;
import com.emitrom.lienzo.client.core.event.NodeDragStartHandler;
import com.emitrom.lienzo.client.core.event.NodeMouseEnterEvent;
import com.emitrom.lienzo.client.core.event.NodeMouseEnterHandler;
import com.emitrom.lienzo.client.core.event.NodeMouseExitEvent;
import com.emitrom.lienzo.client.core.event.NodeMouseExitHandler;
import com.emitrom.lienzo.client.core.shape.GridLayer;
import com.emitrom.lienzo.client.core.shape.IPrimitive;
import com.emitrom.lienzo.client.core.shape.Line;
import com.emitrom.lienzo.client.core.shape.Node;
import com.emitrom.lienzo.client.core.shape.Shape;
import com.emitrom.lienzo.client.core.types.Point2D;
import com.emitrom.lienzo.client.core.types.Transform;
import com.emitrom.lienzo.shared.core.types.ColorName;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.MouseEvent;
import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiFactory;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.ResizeComposite;
import com.google.gwt.user.client.ui.SimpleLayoutPanel;

import edu.umich.imlc.mydesk.cloud.frontend.BasicPresenter;
import edu.umich.imlc.mydesk.cloud.frontend.PresentersView;
import edu.umich.imlc.mydesk.cloud.frontend.app.drawables.LienzoLayer;
import edu.umich.imlc.mydesk.cloud.frontend.app.obj.GWT_Node;

/**
 * Responsible for handling or delegating all user interaction
 */
public class UIController extends ResizeComposite 
  implements PresentersView, NodeDragEndHandler, NodeDragStartHandler,
  NodeMouseEnterHandler, NodeMouseExitHandler
{
  private static ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);
  
  @UiTemplate("UIView.ui.xml")
  interface ViewUiBinder extends UiBinder<SimpleLayoutPanel, UIController> {}

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
  
  @UiField
  CanvasPanel canvas;
  
  @UiField(provided=true)
  FlowPanel canvasDiv = new FlowPanel();
  
  @UiField(provided=true)
  RadioButton rbOvalAdd = new RadioButton(MODE_SELECT_GROUP, "Oval"),
              rbEdgeAdd = new RadioButton(MODE_SELECT_GROUP, "Edge");
  
  // ---------------------------------------------------------------------------
  
  Line line1 = null, line2 = null;
  GridLayer gridLayer = null;
  LienzoLayer baseLayer = null;
  App_WeMap presenter = null;
  
  Point2D center = new Point2D(0,0);
  ModeType_e modeType = ModeType_e.NONE;
  
  final NodeNotePanel nodeNotePnl = new NodeNotePanel();
  final EditorOptionHandler editOptionHandler = new EditorOptionHandler();
  final EditNodeDialog nodeDialogBox = new EditNodeDialog(editOptionHandler);  
  
  String objID = null;
  double startX, startY, endX, endY;
  
  // ---------------------------------------------------------------------------
  // ---------------------------------------------------------------------------
  
  public UIController()
  {
    canvasDiv.setWidth(Integer.toString(CANVAS_WIDTH) + "px");
    canvasDiv.setHeight(Integer.toString(CANVAS_HEIGHT) + "px");
    initWidget(uiBinder.createAndBindUi(this));    
    initModeSelectionPanel();    
    corePanel.getElement().getStyle().setBackgroundColor("white");
    doInitDraw();
  }
  
  // ---------------------------------------------------------------------------
  
  @UiFactory
  CanvasPanel makeCanvas()
  {
    return new CanvasPanel(CANVAS_WIDTH, CANVAS_HEIGHT, this);
  }
  
  // ---------------------------------------------------------------------------
  
  void initModeSelectionPanel()
  {
    setMode(ModeType_e.OVAL_ADD);
    rbOvalAdd.setValue(true);
    rbEdgeAdd.setValue(false);
    rbEdgeAdd.setEnabled(false);
  }
  
  // ---------------------------------------------------------------------------
  
  public void doInitDraw()
  {
    baseLayer = new LienzoLayer();
    baseLayer.setNodeDragStartHandler(this);
    baseLayer.setNodeDragEndHandler(this);
    baseLayer.setNodeMouseEnterHandler(this);
    baseLayer.setNodeMouseExitHandler(this);
    canvas.add(baseLayer);
    
    line1 = new Line(0, 0, 0, 0).setStrokeColor(ColorName.BLUE)
        .setStrokeWidth(LINE_WIDTH);
    line2 = new Line(0, 0, 0, 0).setStrokeColor(ColorName.GREEN)
        .setStrokeWidth(LINE_WIDTH).setDashArray(2, 2);
    gridLayer = new GridLayer(200, line1, 50, line2);
    canvas.setBackgroundLayer(gridLayer);    
    baseLayer.draw();
  }
  
  // ---------------------------------------------------------------------------
  
  void checkPresenter()
  {
    if(presenter == null)
      throw new IllegalStateException();
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
    presenter.setDrawingSurface(baseLayer);
    updateUndoRedoButtons();
  }
  
  // ---------------------------------------------------------------------------
  
  void showNewNodeEditor(int x, int y)
  {
    Transform local = canvas.getViewport().getAbsoluteTransform();
    Transform global = local.getInverse();
    center.setX(x).setY(y);
    global.transform(center, center);
    nodeDialogBox.show();
  }
  
  // ---------------------------------------------------------------------------
  
  void showNodeEditor(String objID)
  {
    GWT_Node node = presenter.getNode(objID);
    if(node == null)
      throw new IllegalStateException();
    String color = node.getColor();
    String note = node.getNote();
    String title = node.getTitle();
    nodeDialogBox.show(objID, title, note, color);
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
  
  void setMode(ModeType_e mode)
  {
    assert(canvas != null);
    modeType = mode;
    switch(mode)
    {
      case OVAL_ADD:
        break;
      case EDGE_ADD:
        break;
      default: break;
    }
  }
  
  // ---------------------------------------------------------------------------
  
  @UiHandler({"rbOvalAdd", "rbEdgeAdd"})
  void doModeSelection(ClickEvent event)
  {
    Object src = event.getSource();
    if(src == rbEdgeAdd)
    {
      setMode(ModeType_e.EDGE_ADD);
    }
    else if(src == rbOvalAdd)
    {
      setMode(ModeType_e.OVAL_ADD);
    }
  }
  
  // -------------------------------------------------------------------------
  
  boolean doRightMouseButtonAction(AbstractNodeMouseEvent
      <? extends MouseEvent<?>, ? extends EventHandler> event)
  {
    if(!event.isButtonRight())
      return false;
    int x = event.getX(), y = event.getY();
    Shape<?> s = canvas.findShapeAtPoint(x, y);
    switch(modeType)
    {
      default: break;
      case OVAL_ADD:
        break;
      case EDGE_ADD:
        break;
    }
    if(s == null)
      showNewNodeEditor(x, y);
    else
    {
      String o = ((Node<?>) s).getID();
      if(o == null)
        throw new IllegalStateException();
      showNodeEditor(o);
    }
    return true;
  }
  
  // ---------------------------------------------------------------------------
  
  public void onNodeDragStart(NodeDragStartEvent event)
  {
    if(objID != null)
      throw new IllegalStateException();
    IPrimitive<?> node = event.getDragContext().getNode();
    objID = ((Node<?>) node).getID();
    startX = node.getX();
    startY = node.getY();
    nodeNotePnl.hide();
  }

  // ---------------------------------------------------------------------------
  
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
    updateUndoRedoButtons();
    objID = null;      
  }

  // ---------------------------------------------------------------------------
  
  public void onNodeMouseExit(NodeMouseExitEvent event)
  {
    checkPresenter();
    nodeNotePnl.hide();
  }

  // ---------------------------------------------------------------------------
  
  public void onNodeMouseEnter(NodeMouseEnterEvent event)
  {
    checkPresenter();
    int x = event.getX(), y = event.getY();
    Shape<?> s = canvas.findShapeAtPoint(x, y);
    if(s == null)
      throw new IllegalStateException();
    
    String o = ((Node<?>) s).getID();
    if(o == null)
      throw new IllegalStateException();
    String note = presenter.getNote(o);
    nodeNotePnl.setNote(note);
    nodeNotePnl.show(x + canvas.getAbsoluteLeft(), y + canvas.getAbsoluteTop());
  }
  
  // ===========================================================================
  // ===========================================================================
  
  static enum ModeType_e
  {
    NONE, OVAL_ADD, EDGE_ADD;
  }

  // ===========================================================================
  // ===========================================================================
  
  class EditorOptionHandler implements EditNodeDialog.OptionHandler
  {
    @Override
    public void editNode(String objID, String title, String note, String color)
    {
      checkPresenter();
      presenter.editNode(objID, title, note, color);
      closeDialogBox();
    }
    
    @Override
    public void createNewNode(String title, String note, String color)
    {
      checkPresenter();
      presenter.createNewNode(center.getX(), center.getY(), title, note, color);
      closeDialogBox();
    }

    @Override
    public void onCancel()
    {
      nodeDialogBox.hide();
    }
    
    void closeDialogBox()
    {
      baseLayer.draw();
      updateUndoRedoButtons();
      nodeDialogBox.hide();
    }
  }
    
  // ---------------------------------------------------------------------------
}
