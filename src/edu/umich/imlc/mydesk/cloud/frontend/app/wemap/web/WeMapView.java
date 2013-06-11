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
  private static final int DEFAULT_GRID_SPACING = 80;
  private static final int DEFAULT_LINE_WIDTH = 2;
  
  private static final double CENTER_X = 0.5 * CANVAS_WIDTH;
  private static final double CENTER_Y = 0.5 * CANVAS_HEIGHT;
  
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
  
  // ---------------------------------------------------------------------------
  // ---------------------------------------------------------------------------
  
  static class WeMapCanvas extends Pan_Zoom_Canvas implements ClickHandler
  {
    Canvas gridCanvas = null;
    Button btnCenterMap = null;
    InlineLabel iLblSpace = null;
    int gridSpace = DEFAULT_GRID_SPACING;
    int lineWidth = DEFAULT_LINE_WIDTH;
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
      orginX = CENTER_X - 0.5 * scaleX * getCanvasWidth();
      orginY = CENTER_Y - 0.5 * scaleY * getCanvasHeight();
      render();
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
      btnCenterMap.addClickHandler(this);
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
      super.renderViewPort(context);
    }
    
    // -------------------------------------------------------------------------
    
    @Override
    protected void renderToBackCanvas()
    {
      super.renderToBackCanvas();
      drawGrid();
      copyToBackCanvas();
    }
    
    // -------------------------------------------------------------------------
    
    void drawGrid()
    {
      Context2d context = gridCanvas.getContext2d();
      context.clearRect(0, 0, world_width, world_height);
      context.save();
      int width = world_width;
      int height = world_height;
      
      scaleGrid();      
      context.setLineWidth(lineWidth);
      context.setStrokeStyle("gray");
      context.beginPath();
      for(int i = 0; i < width; i += gridSpace)
      {
        context.moveTo(i, 0);
        context.lineTo(i, height);
      }
      
      for(int i = 0; i < height; i+= gridSpace)
      {
        context.moveTo(0,i);
        context.lineTo(width, i);        
      }
      context.stroke();
      context.restore();
      drawTestScene(context);
      context.drawImage(backCanvas.getCanvasElement(), 0, 0, 
          world_width, world_height, 0, 0, world_width, world_height);      
    }
    
    // -------------------------------------------------------------------------
    
    void scaleGrid()
    {
      double ratio = world_width / getCanvasWidth();
      if((scaleX >= 1.0) || (scaleY >= 1.0))
      {
        //gridSpace = (int)Math.ceil(DEFAULT_GRID_SPACING * ratio);
        //lineWidth = (int)Math.ceil(DEFAULT_LINE_WIDTH  * ratio);
      }
      else
      {
        gridSpace = DEFAULT_GRID_SPACING;
        lineWidth = DEFAULT_LINE_WIDTH;
      }
    }
    
    // -------------------------------------------------------------------------
    
    void copyToBackCanvas()
    {
      Context2d context = backCanvas.getContext2d();
      context.clearRect(0, 0, world_width, world_height);
      context.drawImage(gridCanvas.getCanvasElement(), 0, 0, 
          world_width, world_height, 0, 0, world_width, world_height);
    }
  } // End WeMapCanvas
  
  // ---------------------------------------------------------------------------
}
