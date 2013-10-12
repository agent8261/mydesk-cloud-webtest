package edu.umich.imlc.mydesk.cloud.frontend.app.wemap;

import com.emitrom.lienzo.client.core.LienzoGlobals;
import com.emitrom.lienzo.client.core.event.OrientationChangeEvent;
import com.emitrom.lienzo.client.core.event.ResizeChangeEvent;
import com.emitrom.lienzo.client.core.event.ResizeEndEvent;
import com.emitrom.lienzo.client.core.event.ResizeStartEvent;
import com.emitrom.lienzo.client.core.event.OrientationChangeEvent.Orientation;
import com.emitrom.lienzo.client.core.i18n.MessageConstants;
import com.emitrom.lienzo.client.core.mediator.IMediator;
import com.emitrom.lienzo.client.core.mediator.Mediators;
import com.emitrom.lienzo.client.core.shape.Layer;
import com.emitrom.lienzo.client.core.shape.Scene;
import com.emitrom.lienzo.client.core.shape.Shape;
import com.emitrom.lienzo.client.core.shape.Viewport;
import com.emitrom.lienzo.shared.core.types.DataURLType;
import com.emitrom.lienzo.shared.core.types.IColor;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.dom.client.Style.Cursor;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.Label;

/**
 * This is an alteration of LienzoPanel 
 */
public class CanvasPanel extends FocusPanel
{
  private final UICanvasController m_events;
  private final UIController uiController;
  
  private final int m_wide;
  private final int m_high;

  private Viewport m_view;
  private boolean m_resizing = false;
  private Timer m_resize_timer;
  private int m_resize_check_repeat_interval = 750;
  private Orientation m_orientation;

  // ---------------------------------------------------------------------------
  // ---------------------------------------------------------------------------
  
  public CanvasPanel(int wide, int high, UIController controller)
  {
    uiController = controller;
    m_wide = wide;
    m_high = high;
    m_view = new Viewport(wide, high);
    if( LienzoGlobals.getInstance().isCanvasSupported() )
    {
      getElement().appendChild(m_view.getElement());
      setPixelSize(wide, high);
      m_events = new UICanvasController(this, uiController);
      initResizeTimer();
      Window.addResizeHandler(new CanvasResizeHandler());
    }
    else
    {
      add(new Label(MessageConstants.MESSAGES.getCanvasUnsupportedMessage()));
      m_events = null;
    }
  }

  // ---------------------------------------------------------------------------
  
  public CanvasPanel add(Layer layer)
  { getScene().add(layer); return this; }

  // ---------------------------------------------------------------------------
  
  public CanvasPanel remove(Layer layer)
  { getScene().remove(layer); return this; }

  // ---------------------------------------------------------------------------
  
  public CanvasPanel removeAll()
  { getScene().removeAll(); return this; }

  // ---------------------------------------------------------------------------
  
  @Override
  public void setPixelSize(int wide, int high)
  { super.setPixelSize(wide, high); m_view.setPixelSize(wide, high); }

  // ---------------------------------------------------------------------------
  
  public void setCursor(final Cursor cursor)
  {
    Scheduler.get().scheduleDeferred(new ScheduledCommand()
    {
      @Override
      public void execute()
      {
        getElement().getStyle().setCursor(cursor);
      }
    });
  }

  // ---------------------------------------------------------------------------
  
  public Scene getScene()
  { return m_view.getScene(); }

  // ---------------------------------------------------------------------------
  
  public Viewport getViewport()
  { return m_view; }

  // ---------------------------------------------------------------------------
  
  public void setBackgroundLayer(Layer layer)
  { m_view.setBackgroundLayer(layer); }

  // ---------------------------------------------------------------------------
  
  public Layer getDragLayer()
  {
    return m_view.getDraglayer();
  }

  // ---------------------------------------------------------------------------
  
  public void setResizeCheckRepeatInterval(int resizeCheckRepeatInterval)
  {
    m_resize_check_repeat_interval = resizeCheckRepeatInterval;
  }

  // ---------------------------------------------------------------------------
  
