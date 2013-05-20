package edu.umich.imlc.mydesk.cloud.frontend.wesketch;


import java.io.Serializable;
import com.google.gwt.canvas.dom.client.Context2d;

public class ClearEvent_GWT extends WeSketchBaseEvent_GWT implements Serializable
{
  static int WIDTH = 800;
  static int HEIGHT = 1050;
  
  private static final long serialVersionUID = 8010213367123885458L;

  // ---------------------------------------------------------------------------
  // ---------------------------------------------------------------------------

  public ClearEvent_GWT(int eventPointer_, String slideID_)
  {
    super(eventPointer_, slideID_);
  }// ctor

  // ---------------------------------------------------------------------------

  public ClearEvent_GWT(int eventPointer_)
  {
    this(eventPointer_, null);
  }// ctor

  // ---------------------------------------------------------------------------

  // No-arg constructor for serialization
  public ClearEvent_GWT()
  {

  }

  // ---------------------------------------------------------------------------

  @Override
  public void draw(Context2d context)
  {    
    context.clearRect(0, 0, WIDTH, HEIGHT);
  }// draw

}// class
