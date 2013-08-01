package edu.umich.imlc.mydesk.cloud.frontend.app.wemap;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.DivElement;
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
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class EditNodeDialog extends Composite 
  implements ClickHandler, PopupPanel.PositionCallback
{
  // -------------------------------------------------------------------------
  
  public static interface OptionHandler
  {
    void onConfirm(String title, String note, String color);
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
  // ---------------------------------------------------------------------------
  @UiField
  DivElement divCurrentColor;
  @UiField
  DisclosurePanel cpPanel;
  @UiField
  Label lblDisclosurePnl;
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
  
  public void show()
  {
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
      optHandler.onConfirm(title, note, color);
    }
  }

  // ---------------------------------------------------------------------------

  private void doCtor()
  {
    dialogBox.setText("Add Node");
    dialogBox.setAnimationEnabled(true);
    dialogBox.setGlassEnabled(true);
    cPicker.addButtonHandler(cpHandler);
    btnConfirm.addClickHandler(this);
    btnCancel.addClickHandler(this);
  }

  // ---------------------------------------------------------------------------
  // ---------------------------------------------------------------------------
  
  class ColorPickerHandler implements CPicker.ButtonHandler
  {
    @Override
    public void onOk(String selectedColor)
    {
      System.out.println("Color : " + selectedColor);
      currentColor = selectedColor;
      divCurrentColor.getStyle().setBackgroundColor(currentColor);
    }

    @Override
    public void onCancel()
    {
      cpPanel.setOpen(false);
    }
  }
  
  // ---------------------------------------------------------------------------
}






