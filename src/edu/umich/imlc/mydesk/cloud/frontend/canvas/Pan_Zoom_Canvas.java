package edu.umich.imlc.mydesk.cloud.frontend.canvas;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.event.dom.client.MouseWheelEvent;
import com.google.gwt.event.dom.client.MouseWheelHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ResizeComposite;
import com.google.gwt.user.client.ui.VerticalPanel;

import edu.umich.imlc.mydesk.cloud.frontend.BasicView;

public class Pan_Zoom_Canvas extends ResizeComposite 
  implements BasicView, MouseDownHandler, MouseUpHandler,
  MouseMoveHandler, MouseWheelHandler
{
  static final String BORDER_STYLE = "canvasBorder";
  private final String APP_TITLE_STYLE = "weSketchHeader";
  static final int FRAME_INTERVAL = 15;  
  static final double ZOOM_FACTOR = 2.0;
  static final String TITLE_STYLE = "weSketchTitle";

  final int canvas_width;
  final int canvas_height;
  
  DockLayoutPanel view = null;
  HorizontalPanel northPanel = null;
  HorizontalPanel southPanel = null;
  VerticalPanel titlePanel = null;
  HorizontalPanel centerPanel = new HorizontalPanel();
  
  Canvas canvas = null;
  Label lblTitle = new Label("Canvas");
  Label appLabel = new Label("WeSketch");
  
  boolean isAnimating = false;
  boolean isZooming = false;
  boolean isPanning = false;
  
  int mouseX = 0;
  int mouseY = 0;
  
  double orginX = 0;
  double orginY = 0;  
  double scale = 0.1;
  
  DrawableFile file = null;
  Canvas_Timer canvasTimer = new Canvas_Timer();

  // ---------------------------------------------------------------------------
  // ---------------------------------------------------------------------------

  public Pan_Zoom_Canvas
    (int canvas_width, int canvas_height, double scale)
  {
    this.canvas_width = canvas_width;
    this.canvas_height = canvas_height;
    this.scale = scale;
    
    view = new DockLayoutPanel(Unit.PCT);
    view.setHeight("95%");
    view.setWidth("95%");
    view.setStyleName
      ("roundedCornersWide borderWidthMedium borderColorLightBlue borderStyleSolid paddingMedium marginThick");
    lblTitle.setStyleName(TITLE_STYLE);
    
    northPanel = new HorizontalPanel();
    northPanel.setWidth("100%");
    northPanel.setHeight("100%");
    northPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
    northPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
    appLabel.setStyleName(APP_TITLE_STYLE);
    
    titlePanel = new VerticalPanel();
    titlePanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
    titlePanel.add(appLabel);
    lblTitle.setStyleName(TITLE_STYLE);
    titlePanel.add(lblTitle);
    northPanel.add(titlePanel);
    view.addNorth(northPanel, 10);
    
    canvas = Canvas.createIfSupported();
    canvas.setStyleName(BORDER_STYLE);
    canvas.setCoordinateSpaceHeight(canvas_height);
    canvas.setCoordinateSpaceWidth(canvas_width);
    
    canvas.addMouseDownHandler(this);
    canvas.addMouseMoveHandler(this);
    canvas.addMouseUpHandler(this);
    canvas.addMouseWheelHandler(this);
    
    centerPanel.setHeight("100%");
    centerPanel.setWidth("100%");
    centerPanel.setHorizontalAlignment(HorizontalPanel.ALIGN_CENTER);
    centerPanel.add(canvas);
    
    view.add(centerPanel);
    
    initWidget(view);
    view.setSize("800px", "600px");
  }
  
  // ---------------------------------------------------------------------------
  
  public void drawFile(DrawableFile file)
  {
    this.file = file;
    render();    
  }
  
  // ---------------------------------------------------------------------------
  
  public void setTitleLabel(String text)
  {
    lblTitle.setText(text);
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
  
  @Override
  public void onMouseDown(MouseDownEvent event)
  {
    if(isZooming)
      return;
    isPanning = true;
    mouseX = event.getX();
    mouseY = event.getY();
    startAnimation();
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
  public void onMouseMove(MouseMoveEvent event)
  {
    doMouseMove(event.getX(), event.getY());
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
    context.clearRect(0, 0, canvas_width, canvas_height);
    drawGrid(context);
    
    context.translate(orginX, orginY);
    context.scale(scale, scale);
    
    if(isZooming)
      isZooming = false;
    
    if(file == null)
      drawTestScene(context);
    else
      file.draw(context);
    
    context.restore();
  }
  
  // ---------------------------------------------------------------------------
  
  private void drawGrid(Context2d context)
  {
    context.save();
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
    context.restore();
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
  
  // might be useful for slippery pan. unused now
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