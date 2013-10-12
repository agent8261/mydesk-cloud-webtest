package edu.umich.imlc.protocolbuffer.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.google.protobuf.ByteString;

/**
 * This is the GWT-compatible version of GenericProperty.
 * 
 * @author vidal@umich.edu
 */
public class GenericProperty_GWT implements Serializable
{

  /**
   * 
   */
  private static final long serialVersionUID = 6371909736880179166L;

  private ProtocolBufferScalarType_GWT type;
  private String string_value;
  private ArrayList<String> string_list;
  private Boolean bool_value;
  private ArrayList<Boolean> bool_list;
  private byte[] bytes_value;
  private ArrayList<byte[]> bytes_list;
  private Integer int_value;
  private ArrayList<Integer> int_list;
  private Long long_value;
  private ArrayList<Long> long_list;
  private Double double_value;
  private ArrayList<Double> double_list;
  private Float float_value;
  private ArrayList<Float> float_list;

  private String property_name;

  // ---------------------------------------------------------------------------
  // ---------------------------------------------------------------------------

  private void clear()
  {
    string_value = null;
    string_list = null;
    bool_value = null;
    bool_list = null;
    bytes_value = null;
    bytes_list = null;
    int_value = null;
    int_list = null;
    long_value = null;
    long_list = null;
    double_value = null;
    double_list = null;
    float_value = null;
    float_list = null;
    type = ProtocolBufferScalarType_GWT.NOT_SET;
  }// clear

  // ---------------------------------------------------------------------------

  public ProtocolBufferScalarType_GWT getType()
  {
    return type;
  }

  // ---------------------------------------------------------------------------

  public void setPropertyName(String name)
  {
    property_name = name;
  }

  // ---------------------------------------------------------------------------

  public String getPropertyName()
  {
    return property_name;
  }

  // ---------------------------------------------------------------------------
  // ---------------------------------------------------------------------------

  public void setStringValue(String value)
  {
    clear();
    type = ProtocolBufferScalarType_GWT.STRING;
    string_value = value;
  }//

  // ---------------------------------------------------------------------------

  public String getStringValue()
  {
    return string_value;
  }

  // ---------------------------------------------------------------------------

  public void addStringElement(String value)
  {
    if( type != ProtocolBufferScalarType_GWT.STRING_LIST || string_list == null )
    {
      clear();
      type = ProtocolBufferScalarType_GWT.STRING_LIST;
      string_list = new ArrayList<String>();
    }

    string_list.add(value);
  }// addStringElement

  // ---------------------------------------------------------------------------

  public void addAllStringElement(Collection<? extends String> elements)
  {
    if( type != ProtocolBufferScalarType_GWT.STRING_LIST || string_list == null )
    {
      clear();
      type = ProtocolBufferScalarType_GWT.STRING_LIST;
      string_list = new ArrayList<String>();
    }

    string_list.addAll(elements);
  }// addAllStringElement

  // ---------------------------------------------------------------------------

  public List<String> getStringList()
  {
    return Collections.unmodifiableList(string_list);
  }

  // ---------------------------------------------------------------------------

  public String[] getStringListAsArray()
  {
    return string_list.toArray(new String[0]);
  }

  // ---------------------------------------------------------------------------
  // ---------------------------------------------------------------------------

  public void setBoolValue(boolean value)
  {
    clear();
    type = ProtocolBufferScalarType_GWT.BOOL;
    bool_value = value;
  }// setBoolValue

  // ---------------------------------------------------------------------------

  public boolean getBoolValue()
  {
    return bool_value;
  }

  // ---------------------------------------------------------------------------

  public void addBoolElement(boolean value)
  {
    if( type != ProtocolBufferScalarType_GWT.BOOL_LIST || bool_list == null )
    {
      clear();
      type = ProtocolBufferScalarType_GWT.BOOL_LIST;
      bool_list = new ArrayList<Boolean>();
    }

    bool_list.add(value);
  }// addBoolElement

  // ---------------------------------------------------------------------------

