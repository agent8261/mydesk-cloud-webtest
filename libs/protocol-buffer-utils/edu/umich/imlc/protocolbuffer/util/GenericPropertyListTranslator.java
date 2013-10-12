package edu.umich.imlc.protocolbuffer.util;

import edu.umich.imlc.protocolbuffer.general.ProtocolBufferTransport.GenericProperty;
import edu.umich.imlc.protocolbuffer.general.ProtocolBufferTransport.GenericPropertyList;

/**
 * Translator class for converting between GenericPropertyList (backend-side) an
 * GenericPropertyMap_GWT (frontend-side).
 * 
 * @author vidal@umich.edu
 */
public class GenericPropertyListTranslator
{
  // ---------------------------------------------------------------------------
  // ---------------------------------------------------------------------------

  public static GenericPropertyMap_GWT translate(GenericPropertyList list)
  {
    GenericPropertyMap_GWT map_gwt = new GenericPropertyMap_GWT();

    for( GenericProperty property : list.getPropertyList() )
    {
      GenericProperty_GWT property_gwt = GenericPropertyTranslator
          .translate(property);
      map_gwt.put(property_gwt);
    }// for

    return map_gwt;
  }// translate

  // ---------------------------------------------------------------------------

  public static GenericPropertyList translate(GenericPropertyMap_GWT map)
  {
    GenericPropertyList.Builder listBuilder = GenericPropertyList.newBuilder();

    for( String propertyName : map.keySet() )
    {
      GenericProperty property_pb = GenericPropertyTranslator.translate(map
          .get(propertyName));
      listBuilder.addProperty(property_pb);
    }// for

    return listBuilder.build();
  }// translate

}// class
