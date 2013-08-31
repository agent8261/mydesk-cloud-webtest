package edu.umich.imlc.mydesk.cloud.frontend.app.wemap;

import java.util.ArrayList;

import com.emitrom.lienzo.client.core.event.AbstractNodeMouseEvent;
import com.emitrom.lienzo.client.core.event.INodeXYEvent;
import com.emitrom.lienzo.client.core.event.NodeDragEndEvent;
import com.emitrom.lienzo.client.core.event.NodeDragMoveEvent;
import com.emitrom.lienzo.client.core.event.NodeDragStartEvent;
import com.emitrom.lienzo.client.core.event.NodeGestureChangeEvent;
import com.emitrom.lienzo.client.core.event.NodeGestureEndEvent;
import com.emitrom.lienzo.client.core.event.NodeGestureStartEvent;
import com.emitrom.lienzo.client.core.event.NodeMouseClickEvent;
import com.emitrom.lienzo.client.core.event.NodeMouseDoubleClickEvent;
import com.emitrom.lienzo.client.core.event.NodeMouseDownEvent;
import com.emitrom.lienzo.client.core.event.NodeMouseEnterEvent;
import com.emitrom.lienzo.client.core.event.NodeMouseExitEvent;
import com.emitrom.lienzo.client.core.event.NodeMouseMoveEvent;
import com.emitrom.lienzo.client.core.event.NodeMouseOutEvent;
import com.emitrom.lienzo.client.core.event.NodeMouseOverEvent;
import com.emitrom.lienzo.client.core.event.NodeMouseUpEvent;
import com.emitrom.lienzo.client.core.event.NodeMouseWheelEvent;
import com.emitrom.lienzo.client.core.event.NodeTouchCancelEvent;
import com.emitrom.lienzo.client.core.event.NodeTouchEndEvent;
import com.emitrom.lienzo.client.core.event.NodeTouchMoveEvent;
import com.emitrom.lienzo.client.core.event.NodeTouchStartEvent;
import com.emitrom.lienzo.client.core.event.TouchPoint;
import com.emitrom.lienzo.client.core.mediator.Mediators;
import com.emitrom.lienzo.client.core.mediator.MousePanMediator;
import com.emitrom.lienzo.client.core.mediator.MouseWheelZoomMediator;
import com.emitrom.lienzo.client.core.shape.IPrimitive;
import com.emitrom.lienzo.client.core.shape.Node;
import com.emitrom.lienzo.client.core.shape.Shape;
import com.emitrom.lienzo.client.core.shape.Viewport;
import com.emitrom.lienzo.client.widget.DragContext;
import com.emitrom.lienzo.shared.core.types.NodeType;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.dom.client.Touch;
import com.google.gwt.dom.client.Style.Cursor;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.DoubleClickEvent;
import com.google.gwt.event.dom.client.DoubleClickHandler;
import com.google.gwt.event.dom.client.GestureChangeEvent;
import com.google.gwt.event.dom.client.GestureChangeHandler;
import com.google.gwt.event.dom.client.GestureEndEvent;
import com.google.gwt.event.dom.client.GestureEndHandler;
import com.google.gwt.event.dom.client.GestureStartEvent;
import com.google.gwt.event.dom.client.GestureStartHandler;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseEvent;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.event.dom.client.MouseWheelEvent;
import com.google.gwt.event.dom.client.MouseWheelHandler;
import com.google.gwt.event.dom.client.TouchCancelEvent;
import com.google.gwt.event.dom.client.TouchCancelHandler;
import com.google.gwt.event.dom.client.TouchEndEvent;
import com.google.gwt.event.dom.client.TouchEndHandler;
import com.google.gwt.event.dom.client.TouchEvent;
import com.google.gwt.event.dom.client.TouchMoveEvent;
import com.google.gwt.event.dom.client.TouchMoveHandler;
import com.google.gwt.event.dom.client.TouchStartEvent;
import com.google.gwt.event.dom.client.TouchStartHandler;
import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.GwtEvent.Type;

