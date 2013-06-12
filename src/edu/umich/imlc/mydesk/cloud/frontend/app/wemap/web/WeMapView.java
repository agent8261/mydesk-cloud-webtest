package edu.umich.imlc.mydesk.cloud.frontend.app.wemap.web;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.CssColor;
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
    int gridStartX = 0; int gridStartY = 0;
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
        System.out.println("Grid x: " + gridStartX + " y: " + gridStartY);
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
      context.save();
      context.setFillStyle(CssColor.make(200, 0, 0));
      context.fillRect(360.0, 0.0, 24, 24);
      context.restore();
      
      context.save();
      int width = getCanvasWidth();
      int height = getCanvasHeight();
      
      scaleGrid();
      context.setLineWidth(lineWidth);
      context.setStrokeStyle("gray");
      context.beginPath();
      for(int i = gridStartX; i < width; i += gridSpace)
      {
        context.moveTo(i, 0);
        context.lineTo(i, height);
      }
      
      for(int i = gridStartY; i < height; i += gridSpace)
      {
        context.moveTo(0,i);
        context.lineTo(width, i);        
      }
      context.stroke();
      context.restore();
      
      context.save();
      context.setStrokeStyle("black");
      context.setLineWidth(lineWidth * 2);
      context.translate(originX, originY);
      context.scale(scaleX, scaleY);
      
      context.beginPath();
      context.moveTo(0.0, 0.0);
      context.lineTo(0, world_height);
      context.moveTo(0.0, 0.0);
      context.lineTo(0, 0 - world_height);
      
      context.moveTo(0.0, 0.0);
      context.lineTo(world_width, 0.0);
      context.moveTo(0.0, 0.0);
      context.lineTo(0 - world_width, 0.0);
      context.stroke();
      context.restore();
      //
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
          round((int)Math.floor(MIN_GRID_SPACING / (IMPLIED_SCALE * scaleX)));      
      gridSpace = (int)Math.floor(worldSize * IMPLIED_SCALE * scaleX);
      
      origin.a1 = round((int)((0 - originX) / (IMPLIED_SCALE * scaleX)));
      origin.a2 = round((int)((0 - originY) / (IMPLIED_SCALE * scaleX)));
      gridStartX = (int)Math.floor(origin.a1 * scaleX * IMPLIED_SCALE + originX);
      gridStartY = (int)Math.floor(origin.a2 * scaleY * IMPLIED_SCALE + originY);
    }
    
    // -------------------------------------------------------------------------
    
    void copyToBackCanvas()
    {
      Context2d context = backCanvas.getContext2d();
      context.clearRect(0, 0, world_width, world_height);
      context.drawImage(gridCanvas.getCanvasElement(), 0, 0, 
          world_width, world_height, 0, 0, world_width, world_height);
    }
    
    // ---------------------------------------------------------------------------
    
    int round(int numToRound) 
    { 
      int remainder = numToRound % WEMAP_GRID_SPACING;     
      if (remainder == 0)
        return numToRound;
       
      //return (numToRound + WEMAP_GRID_SPACING - remainder);
      return (numToRound - remainder);
    } 
  } // End WeMapCanvas
  
  // ---------------------------------------------------------------------------
}
