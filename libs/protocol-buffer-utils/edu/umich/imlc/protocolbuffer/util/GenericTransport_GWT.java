package edu.umich.imlc.protocolbuffer.util;

import java.io.Serializable;

/**
 * GWT-compatible version of GenericTransport.
 * 
 * @author vidal@umich.edu
 */
public class GenericTransport_GWT implements Serializable
{
  /**
   * 
   */
  private static final long serialVersionUID = -4903829004208256171L;

  private byte[] payload;
  private GenericPropertyMap_GWT property_map;
  private String type_name;

  // ---------------------------------------------------------------------------
  // ---------------------------------------------------------------------------

  public GenericTransport_GWT()
  {

  }// ctor

  // ---------------------------------------------------------------------------

  public void setPropertyMap(GenericPropertyMap_GWT property_map_)
  {
    property_map = property_map_;
  }

  // ---------------------------------------------------------------------------

  public GenericPropertyMap_GWT getPropertyMap()
  {
    return property_map;
  }

  // ---------------------------------------------------------------------------

  public void setPayload(byte[] payload_)
  {
    payload = payload_;
  }

  // ---------------------------------------------------------------------------

  public byte[] getPayload()
  {
    return payload;
  }

  // ---------------------------------------------------------------------------

  public void setTypeName(String type_name_)
  {
    type_name = type_name_;
  }

  // ---------------------------------------------------------------------------

  public String getTypeName()
  {
    return type_name;
  }

  // ---------------------------------------------------------------------------

}// class
