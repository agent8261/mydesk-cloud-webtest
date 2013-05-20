package edu.umich.imlc.mydesk.cloud.frontend.wesketch;

import java.io.Serializable;
import com.google.gwt.canvas.dom.client.Context2d;

/**
 * This class represents a single event in either a WeSketch file or for sending
 * events across Collabrify.<br>
 * When sending events across Collabrify, the slideID field must be populated.<br>
 * When adding events to a slide as part of a file, the slideID field should
 * explicitly cleared in order to save space.
 * 
 */
public abstract class WeSketchBaseEvent_GWT implements
    Comparable<WeSketchBaseEvent_GWT>, Serializable
{
  private static final long serialVersionUID = 8283297976267372174L;
  private String slideID = null;
  private int eventPointer;

  // ---------------------------------------------------------------------------
  // ---------------------------------------------------------------------------

  public WeSketchBaseEvent_GWT(int eventPointer_, String slideID_)
  {
    eventPointer = eventPointer_;
    slideID = slideID_;
  }// ctor

  // ---------------------------------------------------------------------------

  // No-arg constructor for serialization
  public WeSketchBaseEvent_GWT()
  {
  }// ctor

  // ---------------------------------------------------------------------------

  public abstract void draw(Context2d context);

  // ---------------------------------------------------------------------------

  public int compareTo(WeSketchBaseEvent_GWT other)
  {
    return ((Integer) eventPointer).compareTo(other.eventPointer);
  }// compareTo

  // ---------------------------------------------------------------------------

  public String getSlideID()
  {
    return slideID;
  }

  // ---------------------------------------------------------------------------

  public void setSlideID(String slideID_)
  {
    slideID = slideID_;
  }

  // ---------------------------------------------------------------------------

  public int getEventPointer()
  {
    return eventPointer;
  }

  // ---------------------------------------------------------------------------

  public void setEventPointer(int eventPointer_)
  {
    eventPointer = eventPointer_;
  }

}// class
