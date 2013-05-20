package edu.umich.imlc.mydesk.cloud.frontend.wesketch.web;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RequiresResize;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;

import edu.umich.imlc.mydesk.cloud.frontend.utilities.FrontendUtil;

/**
 * Defines the View for the WeSketch app. There is no business logic here aside
 * from Canvas refresh.
 * 
 */
class WeSketchView extends DockLayoutPanel implements WeSketchViewInterface
{
  private static final String BORDER_STYLE = "canvasBorder";
  private final String TITLE_STYLE = "weSketchTitle";
  private final String APP_TITLE_STYLE = "weSketchHeader";
  public static final double DEFAULT_WIDTH_TO_HEIGHT_RATIO = .8;
  public static final int DEFAULT_VIRTUAL_COORDINATE_SIZE_X = 800;
  public static final int DEFAULT_VIRTUAL_COORDINATE_SIZE_Y = (int) (DEFAULT_VIRTUAL_COORDINATE_SIZE_X / DEFAULT_WIDTH_TO_HEIGHT_RATIO);
  public static final String DEFAULT_ABSOLUTE_WIDTH = "800px";
  public static final String DEFAULT_ABSOLUTE_HEIGHT = "1000px";

  private final Button backButton = new Button("Back");
  private Canvas frontCanvas;
  private Canvas backCanvas;
  private final Button cancelButton = new Button("Cancel");
  private final Button nextSlideButton = new Button(">");
  private final Button previousSlideButton = new Button("<");
  private final HorizontalPanel slideControlPanel = new HorizontalPanel();
  private final VerticalPanel slideInfoPanel = new VerticalPanel();
  private final Label currentSlideLabel = new Label();
  @SuppressWarnings("unused")
  private final VerticalPanel westPanel = new VerticalPanel();
  @SuppressWarnings("unused")
  private final VerticalPanel eastPanel = new VerticalPanel();
  private final HorizontalPanel northPanel = new HorizontalPanel();
  private final HorizontalPanel southPanel = new HorizontalPanel();
  private final LayoutPanel centerPanel = new LayoutPanel();
  private final HorizontalPanel canvasWrapper = new HorizontalPanel();
  private final Label titleLabel = new Label("Title Label");
  private final VerticalPanel titlePanel = new VerticalPanel();
  private final Label appLabel = new Label("WeSketch");

  // ---------------------------------------------------------------------------
  // ---------------------------------------------------------------------------

  public WeSketchView()
  {
    super(Unit.PCT);
    FrontendUtil.printMethodName();
    
    // NOTE: for some reason, 100% cuts off the right and bottom border edges
    setHeight("95%");
    setWidth("95%");
    setStyleName("roundedCornersWide borderWidthMedium borderColorLightBlue borderStyleSolid paddingMedium marginThick");


    if( !Canvas.isSupported() )
    {
      // TODO: Handle this
    }// if

    frontCanvas = Canvas.createIfSupported();
    frontCanvas.setStyleName(BORDER_STYLE);
    backCanvas = Canvas.createIfSupported();

    // Set the virtual coordinate space
    // (Deliberately not also setting absolute size.)
    frontCanvas.setCoordinateSpaceWidth(DEFAULT_VIRTUAL_COORDINATE_SIZE_X);
    frontCanvas.setCoordinateSpaceHeight(DEFAULT_VIRTUAL_COORDINATE_SIZE_Y);
    backCanvas.setCoordinateSpaceWidth(DEFAULT_VIRTUAL_COORDINATE_SIZE_X);
    backCanvas.setCoordinateSpaceHeight(DEFAULT_VIRTUAL_COORDINATE_SIZE_Y);

    setupNorthPanel();
    setupWestPanel();
    setupEastPanel();
    setupSouthPanel();
    setupCenterPanel();

    addNorth(northPanel, 10);
    // addWest(westPanel, 10);
    // addEast(eastPanel, 10);
    addSouth(southPanel, 10);
    add(centerPanel);
    resizeCanvas();

  }// ctor

  // ---------------------------------------------------------------------------

  private void setupNorthPanel()
  {
    FrontendUtil.printMethodName();
    northPanel.setWidth("100%");
    northPanel.setHeight("100%");
    northPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
    northPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
    appLabel.setStyleName(APP_TITLE_STYLE);
    titlePanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
    titlePanel.add(appLabel);
    titleLabel.setStyleName(TITLE_STYLE);
    titlePanel.add(titleLabel);
    northPanel.add(titlePanel);
    // HTML html = new HTML("<h1>WeSketch Viewer</h1>");
    // northPanel.add(html);
  }//

