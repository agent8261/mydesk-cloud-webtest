package edu.umich.imlc.mydesk.cloud.frontend.app.wemap;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.CanvasGradient;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.ImageData;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;

public class CPicker extends Composite implements ClickHandler
{
  // ---------------------------------------------------------------------------
  
  public static interface ButtonHandler
  {
    public void onOk(String selectedColor);
    public void onCancel();
  }
  
  // ---------------------------------------------------------------------------
  
  @UiTemplate("CPicker.ui.xml")
  interface CPickerUiBinder extends UiBinder<HTMLPanel, CPicker>{}  
  private static CPickerUiBinder uiBinder = GWT.create(CPickerUiBinder.class);
  
  // ---------------------------------------------------------------------------
  
  private static final int SUNK_EVENTS = Event.ONMOUSEDOWN | Event.ONMOUSEUP |
      Event.ONMOUSEOUT | Event.ONMOUSEMOVE | Event.ONKEYUP;  
  private static final int SATVAL_SIZE = 200;
  private static final int HUE_H = 200, HUE_W = 45;
  private static final int CANVAS_PADDING = 4;
  private static final int CANVAS_OFFSET  = 2;
  private static final int GRAD_INC = 4;  
  private static final int SV_SIZE_OFF = SATVAL_SIZE + CANVAS_PADDING;
  private static final int HUE_H_OFF = HUE_H + CANVAS_PADDING;
  private static final int HUE_W_OFF = HUE_W + CANVAS_PADDING;
  private static final double KEY_INCREMENT = 2;
  
  // ---------------------------------------------------------------------------
  
  @UiField
  Element divSelSatVal;
  
  @UiField
  Button btnOk, btnCancel;

  @UiField(provided=true) 
  Canvas canvasHue, canvasSatValue;
  
  // ---------------------------------------------------------------------------
  
  private Canvas canvasBackHue = null, canvasBackSatVal = null;  
  private Indicator indHue, indSatVale;
  private ButtonHandler btnHandler = null;
  private boolean isMovingHueGage = false;
  private boolean isMovingSatValGage = false;
  
  private Color.Hsv hsv = new Color.Hsv();
  private String currentSelectedColor = null;
  
  // ---------------------------------------------------------------------------
  // ---------------------------------------------------------------------------
  
  public CPicker()
  {
    initCanvasHue();
    initCanvasSatVal();
    initWidget(uiBinder.createAndBindUi(this));
    sinkEvents(SUNK_EVENTS);
    btnOk.addClickHandler(this);
    btnCancel.addClickHandler(this);    
    drawBackHueCanvas();
    drawHueCanvas();
  }
  
  // ---------------------------------------------------------------------------
  
  public void addButtonHandler(ButtonHandler handler)
  {
    if(handler == null)    
      throw new IllegalArgumentException();
    btnHandler = handler;
  }
  
  // ---------------------------------------------------------------------------
  
  @Override
  public void onClick(ClickEvent event)
  {
    if(btnHandler == null){ return; }    
    Object src = event.getSource();
    if(src == btnOk){ btnHandler.onOk(currentSelectedColor); }
    else if(src == btnCancel){ btnHandler.onCancel(); }
  }

  // ---------------------------------------------------------------------------
  
  @Override
  public void onBrowserEvent(Event event)
  {
    super.onBrowserEvent(event);
    double offX = 0.0, offY = 0.0;
    Element e = Element.as(event.getEventTarget());
    
    int type = event.getTypeInt();
    if((type & SUNK_EVENTS) != 0)
    {
      offX = event.getClientX() - e.getAbsoluteLeft();
      offY = event.getClientY() - e.getAbsoluteTop();
      event.preventDefault();
    }
    switch(type)
    {
      case Event.ONMOUSEDOWN:  doMouseDown(event, e, offX, offY);  break;
      case Event.ONMOUSEUP:    doMouseUp(event, e, offX, offY);    break;
      case Event.ONMOUSEMOVE:  doMouseMove(event, e, offX, offY);  break;
      case Event.ONMOUSEOUT:   doMouseOut(event, e, offX, offY);   break;
      case Event.ONKEYUP: case Event.ONKEYPRESS:
        doKeyUpPress(event, e, offX, offY); break;
      default: break;
    }
  }
  
  // ---------------------------------------------------------------------------
  
