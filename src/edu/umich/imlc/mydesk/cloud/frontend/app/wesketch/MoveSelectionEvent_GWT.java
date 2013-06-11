package edu.umich.imlc.mydesk.cloud.frontend.app.wesketch;

import java.io.Serializable;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.ImageData;

public class MoveSelectionEvent_GWT extends WeSketchBaseEvent_GWT implements
    Serializable
{
  /**
   * 
   */
  private static final long serialVersionUID = 72179619193961635L;

  public Point_GWT sourceTopLeft;
  public Point_GWT sourceBottomRight;
  public Point_GWT destinationTopLeft;
  public Point_GWT destinationBottomRight;

  // ---------------------------------------------------------------------------
  // ---------------------------------------------------------------------------

  public MoveSelectionEvent_GWT(int eventPointer, String slideID)
  {
    super(eventPointer, slideID);
  }// ctor

  // ---------------------------------------------------------------------------

  public MoveSelectionEvent_GWT(int eventPointer)
  {
    this(eventPointer, null);
  }// ctor

  // ---------------------------------------------------------------------------

  /**
   * No-arg constructor for serialization
   */
  public MoveSelectionEvent_GWT()
  {
  }

  // ---------------------------------------------------------------------------

  @Override
  public void draw(Context2d context)
  {
    //context.save();
    ImageData data = context.getImageData(sourceTopLeft.x, sourceTopLeft.y,
        Math.abs(sourceTopLeft.x - sourceBottomRight.x),
        Math.abs(sourceTopLeft.y - sourceBottomRight.y));

    context.clearRect(sourceTopLeft.x, sourceTopLeft.y,
        Math.abs(sourceTopLeft.x - sourceBottomRight.x),
        Math.abs(sourceTopLeft.y - sourceBottomRight.y));
    
    context.putImageData(data, destinationTopLeft.x, destinationTopLeft.y);
    //context.restore();
  }// draw

}// class
