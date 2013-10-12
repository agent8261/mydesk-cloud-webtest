package edu.umich.imlc.protocolbuffer.util;

import com.google.protobuf.ByteString;

import edu.umich.imlc.protocolbuffer.general.ProtocolBufferTransport.GenericProperty;
import edu.umich.imlc.protocolbuffer.general.ProtocolBufferTransport.ProtocolBufferScalarType;

/**
 * Convenience class of Static methods to construct individual properties. This
 * class is not GWT-compatible.
 * 
 * @author vidal@umich.edu
 */
public class GenericPropertyMaker
{
  // ---------------------------------------------------------------------------
  // ---------------------------------------------------------------------------

  public static GenericProperty make(String propertyName, boolean value)
  {
    return GenericProperty.newBuilder().setPropertyName(propertyName)
        .setBoolValue(value).setScalarType(ProtocolBufferScalarType.BOOL)
        .build();
  }// make

  // ---------------------------------------------------------------------------

  public static GenericProperty make(String propertyName, Boolean value)
  {
    return make(propertyName, (boolean) value);
  }// make

  // ---------------------------------------------------------------------------

  public static GenericProperty make(String propertyName, int value)
  {
    return GenericProperty.newBuilder().setPropertyName(propertyName)
        .setInt32Value(value).setScalarType(ProtocolBufferScalarType.INT32)
        .build();
  }// make

  // ---------------------------------------------------------------------------

  public static GenericProperty make(String propertyName, Integer value)
  {
    return make(propertyName, (int) value);
  }// make

  // ---------------------------------------------------------------------------

  public static GenericProperty make(String propertyName, long value)
  {
    return GenericProperty.newBuilder().setPropertyName(propertyName)
        .setInt64Value(value).setScalarType(ProtocolBufferScalarType.INT64)
        .build();
  }// make

  // ---------------------------------------------------------------------------

  public static GenericProperty make(String propertyName, Long value)
  {
    return make(propertyName, (long) value);
  }// make

  // ---------------------------------------------------------------------------

  public static GenericProperty make(String propertyName, byte[] value)
  {
    return GenericProperty.newBuilder().setPropertyName(propertyName)
        .setBytesValue(ByteString.copyFrom(value))
        .setScalarType(ProtocolBufferScalarType.BYTES).build();
  }// make

  // ---------------------------------------------------------------------------

  public static GenericProperty make(String propertyName, Byte[] value)
  {
    byte[] newValue = new byte[value.length];
    for( int i = 0; i < value.length; i++ )
    {
      newValue[i] = value[i];
    }
    return make(propertyName, newValue);
  }// make

  // ---------------------------------------------------------------------------

  public static GenericProperty make(String propertyName, ByteString value)
  {
    return GenericProperty.newBuilder().setPropertyName(propertyName)
        .setBytesValue(value).setScalarType(ProtocolBufferScalarType.BYTES)
        .build();
  }// make

  // ---------------------------------------------------------------------------

  public static GenericProperty make(String propertyName, String value)
  {
    return GenericProperty.newBuilder().setPropertyName(propertyName)
        .setStringValue(value).setScalarType(ProtocolBufferScalarType.STRING)
        .build();
  }// make

  // ---------------------------------------------------------------------------

  public static GenericProperty make(String propertyName, double value)
  {
    return GenericProperty.newBuilder().setPropertyName(propertyName)
        .setDoubleValue(value).setScalarType(ProtocolBufferScalarType.DOUBLE)
        .build();
  }// make

  // ---------------------------------------------------------------------------

  public static GenericProperty make(String propertyName, Double value)
  {
    return make(propertyName, (double) value);
  }// make

  // ---------------------------------------------------------------------------

  public static GenericProperty make(String propertyName, float value)
  {
    return GenericProperty.newBuilder().setPropertyName(propertyName)
        .setFloatValue(value).setScalarType(ProtocolBufferScalarType.FLOAT)
        .build();
  }// make

  // ---------------------------------------------------------------------------

  public static GenericProperty make(String propertyName, Float value)
  {
    return make(propertyName, (float) value);
  }// make

  // ---------------------------------------------------------------------------

