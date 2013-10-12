package edu.umich.imlc.protocolbuffer.util;

import edu.umich.imlc.protocolbuffer.general.ProtocolBufferTransport.GenericProperty;

/**
 * Translator class for converting between GenericProperty (backend-side) an
 * GenericProperty_GWT (frontend-side).
 * 
 * @author vidal@umich.edu
 */
public class GenericPropertyTranslator
{
  // ---------------------------------------------------------------------------
  // ---------------------------------------------------------------------------

  public static GenericProperty translate(GenericProperty_GWT property_gwt)
  {
    assert (property_gwt.getType() != ProtocolBufferScalarType_GWT.NOT_SET);
    switch ( property_gwt.getType() )
    {
      case BOOL:
        return GenericPropertyMaker.make(property_gwt.getPropertyName(),
            property_gwt.getBoolValue());
      case BOOL_LIST:
        return GenericPropertyMaker.make(property_gwt.getPropertyName(),
            property_gwt.getBoolListAsArray());
      case BYTES:
        return GenericPropertyMaker.make(property_gwt.getPropertyName(),
            property_gwt.getBytesValue());
      case BYTES_LIST:
        return GenericPropertyMaker.make(property_gwt.getPropertyName(),
            property_gwt.getBytesListAsArray());
      case DOUBLE:
        return GenericPropertyMaker.make(property_gwt.getPropertyName(),
            property_gwt.getDoubleValue());
      case DOUBLE_LIST:
        return GenericPropertyMaker.make(property_gwt.getPropertyName(),
            property_gwt.getDoubleListAsArray());
      case FLOAT:
        return GenericPropertyMaker.make(property_gwt.getPropertyName(),
            property_gwt.getFloatValue());
      case FLOAT_LIST:
        return GenericPropertyMaker.make(property_gwt.getPropertyName(),
            property_gwt.getFloatListAsArray());
      case INT:
        return GenericPropertyMaker.make(property_gwt.getPropertyName(),
            property_gwt.getIntValue());
      case INT_LIST:
        return GenericPropertyMaker.make(property_gwt.getPropertyName(),
            property_gwt.getIntListAsArray());
      case LONG:
        return GenericPropertyMaker.make(property_gwt.getPropertyName(),
            property_gwt.getLongValue());
      case LONG_LIST:
        return GenericPropertyMaker.make(property_gwt.getPropertyName(),
            property_gwt.getLongListAsArray());
      case STRING:
        return GenericPropertyMaker.make(property_gwt.getPropertyName(),
            property_gwt.getStringValue());
      case STRING_LIST:
        return GenericPropertyMaker.make(property_gwt.getPropertyName(),
            property_gwt.getStringListAsArray());
      default:
        assert ("This shouldn't happen".isEmpty());
        break;
    }// switch

    return null;
  }// translate

  // ---------------------------------------------------------------------------

  public static GenericProperty_GWT translate(GenericProperty property)
  {
    GenericProperty_GWT gwt = new GenericProperty_GWT();
    gwt.setPropertyName(property.getPropertyName());

    if( property.hasStringValue() )
    {
      gwt.setStringValue(property.getStringValue());
    }
    else if( property.hasBoolValue() )
    {
      gwt.setBoolValue(property.getBoolValue());
    }
    else if( property.hasBytesValue() )
    {
      gwt.setBytesValue(property.getBytesValue().toByteArray());
    }
    else if( property.hasDoubleValue() )
    {
      gwt.setDoubleValue(property.getDoubleValue());
    }
    else if( property.hasFloatValue() )
    {
      gwt.setFloatValue(property.getFloatValue());
    }
    else if( property.hasInt32Value() )
    {
      gwt.setIntValue(property.getInt32Value());
    }
    else if( property.hasInt64Value() )
    {
      gwt.setLongValue(property.getInt64Value());
    }
    else if( property.hasSint32Value() )
    {
      gwt.setIntValue(property.getSint32Value());
    }
    else if( property.hasSint64Value() )
    {
      gwt.setLongValue(property.getSint64Value());
    }
    else if( property.hasUint32Value() )
    {
      gwt.setIntValue(property.getUint32Value());
    }
    else if( property.hasUint64Value() )
    {
      gwt.setLongValue(property.getUint64Value());
    }
    else if( property.hasSfixed32Value() )
    {
      gwt.setIntValue(property.getSfixed32Value());
    }
    else if( property.hasSfixed64Value() )
    {
      gwt.setLongValue(property.getSfixed64Value());
    }
    else if( property.hasFixed32Value() )
    {
      gwt.setIntValue(property.getFixed32Value());
    }
    else if( property.hasFixed64Value() )
    {
      gwt.setLongValue(property.getFixed64Value());
    }
    else if( property.getStringElementCount() > 0 )
    {
      gwt.addAllStringElement(property.getStringElementList());
    }
    else if( property.getBoolElementCount() > 0 )
    {
      gwt.addAllBoolElement(property.getBoolElementList());
    }
    else if( property.getBytesElementCount() > 0 )
    {
      gwt.addAllBytesElement(property.getBytesElementList());
    }
    else if( property.getDoubleElementCount() > 0 )
    {
      gwt.addAllDoubleElement(property.getDoubleElementList());
    }
    else if( property.getFloatElementCount() > 0 )
    {
      gwt.addAllFloatElement(property.getFloatElementList());
    }
    else if( property.getInt32ElementCount() > 0 )
    {
      gwt.addAllIntElement(property.getInt32ElementList());
    }
    else if( property.getInt64ElementCount() > 0 )
    {
      gwt.addAllLongElement(property.getInt64ElementList());
    }
    else if( property.getSint32ElementCount() > 0 )
    {
      gwt.addAllIntElement(property.getSint32ElementList());
    }
    else if( property.getSint64ElementCount() > 0 )
    {
      gwt.addAllLongElement(property.getSint64ElementList());
    }
    else if( property.getUint32ElementCount() > 0 )
    {
      gwt.addAllIntElement(property.getUint32ElementList());
    }
    else if( property.getUint64ElementCount() > 0 )
    {
      gwt.addAllLongElement(property.getUint64ElementList());
    }
    else if( property.getSfixed32ElementCount() > 0 )
    {
      gwt.addAllIntElement(property.getSfixed32ElementList());
    }
    else if( property.getSfixed64ElementCount() > 0 )
    {
      gwt.addAllLongElement(property.getSfixed64ElementList());
    }
    else if( property.getFixed32ElementCount() > 0 )
    {
      gwt.addAllIntElement(property.getFixed32ElementList());
    }
    else if( property.getFixed64ElementCount() > 0 )
    {
      gwt.addAllLongElement(property.getFixed64ElementList());
    }
    else
    {
      assert ("This shouldn't happen".isEmpty());
    }

    return gwt;
  }// translate

  // ---------------------------------------------------------------------------


}// class
