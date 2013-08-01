package edu.umich.imlc.mydesk.cloud.frontend.app.drawables;

import com.emitrom.lienzo.client.core.event.NodeDragEndHandler;
import com.emitrom.lienzo.client.core.event.NodeDragStartHandler;
import com.emitrom.lienzo.client.core.shape.Layer;

import edu.umich.imlc.mydesk.cloud.frontend.app.drawables.DrawableObject.DrawingSurface;

public class LienzoLayer extends Layer implements DrawingSurface
{
  NodeDragEndHandler endHandler = null;
  NodeDragStartHandler startHandler = null;
  
  @Override
  public void remove(DrawableObject obj)
  {
    obj.removeFrom(this);
  }
  
  public void setNodeDragEndHandler(NodeDragEndHandler hndlr)
  {
    this.endHandler = hndlr;
  }
  
  public NodeDragEndHandler getNodeDragEndHandler()
  {
    return endHandler;
  }
  
  public void setNodeDragStartHandler(NodeDragStartHandler hndlr)
  {
    this.startHandler = hndlr;
  }
  
  public NodeDragStartHandler getNodeDragStartHandler()
  {
    return startHandler;
  }
}