  public static GenericProperty make(String propertyName, boolean[] values)
  {
    GenericProperty.Builder builder = GenericProperty.newBuilder()
        .setPropertyName(propertyName)
        .setScalarType(ProtocolBufferScalarType.BOOL_LIST);

    for( boolean value : values )
    {
      builder.addBoolElement(value);
    }

    return builder.build();
  }// make

  // ---------------------------------------------------------------------------

  public static GenericProperty make(String propertyName, Boolean[] values)
  {
    GenericProperty.Builder builder = GenericProperty.newBuilder()
        .setPropertyName(propertyName)
        .setScalarType(ProtocolBufferScalarType.BOOL_LIST);

    for( boolean value : values )
    {
      builder.addBoolElement(value);
    }

    return builder.build();
  }// make

  // ---------------------------------------------------------------------------

  public static GenericProperty make(String propertyName, int[] values)
  {
    GenericProperty.Builder builder = GenericProperty.newBuilder()
        .setPropertyName(propertyName)
        .setScalarType(ProtocolBufferScalarType.INT32_LIST);

    for( int value : values )
    {
      builder.addInt32Element(value);
    }

    return builder.build();
  }// make

  // ---------------------------------------------------------------------------

  public static GenericProperty make(String propertyName, Integer[] values)
  {
    GenericProperty.Builder builder = GenericProperty.newBuilder()
        .setPropertyName(propertyName)
        .setScalarType(ProtocolBufferScalarType.INT32_LIST);

    for( int value : values )
    {
      builder.addInt32Element(value);
    }

    return builder.build();
  }// make

  // ---------------------------------------------------------------------------

  public static GenericProperty make(String propertyName, long[] values)
  {
    GenericProperty.Builder builder = GenericProperty.newBuilder()
        .setPropertyName(propertyName)
        .setScalarType(ProtocolBufferScalarType.INT64_LIST);

    for( long value : values )
    {
      builder.addInt64Element(value);
    }

    return builder.build();
  }// make

  // ---------------------------------------------------------------------------

  public static GenericProperty make(String propertyName, Long[] values)
  {
    GenericProperty.Builder builder = GenericProperty.newBuilder()
        .setPropertyName(propertyName)
        .setScalarType(ProtocolBufferScalarType.INT64_LIST);

    for( long value : values )
    {
      builder.addInt64Element(value);
    }

    return builder.build();
  }// make

  // ---------------------------------------------------------------------------

  public static GenericProperty make(String propertyName, byte[][] arrays)
  {
    GenericProperty.Builder builder = GenericProperty.newBuilder()
        .setPropertyName(propertyName)
        .setScalarType(ProtocolBufferScalarType.BYTES_LIST);

    for( byte[] array : arrays )
    {
      builder.addBytesElement(ByteString.copyFrom(array));
    }

    return builder.build();
  }// make

  // ---------------------------------------------------------------------------

  public static GenericProperty make(String propertyName, Byte[][] arrays)
  {
    GenericProperty.Builder builder = GenericProperty.newBuilder()
        .setPropertyName(propertyName)
        .setScalarType(ProtocolBufferScalarType.BYTES_LIST);

    for( Byte[] array : arrays )
    {
      byte[] temp = new byte[array.length];
      for( int i = 0; i < array.length; i++ )
        temp[i] = array[i];

      builder.addBytesElement(ByteString.copyFrom(temp));
    }// for

    return builder.build();
  }// make

  // ---------------------------------------------------------------------------

  public static GenericProperty make(String propertyName, ByteString[] values)
  {
    GenericProperty.Builder builder = GenericProperty.newBuilder()
        .setPropertyName(propertyName)
        .setScalarType(ProtocolBufferScalarType.BYTES_LIST);

    for( ByteString value : values )
    {
      builder.addBytesElement(value);
    }

    return builder.build();
  }// make

  // ---------------------------------------------------------------------------

  public static GenericProperty make(String propertyName, String[] values)
  {
    GenericProperty.Builder builder = GenericProperty.newBuilder()
        .setPropertyName(propertyName)
        .setScalarType(ProtocolBufferScalarType.STRING_LIST);

    for( String value : values )
    {
      builder.addStringElement(value);
    }

    return builder.build();
  }// make