  private void initCanvasHue()
  {
    canvasHue = Canvas.createIfSupported();
    canvasHue.setCoordinateSpaceHeight(HUE_H_OFF);
    canvasHue.setCoordinateSpaceWidth(HUE_W_OFF);
    
    indHue = new Indicator(HUE_W, 7, HUE_W, HUE_H);
    indHue.setX(0.5 * HUE_W).setY(1);
    canvasBackHue = Canvas.createIfSupported();
    canvasBackHue.setCoordinateSpaceHeight(HUE_H_OFF);
    canvasBackHue.setCoordinateSpaceWidth(HUE_W_OFF);
  }
  
  // ---------------------------------------------------------------------------
  
  private void initCanvasSatVal()
  {
    canvasSatValue = Canvas.createIfSupported();
    canvasSatValue.setCoordinateSpaceHeight(SV_SIZE_OFF);
    canvasSatValue.setCoordinateSpaceWidth(SV_SIZE_OFF);
    
    canvasBackSatVal = Canvas.createIfSupported();
    canvasBackSatVal.setCoordinateSpaceHeight(SV_SIZE_OFF);
    canvasBackSatVal.setCoordinateSpaceWidth(SV_SIZE_OFF);
    
    indSatVale = new Indicator(10, 10, SATVAL_SIZE, SATVAL_SIZE);
    indSatVale.setCenter(0, 0);
    //indSatVale.setCenter(SATVAL_SIZE - 1, SATVAL_SIZE - 1);
  }
  
  // ---------------------------------------------------------------------------
  
  private void doKeyUpPress(Event event, Element element, double x, double y)
  {
    int key = event.getKeyCode();
    if(element == canvasHue.getElement())
    {
      switch(key)
      {
        case KeyCodes.KEY_UP:
          indHue.incrementY(-1.0 * KEY_INCREMENT); break;
        case KeyCodes.KEY_DOWN:
          indHue.incrementY(KEY_INCREMENT); break;
        default: break;
      }
      if((key == KeyCodes.KEY_UP) || (key == KeyCodes.KEY_DOWN))
      { drawHueCanvas(); }
    }
    else if(element == canvasSatValue.getCanvasElement())
    {
      switch(key)
      {
        case KeyCodes.KEY_UP:
          indSatVale.incrementY(-1.0 * KEY_INCREMENT); break;
        case KeyCodes.KEY_DOWN:
          indSatVale.incrementY(KEY_INCREMENT); break;
        case KeyCodes.KEY_LEFT:
          indSatVale.incrementX(-1.0 * KEY_INCREMENT); break;
        case KeyCodes.KEY_RIGHT:
          indSatVale.incrementY(KEY_INCREMENT); break;
        default: break;
      }
      if((key == KeyCodes.KEY_UP) || (key == KeyCodes.KEY_DOWN) || 
          (key == KeyCodes.KEY_LEFT) || (key == KeyCodes.KEY_RIGHT))
      { drawSatValCanvas(); }
    }
  }
  
  // ---------------------------------------------------------------------------
  
  private void doMouseDown(Event event, Element element, double x, double y)
  {
    if(element == canvasHue.getElement())
    {
      isMovingHueGage = true;
      indHue.doStartY(y);
      drawHueCanvas();
    }
    else if(element == canvasSatValue.getCanvasElement())
    {
      isMovingSatValGage = true;
      indSatVale.doStartX(x).doStartY(y);
      drawSatValCanvas();
    }
  }
  
  // ---------------------------------------------------------------------------
  
  private void doMouseUp(Event event, Element element, double x, double y)
  {
    if(element == canvasHue.getElement())
    {
      isMovingHueGage = false;
      indHue.doMoveY(y).doFinish();
      drawHueCanvas();
    }
    else if(element == canvasSatValue.getCanvasElement())
    {
      isMovingSatValGage = false;
      indSatVale.doMoveX(x).doMoveY(y).doFinish();
      drawSatValCanvas();
    }
  }
  
  // ---------------------------------------------------------------------------
  
  private void doMouseMove(Event event, Element element, double x, double y)
  {
    if(element == canvasHue.getElement())
    {
      if(isMovingHueGage)
      {
        indHue.doMoveY(y);
        drawHueCanvas();
      }
    }
    else if(element == canvasSatValue.getCanvasElement())
    {
      if(isMovingSatValGage)
      {
        indSatVale.doMoveX(x).doMoveY(y);
        drawSatValCanvas();
      }
    }
  }
  
  // ---------------------------------------------------------------------------
  
