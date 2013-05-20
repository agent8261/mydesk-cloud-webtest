package edu.umich.imlc.mydesk.cloud.frontend.app.wemap.web;

import java.util.Map;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.ResizeComposite;

import edu.umich.imlc.mydesk.cloud.frontend.BasicView;
import edu.umich.imlc.mydesk.cloud.frontend.app.wemap.Edge_GWT;
import edu.umich.imlc.mydesk.cloud.frontend.app.wemap.Node_GWT;
import edu.umich.imlc.mydesk.cloud.frontend.app.wemap.WeMapFile_GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.event.dom.client.MouseWheelEvent;
import com.google.gwt.event.dom.client.MouseWheelHandler;
import com.google.gwt.user.client.ui.HorizontalPanel;

public class View_WeMap extends ResizeComposite 
  implements BasicView, MouseDownHandler, MouseUpHandler, 
  MouseMoveHandler, MouseWheelHandler
{
  private static final int CANVAS_WIDTH = 400;
  private static final int CANVAS_HEIGHT = 400;
  private static final int FRAME_INTERVAL = 15;
  
  private static final double ZOOM_FACTOR = 2.0;
  private static final String CSS_CANVAS_BORDER = "CanvasBorder";
  
  private LayoutPanel view = null;
  private Canvas canvas = null;
  private Label lblCanvas = new Label("Canvas");
  
  private boolean isAnimating = false;
  private boolean isZooming = false;
  private boolean isPanning = false;
  
  private int mouseX = 0;
  private int mouseY = 0;
  
  private double orginX = 0;
  private double orginY = 0;  
  private double scale = 0.1;
  
  private WeMapFile_GWT file = null;
  private Canvas_Timer canvasTimer = new Canvas_Timer();
  
  private final HorizontalPanel horizontalPanel = new HorizontalPanel();
  
  
  // ---------------------------------------------------------------------------
  // ---------------------------------------------------------------------------
  
  public View_WeMap()
  {
    view = new LayoutPanel();
    
    view.add(horizontalPanel);
    horizontalPanel.add(lblCanvas);
    
    canvas = Canvas.createIfSupported();    
    canvas.setCoordinateSpaceHeight(CANVAS_HEIGHT);
    canvas.setCoordinateSpaceWidth(CANVAS_WIDTH);
    canvas.setStyleName(CSS_CANVAS_BORDER);
    
    canvas.addMouseDownHandler(this);
    canvas.addMouseMoveHandler(this);
    canvas.addMouseUpHandler(this);
    canvas.addMouseWheelHandler(this);
    
    view.add(canvas);
    view.setWidgetLeftWidth(canvas, 52.0, Unit.PX, CANVAS_WIDTH + 6.0, Unit.PX);
    view.setWidgetTopHeight(canvas, 40.0, Unit.PX, CANVAS_HEIGHT + 6.0, Unit.PX);
    
    initWidget(view);
    view.setSize("800px", "600px");
  }
  
  // ---------------------------------------------------------------------------
  
  public void drawWeMapFile(WeMapFile_GWT file)
  {
    this.file = file;
    render();
  }
  
  // ---------------------------------------------------------------------------
  
  @Override
  public void onMouseDown(MouseDownEvent event)
  {    
    doMouseDown(event.getX(), event.getY());
  }
  
   void doMouseDown(int x, int y)
   {
     if(isZooming)
       return;
     isPanning = true;
     mouseX = x;
     mouseY = y;
     startAnimation();   
   }
   
  // ---------------------------------------------------------------------------
  
  @Override
  public void onMouseMove(MouseMoveEvent event)
  {
    doMouseMove(event.getX(), event.getY());
  }

  // ---------------------------------------------------------------------------
  
  @Override
  public void onMouseUp(MouseUpEvent event)
  {
    doMouseMove(event.getX(), event.getY());
    isPanning = false;
    if(!isZooming)
      stopAnimation();
  }
  
  // ---------------------------------------------------------------------------
  
  @Override
  public void onMouseWheel(MouseWheelEvent event)
  {
    if(isPanning)
      return;
    
    isZooming = true;
    double dir = (event.isNorth())? 1 : -1;    
    double zoom = Math.pow(ZOOM_FACTOR, dir);
    
    Point p = toWorld(event.getX(), event.getY());
    orginX = orginX + scale * p.x - scale * zoom * p.x;
    orginY = orginY + scale * p.y - scale * zoom * p.y;
    scale *= zoom;
    render();
  }
  
  // ---------------------------------------------------------------------------
  
  private void doMouseMove(int x, int y)
  {
    if(isPanning)
    {
      int tx = (x - mouseX);
      int ty = (y - mouseY);
      
      orginX = tx  + orginX;
      orginY = ty  + orginY;
      mouseX = x; mouseY = y;
    }
  }
  
  // ---------------------------------------------------------------------------
  
  private void render()
  {
    Context2d context = canvas.getContext2d();
    context.save();
    context.clearRect(0, 0, CANVAS_WIDTH, CANVAS_HEIGHT);
    drawGrid(context);
    
    context.translate(orginX, orginY);
    context.scale(scale, scale);
    
    if(isZooming)
      isZooming = false;
    
    if(file == null)
      drawTestScene(context);
    else
      drawMap(context);
    
    context.restore();
  }
  
  // ---------------------------------------------------------------------------
  
  private void drawGrid(Context2d context)
  {
    context.setStrokeStyle("rgb(200,0,0)");

    context.setLineWidth(1);
    context.beginPath();
    context.moveTo(200, 0);
    context.lineTo(200, canvas.getCoordinateSpaceHeight());
    context.stroke();
    
    context.setLineWidth(1);
    context.beginPath();
    context.moveTo(0,200);
    context.lineTo(canvas.getCoordinateSpaceWidth(), 200);
    context.stroke();
  }
  
  // ---------------------------------------------------------------------------
  
  private void drawMap(Context2d context)
  {
    assert(file != null);
    for(Map.Entry<String, Edge_GWT> entry: file.getEdges().entrySet())
    { entry.getValue().draw(context); }
    
    for(Map.Entry<String, Node_GWT> entry: file.getNodes().entrySet())
    { entry.getValue().draw(context); }
  }
  
  // ---------------------------------------------------------------------------
  
  private void drawTestScene(Context2d context)
  {
    context.setFillStyle("rgb(200,0,0)");
    context.fillRect(0, 0, 55, 50);
    
    context.setFillStyle("rgba(0, 0, 200, 0.5)");
    context.fillRect(20, 20, 55, 50);
  }
  
  // ---------------------------------------------------------------------------
  
  private void startAnimation()
  {
    if(!isAnimating)
    {
      isAnimating = true;
      canvasTimer.scheduleRepeating(FRAME_INTERVAL);
    }    
  }
  
  // ---------------------------------------------------------------------------
  
  private void stopAnimation()
  {
    isAnimating = false;
    canvasTimer.cancel();
    //TODO: slippery pan
  }
  
  // ---------------------------------------------------------------------------
  
  // used might be good for slippery pan
  double easeOut( double time, double start, double end, double duration ) 
  {
    return end * ( ( time = time / duration - 1.0 ) * time * time + 1.0 ) + start;
  }
  
  // ---------------------------------------------------------------------------
  // ---------------------------------------------------------------------------
  
  private class Point
  {
    double x;
    double y;
  }
  
  private Point toWorld(int screenX, int screenY)
  {
    Point p = new Point();
    p.x = (screenX - orginX) / scale;
    p.y = (screenY - orginY) / scale;
    return p;
  }
  
  // ---------------------------------------------------------------------------
  // ---------------------------------------------------------------------------
  
  // Draws the canvas 33.33fps if FRAME_INTERVAL = 30;
  private class Canvas_Timer extends Timer
  {
    @Override
    public void run()
    {
      render();
    }   
  }
  
  // ---------------------------------------------------------------------------
}
