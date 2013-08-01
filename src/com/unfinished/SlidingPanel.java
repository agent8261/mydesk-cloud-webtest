package com.unfinished;

import com.google.gwt.animation.client.Animation;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Image;

import edu.umich.imlc.mydesk.cloud.frontend.app.canvas.DataCache;

public class SlidingPanel extends Composite
{
  static int PANEL_WIDTH = 300;
  static int PANEL_HEIGHT = 300;
  
  static 
  { DataCache.IMPL.canvasStyle().ensureInjected(); }
  
  FlowPanel panel = new FlowPanel();
  Image wLink;
  Image iLink;
  
  // ---------------------------------------------------------------------------
  // ---------------------------------------------------------------------------
  
  public SlidingPanel()
  {
    //wLink = new Image(DataCache.IMPL.invertedlink());
    //iLink = new Image(DataCache.IMPL.waterlink());
    Element e = panel.getElement();
    e.getStyle().setWidth(PANEL_WIDTH, Unit.PX);
    e.getStyle().setHeight(PANEL_HEIGHT, Unit.PX);
    panel.add(wLink);
    initWidget(panel);
  }
  
  // ---------------------------------------------------------------------------
  // ---------------------------------------------------------------------------
  
  public class ScrollAnimator extends Animation
  {
    double posInElement = 0;
    double posOutElement = PANEL_HEIGHT;
    
    @Override
    protected void onStart()
    {
      super.onStart();
      posInElement = 0; posOutElement = PANEL_HEIGHT;
      panel.add(iLink);
      Element e = iLink.getElement();
      e.getStyle().setLeft(PANEL_WIDTH, Unit.PX);
    }
    // .........................................................................S
    @Override
    protected void onUpdate(double progress)
    {
      
    }
    // .........................................................................
    @Override
    protected void onComplete()
    {
      super.onComplete();
    }
  }
  
  // ---------------------------------------------------------------------------
}
