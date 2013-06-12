package edu.umich.imlc.mydesk.cloud.frontend.app.wesketch.web;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.VerticalPanel;

import edu.umich.imlc.mydesk.cloud.frontend.BasicView;
import edu.umich.imlc.mydesk.cloud.frontend.app.wesketch.WeSketchFile_GWT;
import edu.umich.imlc.mydesk.cloud.frontend.canvas.CanvasStyle;
import edu.umich.imlc.mydesk.cloud.frontend.canvas.DataCache;
import edu.umich.imlc.mydesk.cloud.frontend.canvas.Pan_Zoom_Canvas;

//WeSketch: 800x1050
public class WeSketchView extends Composite implements BasicView
{
  private static final int CANVAS_WIDTH = 400;
  private static final int CANVAS_HEIGHT = 400;
  private static final int WORLD_WIDTH = 1100;
  private static final int WORLD_HEIGHT = 1100;
    
  private static final String APP_LABEL = "WeSketch";
  
  private WeSketchFile_GWT sketchfile = null;  
  private FlowPanel corePanel = new FlowPanel();
  private VerticalPanel contentPanel = new VerticalPanel();  
  private WeSketchCanvas sketchCanvas = new WeSketchCanvas();
  
  // ---------------------------------------------------------------------------
  // ---------------------------------------------------------------------------
  
  public WeSketchView()
  {
    CanvasStyle css = DataCache.IMPL.canvasStyle();
    corePanel.setStyleName(css.pzCanvasBorder());
    
    contentPanel.setStyleName(css.contentPane());
    contentPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
    contentPanel.add(sketchCanvas);    
    corePanel.add(contentPanel);
    initWidget(corePanel);
  }

  // ---------------------------------------------------------------------------
  
  public void drawFile(WeSketchFile_GWT file)
  {
    sketchfile = file;
    assert(sketchfile != null);
    sketchCanvas.drawSketchFile();
  }
  
  // ===========================================================================
  // ===========================================================================
  
  private class WeSketchCanvas extends Pan_Zoom_Canvas implements ClickHandler
  {
    private Button nextSlideBtn = new Button(">");
    private Button prevSlideBtn = new Button("<");
    
    private FlowPanel slideCtrlPanel = new FlowPanel();
    private InlineLabel iLblCurrentSlide = new InlineLabel(" 0");
    private InlineLabel iLblSlash = new InlineLabel(" / ");
    private InlineLabel iLblTotalSlides = new InlineLabel("0 ");
    
    // -------------------------------------------------------------------------
    // -------------------------------------------------------------------------
    
    public WeSketchCanvas()
    {
      defaultInit(CANVAS_WIDTH, CANVAS_HEIGHT, WORLD_WIDTH, WORLD_HEIGHT);
      setAppLabel(APP_LABEL);      
      prevSlideBtn.addClickHandler(this);
      nextSlideBtn.addClickHandler(this);
      
      slideCtrlPanel.add(prevSlideBtn);
      slideCtrlPanel.add(iLblCurrentSlide);
      slideCtrlPanel.add(iLblSlash);
      slideCtrlPanel.add(iLblTotalSlides);
      slideCtrlPanel.add(nextSlideBtn);
    }
    
    // ---------------------------------------------------------------------------
    
    public void drawSketchFile()
    {
      iLblTotalSlides.setText(sketchfile.getTotalSlides() + " ");
      updateSlideControls();      
    }
    
    // ---------------------------------------------------------------------------
    
    @Override
    public void onClick(ClickEvent event)
    {
      if(event.getSource() == nextSlideBtn)
        sketchfile.doNextSlide();
      else
        sketchfile.doPrevSlide();
      updateSlideControls();
    }
    
    // ---------------------------------------------------------------------------
    
    @Override
    protected void initCanvas(CanvasStyle css)
    {
      super.initCanvas(css);
      canvasPanel.add(slideCtrlPanel);
    }
    
    // ---------------------------------------------------------------------------
    
    private void updateSlideControls()
    {
      int index = sketchfile.getCurrentSlideIndex() + 1;
      iLblCurrentSlide.setText(" " + index);
      nextSlideBtn.setEnabled(sketchfile.hasNextSlide());
      prevSlideBtn.setEnabled(sketchfile.hasPrevSlide());
      drawFile(sketchfile);
    }
    
    // -------------------------------------------------------------------------
  } // End WeSketchCanvas
  
  // ---------------------------------------------------------------------------
}