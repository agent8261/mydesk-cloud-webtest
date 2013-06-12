package edu.umich.imlc.mydesk.cloud.frontend.app.wemap.web;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.VerticalPanel;

import edu.umich.imlc.mydesk.cloud.frontend.BasicView;
import edu.umich.imlc.mydesk.cloud.frontend.app.wemap.WeMapFile_GWT;
import edu.umich.imlc.mydesk.cloud.frontend.canvas.CanvasStyle;
import edu.umich.imlc.mydesk.cloud.frontend.canvas.DataCache;
import edu.umich.imlc.mydesk.cloud.frontend.canvas.Pan_Zoom_Canvas;

public class WeMapView extends Composite implements BasicView
{
  private static final int CANVAS_WIDTH = 400;
  private static final int CANVAS_HEIGHT = 400;
  private static final int WORLD_WIDTH = 4000;
  private static final int WORLD_HEIGHT = 4000;
  private static final int WEMAP_GRID_SPACING = 40;
  private static final int MIN_GRID_SPACING = 24;
  private static final int DEFAULT_LINE_WIDTH = 1;
  
  private static final double CENTER_X = 0.5 * CANVAS_WIDTH;
  private static final double CENTER_Y = 0.5 * CANVAS_HEIGHT;
  private static final double IMPLIED_SCALE = CANVAS_WIDTH / (double)WORLD_WIDTH;
  private static final String APP_LABEL = "WeMap";
  
  private WeMapCanvas pzCanvas = new WeMapCanvas();
  private FlowPanel corePanel = new FlowPanel();
  private VerticalPanel contentPanel = new VerticalPanel();
  
  // ---------------------------------------------------------------------------
  // ---------------------------------------------------------------------------
  
  public WeMapView()
  {
    CanvasStyle css = DataCache.IMPL.canvasStyle();
    corePanel.setStyleName(css.pzCanvasBorder());
    corePanel.addStyleName(css.alignCenter());
    corePanel.addStyleName(css.outlineOn());
    contentPanel.setStyleName(css.contentPane());
    contentPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
    
    contentPanel.add(pzCanvas);
    corePanel.add(contentPanel);
    initWidget(corePanel);
  }

  // ---------------------------------------------------------------------------
  
  public void drawFile(WeMapFile_GWT file)
  {
    pzCanvas.drawFile(file);      
  }
  
  // ===========================================================================
  // ===========================================================================
  
  static class WeMapCanvas extends Pan_Zoom_Canvas implements ClickHandler
  {
    Canvas gridCanvas = null;
    Button btnCenterMap = null;
    Button btnPrint = null;
    InlineLabel iLblSpace = null;
    int gridSpace = MIN_GRID_SPACING;
    int lineWidth = DEFAULT_LINE_WIDTH;
    int width, height;
    
    Vec2d origin = new Vec2d();
    
    // -------------------------------------------------------------------------
    // -------------------------------------------------------------------------
    
    public WeMapCanvas()
    {
      defaultInit(CANVAS_WIDTH, CANVAS_HEIGHT, WORLD_WIDTH, WORLD_HEIGHT);      
      setAppLabel(APP_LABEL);
    }
    
    // ---------------------------------------------------------------------------
    
    @Override
    public void onClick(ClickEvent event)
    {
      if(event.getSource() == btnCenterMap)
      {
        originX = CENTER_X - 0.5 * scaleX * getCanvasWidth();
        originY = CENTER_Y - 0.5 * scaleY * getCanvasHeight();
        render();
      }
      else
      {
        printOrigin();
        System.out.println("rounded x: " + origin.a1 + " y: " + origin.a2);
      }
    }
    
    // -------------------------------------------------------------------------
    
    @Override
    protected void initHeader(CanvasStyle css)
    {
      super.initHeader(css);
      iLblSpace = new InlineLabel(" ");
      headerPanel.add(iLblSpace);
      btnCenterMap = new Button("Center");
      headerPanel.add(btnCenterMap);
      btnPrint = new Button("Print");
      headerPanel.add(btnPrint);
      btnCenterMap.addClickHandler(this);
      btnPrint.addClickHandler(this);
      btnCenterMap.getElement().setId("btnCenter");      
    }
   
    // -------------------------------------------------------------------------
    
