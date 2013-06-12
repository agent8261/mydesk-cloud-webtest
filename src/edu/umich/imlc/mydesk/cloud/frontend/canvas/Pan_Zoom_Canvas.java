package edu.umich.imlc.mydesk.cloud.frontend.canvas;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.CssColor;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.MouseWheelEvent;
import com.google.gwt.event.dom.client.MouseWheelHandler;
import com.google.gwt.event.logical.shared.AttachEvent;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Pan and Zoom enabled canvas
 */
public class Pan_Zoom_Canvas extends Composite 
  implements MouseWheelHandler
{
  static 
  { DataCache.IMPL.canvasStyle().ensureInjected(); }
  
  protected static final double ZOOM_FACTOR = 1.5;
  protected static final int FRAME_INTERVAL = 15;
  protected static final int SUNK_EVENTS = Event.MOUSEEVENTS;
  protected static final int MIN_HEIGHT = 200;
  protected static final int MAX_HEIGHT = 650;
  
  static final String CSSCLASS_CANVAS_VIEWPORT = "pzCanvasViewport";
  static final String CSSCLASS_HIDDEN_PANEL = "hiddenPanel";
  
  static final double MAX_SCALE= 5.0;
  static final double MIN_SCALE = 0.1;
  
  protected Canvas viewport = null;  
  protected Canvas backCanvas = null;
  
  protected Label lblTitle = new Label("Canvas");
  protected Label lblApp = new Label("App Label");
  protected Label lblDrag = new Label("Click & Drag to Resize!");
  
  InlineLabel iLblTitle = new InlineLabel("Canvas");
  InlineLabel iLblDash = new InlineLabel(" - ");
  InlineLabel iLblApp = new InlineLabel("App Label");
  
  protected VerticalPanel corePanel = new VerticalPanel();
  protected FlowPanel canvasPanel = new FlowPanel();
  protected FlowPanel headerPanel = new FlowPanel();
  protected FlowPanel hiddenPanel = new FlowPanel();
  
  protected boolean isAnimating = false;
  protected boolean isZooming = false;
  protected boolean isPanning = false;
  protected boolean isDragging = false;
  
  protected int world_width    = 0, world_height    = 0;
  private int canvas_width   = 0, canvas_height   = 0;  
  protected int mouseX = 0,         mouseY = 0;
  protected int deltaY = 0;
  protected int zoomCount = 0;
  
  protected double originX = 0.0, originY = 0.0;  
  protected double scaleX = 1.0, scaleY = 1.0;
  
  protected DrawableFile file = null;
  protected Canvas_Timer canvasTimer = null;
  
  // ---------------------------------------------------------------------------
  // ---------------------------------------------------------------------------

  public Pan_Zoom_Canvas()
  {
    sinkEvents(SUNK_EVENTS);
    corePanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
    initWidget(corePanel);
  }
  
  // ---------------------------------------------------------------------------
  
  public void defaultInit(int canvas_width, int canvas_height, 
      int world_width, int world_height)
  {
    this.world_width = world_width; this.world_height = world_height;
    this.canvas_width = canvas_width; this.canvas_height = canvas_height;    
    
    CanvasStyle css = DataCache.IMPL.canvasStyle();
    backCanvas = Canvas.createIfSupported();
    viewport = Canvas.createIfSupported();    
    initCanvas(css);
    initHeader(css);
    
    hiddenPanel.setStyleName(css.hiddenElement());    
    corePanel.add(hiddenPanel);
    corePanel.add(headerPanel);
    corePanel.add(canvasPanel);
    corePanel.add(lblDrag);
  }
  
  // ---------------------------------------------------------------------------
  
  public void drawFile(DrawableFile file)
  {
    reset_Reload(file);
    renderToBackCanvas();
    render();
  }
  
  // ---------------------------------------------------------------------------
  
  public void setTitleLabel(String text)
  {
    iLblTitle.setText(text);
  }
  
  // ---------------------------------------------------------------------------
  
  public void setAppLabel(String text)
  {
    iLblApp.setText(text);
  }
  
  // ---------------------------------------------------------------------------
  
  @Override
  public void onBrowserEvent(Event event)
  {
    super.onBrowserEvent(event);
    int type = event.getTypeInt();
    
    if((type & SUNK_EVENTS) != 0)
      event.preventDefault();
    
    switch(type)
    {
      case Event.ONMOUSEDOWN: doMouseDown(event); break;
      case Event.ONMOUSEMOVE: doMouseMove(event); break;
      case Event.ONMOUSEOUT : doMouseOut(event) ; break;
      case Event.ONMOUSEUP  : doMouseUp(event)  ; break;
      default: break;
    }
  }
  
  // ---------------------------------------------------------------------------
  
  @Override
  public void onMouseWheel(MouseWheelEvent event)
  {
    if(isPanning || isDragging)
      return;
    
    isZooming = true;
    int dir = (event.isNorth())? 1 : -1;    
    double zoom = Math.pow(ZOOM_FACTOR, (double)dir);
    double zoomX = scaleX * zoom;
    double zoomY = scaleY * zoom;
    
    if((zoomX > MAX_SCALE) || (zoomY > MAX_SCALE) || 
       (zoomX < MIN_SCALE) || (zoomY < MIN_SCALE))
    {
      isZooming = false; return;
    }
    zoomCount += dir;
    Vec2d p = toWorld(event.getX(), event.getY());
    originX = originX + (scaleX * p.a1) - (zoomX * p.a1);
    originY = originY + (scaleY * p.a2) - (zoomY * p.a2);
    scaleX = zoomX;
    scaleY = zoomY;
    render();
    isZooming = false;
  }
  
  // ---------------------------------------------------------------------------
  
  protected void doMouseDown(Event event)
  {
    Element e = Element.as(event.getEventTarget());
    if(e == lblDrag.getElement())
    {
      if(isPanning || isZooming)
        return;
      isDragging = true;
      mouseY = event.getClientY();
    }
    else if(e == viewport.getElement())
    {
      if(isZooming || isDragging)
        return;
      isPanning = true;
      Vec2i p = getViewMouseCoords(event);
      mouseX = p.a1; mouseY = p.a2;
      deltaY = 0;
      startAnimation();
    }
  }
  
  // ---------------------------------------------------------------------------
  
  protected void doMouseMove(Event event)
  {
    doPan(event);
    doDrag(event);
  }
  
  // ---------------------------------------------------------------------------
  
  protected void doMouseOut(Event event)
  {
    if(isPanning)
      finishPan(event);
    else if(isDragging)
    {
      Element e = Element.as(event.getEventTarget());
      if((e == viewport.getElement()) || (e == lblDrag.getElement()))
        return;
      finishDrag(event);
    }
  }
  
  // ---------------------------------------------------------------------------
  
  protected void doMouseUp(Event event)
  {
    if(isPanning)
      finishPan(event);
    else if(isDragging)
      finishDrag(event);
  }
  
  // ---------------------------------------------------------------------------
  
  protected void doPan(Event event)
  {
    if(isPanning)
    {
      Vec2i p = getViewMouseCoords(event);
      originX = (p.a1 - mouseX)  + originX;
      originY = (p.a2 - mouseY)  + originY;            
      mouseX = p.a1; mouseY = p.a2;
    }
  }
  
  // ---------------------------------------------------------------------------
  
  protected void doDrag(Event event)
  {
    if(isDragging)
    {
      int dy = event.getClientY() - mouseY;      
      int newHeight = canvas_height + dy;
      if((newHeight < MIN_HEIGHT) || (newHeight > MAX_HEIGHT))
        return;
      deltaY = dy;
      viewport.setCoordinateSpaceHeight(getCanvasHeight());
      viewport.setCoordinateSpaceWidth(getCanvasWidth());
      render();
    }
  }

  // ---------------------------------------------------------------------------
  
  protected void finishDrag(Event event)
  {
    assert(isDragging);
    doDrag(event);
    canvas_height += deltaY;
    deltaY = 0;
    isDragging = false;
  }
  
  // ---------------------------------------------------------------------------
  
  protected void finishPan(Event event)
  {
    assert(isPanning);
    doPan(event);
    stopAnimation();
    isPanning = false;
  }
  
  // ---------------------------------------------------------------------------
  
  protected int getCanvasWidth()
  {
    return (getCanvasHeight() * world_width) / world_height;
  }
  
  // ---------------------------------------------------------------------------
  
  protected int getCanvasHeight()
  {
    return canvas_height + deltaY;
  }
  
  // ---------------------------------------------------------------------------
  
  protected void printOrigin()
  {
    System.out.println("Orgin x: " + originX + " y: " + originY);
  }
  
  // ---------------------------------------------------------------------------
  
  protected void render()
  {
    Context2d context = viewport.getContext2d();
    context.clearRect(0, 0, getCanvasWidth(), getCanvasHeight());
    renderViewPort(context);
  }
  
  // ---------------------------------------------------------------------------
  
  protected void renderViewPort(Context2d context)
  {
    context.save();       
    context.translate(originX, originY);
    context.scale(scaleX, scaleY);
    context.drawImage(backCanvas.getCanvasElement(), 0, 0, 
        world_width, world_height, 0, 0, getCanvasWidth(), getCanvasHeight());    
    context.restore();
  }
  
  // ---------------------------------------------------------------------------
  
  protected void drawText(Context2d context)
  {
    context.save();
    int x = 65;
    int y = getCanvasHeight()/2 + 35;
    
    String text = "[" + zoomCount + "] Scale X: " + scaleX + " Y: " + scaleY; 
    context.setStrokeStyle("blue");
    context.fillText(text, x, y);
    context.strokeText(text, x, y);
    context.restore();
  }
  
  // ---------------------------------------------------------------------------
  
  protected void renderToBackCanvas()
  {
    Context2d context = backCanvas.getContext2d();
    context.save();
    context.clearRect(0, 0, world_width, world_height);
    if(file == null)
      drawTestScene(context);
    else
      file.draw(context);      
    context.restore();
  }
  
  // ---------------------------------------------------------------------------
  
  protected void drawTestScene(Context2d context)
  {
    context.setFillStyle("rgb(200,0,0)");
    
    double size = 24 * (scaleX * 10);
    context.fillRect(0, 0, size, size);
    
    context.fillRect(400.0, 2400.0, 550.0, 500.0);
    context.setFillStyle("rgba(0, 0, 200, 0.5)");
    context.fillRect(600.0, 2600.0, 550.0, 500.0);
    context.fillRect(1750.0, 1750.0, 500.0, 500.0);
    
    for (int i=0; i<6 ;i++)
    {
      for (int j=0; j<6 ;j++)
      {
        int g = (int) Math.floor(255.0 - 42.5 *i);
        int b = (int) Math.floor(255.0 - 42.5 *j);
        context.setFillStyle(CssColor.make(g, b, 0));
        double x = 1400 + j * 250.0;
        double y = i * 250.0;
        context.fillRect(x , y, 250.0, 250.0);
      }
    }
  }
  
  // ---------------------------------------------------------------------------
  
  protected void startAnimation()
  {
    if(!isAnimating)
    {
      assert(canvasTimer == null);
      canvasTimer = new Canvas_Timer();
      isAnimating = true;
      canvasTimer.scheduleRepeating(FRAME_INTERVAL);
    }
  }
  
  // ---------------------------------------------------------------------------
  
  protected void stopAnimation()
  {
    if(canvasTimer != null)
      canvasTimer.cancel();
    canvasTimer = null;
    isAnimating = false;
    //TODO: slippery pan
  }

  // ---------------------------------------------------------------------------
  
  protected void initHeader(CanvasStyle css)
  {
    headerPanel.add(iLblTitle);
    headerPanel.add(iLblDash);
    headerPanel.add(iLblApp);
    headerPanel.setStyleName(css.pzCanvasHeader());
  }

  // ---------------------------------------------------------------------------
  
  protected void initCanvas(CanvasStyle css)
  {
    backCanvas.setCoordinateSpaceWidth(world_width);
    backCanvas.setCoordinateSpaceHeight(world_height);
        
    viewport.getElement().setClassName(CSSCLASS_CANVAS_VIEWPORT);
    viewport.setStyleName(css.pzCanvasContext());
    viewport.addStyleName(css.outlineOn());
    viewport.setCoordinateSpaceWidth(canvas_width);
    viewport.setCoordinateSpaceHeight(canvas_height);
    viewport.addMouseWheelHandler(this);    
    canvasPanel.add(viewport);
    hiddenPanel.add(backCanvas);
  }
  
  // ---------------------------------------------------------------------------
  
  protected void reset_Reload(DrawableFile file)
  {
    this.file = file;
    if(file != null)
      setTitleLabel(file.getFileName());
    else
      setTitleLabel("Test File");
    
    mouseX = mouseY = deltaY = 0;
    originX = originY = 0.0; scaleX = scaleY = 1.0;
    isAnimating = isZooming = isPanning = isDragging = false;
  }

  // ---------------------------------------------------------------------------
  // ---------------------------------------------------------------------------
  
  protected static class Vec2d
  {
    public double a1;
    public double a2;
    public Vec2d(){}
    public Vec2d(double x, double y){ a1 = x; a2 = y; }
    public Vec2d(Vec2d vec){ a1 = vec.a1; a2 = vec.a2; }
  }
  
  protected Vec2d toWorld(int viewX, int viewY)
  {
    Vec2d p = new Vec2d();
    p.a1 = (viewX - originX) / scaleX;
    p.a2 = (viewY - originY) / scaleY;
    return p;
  }
  
  // ---------------------------------------------------------------------------
  // ---------------------------------------------------------------------------
  
  protected static class Vec2i
  {
    public int a1;
    public int a2;
    public Vec2i(){}
  }
  
  protected Vec2i getViewMouseCoords(Event event)
  {
    Vec2i p = new Vec2i();
    p.a1 = event.getClientX() - viewport.getAbsoluteLeft();
    p.a2 = event.getClientY() - viewport.getAbsoluteTop();
    return p;
  }
  
  // ---------------------------------------------------------------------------
  // ---------------------------------------------------------------------------
  
  // Draws the canvas 33.33fps if FRAME_INTERVAL = 30;
  protected class Canvas_Timer extends Timer
  {
    @Override
    public void run()
    {
      render();
    }   
  }
  
  // ------------------------------------------------------------------------
  // ------------------------------------------------------------------------
  
  protected class CanvasAttach implements AttachEvent.Handler
  {
    @Override
    public void onAttachOrDetach(AttachEvent event)
    {
      printElementsPosition(viewport.getElement(), "Viewport");
      printElementsPosition(lblApp.getElement(), "App Label");
      printElementsPosition(lblDrag.getElement(), "Drag Label");
    }
    
    public void printElementsPosition(Element e, String header)
    {
      String s = "Abs Left: " + e.getAbsoluteLeft() + " top: " + e.getAbsoluteTop();
      String r = "Left : " + e.getOffsetLeft() + " top: " + e.getOffsetTop();
      System.out.println(header);
      System.out.println(s);
      System.out.println(r);
      System.out.println("");
    }
  }
  
  // ---------------------------------------------------------------------------  
}