/*
 * Responsible for handling user interaction with the Canvas panel
 * This is an based on LienzoHandlerManger
 */
final class UICanvasController implements ClickHandler, DoubleClickHandler,
  MouseMoveHandler, MouseUpHandler, MouseDownHandler, MouseOutHandler,
  MouseOverHandler, MouseWheelHandler, TouchCancelHandler, TouchEndHandler,
  TouchMoveHandler, TouchStartHandler, GestureStartHandler, GestureEndHandler,
  GestureChangeHandler
{
  private final CanvasPanel canvas;
  private final UIController uiController;
  
  private boolean m_dragging = false;
  private boolean m_dragging_using_touches = false;
  private boolean m_dragging_dispatch_move = false;
  private boolean m_dragging_ignore_clicks = false;
  private boolean m_dragging_mouse_pressed = false;
  private boolean enableDragging = true;

  private IPrimitive<?> m_dragnode = null;
  private IPrimitive<?> m_overprim = null;
  private DragContext m_dragContext;

  ArrayList<TouchPoint> m_touches = null;
  private Mediators m_mediators;
  MouseWheelZoomMediator zoomHndlr = new MouseWheelZoomMediator();
  MousePanMediator panHndlr = new MousePanMediator();
  
  // ---------------------------------------------------------------------------
  // ---------------------------------------------------------------------------

  public UICanvasController(CanvasPanel canvasPanel, UIController controller)
  {
    uiController = controller;
    canvas = canvasPanel;
    Viewport vp = canvas.getViewport();
    zoomHndlr.setViewport(vp);
    panHndlr.setViewport(vp);
    
    m_mediators = vp.getMediators();
    if(null != canvas)
      addHandlers();
  }

  // ---------------------------------------------------------------------------

  public void setEnableDragging(boolean toogle)
  {
    enableDragging = toogle;
  }

  // ---------------------------------------------------------------------------

  @Override
  public void onClick(ClickEvent event)
  {
    event.preventDefault();
    onNodeMouseClick(new NodeMouseClickEvent(event));
  }

  // ---------------------------------------------------------------------------

  @Override
  public void onDoubleClick(DoubleClickEvent event)
  {
    onNodeMouseDoubleClick(new NodeMouseDoubleClickEvent(event));
  }

  // ---------------------------------------------------------------------------

  @Override
  public void onMouseMove(MouseMoveEvent event)
  {
    event.preventDefault();
    NodeMouseMoveEvent nodeEvent = new NodeMouseMoveEvent(event);
    if(doDefaltAction(nodeEvent)) 
      return;
    onNodeMouseMove(nodeEvent);
  }

  // ---------------------------------------------------------------------------

  @Override
  public void onMouseUp(MouseUpEvent event)
  {
    event.preventDefault();
    NodeMouseUpEvent nodeEvent = new NodeMouseUpEvent(event);
    if(doDefaltAction(nodeEvent)) 
      return;
    onNodeMouseUp(nodeEvent);
  }

  // ---------------------------------------------------------------------------

  @Override
  public void onMouseDown(MouseDownEvent event)
  {
    event.preventDefault();
    NodeMouseDownEvent nodeEvent = new NodeMouseDownEvent(event);

    if(event.getNativeButton() == NativeEvent.BUTTON_RIGHT)
    {
      doDefaltAction(nodeEvent);
      return;
    }
    m_dragging_mouse_pressed = true;
    doPrepareDragging(nodeEvent);
    if(m_dragging)
      return;
    if(doDefaltAction(nodeEvent)) 
      return;
    fireDefaultEvent(nodeEvent);
  }

  // ---------------------------------------------------------------------------

  @Override
  public void onMouseOut(MouseOutEvent event)
  {
    event.preventDefault();
    NodeMouseOutEvent nodeEvent = new NodeMouseOutEvent(event);
    if(doDefaltAction(nodeEvent)) 
      return;
    onNodeMouseOut(nodeEvent);
  }

  // ---------------------------------------------------------------------------

  @Override
  public void onMouseOver(MouseOverEvent event)
  {
    event.preventDefault();
    NodeMouseOverEvent nodeEvent = new NodeMouseOverEvent(event);
    if(doDefaltAction(nodeEvent)) 
      return;
    onNodeMouseOver(nodeEvent);
  }

  // ---------------------------------------------------------------------------
  @Override
  public void onMouseWheel(MouseWheelEvent event)
  {
    event.preventDefault();
    NodeMouseWheelEvent nodeEvent = new NodeMouseWheelEvent(event);
    if(false == doDefaltAction(nodeEvent))
    {
      fireEvent(nodeEvent);
    }
    event.stopPropagation();
  }

  // ---------------------------------------------------------------------------

  @Override
  public void onTouchCancel(TouchCancelEvent event)
  {
    event.preventDefault();
    NodeTouchCancelEvent nodeEvent =
      new NodeTouchCancelEvent(getTouches(event));
    if(doDefaltAction(nodeEvent))
      return;
    onNodeMouseOut(nodeEvent);
  }

  // ---------------------------------------------------------------------------

  @Override
  public void onTouchEnd(TouchEndEvent event)
  {
    event.preventDefault();
    NodeTouchEndEvent nodeEvent = new NodeTouchEndEvent(m_touches);
    if(doDefaltAction(nodeEvent)) 
      return;
    onNodeMouseUp(nodeEvent);
  }

  // ---------------------------------------------------------------------------
  @Override
  public void onTouchMove(TouchMoveEvent event)
  {
    event.preventDefault();
    m_touches = getTouches(event);
    NodeTouchMoveEvent nodeEvent = new NodeTouchMoveEvent(m_touches);
    if(doDefaltAction(nodeEvent)) 
      return;
    onNodeMouseMove(nodeEvent);
  }

  // ---------------------------------------------------------------------------
  @Override
  public void onTouchStart(TouchStartEvent event)
  {
    event.preventDefault();
    m_touches = getTouches(event);
    NodeTouchStartEvent nodeEvent = new NodeTouchStartEvent(m_touches);
    if(doDefaltAction(nodeEvent)) 
      return;
    onNodeMouseDown(nodeEvent);
  }

  // ---------------------------------------------------------------------------
  @Override
  public void onGestureStart(GestureStartEvent event)
  {
    event.preventDefault();
    NodeGestureStartEvent nodeEvent =
      new NodeGestureStartEvent(event.getScale(), event.getRotation());
    if(doDefaltAction(nodeEvent)) 
      return;
    fireEvent(nodeEvent);
  }

  // ---------------------------------------------------------------------------

  @Override
  public void onGestureEnd(GestureEndEvent event)
  {
    event.preventDefault();
    NodeGestureEndEvent nodeEvent =
      new NodeGestureEndEvent(event.getScale(), event.getRotation());
    if(doDefaltAction(nodeEvent)) 
      return;
    fireEvent(nodeEvent);
  }

  // ---------------------------------------------------------------------------

  @Override
  public void onGestureChange(GestureChangeEvent event)
  {
    event.preventDefault();
    NodeGestureChangeEvent nodeEvent =
      new NodeGestureChangeEvent(event.getScale(), event.getRotation());
    if(doDefaltAction(nodeEvent)) 
      return;
    fireEvent(nodeEvent);
  }

  // ---------------------------------------------------------------------------
  // ---------------------------------------------------------------------------

  boolean doDefaltAction(GwtEvent<?> event)
  {
    if(event instanceof AbstractNodeMouseEvent<?,?>)
    {
      @SuppressWarnings("unchecked")
      AbstractNodeMouseEvent <? extends MouseEvent<?>, ? extends EventHandler> 
      aEvent = 
      (AbstractNodeMouseEvent<? extends MouseEvent<?>, ? extends EventHandler>) 
      event;
              
      if(uiController.doRightMouseButtonAction(aEvent))
        return true;
    }
    if(panHndlr.handleEvent(event))
      return true;
    if(zoomHndlr.handleEvent(event))
      return true;
    if(m_mediators.handleEvent(event))
      return true;
    return false;
  }
  
  // ---------------------------------------------------------------------------
  
  private final void addHandlers()
  {
    canvas.addClickHandler(this);
    canvas.addDoubleClickHandler(this);
    canvas.addMouseMoveHandler(this);
    canvas.addMouseUpHandler(this);
    canvas.addMouseDownHandler(this);
    canvas.addMouseOutHandler(this);
    canvas.addMouseOverHandler(this);

    canvas.addMouseWheelHandler(this);
    canvas.addTouchCancelHandler(this);
    canvas.addTouchEndHandler(this);
    canvas.addTouchMoveHandler(this);

    canvas.addTouchStartHandler(this);
    canvas.addGestureStartHandler(this);
    canvas.addGestureEndHandler(this);
    canvas.addGestureChangeHandler(this);
  }

  // ---------------------------------------------------------------------------

  private final void onNodeMouseClick(INodeXYEvent event)
  {
    if(m_dragging_ignore_clicks)
    {
      m_dragging_ignore_clicks = false;
      return;
    }
    IPrimitive<?> prim =
      findPrimitiveForEvent(event, NodeMouseClickEvent.getType());

    if(null != prim)
      prim.fireEvent(event.getNodeEvent());
    else
      fireEvent(event.getNodeEvent());
  }

  // ---------------------------------------------------------------------------

  private final void onNodeMouseDoubleClick(INodeXYEvent event)
  {
    IPrimitive<?> prim =
      findPrimitiveForEvent(event, NodeMouseDoubleClickEvent.getType());

    if(null != prim)
      prim.fireEvent(event.getNodeEvent());
    else
      fireEvent(event.getNodeEvent());
  }

  // ---------------------------------------------------------------------------

  private final void onNodeMouseMove(INodeXYEvent event)
  {
    if((m_dragging) && (m_dragging_using_touches))
      return;

    if(m_dragging_mouse_pressed)
    {
      if(false == m_dragging)
      {
        doPrepareDragging(event);
        if(false == m_dragging)
          m_dragging_mouse_pressed = false;
      }
    }
    if(m_dragging)
    {
      doDragMove(event);
      return;
    }
    doCheckEnterExitShape(event);
    IPrimitive<?> prim =
      findPrimitiveForEvent(event, event.getNodeEvent().getAssociatedType());

    if(null != prim)
      prim.fireEvent(event.getNodeEvent());
    else
      fireEvent(event.getNodeEvent());
  }
  
  // ---------------------------------------------------------------------------

  private final boolean onNodeMouseUp(INodeXYEvent event)
  {
    m_dragging_mouse_pressed = false;

    if(m_dragging)
    {
      doDragCancel(event);
      m_dragging_ignore_clicks = true;
      return true;
    }
    IPrimitive<?> prim =
      findPrimitiveForEvent(event, event.getNodeEvent().getAssociatedType());
    if(null != prim)
    {
      prim.fireEvent(event.getNodeEvent());
      return true;
    }
    else
    {
      fireEvent(event.getNodeEvent());
      return false;
    }
  }
  
  // ---------------------------------------------------------------------------

  private final boolean onNodeMouseDown(INodeXYEvent event)
  {
    if(m_dragging_mouse_pressed)
      return true;
    if(m_dragging)
      doDragCancel(event);
    m_dragging_mouse_pressed = true;

    IPrimitive<?> prim =
      findPrimitiveForEvent(event, event.getNodeEvent().getAssociatedType());
    if(null != prim)
    {
      prim.fireEvent(event.getNodeEvent());
      return true;
    }
    else
    {
      fireEvent(event.getNodeEvent());
    }
    return false;
  }

  // ---------------------------------------------------------------------------

  private final void onNodeMouseOut(INodeXYEvent event)
  {
    m_dragging_mouse_pressed = false;
    if(m_dragging)
      doDragCancel(event);
    doCancelEnterExitShape(event);
    fireEvent(event.getNodeEvent());
  }

  // ---------------------------------------------------------------------------

  private final void onNodeMouseOver(INodeXYEvent event)
  {
    Node<?> node = doCheckEnterExitShape(event);
    if((null != node) && (node.isListening()) && (node.isVisible())
      && (node.isEventHandled(NodeMouseOverEvent.getType())))
    {
      node.fireEvent(event.getNodeEvent());
    }
    fireEvent(event.getNodeEvent());
  }
  
  // ---------------------------------------------------------------------------

  private final ArrayList<TouchPoint> getTouches(TouchEvent<?> event)
  {
    ArrayList<TouchPoint> touches = new ArrayList<TouchPoint>();
    JsArray<Touch> jsarray = event.getTouches();
    Element element = event.getRelativeElement();

    if((null != jsarray) && (jsarray.length() > 0))
    {
      int size = jsarray.length();
      for(int i = 0; i < size; i++)
      {
        Touch touch = jsarray.get(i);
        touches.add(new TouchPoint(touch.getRelativeX(element), touch
          .getRelativeY(element)));
      }
    }
    else
    {
      int x = event.getNativeEvent().getClientX() - element.getAbsoluteLeft()
          + element.getScrollLeft() + element.getOwnerDocument().getScrollLeft();
      int y = event.getNativeEvent().getClientY() - element.getAbsoluteTop()
          + element.getScrollTop() + element.getOwnerDocument().getScrollTop();
      touches.add(new TouchPoint(x, y));
    }
    return touches;
  }


  // ---------------------------------------------------------------------------

  private final Shape<?> findShapeAtPoint(int x, int y)
  {
    return canvas.getViewport().findShapeAtPoint(x, y);
  }

  // ---------------------------------------------------------------------------

  private final void doDragCancel(INodeXYEvent event)
  {
    if(m_dragging)
    {
      doDragMove(event);
      canvas.setCursor(Cursor.DEFAULT);
      m_dragnode.setVisible(true);
      m_dragContext.dragDone();

      m_dragnode.getLayer().draw();
      canvas.getDragLayer().clear();
      NodeDragEndEvent nEvt = new NodeDragEndEvent(m_dragContext);
      uiController.onNodeDragEnd(nEvt);
      m_dragnode.fireEvent(nEvt);
      m_dragnode = null;

      m_dragging = false;
      m_dragging_dispatch_move = false;
      m_dragging_using_touches = false;
    }
  }

  // ---------------------------------------------------------------------------

  private final void doDragStart(IPrimitive<?> node, INodeXYEvent event)
  {
    if(m_dragging)
      doDragCancel(event);
    canvas.setCursor(Cursor.CROSSHAIR);
    m_dragContext = new DragContext(event, node);
    m_dragnode = node;
    NodeDragStartEvent nEvt = new NodeDragStartEvent(m_dragContext);
    uiController.onNodeDragStart(nEvt);
    m_dragnode.fireEvent(nEvt);
    m_dragging = true;

    m_dragnode.setVisible(false);
    m_dragnode.getLayer().draw();
    m_dragContext.drawNode(canvas.getDragLayer().getContext());
    m_dragging_dispatch_move =
      m_dragnode.isEventHandled(NodeDragMoveEvent.getType());
    m_dragging_using_touches =
      ((event.getNodeEvent().getAssociatedType() == NodeTouchMoveEvent
        .getType()) || (event.getNodeEvent().getAssociatedType() == NodeTouchStartEvent
        .getType()));
  }

  // ---------------------------------------------------------------------------

  private final void doDragMove(INodeXYEvent event)
  {
    canvas.getDragLayer().clear();
    m_dragContext.dragUpdate(event);

    if(m_dragging_dispatch_move)
    {
      m_dragnode.fireEvent(new NodeDragMoveEvent(m_dragContext));
    }
    canvas.getDragLayer().draw();
    m_dragContext.drawNode(canvas.getDragLayer().getContext());
  }


  // ---------------------------------------------------------------------------

  private final IPrimitive<?> findPrimitiveForEvent
    (INodeXYEvent event, Type<?> type)
  {
    IPrimitive<?> find = null;
    Node<?> node = findShapeAtPoint(event.getX(), event.getY());
    while((null != node) && (node.getNodeType() != NodeType.LAYER))
    {
      IPrimitive<?> prim = node.asPrimitive();
      if((null != prim) && (prim.isListening()) && (prim.isVisible()) && 
         (prim.isEventHandled(type)))
      {
        find = prim;
      }
      node = node.getParent();
    }
    return find;
  }

  // ---------------------------------------------------------------------------

  private final void doPrepareDragging(INodeXYEvent event)
  {
    if(!enableDragging)
    {
      return;
    }
    IPrimitive<?> find = null;
    Node<?> node = findShapeAtPoint(event.getX(), event.getY());
    while((null != node) && (node.getNodeType() != NodeType.LAYER))
    {
      IPrimitive<?> prim = node.asPrimitive();
      if((null != prim) && (prim.isDraggable()) && (prim.isListening())
        && (prim.isVisible()))
      {
        find = prim;
      }
      node = node.getParent();
    }
    if(null != find)
    {
      doDragStart(find, event);
    }
  }

  // ---------------------------------------------------------------------------

  private final void fireDefaultEvent(INodeXYEvent event)
  {
    IPrimitive<?> prim =
      findPrimitiveForEvent(event, event.getNodeEvent().getAssociatedType());
    if(null != prim)
    {
      prim.fireEvent(event.getNodeEvent());
      return;
    }
    else
      fireEvent(event.getNodeEvent());
    return;
  }

  // ---------------------------------------------------------------------------

  private final void doCancelEnterExitShape(INodeXYEvent event)
  {
    if((null != m_overprim)
      && (m_overprim.isEventHandled(NodeMouseExitEvent.getType())))
    {
      fireNodeMouseExitEvent(event);
    }
    m_overprim = null;
  }

  // ---------------------------------------------------------------------------

  private final Shape<?> doCheckEnterExitShape(INodeXYEvent event)
  {
    Shape<?> shape = findShapeAtPoint(event.getX(), event.getY());
    if(shape != null)
    {
      IPrimitive<?> prim = shape.asPrimitive();
      if(null != m_overprim)
      {
        if(prim != m_overprim)
        {
          if(m_overprim.isEventHandled(NodeMouseExitEvent.getType()))
          {
            fireNodeMouseExitEvent(event);
          }
        }
      }
      if(prim != m_overprim)
      {
        if((null != prim)
          && (prim.isEventHandled(NodeMouseEnterEvent.getType())))
        {
          NodeMouseEnterEvent nEvt = new NodeMouseEnterEvent(event.getX(), event.getY());
          uiController.onNodeMouseEnter(nEvt);
          prim.fireEvent(nEvt);
        }
        m_overprim = prim;
      }
    }
    else
    {
      doCancelEnterExitShape(event);
    }
    return shape;
  }

  // ---------------------------------------------------------------------------
  
  private final void fireNodeMouseExitEvent(INodeXYEvent event)
  {
    NodeMouseExitEvent nEvt = new NodeMouseExitEvent(event.getX(), event.getY());
    uiController.onNodeMouseExit(nEvt);
    m_overprim.fireEvent(nEvt);
  }
  
  // ---------------------------------------------------------------------------

  private final void fireEvent(GwtEvent<?> event)
  {
    canvas.getViewport().fireEvent(event);
  }

  // ---------------------------------------------------------------------------
}