    @Override
    protected void initCanvas(CanvasStyle css)
    {
      super.initCanvas(css);
      gridCanvas = Canvas.createIfSupported();
      assert(gridCanvas != null);
      gridCanvas.setCoordinateSpaceWidth(world_width);
      gridCanvas.setCoordinateSpaceHeight(world_height);
      hiddenPanel.add(gridCanvas);
    }
 
    // -------------------------------------------------------------------------
    
    @Override
    protected void renderViewPort(Context2d context)
    {
      drawGrid(context);
      super.renderViewPort(context);
    }
    
    // -------------------------------------------------------------------------
    
    void drawGrid(Context2d context)
    {
      scaleGrid();
      width = roundUp((int)Math.ceil(world_width * IMPLIED_SCALE));
      height = roundUp((int)Math.ceil(world_height * IMPLIED_SCALE));

      Line [] borders = new Line[4];
      borders[0] = set(0.0, 0.0, 0.0, height);
      borders[1] = set(0.0, 0.0, width, 0.0);
      
      context.save();
      context.translate(originX, originY);
      context.scale(scaleX, scaleY);
      context.setLineWidth(lineWidth);
      context.setStrokeStyle("gray");
      
      context.beginPath();            
      for(int j = 0; true; j += gridSpace)
      {
        drawLine(context, j, 0.0, j, height);
        if(!(j < width))
        {  j += gridSpace;  borders[2] = set(j, 0.0, j, height);  break;  }
      }
      /*
      for(int j = 0; true; j += gridSpace)
      {
        drawLine(context, 0.0, j, width, j);
        if(!(j < height))
        {  j += gridSpace;  borders[3] = set(0.0, j, width, j);  break;  }
      }*/
      context.stroke();

      //drawGridBorder(context, borders);
      context.restore();
    }
    
    // -------------------------------------------------------------------------
    
    /**
     * Adjust the gridSpace:
     *   1. scale the "min grid spacing" to the world size
     *   2. round up to the nearest multiple of 40
     *   3. convert back to view-port size
     *  
     * Adjust the grid's starting point
     *   1. convert view-port's origin to world coordinates
     *   2. round up to the nearest multiple of 40
     *   3. convert back to view coordinates
     */
    void scaleGrid()
    {
      int worldSize = 
          roundDown((int)Math.floor(MIN_GRID_SPACING / (IMPLIED_SCALE * scaleX)));      
      gridSpace = (int)Math.floor(worldSize * IMPLIED_SCALE * scaleX);
    }
    
    // -------------------------------------------------------------------------
    
    void drawLine(Context2d context, double mX, double mY, double lX, double lY)
    {
      context.moveTo(mX, lY); context.lineTo(lX, lY);
    }
    
    // -------------------------------------------------------------------------
    
    void drawGridBorder(Context2d context, Line [] lines)
    {
      context.setStrokeStyle("black");
      context.setLineWidth(lineWidth * 2);
      
      context.beginPath();
      lines[0].draw(context);
      lines[1].draw(context);
      lines[2].draw(context);
      //for(Line line: lines)
        //line.draw(context);
      context.stroke();      
    }
    
    // ---------------------------------------------------------------------------
    
    int roundDown(int numToRound) 
    { 
      int r = numToRound % WEMAP_GRID_SPACING;
      return (r == 0) ? numToRound : (numToRound - r);
    }
    
    // ---------------------------------------------------------------------------
    
    int roundUp(int numToRound) 
    { 
      int r = numToRound % WEMAP_GRID_SPACING;
      return (r == 0) ? numToRound : (numToRound + WEMAP_GRID_SPACING - r);
    }
    
    // -------------------------------------------------------------------------
    // -------------------------------------------------------------------------
    
    /*
     * Used for performance. 
     * Saves location so line can be drawn later
     */
    class Line
    {
      double mX, mY;
      double lX, lY;
      
      void draw(Context2d context)
      {
        drawLine(context, mX, mY, lX, lY);
      }
    } // End Line
    
    // --------------------------------------------------------------------------
    
    Line set(double mX, double mY, double lX, double lY)
    {
      Line l = new Line();
      l.mX = mX; l.mY = mY; l.lX = lX; l.lY = lY;
      return l;
    }
    
    // --------------------------------------------------------------------------
  } // End WeMapCanvas
  
  // ----------------------------------------------------------------------------
}
