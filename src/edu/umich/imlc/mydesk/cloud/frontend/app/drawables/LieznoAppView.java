package edu.umich.imlc.mydesk.cloud.frontend.app.drawables;

import com.emitrom.lienzo.client.core.event.NodeDragEndEvent;
import com.emitrom.lienzo.client.core.event.NodeDragEndHandler;
import com.emitrom.lienzo.client.core.event.NodeDragStartEvent;
import com.emitrom.lienzo.client.core.event.NodeDragStartHandler;

/**
 * Terrible code ahead 
 */
public class LieznoAppView 
  implements NodeDragEndHandler, NodeDragStartHandler
{
  private boolean is_Dragging = false;
  
  // ---------------------------------------------------------------------------
  
  public static LieznoAppView getInstance()
  {
    return InstanceHolder.INSTANCE;
  }
  
  // ---------------------------------------------------------------------------
  
  public boolean isDragging()
  {
    return is_Dragging;
  }
  
  // ---------------------------------------------------------------------------
  
  @Override
  public void onNodeDragStart(NodeDragStartEvent event)
  {
    is_Dragging = true;
  }

  // ---------------------------------------------------------------------------
  
  @Override
  public void onNodeDragEnd(NodeDragEndEvent event)
  {
    is_Dragging = false;
  }
  
  // ---------------------------------------------------------------------------
  // ---------------------------------------------------------------------------
  
  private LieznoAppView(){}
  
  private static class InstanceHolder
  {
    private static final LieznoAppView INSTANCE = new LieznoAppView();
  }
}
