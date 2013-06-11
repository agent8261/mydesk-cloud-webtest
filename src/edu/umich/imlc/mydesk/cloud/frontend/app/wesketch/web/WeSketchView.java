package edu.umich.imlc.mydesk.cloud.frontend.app.wesketch.web;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Label;
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
  
  private WeSketchFile_GWT file = null;
  
  private FlowPanel corePanel = new FlowPanel();
  private VerticalPanel contentPanel = new VerticalPanel();  
  private Grid slideCtrl = new Grid(1,4);
  
  private Pan_Zoom_Canvas pzCanvas = null;    
  private Button nextSlideBtn = new Button(">");
  private Button prevSlideBtn = new Button("<");
  
  private Label currentSlideIndexLbl = new Label("0");
  private Label totalSlidesLbl = new Label("/ 0");
  
  // ---------------------------------------------------------------------------
  // ---------------------------------------------------------------------------
  
  public WeSketchView()
  {
    pzCanvas = new Pan_Zoom_Canvas();
    pzCanvas.defaultInit(CANVAS_WIDTH, CANVAS_HEIGHT, WORLD_WIDTH, WORLD_HEIGHT);
    pzCanvas.setAppLabel(APP_LABEL);    

    CanvasStyle css = DataCache.IMPL.canvasStyle();
    corePanel.setStyleName(css.pzCanvasBorder());
    slideCtrl.setStyleName(css.WeSketchSlideCtrl());
    contentPanel.setStyleName(css.contentPane());
    contentPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
    
    prevSlideBtn.addClickHandler(new DoPrevSlideHandler());
    nextSlideBtn.addClickHandler(new DoNextSlideHandler());
    
    slideCtrl.setWidget(0, 0, prevSlideBtn);
    slideCtrl.setWidget(0, 1, currentSlideIndexLbl);
    slideCtrl.setWidget(0, 2, totalSlidesLbl);
    slideCtrl.setWidget(0, 3, nextSlideBtn);
    
    //pzCanvas.add(slideCtrl);
    contentPanel.add(pzCanvas);
    corePanel.add(contentPanel);
    initWidget(corePanel);
  }

  // ---------------------------------------------------------------------------
  
  public void drawFile(WeSketchFile_GWT file)
  {
    this.file = file;
    assert(this.file != null);
    String total = "/ " + file.getTotalSlides();
    totalSlidesLbl.setText(total);
    updateSlideControls();
  }
  
  // ---------------------------------------------------------------------------
  
  private void updateSlideControls()
  {
    int index = file.getCurrentSlideIndex();
    currentSlideIndexLbl.setText(String.valueOf(index + 1));
    nextSlideBtn.setEnabled(file.hasNextSlide());
    prevSlideBtn.setEnabled(file.hasPrevSlide());
    pzCanvas.drawFile(file);
  }
  
  // ---------------------------------------------------------------------------
  // ---------------------------------------------------------------------------
  
  class DoNextSlideHandler implements ClickHandler
  {
    @Override
    public void onClick(ClickEvent event)
    {
      file.doNextSlide();
      updateSlideControls();
    }
  }
  
  // ---------------------------------------------------------------------------
  // ---------------------------------------------------------------------------
  
  class DoPrevSlideHandler implements ClickHandler
  {
    @Override
    public void onClick(ClickEvent event)
    {
      file.doPrevSlide();
      updateSlideControls();
    }
  }
  // ---------------------------------------------------------------------------
}