  // ---------------------------------------------------------------------------

  public static GenericProperty make(String propertyName, double[] values)
  {
    GenericProperty.Builder builder = GenericProperty.newBuilder()
        .setPropertyName(propertyName)
        .setScalarType(ProtocolBufferScalarType.DOUBLE_LIST);

    for( double value : values )
    {
      builder.addDoubleElement(value);
    }

    return builder.build();
  }// make

  // ---------------------------------------------------------------------------

  public static GenericProperty make(String propertyName, Double[] values)
  {
    GenericProperty.Builder builder = GenericProperty.newBuilder()
        .setPropertyName(propertyName)
        .setScalarType(ProtocolBufferScalarType.DOUBLE_LIST);

    for( double value : values )
    {
      builder.addDoubleElement(value);
    }

    return builder.build();
  }// make

  // ---------------------------------------------------------------------------

  public static GenericProperty make(String propertyName, float[] values)
  {
    GenericProperty.Builder builder = GenericProperty.newBuilder()
        .setPropertyName(propertyName)
        .setScalarType(ProtocolBufferScalarType.FLOAT_LIST);

    for( float value : values )
    {
      builder.addFloatElement(value);
    }
    return builder.build();
  }// make

  // ---------------------------------------------------------------------------

  public static GenericProperty make(String propertyName, Float[] values)
  {
    GenericProperty.Builder builder = GenericProperty.newBuilder()
        .setPropertyName(propertyName)
        .setScalarType(ProtocolBufferScalarType.FLOAT_LIST);

    for( float value : values )
    {
      builder.addFloatElement(value);
    }
    return builder.build();
  }// make

  // ---------------------------------------------------------------------------

  // java.lang.Long
  // java.lang.String
  // java.lang.Integer
  // java.lang.Boolean
  // byte[]
  // java.lang.Float
  // java.lang.Double
  // long[]
  // java.lang.String[]
  // int[]
  // boolean[]
  // java.util.ArrayList
  // float[]
  // double[]
  // byte[][]
  /**
   * This is the catch-all. Ideally one of the other definitions gets used.
   */
  public static GenericProperty make(String propertyName, Object object)
  {
    if( object.getClass().getCanonicalName()
        .equals(Boolean.class.getCanonicalName()) )
      return make(propertyName, (Boolean) object);

    if( object.getClass().getCanonicalName()
        .equals(Integer.class.getCanonicalName()) )
      return make(propertyName, (Integer) object);

    if( object.getClass().getCanonicalName()
        .equals(Long.class.getCanonicalName()) )
      return make(propertyName, (Long) object);

    if( object.getClass().getCanonicalName()
        .equals(Float.class.getCanonicalName()) )
      return make(propertyName, (Float) object);

    if( object.getClass().getCanonicalName()
        .equals(Double.class.getCanonicalName()) )
      return make(propertyName, (Double) object);

    if( object.getClass().getCanonicalName()
        .equals(String.class.getCanonicalName()) )
      return make(propertyName, (String) object);

    if( object.getClass().getCanonicalName().equals("byte[]") )
      return make(propertyName, (byte[]) object);

    // Arrays
    if( object.getClass().getCanonicalName().equals("boolean[]") )
      return make(propertyName, (boolean[]) object);

    if( object.getClass().getCanonicalName().equals("int[]") )
      return make(propertyName, (int[]) object);

    if( object.getClass().getCanonicalName().equals("long[]") )
      return make(propertyName, (long[]) object);

    if( object.getClass().getCanonicalName().equals("float[]") )
      return make(propertyName, (float[]) object);

    if( object.getClass().getCanonicalName().equals("double[]") )
      return make(propertyName, (double[]) object);

    if( object.getClass().getCanonicalName().equals("java.lang.String[]") )
      return make(propertyName, (String[]) object);

    if( object.getClass().getCanonicalName().equals("byte[][]") )
      return make(propertyName, (byte[][]) object);


    assert ("This shouldn't happen".isEmpty());

    return null;
  }// make


}// class
