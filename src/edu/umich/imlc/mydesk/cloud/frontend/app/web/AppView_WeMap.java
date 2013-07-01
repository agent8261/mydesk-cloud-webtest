package edu.umich.imlc.mydesk.cloud.frontend.app.web;

import com.emitrom.lienzo.client.core.shape.GridLayer;
import com.emitrom.lienzo.client.core.shape.Line;
import com.emitrom.lienzo.client.core.shape.Text;
import com.emitrom.lienzo.client.widget.LienzoPanel;
import com.emitrom.lienzo.shared.core.types.ColorName;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.ResizeComposite;
import com.google.gwt.user.client.ui.SimpleLayoutPanel;

import edu.umich.imlc.mydesk.cloud.frontend.BasicView;
import edu.umich.imlc.mydesk.cloud.frontend.app.drawables.LienzoLayer;
import edu.umich.imlc.mydesk.cloud.frontend.app.obj.GWT_Oval;

public class AppView_WeMap extends ResizeComposite 
  implements BasicView, MouseDownHandler, MouseUpHandler, ClickHandler, 
  EditNodeDialog.OptionHandler
{
  static final int CANVAS_WIDTH = 400;
  static final int CANVAS_HEIGHT = 400;
  
  static final int WORLD_WIDTH = 4000;
  static final int WORLD_HEIGHT = 4000;
  static final int MIN_NODE_WIDTH = 50;
  static final int MIN_NODE_HEIGHT = 30;
  
  static final String MODE_SELECT_GROUP = "Mode Selection";
  
  SimpleLayoutPanel corePanel = new SimpleLayoutPanel();
  FlowPanel contentPanel = new FlowPanel();
  FlowPanel canvasPanel = new FlowPanel();
  HorizontalPanel modeSelectionPanel = new HorizontalPanel();  
  LienzoPanel canvas;
  
  InlineLabel ilblMode = new InlineLabel("Mode: ");
  RadioButton rbAdd = new RadioButton(MODE_SELECT_GROUP, "Add");
  RadioButton rbDelete = new RadioButton(MODE_SELECT_GROUP, "Delete");
  RadioButton rbMove = new RadioButton(MODE_SELECT_GROUP, "Move");
  
  Text text = null;
  Line line1 = null;
  Line line2 = null;
  GridLayer gridLayer = null;
  LienzoLayer baseLayer = null;
  
  ModeType_e modeType = ModeType_e.ADD;
  EditNodeDialog nodeDialogBox = null;
  
  int centerX = 0, centerY = 0;
  GWT_Oval node;
  GWT_Oval ulNode, uRNode;
  
  // ---------------------------------------------------------------------------
  // ---------------------------------------------------------------------------
  
  public AppView_WeMap()
  {
    initModeSelectionPanel();
    canvasPanel.setWidth(Integer.toString(CANVAS_WIDTH) + "px");
    canvasPanel.setHeight(Integer.toString(CANVAS_HEIGHT) + "px");
    canvas = new LienzoPanel(CANVAS_WIDTH, CANVAS_HEIGHT);
    canvasPanel.add(canvas);
    contentPanel.add(canvasPanel);
    corePanel.add(contentPanel);
    
    canvas.addMouseUpHandler(this);
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
    
    rbAdd.setValue(true);
    rbDelete.setEnabled(false);
    rbMove.setEnabled(false);
    
    contentPanel.add(modeSelectionPanel);    
  }
  
  // ---------------------------------------------------------------------------
  
  public void doInitDraw()
  {
    line1 = new Line(0, 0, 0, 0).setStrokeColor(ColorName.BLUE);  // primary line
    line2 = new Line(0, 0, 0, 0).setStrokeColor(ColorName.GREEN); // secondary line
    line2.setDashArray(2, 2); // the secondary lines are dashed lines
    gridLayer = new GridLayer(200, line1, 50, line2);
    canvas.setBackgroundLayer(gridLayer);
    
    baseLayer = new LienzoLayer();
    canvas.add(baseLayer);
    ulNode = new GWT_Oval(100, 100, null, null, "#000000", baseLayer);
    uRNode  = new GWT_Oval(400, 400, null, null, "#f8b40d", baseLayer);
    canvas.getViewport().viewGlobalArea(0, 0, CANVAS_WIDTH, CANVAS_HEIGHT);
    //canvas.getViewport().viewLocalArea(0, 0, CANVAS_WIDTH, CANVAS_HEIGHT);
    baseLayer.draw();
  }
  
  // ---------------------------------------------------------------------------
  
  @Override
  public void onMouseUp(MouseUpEvent event)
  {
    switch(modeType)
    {
      case ADD: showNodeDialogBox(event.getX(), event.getY()); break;
      case DELETE: break;
      case MOVE: break;
      case NONE: break;
      default: break;
    }
  }

  // ---------------------------------------------------------------------------
  
  @Override
  public void onMouseDown(MouseDownEvent event)
  {
    switch(modeType)
    {
      case ADD: break;
      case DELETE: break;
      case MOVE: break;
      case NONE: break;
      default: break;
    }
  }

  // ---------------------------------------------------------------------------
  
  @Override
  public void onClick(ClickEvent event)
  {
    Object src = event.getSource();
    if(src == rbAdd)
    {
      modeType = ModeType_e.ADD;
    }
  }
  
  // ---------------------------------------------------------------------------
  
  @Override
  public void onConfirm(String title, String note, String color)
  { 
    createNewNode(title, note, color);
    nodeDialogBox.hide();
  }
    
  // ---------------------------------------------------------------------------
  
  @Override
  public void onCancel()
  {
    nodeDialogBox.hide();
  }
  
  // ---------------------------------------------------------------------------
  
  void showNodeDialogBox(int x, int y)
  {
    if(nodeDialogBox == null)
      nodeDialogBox = new EditNodeDialog(this);
    
    centerX = x;
    centerY = y;
    nodeDialogBox.show();
  } 
  
  // ---------------------------------------------------------------------------
  
  void createNewNode(String title, String note, String color)
  {
    node = new GWT_Oval(centerX, centerY, title, note, "#000000", baseLayer);
    baseLayer.draw();
  }
  
  // ---------------------------------------------------------------------------
  // ---------------------------------------------------------------------------
  
  static enum ModeType_e
  {
    NONE, ADD, MOVE, DELETE;
  }

  // ---------------------------------------------------------------------------
}