  public void addAllBoolElement(Collection<? extends Boolean> elements)
  {
    if( type != ProtocolBufferScalarType_GWT.BOOL_LIST || bool_list == null )
    {
      clear();
      type = ProtocolBufferScalarType_GWT.BOOL_LIST;
      bool_list = new ArrayList<Boolean>();
    }

    bool_list.addAll(elements);
  }// addAllBoolElement

  // ---------------------------------------------------------------------------

  public List<Boolean> getBoolList()
  {
    return Collections.unmodifiableList(bool_list);
  }

  // ---------------------------------------------------------------------------

  public Boolean[] getBoolListAsArray()
  {
    return bool_list.toArray(new Boolean[0]);
  }

  // ---------------------------------------------------------------------------
  // ---------------------------------------------------------------------------

  public void setBytesValue(byte[] value)
  {
    clear();
    type = ProtocolBufferScalarType_GWT.BYTES;
    bytes_value = value;
  }// setBytesValue

  // ---------------------------------------------------------------------------

  public byte[] getBytesValue()
  {
    return bytes_value;
  }

  // ---------------------------------------------------------------------------

  public void addBytesElement(byte[] value)
  {
    if( type != ProtocolBufferScalarType_GWT.BYTES_LIST || bytes_list == null )
    {
      clear();
      type = ProtocolBufferScalarType_GWT.BYTES_LIST;
      bytes_list = new ArrayList<byte[]>();
    }

    bytes_list.add(value);

  }// addBytesElement

  // ---------------------------------------------------------------------------

  public void addAllBytesElement(Collection<? extends byte[]> elements)
  {
    if( type != ProtocolBufferScalarType_GWT.BYTES_LIST || bytes_list == null )
    {
      clear();
      type = ProtocolBufferScalarType_GWT.BYTES_LIST;
      bytes_list = new ArrayList<byte[]>();
    }

    bytes_list.addAll(elements);

  }// addAllBytesElement

  // ---------------------------------------------------------------------------

  public void addAllBytesElement(Iterable<ByteString> elements)
  {
    if( type != ProtocolBufferScalarType_GWT.BYTES_LIST || bytes_list == null )
    {
      clear();
      type = ProtocolBufferScalarType_GWT.BYTES_LIST;
      bytes_list = new ArrayList<byte[]>();
    }

    for( ByteString byteString : elements )
    {
      bytes_list.add(byteString.toByteArray());
    }

  }// addAllBytesElement

  // ---------------------------------------------------------------------------

  public List<byte[]> getBytesList()
  {
    return Collections.unmodifiableList(bytes_list);
  }

  // ---------------------------------------------------------------------------

  public byte[][] getBytesListAsArray()
  {
    return bytes_list.toArray(new byte[0][0]);
  }

  // ---------------------------------------------------------------------------
  // ---------------------------------------------------------------------------

  public void setIntValue(int value)
  {
    clear();
    type = ProtocolBufferScalarType_GWT.INT;
    int_value = value;
  }// setIntValue

  // ---------------------------------------------------------------------------

  public int getIntValue()
  {
    return int_value;
  }

  // ---------------------------------------------------------------------------

  public void addIntElement(int value)
  {
    if( type != ProtocolBufferScalarType_GWT.INT_LIST || int_list == null )
    {
      clear();
      type = ProtocolBufferScalarType_GWT.INT_LIST;
      int_list = new ArrayList<Integer>();
    }

    int_list.add(value);

  }// addIntElement

  // ---------------------------------------------------------------------------

  public void addAllIntElement(Collection<? extends Integer> elements)
  {
    if( type != ProtocolBufferScalarType_GWT.INT_LIST || int_list == null )
    {
      clear();
      type = ProtocolBufferScalarType_GWT.INT_LIST;
      int_list = new ArrayList<Integer>();
    }

    int_list.addAll(elements);

  }// addAllIntElement

  // ---------------------------------------------------------------------------

  public List<Integer> getIntList()
  {
    return Collections.unmodifiableList(int_list);
  }

  // ---------------------------------------------------------------------------

  public Integer[] getIntListAsArray()
  {
    return int_list.toArray(new Integer[0]);
  }

  // ---------------------------------------------------------------------------
  // ---------------------------------------------------------------------------

  public void setLongValue(long value)
  {
    clear();
    type = ProtocolBufferScalarType_GWT.LONG;
    long_value = value;
  }//

