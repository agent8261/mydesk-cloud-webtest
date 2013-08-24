package edu.umich.imlc.mydesk.cloud.frontend.app.wemap;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;


public class EditNodeDialog extends Composite 
  implements ClickHandler, PopupPanel.PositionCallback
{
  // -------------------------------------------------------------------------
  
  public static interface OptionHandler
  {
    void editNode(String objID, String title, String note, String color);
    void createNewNode(String title, String note, String color);
    void onCancel();
  }
  
  // ---------------------------------------------------------------------------
  
  @UiTemplate("EditNodeDialog.ui.xml")
  interface EditNodeUiBinder extends UiBinder<Widget, EditNodeDialog>{}
  private static EditNodeUiBinder uiBind = GWT .create(EditNodeUiBinder.class);
  
  // ---------------------------------------------------------------------------
  
  static { WeMapDataCache.IMPL.weMapStyle().ensureInjected(); }
  
  private static final int CPICKER_TOP_OFFSET = 205;
  private static final int CPICKER_LEFT_OFFSET = 158;
  private static final String DEFAULT_COLOR = "#000000";
  static final String CREATE_TITLE = "Create a New Node";
  static final String EDIT_TITLE = "Edit a Node";
  
  // ---------------------------------------------------------------------------
  @UiField
  DivElement divCurrentColor;
  @UiField
  DisclosurePanel cpPanel;
  @UiField
  CPicker cPicker;
  @UiField
  DialogBox dialogBox;
  @UiField
  Button btnConfirm, btnCancel;
  @UiField
  TextBox tboxTitle, tboxNote;
  
  // ---------------------------------------------------------------------------
  
  final ColorPickerHandler cpHandler = new ColorPickerHandler();
  final OptionHandler optHandler;  
  String currentColor = null;
  String objID;
  
  DialogBox.Caption c;
  boolean isEditing = false;
  
  
  // ---------------------------------------------------------------------------
  
  public EditNodeDialog(OptionHandler handler)
  {
    if(handler == null)    
      throw new IllegalArgumentException();
    optHandler = handler;
    initWidget(uiBind.createAndBindUi(this));
    doCtor();
  }

  // ---------------------------------------------------------------------------
  
  public void show(String objID, String title, String note, String color)
  {
    if(!isEditing)
    {
      isEditing = true;
      dialogBox.setTitle(EDIT_TITLE);
    }
    this.objID = objID;
    tboxTitle.setText(title); tboxNote.setText(note);
    setCurrentColor(color);
    dialogBox.setPopupPositionAndShow(this);
  }
  
  // ---------------------------------------------------------------------------
  
  public void show()
  {
    if(isEditing)
    {
      isEditing = false;
      dialogBox.setTitle(CREATE_TITLE);
    }
    dialogBox.setPopupPositionAndShow(this);
  }
  
  // ---------------------------------------------------------------------------
  
  public void hide()
  {
    dialogBox.hide();
  }
  
  // ---------------------------------------------------------------------------

  @Override
  public void setPosition(int offsetWidth, int offsetHeight)
  {
    int left = (Window.getClientWidth() / 2) - CPICKER_LEFT_OFFSET; 
    int top = (Window.getClientHeight() / 2) - CPICKER_TOP_OFFSET;
    int l = (left > 0)? left : 0;
    int t = (top > 0)? top : 0;
    dialogBox.setPopupPosition(l, t);
  }

  // ---------------------------------------------------------------------------

  @Override
  public void onClick(ClickEvent event)
  {
    Object src = event.getSource();
    if(src == btnCancel)
    {
      optHandler.onCancel();
    }
    else if(src == btnConfirm)
    {
      String title = tboxTitle.getText();
      String note = tboxNote.getText();
      String color = (currentColor != null) ? currentColor : DEFAULT_COLOR;
      tboxTitle.setText("");
      tboxNote.setText("");
      if(isEditing)
        optHandler.editNode(objID, title, note, color);
      else
        optHandler.createNewNode(title, note, color);
    }
  }

  // ---------------------------------------------------------------------------

  private void doCtor()
  {
    dialogBox.setText(CREATE_TITLE);
    dialogBox.setAnimationEnabled(true);
    dialogBox.setGlassEnabled(true);
    cPicker.addButtonHandler(cpHandler);
    btnConfirm.addClickHandler(this);
    btnCancel.addClickHandler(this);
    setCurrentColor(DEFAULT_COLOR);
  }
  
  // ---------------------------------------------------------------------------
  
  private void setCurrentColor(String color)
  {
    currentColor = color;
    Style s = divCurrentColor.getStyle(); 
    s.setBackgroundColor(currentColor);
  }

  // ---------------------------------------------------------------------------
  // ---------------------------------------------------------------------------
  
  class ColorPickerHandler implements CPicker.ButtonHandler
  {
    @Override
    public void onOk(String selectedColor)
    {
      setCurrentColor(selectedColor);
      cpPanel.setOpen(false);
    }
    @Override
    public void onCancel()
    {
      cpPanel.setOpen(false);
    }
  }
  
  // ---------------------------------------------------------------------------
}






