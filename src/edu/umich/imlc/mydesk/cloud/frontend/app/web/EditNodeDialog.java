package edu.umich.imlc.mydesk.cloud.frontend.app.web;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.TextBox;

import edu.umich.imlc.mydesk.cloud.frontend.app.web.WeMapDataCache.WeMapCssStyle;
import edu.umich.imlc.mydesk.cloud.frontend.flexi.CPicker;
import edu.umich.imlc.mydesk.cloud.frontend.flexi.Color;

public class EditNodeDialog 
  implements ClickHandler, PopupPanel.PositionCallback
{
  static 
  { WeMapDataCache.IMPL.weMapStyle().ensureInjected(); }
  
  // -------------------------------------------------------------------------
  
  public static interface OptionHandler
  {
    void onConfirm(String title, String note, String color);
    void onCancel();
  }
  
  // -------------------------------------------------------------------------
  // -------------------------------------------------------------------------
  
  DialogBox dialogBox = new DialogBox();
  FlowPanel editorPanel = new FlowPanel();
  FlexTable editorTable = new FlexTable();
  
  InlineLabel ilblTitle = new InlineLabel("Title: ");
  InlineLabel ilblNote = new InlineLabel("Note: ");
  
  TextBox tboxTitle = new TextBox();
  TextBox tboxNote = new TextBox();
  Button btnAdd = new Button("Create");
  Button btnCancel = new Button("Cancel");
  Button btnSelectColor = new Button("Ok");
  Button btnCpCancel = new Button("Cancel");
  
  DisclosurePanel pnlColorPickRoot = new DisclosurePanel("Pick Color");
  FlowPanel pnlColorPick = new FlowPanel();
  FlowPanel pnlSelectedColor = new FlowPanel();
  CPicker colorPicker = new CPicker();
  
  final OptionHandler optHandler;
  
  Color.Rgb selectedColor = new Color.Rgb();
  
  // -------------------------------------------------------------------------
  // -------------------------------------------------------------------------
  
  public EditNodeDialog(OptionHandler handler)
  {
    if(handler == null)    
      throw new IllegalArgumentException();
    
    optHandler = handler;
    dialogBox.setText("Add Node");
    dialogBox.setAnimationEnabled(true);
    dialogBox.setGlassEnabled(true);
    initEditorPanel();
    dialogBox.setWidget(editorPanel);
  }
  
  // -------------------------------------------------------------------------
  
  public void show()
  {
    dialogBox.setPopupPositionAndShow(this);
  }
  
  // -------------------------------------------------------------------------
  
  public void hide()
  {
    dialogBox.hide();
  }
  
  // -------------------------------------------------------------------------
  
  @Override
  public void onClick(ClickEvent event)
  {
    Object src = event.getSource();
    if(src == btnCancel)
    {
      optHandler.onCancel();
    }
    else if(src == btnAdd)
    {
      String title = tboxTitle.getText();
      String note = tboxNote.getText();
      String color = selectedColor.toString();
      tboxTitle.setText("");
      tboxNote.setText("");
      optHandler.onConfirm(title, note, color);
    }
    else if(src == btnSelectColor)
    {
      updateSelectedColor();
    }
  }
  
  // ---------------------------------------------------------------------------
  
  @Override
  public void setPosition(int offsetWidth, int offsetHeight)
  {
    int top = (Window.getClientHeight() / 2) - 256;
    int left = (Window.getClientWidth() / 2) - 215; 
    dialogBox.setPopupPosition(left, top);
  }
  
  // -------------------------------------------------------------------------
  
  void initEditorPanel()
  {
    WeMapCssStyle css = WeMapDataCache.IMPL.weMapStyle();
    pnlSelectedColor.setStyleName(css.selectedColorPanel());
    tboxTitle.getElement().getStyle().setWidth(350, Unit.PX);
    
    pnlColorPick.add(colorPicker);
    pnlColorPick.add(btnSelectColor);
    pnlColorPick.add(btnCpCancel);
    pnlColorPickRoot.add(pnlColorPick);
    
    editorTable.setWidget(0, 0, ilblTitle);
    editorTable.setWidget(1, 0, ilblNote);
    editorTable.setWidget(2, 0, pnlSelectedColor);
    editorTable.setWidget(3, 0, pnlColorPickRoot);
    editorTable.setWidget(4, 0, btnAdd);
    editorTable.getFlexCellFormatter().setColSpan(3, 0, 2);
    
    editorTable.setWidget(0, 1, tboxTitle);
    editorTable.setWidget(1, 1, tboxNote);
    editorTable.setWidget(4, 1, btnCancel);
    editorPanel.add(editorTable);
    updateSelectedColor();
    
    btnCancel.addClickHandler(this);
    btnAdd.addClickHandler(this);
    btnSelectColor.addClickHandler(this);
  }
  
  // -------------------------------------------------------------------------
  
  void updateSelectedColor()
  {
    //selectedColor.copy(colorPicker.getColor());
    pnlSelectedColor.getElement().getStyle()
      .setBackgroundColor(selectedColor.toString());
  }

  // -------------------------------------------------------------------------
}