  // ---------------------------------------------------------------------------

  private void setupWestPanel()
  {

  }//

  // ---------------------------------------------------------------------------

  private void setupEastPanel()
  {

  }//

  // ---------------------------------------------------------------------------

  private void setupSouthPanel()
  {
    FrontendUtil.printMethodName();
    southPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
    southPanel.setWidth("100%");
    slideControlPanel.add(previousSlideButton);
    slideControlPanel.add(nextSlideButton);
    slideInfoPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
    slideInfoPanel.add(currentSlideLabel);
    slideInfoPanel.add(slideControlPanel);
    southPanel.add(slideInfoPanel);
  }//

  // ---------------------------------------------------------------------------

  private void setupCenterPanel()
  {
    FrontendUtil.printMethodName();
    canvasWrapper.setHeight("100%");
    canvasWrapper.setWidth("100%");
    canvasWrapper.setHorizontalAlignment(HorizontalPanel.ALIGN_CENTER);
    canvasWrapper.add(frontCanvas);
    centerPanel.add(canvasWrapper);
  }//

  // ---------------------------------------------------------------------------
  // ---------------------------------------------------------------------------

  @Override
  public Label getCurrentSlideLabel()
  {
    return currentSlideLabel;
  }

  // ---------------------------------------------------------------------------

  @Override
  public Label getTitleLabel()
  {
    return titleLabel;
  }

  // ---------------------------------------------------------------------------

  @Override
  public HasClickHandlers getBackButton()
  {
    return backButton;
  }


  // ---------------------------------------------------------------------------

  @Override
  public HasClickHandlers getCancelButton()
  {
    return cancelButton;
  }

  // ---------------------------------------------------------------------------

  @Override
  public Button getPreviousSlideButton()
  {
    return previousSlideButton;
  }

  // ---------------------------------------------------------------------------

  @Override
  public Button getNextSlideButton()
  {
    return nextSlideButton;
  }

  // ---------------------------------------------------------------------------

  @Override
  public HasValue<String> getFileNameBox()
  {
    // TODO Auto-generated method stub
    return null;
  }

  // ---------------------------------------------------------------------------

  @Override
  public Widget asWidget()
  {
    return this;
  }

  // ---------------------------------------------------------------------------

  @Override
  public Context2d getFrontBufferContext()
  {
    return frontCanvas.getContext2d();
  }

  // ---------------------------------------------------------------------------

  @Override
  public Context2d getBackBufferContext()
  {
    return backCanvas.getContext2d();
  }

  // ---------------------------------------------------------------------------

  @Override
  public Canvas getFrontCanvas()
  {
    return frontCanvas;
  }

  // ---------------------------------------------------------------------------

  @Override
  public Canvas getBackCanvas()
  {
    return backCanvas;
  }

  // ---------------------------------------------------------------------------

  // public void onResize()
  // {
  // super.onResize();
  // }
  @Override
  public void onResize()
  {
    for( Widget child : getChildren() )
    {
      if( child instanceof RequiresResize )
      {
        ((RequiresResize) child).onResize();
      }// if
    }// for
    resizeCanvas();
  }// onResize

  // ---------------------------------------------------------------------------

  public void resizeCanvas()
  {
    Widget centerWidget = centerPanel;
    Element container = getWidgetContainerElement(centerWidget);
    int height = container.getOffsetHeight() - 10;
    int width = container.getOffsetWidth() - 10;
    if( height < 0 || width < 0 )
      return;

    if( height * DEFAULT_WIDTH_TO_HEIGHT_RATIO > width )
    {
      height = (int) (width / DEFAULT_WIDTH_TO_HEIGHT_RATIO);
    }
    else
    {
      width = (int) (height * DEFAULT_WIDTH_TO_HEIGHT_RATIO);
    }


    // System.out.println("CONTAINER SIZE: " + width + "  " + height);
    frontCanvas.setHeight("" + height + "px");
    frontCanvas.setWidth("" + width + "px");
    backCanvas.setHeight("" + height + "px");
    backCanvas.setWidth("" + width + "px");
  }// resizeCanvas;

  // ---------------------------------------------------------------------------

  public void clearFrontCanvas()
  {
    frontCanvas.getContext2d().clearRect(0, 0,
        DEFAULT_VIRTUAL_COORDINATE_SIZE_X, DEFAULT_VIRTUAL_COORDINATE_SIZE_Y);
  }

}// class
