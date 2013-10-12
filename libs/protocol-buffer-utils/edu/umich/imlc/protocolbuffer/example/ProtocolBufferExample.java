package edu.umich.imlc.protocolbuffer.example;


import com.google.protobuf.InvalidProtocolBufferException;

import edu.umich.imlc.protocolbuffer.general.ProtocolBufferTransport.DummyMessage;
import edu.umich.imlc.protocolbuffer.general.ProtocolBufferTransport.GenericProperty;
import edu.umich.imlc.protocolbuffer.general.ProtocolBufferTransport.GenericPropertyList;
import edu.umich.imlc.protocolbuffer.general.ProtocolBufferTransport.GenericTransport;
import edu.umich.imlc.protocolbuffer.general.ProtocolBufferTransport.ProtocolBufferScalarType;
import edu.umich.imlc.protocolbuffer.util.GenericPropertyListTranslator;
import edu.umich.imlc.protocolbuffer.util.GenericPropertyMaker;
import edu.umich.imlc.protocolbuffer.util.GenericPropertyMap_GWT;

public class ProtocolBufferExample
{
  public static final String MOCK_MESSAGE = "Hello World!";

  // ---------------------------------------------------------------------------

  public static void main(String[] args)
  {
    GenericTransport transport = makeTheTransport();

    unpackageTheTransport(transport);

  }// main

  // ---------------------------------------------------------------------------

  public static GenericTransport makeTheTransport()
  {
    // Create the thing that we are transporting.
    // DummyMessage dummy = DummyMessage.newBuilder().setMsg(MOCK_MESSAGE)
    // .build();
    DummyMessage.Builder dummyMessageBuilder = DummyMessage.newBuilder();
    dummyMessageBuilder.setMsg(MOCK_MESSAGE);
    DummyMessage dummy = dummyMessageBuilder.build();

    // Start a GenericPropertyList which will contain "meta-data" about the
    // thing we are sending.
    GenericPropertyList.Builder property_list_builder = GenericPropertyList
        .newBuilder();

    // Make some properties and add them to the property list.
    // NOTE: You have the following scalar value types available: STRING, BOOL,
    // BYTES, DOUBLE, FLOAT, INT32, INT64, SINT32, SINT64, UINT32, UINT64,
    // SFIXED32, SFIXED64, FIXED32, and FIXED64.
    // Typically for integral types you should only use INT32 (int) and
    // INT64 (long).
    for( int i = 0; i < 5; i++ )
    {

      // Make property using builders.
      // GenericProperty property = GenericProperty.newBuilder()
      // .setPropertyName("MyPropertyName" + i)
      // .setStringValue("MyPropertyStringValue" + i)
      // .setScalarType(ProtocolBufferScalarType.STRING).build();

      // Make property using GenericPropertyMaker(name, value)
      GenericProperty property = GenericPropertyMaker.make(
          "MyPropertyName" + i, "MyPropertyStringValue" + i);

      property_list_builder.addProperty(property);
    }// for

    GenericPropertyList property_list = property_list_builder.build();

    // Create a GenericTransport. Setting the TypeName is optional.
    GenericTransport transport = GenericTransport.newBuilder()
        .setTypeName("DummyMessage").setPayload(dummy.toByteString())
        .setPropertyList(property_list).build();

    return transport;
  }// makeTheTransport

  // ---------------------------------------------------------------------------

  public static void unpackageTheTransport(GenericTransport transport)
  {
    System.out.println("Received Transport...");

    // Let's see the type name, if it was provided.
    if( transport.hasTypeName() )
      System.out.println("Type: " + transport.getTypeName());

    // Grab the nested object, if any.
    // NOTE: You need to know the type of object you are reconstituting in order
    // to use parseFrom().
    DummyMessage dummy;
    if( transport.hasPayload()
        && transport.getTypeName().equals("DummyMessage") )
    {
      try
      {
        dummy = DummyMessage.parseFrom(transport.getPayload());
        System.out.println("Dummy Message: " + dummy.getMsg());
        assert (dummy.getMsg().equals(MOCK_MESSAGE));
      }
      catch( InvalidProtocolBufferException e )
      {
        e.printStackTrace();
      }
    }// if

    // On the receiving side, you have the option of using either
    // GenericPropertyMap (HashMap<String, GenericProperty>) or
    // GenericPropertyMap_GWT (HashMap<String, GenericProperty_GWT>) which may
    // be simpler than directly handling the GenericPropertyList.
    GenericPropertyMap_GWT properties = GenericPropertyListTranslator
        .translate(transport.getPropertyList());

    for( String property_name : properties.keySet() )
      System.out.println(property_name + ": "
          + properties.get(property_name).getStringValue());

    // NOTE: For a given property name, the receiver must access a value of the
    // same type that the sender set it as.

  }// unpackageTheTransport

}// class
