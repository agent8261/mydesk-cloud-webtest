package edu.umich.imlc.mydesk.cloud.frontend.app.web;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.TextBox;

public class EditNodeDialog 
  implements ClickHandler, PopupPanel.PositionCallback
{
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
  InlineLabel ilblColor = new InlineLabel("Color: ");
  
  TextBox tboxTitle = new TextBox();
  TextBox tboxNote = new TextBox();
  ListBox lboxColor = new ListBox();
  
  Button btnAdd = new Button("Create");
  Button btnCancel = new Button("Cancel");
  final OptionHandler optHandler;
  
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
      String color = lboxColor.getValue(lboxColor.getSelectedIndex());
      
      tboxTitle.setText("");
      tboxNote.setText("");
      
      optHandler.onConfirm(title, note, color);
    }    
  }
  
  // ---------------------------------------------------------------------------
  
  @Override
  public void setPosition(int offsetWidth, int offsetHeight)
  {
    int top = (Window.getClientHeight() / 2) - (offsetHeight/2);
    int left = (Window.getClientWidth() / 2) - (offsetWidth/2); 
    dialogBox.setPopupPosition(left, top);
  }
  
  // -------------------------------------------------------------------------
  
  void initEditorPanel()
  {
    lboxColor.addItem("Blue", "Blue");
    lboxColor.addItem("Red", "Red");
    
    editorTable.setWidget(0, 0, ilblTitle);
    editorTable.setWidget(1, 0, ilblNote);
    editorTable.setWidget(2, 0, ilblColor);
    editorTable.setWidget(3, 0, btnAdd);
    
    editorTable.setWidget(0, 1, tboxTitle);
    editorTable.setWidget(1, 1, tboxNote);
    editorTable.setWidget(2, 1, lboxColor);
    editorTable.setWidget(3, 1, btnCancel);
    editorPanel.add(editorTable);
    
    btnCancel.addClickHandler(this);
    btnAdd.addClickHandler(this);
  }

  // -------------------------------------------------------------------------
}