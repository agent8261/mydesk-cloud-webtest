package edu.umich.imlc.mydesk.cloud.frontend.app.drawables;

import com.emitrom.lienzo.client.core.event.NodeDragEndHandler;
import com.emitrom.lienzo.client.core.event.NodeDragStartHandler;
import com.emitrom.lienzo.client.core.event.NodeMouseEnterHandler;
import com.emitrom.lienzo.client.core.event.NodeMouseExitHandler;
import com.emitrom.lienzo.client.core.event.NodeMouseOverHandler;
import com.emitrom.lienzo.client.core.shape.Layer;

import edu.umich.imlc.mydesk.cloud.frontend.app.drawables.DrawableObject.DrawingSurface;

public class LienzoLayer extends Layer implements DrawingSurface
{
  NodeDragEndHandler endHandler = null;
  NodeDragStartHandler startHandler = null;
  NodeMouseOverHandler mOverHandler = null;
  NodeMouseEnterHandler mEnterHandler = null;
  NodeMouseExitHandler mExitHandler = null;
  
  // ---------------------------------------------------------------------------
  // ---------------------------------------------------------------------------
  
  @Override
  public void remove(DrawableObject obj)
  {
    obj.removeFrom(this);
  }
  
  // ---------------------------------------------------------------------------
  
  public void setNodeMouseExitHandler(NodeMouseExitHandler hndlr)
  {
    this.mExitHandler = hndlr;
  }
  
  // ---------------------------------------------------------------------------
  
  public NodeMouseExitHandler getNodeMouseExitHandler()
  {
    return mExitHandler;
  }
  
  // ---------------------------------------------------------------------------
  
  public void setNodeMouseEnterHandler(NodeMouseEnterHandler hndlr)
  {
    this.mEnterHandler = hndlr;
  }
  
  // ---------------------------------------------------------------------------
  
  public NodeMouseEnterHandler getNodeMouseEnterHandler()
  {
    return mEnterHandler;
  }
  
  // ---------------------------------------------------------------------------
  
  public void setNodeDragEndHandler(NodeDragEndHandler hndlr)
  {
    this.endHandler = hndlr;
  }
  
  // ---------------------------------------------------------------------------
  
  public NodeDragEndHandler getNodeDragEndHandler()
  {
    return endHandler;
  }
  
  // ---------------------------------------------------------------------------
  
  public void setNodeDragStartHandler(NodeDragStartHandler hndlr)
  {
    this.startHandler = hndlr;
  }
  
  // ---------------------------------------------------------------------------
  
  public NodeDragStartHandler getNodeDragStartHandler()
  {
    return startHandler;
  }
  
  // ---------------------------------------------------------------------------
  
  public void setNodeMouseOverHandler(NodeMouseOverHandler hndlr)
  {
    mOverHandler = hndlr;
  }
  
  // ---------------------------------------------------------------------------
  
  public NodeMouseOverHandler getNodeMouseOverHandler()
  {
    return mOverHandler;
  }
  
  // ---------------------------------------------------------------------------
}
