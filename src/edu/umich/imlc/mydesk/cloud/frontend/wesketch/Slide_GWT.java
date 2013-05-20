package edu.umich.imlc.mydesk.cloud.frontend.wesketch;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

import com.google.gwt.canvas.dom.client.Context2d;

/**
 * This class extends TreeMap<Integer, WeSketchBaseEvent>. The events are
 * accessed by event id.
 * 
 */
public class Slide_GWT implements Serializable
{
  /**
   * 
   */
  private static final long serialVersionUID = 4822216555759971714L;
  private String id;
  private byte[] base_bitmap;
  private int event_pointer;
  private TreeMap<Integer, WeSketchBaseEvent_GWT> events = new TreeMap<Integer, WeSketchBaseEvent_GWT>();

  // ---------------------------------------------------------------------------
  // ---------------------------------------------------------------------------

  public Slide_GWT(String id_)
  {
    assert (id_ != null);
    assert (!id_.isEmpty());
    id = id_;
  }// ctor

  // ---------------------------------------------------------------------------

  /**
   * No-arg constructor for serialization.
   * 
   */
  public Slide_GWT()
  {
  }
  
  // ---------------------------------------------------------------------------
  
  public void draw(Context2d context)
  {
    
  }
  
  // ---------------------------------------------------------------------------
  
  public void setBitmap(byte[] bitmap)
  {
    base_bitmap = bitmap;
  }
  
  // ---------------------------------------------------------------------------
  
  public byte[] getBitmap()
  {
    return base_bitmap;
  }
  
  // ---------------------------------------------------------------------------
  
  public void setEventPointer(int pointer)
  {
    event_pointer = pointer;
  }
  
  // ---------------------------------------------------------------------------
  
  public int getEventPointer()
  {
    return event_pointer;
  }

  // ---------------------------------------------------------------------------

  public String getID()
  {
    return id;
  }

  // ---------------------------------------------------------------------------

  /**
   * Inserts an event in-eventPointer-order into the event map.
   */
  public WeSketchBaseEvent_GWT add(WeSketchBaseEvent_GWT event)
  {
    event.setSlideID(null); // eliminate redundancy
    return put(event.getEventPointer(), event);
  }// add

  // ---------------------------------------------------------------------------

  /**
   * Returns hashCode of the slide ID which is a Java String, so the hashCode is
   * guaranteed to be constant across runs of a program.
   */
  @Override
  public int hashCode()
  {
    return id.hashCode();
  }

  // ---------------------------------------------------------------------------

  /**
   * Performs a comparison of slide ID only. Does not compare contents of
   * slides.
   */
  @Override
  public boolean equals(Object obj)
  {
    if( this == obj )
      return true;
    if( obj == null )
      return false;
    if( getClass() != obj.getClass() )
      return false;
    Slide_GWT other = (Slide_GWT) obj;
    if( id == null )
    {
      if( other.id != null )
        return false;
    }
    else if( !id.equals(other.id) )
      return false;
    return true;
  }

  // ---------------------------------------------------------------------------

  public void putAll(Map<? extends Integer, ? extends WeSketchBaseEvent_GWT> map_)
  {    
    // Clear slide IDs in each event to avoid duplication.
    for( WeSketchBaseEvent_GWT event : map_.values() )
    {
      event.setSlideID(null);
    }
    events.putAll(map_);
  }// putAll

  // ---------------------------------------------------------------------------

  public WeSketchBaseEvent_GWT put(Integer key_, WeSketchBaseEvent_GWT value_)
  {
    value_.setSlideID(null);
    return events.put(key_, value_);
  }// put

  // ---------------------------------------------------------------------------

  public WeSketchBaseEvent_GWT remove(Integer key_)
  {
    return events.remove(key_);
  }

  // ---------------------------------------------------------------------------

  /**
   * Calls the values() method of the underlying map. DO NOT MAKE CHANGES.
   */
  public Collection<WeSketchBaseEvent_GWT> getAllEvents()
  {
    return events.values();
  }

  // ---------------------------------------------------------------------------

}// class

