package edu.umich.imlc.mydesk.cloud.frontend.app.wemap;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;

public class NodeNotePanel extends Composite 
  implements PopupPanel.PositionCallback
{
  private static NodeNotePanelUiBinder uiBinder = GWT
      .create(NodeNotePanelUiBinder.class);

  interface NodeNotePanelUiBinder extends UiBinder<Widget, NodeNotePanel>{}
  
  // ---------------------------------------------------------------------------
  // ---------------------------------------------------------------------------
  
  @UiField
  PopupPanel popupPnl;
  @UiField
  Label lblNote;
  
  int x = 0, y = 0;
  
  // ---------------------------------------------------------------------------
  // ---------------------------------------------------------------------------
  
  public NodeNotePanel()
  {
    initWidget(uiBinder.createAndBindUi(this));
  }
  
  // ---------------------------------------------------------------------------
  
  public void setNote(String note)
  {
    lblNote.setText(note);
  }

  // ---------------------------------------------------------------------------
  
  public void show(int x, int y)
  {
    this.x = x; this.y = y;
    popupPnl.setPopupPositionAndShow(this);
  }

  // ---------------------------------------------------------------------------
  
  public void hide()
  {
    popupPnl.hide();
  }
  
  // ---------------------------------------------------------------------------
  
  @Override
  public void setPosition(int offsetWidth, int offsetHeight)
  {
    popupPnl.setPopupPosition(x, y);
  }
  
  // ---------------------------------------------------------------------------
  
}
