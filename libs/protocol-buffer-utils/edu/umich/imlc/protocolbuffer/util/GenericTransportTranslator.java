package edu.umich.imlc.protocolbuffer.util;

import com.google.protobuf.ByteString;

import edu.umich.imlc.protocolbuffer.general.ProtocolBufferTransport.GenericTransport;

/**
 * Translator class for converting between GenericTransport (backend-side) an
 * GenericTransport_GWT (frontend-side).
 * 
 * @author vidal@umich.edu
 */
public class GenericTransportTranslator
{

  // ---------------------------------------------------------------------------
  // ---------------------------------------------------------------------------

  public static GenericTransport translate(GenericTransport_GWT transport_gwt)
  {
    GenericTransport.Builder builder = GenericTransport.newBuilder();

    if( transport_gwt.getPayload() != null )
    {
      builder.setPayload(ByteString.copyFrom(transport_gwt.getPayload()));
    }

    if( transport_gwt.getPropertyMap() != null )
    {
      builder.setPropertyList(GenericPropertyListTranslator
          .translate(transport_gwt.getPropertyMap()));
    }

    if( transport_gwt.getTypeName() != null )
    {
      builder.setTypeName(transport_gwt.getTypeName());
    }

    return builder.build();
  }// translate

  // ---------------------------------------------------------------------------

  public static GenericTransport_GWT translate(GenericTransport transport)
  {
    GenericTransport_GWT gwt = new GenericTransport_GWT();

    if( transport.hasPayload() )
    {
      gwt.setPayload(transport.getPayload().toByteArray());
    }

    if( transport.hasPropertyList() )
    {
      gwt.setPropertyMap(GenericPropertyListTranslator.translate(transport
          .getPropertyList()));
    }

    if( transport.hasTypeName() )
    {
      gwt.setTypeName(transport.getTypeName());
    }

    return gwt;
  }// translate

  // ---------------------------------------------------------------------------

}// class