  public int setResizeCheckRepeatInterval()
  {
    return m_resize_check_repeat_interval;
  }

  // ---------------------------------------------------------------------------
  
  public int getWidth()
  {
    return m_wide;
  }

  // ---------------------------------------------------------------------------
  
  public int getHeight()
  {
    return m_high;
  }

  // ---------------------------------------------------------------------------
  
  public String toJSONString()
  {
    return m_view.toJSONString();
  }

  // ---------------------------------------------------------------------------
  
  public final String toDataURL()
  {
    return m_view.toDataURL();
  }

  // ---------------------------------------------------------------------------
  
  public final String toDataURL(boolean includeBackgroundLayer)
  {
    return m_view.toDataURL(includeBackgroundLayer);
  }

  // ---------------------------------------------------------------------------
  
  public final String toDataURL(DataURLType mimetype)
  {
    return m_view.toDataURL(mimetype);
  }

  // ---------------------------------------------------------------------------
  
  public final String toDataURL(DataURLType mimetype,
      boolean includeBackgroundLayer)
  {
    return m_view.toDataURL(mimetype, includeBackgroundLayer);
  }

  // ---------------------------------------------------------------------------
  
  public CanvasPanel setBackgroundColor(String color)
  {
    getElement().getStyle().setBackgroundColor(color);
    return this;
  }

  // ---------------------------------------------------------------------------
  
  public CanvasPanel setBackgroundColor(IColor color)
  {
    return setBackgroundColor(color.getColorString());
  }

  // ---------------------------------------------------------------------------
  
  public String getBackgroundColor()
  {
    return getElement().getStyle().getBackgroundColor();
  }

  // ---------------------------------------------------------------------------
  
  public Mediators getMediators()
  {
    return m_view.getMediators();
  }

  // ---------------------------------------------------------------------------
  
  public void pushMediator(IMediator mediator)
  {
    m_view.pushMediator(mediator);
  }

  // ---------------------------------------------------------------------------
  
  public Shape<?> findShapeAtPoint(int x, int y)
  {
    return m_view.findShapeAtPoint(x, y);
  }
  
  // ---------------------------------------------------------------------------
  
  public static native void enableWindowMouseWheelScroll(boolean enabled)
  /*-{
		$wnd.mousewheel = function() {
			return enabled;
		}
  }-*/;

  // ---------------------------------------------------------------------------
  
  private void initResizeTimer()
  {
    m_resize_timer = new Timer()
    {
      @Override
      public void run()
      {
        m_resizing = false;
        if( !m_resizing )
        {
          int w = getElement().getParentElement().getClientWidth();
          int h = getElement().getParentElement().getClientHeight();
          getViewport().getHandlerManager().fireEvent(new ResizeEndEvent(w, h));
          cancel();
        }
      }
    };
  }
  
  // ---------------------------------------------------------------------------
  // ---------------------------------------------------------------------------
  
  class CanvasResizeHandler implements ResizeHandler
  {
    @Override
    public void onResize(ResizeEvent event)
    {
      int w = getElement().getParentElement().getClientWidth();
      int h = getElement().getParentElement().getClientHeight();
      setPixelSize(w, h);

      if( !m_resizing )
      {
        m_resizing = true;
        getViewport().getHandlerManager().fireEvent(new ResizeStartEvent(w, h));
        m_resize_timer.scheduleRepeating(m_resize_check_repeat_interval);
      }

      m_resizing = true;
      getViewport().getHandlerManager().fireEvent(new ResizeChangeEvent(w, h));
      Orientation orientation;

      if( w > h )
        orientation = Orientation.LANDSCAPE;
      else
        orientation = Orientation.PORTRAIT;

      if( orientation != m_orientation )
      {
        m_orientation = orientation;
        getViewport().getHandlerManager().fireEvent(new OrientationChangeEvent(w, h));
      }

      Scheduler.get().scheduleDeferred(new DeferedDrawCommand());
    } // onResize
    
    class DeferedDrawCommand implements ScheduledCommand
    {
      @Override
      public void execute()
      { m_view.draw(); }
    }
  }
  
  // ---------------------------------------------------------------------------
}
