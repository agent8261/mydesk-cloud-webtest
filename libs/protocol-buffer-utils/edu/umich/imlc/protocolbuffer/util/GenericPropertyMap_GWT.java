package edu.umich.imlc.protocolbuffer.util;

import java.util.HashMap;

/**
 * Convenience class for using GenericProperties. This class is the
 * GWT-compatible version of GenericPropertyMap, and contains
 * GenericProperty_GWT objects.
 * 
 * @author vidal@umich.edu
 */
public class GenericPropertyMap_GWT extends
    HashMap<String, GenericProperty_GWT>
{
  /**
   * 
   */
  private static final long serialVersionUID = -7056332273455735149L;

  // ---------------------------------------------------------------------------
  // ---------------------------------------------------------------------------

  /**
   * Adds the property into the map using the property name as the key. Returns
   * the previous value for the property name or null if there was no such
   * property name.
   */
  public GenericProperty_GWT put(GenericProperty_GWT property)
  {
    return put(property.getPropertyName(), property);
  }// add

}// class
