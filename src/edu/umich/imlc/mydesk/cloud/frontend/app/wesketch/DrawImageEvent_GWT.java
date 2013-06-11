package edu.umich.imlc.mydesk.cloud.frontend.app.wesketch;

import java.io.Serializable;
import com.google.gwt.canvas.dom.client.Context2d;

public class DrawImageEvent_GWT extends WeSketchBaseEvent_GWT implements Serializable
{
  private static final long serialVersionUID = 3180172034142127726L;
  public int imageID;
  public byte[] imageData;
  public Point_GWT topLeft;
  public Point_GWT bottomRight;

  // ---------------------------------------------------------------------------
  // ---------------------------------------------------------------------------

  public DrawImageEvent_GWT(int eventPointer_, String slideID_)
  {
    super(eventPointer_, slideID_);
  }// ctor

  // ---------------------------------------------------------------------------

  public DrawImageEvent_GWT(int eventPointer_)
  {
    this(eventPointer_, null);
  }// ctor

  // ---------------------------------------------------------------------------

  /**
   * No-arg constructor for serialization
   */
  public DrawImageEvent_GWT()
  {
  }

  // ---------------------------------------------------------------------------

  /**
   * TODO: Find alternate method of base64-converting byte[] to String on
   * client-side.
   */
  @Override
  public void draw(Context2d context)
  {
    //Context2d context = canvas.getContext2d();
    // String imageString = convertBMPImageToString(imageData);
    // Image image = new Image(imageString);
    // ImageElement imageElement = ImageElement.as(image.getElement());
    // context.beginPath();
    // context.drawImage(imageElement, topLeft.x, topLeft.y, bottomRight.x
    // - topLeft.x, bottomRight.y - topLeft.y);
    // context.closePath();
  }// draw

}// class
