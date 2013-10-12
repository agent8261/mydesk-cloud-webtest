package edu.umich.imlc.protocolbuffer.util;

import java.util.HashMap;

import edu.umich.imlc.protocolbuffer.general.ProtocolBufferTransport.GenericProperty;
import edu.umich.imlc.protocolbuffer.general.ProtocolBufferTransport.GenericPropertyList;

/**
 * Convenience class for using GenericProperties. This class is not
 * GWT-compatible (use GenericPropertyMap_GWT instead). To use this class,
 * construct an instance by passing in a GenericPropertyList to the constructor.
 * The properties will be accessible by property name.
 * 
 * @author vidal@umich.edu
 */
@SuppressWarnings("serial")
public class GenericPropertyMap extends HashMap<String, GenericProperty>
{
  // ---------------------------------------------------------------------------
  // ---------------------------------------------------------------------------

  public GenericPropertyMap()
  {

  }// ctor

  // ---------------------------------------------------------------------------

  public GenericPropertyMap(GenericPropertyList genericPropertyList)
  {
    for( GenericProperty property : genericPropertyList.getPropertyList() )
    {
      put(property.getPropertyName(), property);
    }
  }// ctor

  // ---------------------------------------------------------------------------

}// class