  private void doMouseOut(Event event, Element element, double x, double y)
  {
    if(element == canvasHue.getElement())
    {      
      if(isMovingHueGage)
      {
        indHue.doMoveY(y).doFinish();
        drawHueCanvas();
      }
      isMovingHueGage = false;
    }
    else if(element == canvasSatValue.getCanvasElement())
    {
      if(isMovingSatValGage)
      {
        indSatVale.doMoveX(x).doMoveY(y).doFinish();
        drawSatValCanvas();
      }
      isMovingSatValGage = false;
    }
  }
  
  // ---------------------------------------------------------------------------
  
  private void drawBackHueCanvas()
  {
    Context2d context = canvasBackHue.getContext2d();
    context.save();
    context.clearRect(0, 0, HUE_W_OFF, HUE_H_OFF);
    context.translate(CANVAS_OFFSET, CANVAS_OFFSET);
    CanvasGradient grad = context.createLinearGradient(0, HUE_H, 0, 0);
    
    grad.addColorStop(0.00, "#ff0000"); // 0%
    grad.addColorStop(0.13, "#ff00ff"); // 13%
    grad.addColorStop(0.25, "#8000ff"); // 25%
    grad.addColorStop(0.38, "#0040ff"); // 38%
    grad.addColorStop(0.50, "#00ffff"); // 50%
    
    grad.addColorStop(0.63, "#00ff40"); // 63%
    grad.addColorStop(0.75, "#0bed00"); // 75%
    grad.addColorStop(0.88, "#ffff00"); // 88%
    grad.addColorStop(1.00, "#ff0000"); // 100%
    
    context.setFillStyle(grad);
    context.fillRect(0, 0, HUE_W, HUE_H);
    context.restore();
  }
  
  // ---------------------------------------------------------------------------
  
  private void drawBackSatValCanvas()
  {
    Context2d context = canvasBackSatVal.getContext2d();
    context.save();
    context.clearRect(0, 0, SV_SIZE_OFF, SV_SIZE_OFF);
    context.translate(CANVAS_OFFSET, CANVAS_OFFSET);
    for (int x = 0; x < SATVAL_SIZE; x += GRAD_INC)
    {
      CanvasGradient grad = context.createLinearGradient(x, 0, x, SATVAL_SIZE - 1);
      hsv.jso.setS((int)Math.round(x * 100 / (SATVAL_SIZE - 1) ));
      hsv.jso.setV(0);
      grad.addColorStop(0, hsv.toString());
      hsv.jso.setV(100);
      grad.addColorStop(1, hsv.toString());      
      context.setFillStyle(grad);
      context.fillRect(x, 0, GRAD_INC, SATVAL_SIZE);      
    }
    context.restore();
  }
  
  // ---------------------------------------------------------------------------
  
  private void drawHueCanvas()
  {
    Context2d context = canvasHue.getContext2d();
    context.save();
    context.clearRect(0, 0, HUE_W_OFF, HUE_H_OFF);
    context.drawImage(canvasBackHue.getCanvasElement(), 0, 0);

    context.translate(CANVAS_OFFSET, CANVAS_OFFSET);
    indHue.draw(context);
    context.restore();
    updateHue();
  }
  
  // ---------------------------------------------------------------------------
  
  private void drawSatValCanvas()
  {
    Context2d context = canvasSatValue.getContext2d();
    context.save();
    context.clearRect(0, 0, SV_SIZE_OFF, SV_SIZE_OFF);
    context.drawImage(canvasBackSatVal.getCanvasElement(), 0, 0);
    
    context.translate(CANVAS_OFFSET, CANVAS_OFFSET);
    indSatVale.draw(context);
    context.restore();
    updateColor();
  }

  // ---------------------------------------------------------------------------
  
  private void updateHue()
  {
    int hue = (int)Math.round(360.0 * (indHue.getY() + CANVAS_OFFSET) / HUE_H);
    hsv.set(hue, 100, 100);
    //divSelHue.getStyle().setBackgroundColor(hsv.toString());
    drawBackSatValCanvas();
    drawSatValCanvas();
  }
  
  // ---------------------------------------------------------------------------
  
  private void updateColor()
  {
    double x = indSatVale.getX() + CANVAS_OFFSET;
    double y = indSatVale.getY() + CANVAS_OFFSET;
    ImageData img = canvasSatValue.getContext2d().getImageData(x, y, 1, 1);
    int r = img.getRedAt(0, 0);
    int g = img.getGreenAt(0, 0);
    int b = img.getBlueAt(0, 0);
    currentSelectedColor = Color.toRgbString(r, g, b);
    divSelSatVal.getStyle().setBackgroundColor(currentSelectedColor);
  }

  // ---------------------------------------------------------------------------
}