  // ---------------------------------------------------------------------------

  public long getLongValue()
  {
    return long_value;
  }

  // ---------------------------------------------------------------------------

  public void addLongElement(long value)
  {
    if( type != ProtocolBufferScalarType_GWT.LONG || long_list == null )
    {
      clear();
      type = ProtocolBufferScalarType_GWT.LONG;
      long_list = new ArrayList<Long>();
    }

    long_list.add(value);

  }// addLongElement

  // ---------------------------------------------------------------------------

  public void addAllLongElement(Collection<? extends Long> elements)
  {
    if( type != ProtocolBufferScalarType_GWT.LONG || long_list == null )
    {
      clear();
      type = ProtocolBufferScalarType_GWT.LONG;
      long_list = new ArrayList<Long>();
    }

    long_list.addAll(elements);

  }// addAllLongElement

  // ---------------------------------------------------------------------------

  public List<Long> getLongList()
  {
    return Collections.unmodifiableList(long_list);
  }

  // ---------------------------------------------------------------------------

  public Long[] getLongListAsArray()
  {
    return long_list.toArray(new Long[0]);
  }

  // ---------------------------------------------------------------------------
  // ---------------------------------------------------------------------------

  public void setDoubleValue(double value)
  {
    clear();
    type = ProtocolBufferScalarType_GWT.DOUBLE;
    double_value = value;
  }// setDoubleValue

  // ---------------------------------------------------------------------------

  public Double getDoubleValue()
  {
    return double_value;
  }

  // ---------------------------------------------------------------------------

  public void addDoubleElement(double value)
  {
    if( type != ProtocolBufferScalarType_GWT.DOUBLE_LIST || double_list == null )
    {
      clear();
      type = ProtocolBufferScalarType_GWT.DOUBLE_LIST;
      double_list = new ArrayList<Double>();
    }

    double_list.add(value);

  }// addDoubleElement

  // ---------------------------------------------------------------------------

  public void addAllDoubleElement(Collection<? extends Double> elements)
  {
    if( type != ProtocolBufferScalarType_GWT.DOUBLE_LIST || double_list == null )
    {
      clear();
      type = ProtocolBufferScalarType_GWT.DOUBLE_LIST;
      double_list = new ArrayList<Double>();
    }

    double_list.addAll(elements);

  }// addAllDoubleElement

  // ---------------------------------------------------------------------------

  public List<Double> getDoubleList()
  {
    return Collections.unmodifiableList(double_list);
  }

  // ---------------------------------------------------------------------------

  public Double[] getDoubleListAsArray()
  {
    return double_list.toArray(new Double[0]);
  }

  // ---------------------------------------------------------------------------
  // ---------------------------------------------------------------------------

  public void setFloatValue(float value)
  {
    clear();
    type = ProtocolBufferScalarType_GWT.FLOAT;
    float_value = value;
  }// setFloatValue

  // ---------------------------------------------------------------------------

  public float getFloatValue()
  {
    return float_value;
  }

  // ---------------------------------------------------------------------------

  public void addFloatElement(float value)
  {
    if( type != ProtocolBufferScalarType_GWT.FLOAT_LIST || float_list == null )
    {
      clear();
      type = ProtocolBufferScalarType_GWT.FLOAT_LIST;
      float_list = new ArrayList<Float>();
    }

    float_list.add(value);

  }// addFloatElement

  // ---------------------------------------------------------------------------

  public void addAllFloatElement(Collection<? extends Float> elements)
  {
    if( type != ProtocolBufferScalarType_GWT.FLOAT_LIST || float_list == null )
    {
      clear();
      type = ProtocolBufferScalarType_GWT.FLOAT_LIST;
      float_list = new ArrayList<Float>();
    }

    float_list.addAll(elements);

  }// addAllFloatElement

  // ---------------------------------------------------------------------------

  public List<Float> getFloatList()
  {
    return Collections.unmodifiableList(float_list);
  }

  // ---------------------------------------------------------------------------

  public Float[] getFloatListAsArray()
  {
    return float_list.toArray(new Float[0]);
  }

  // ---------------------------------------------------------------------------

}// class
