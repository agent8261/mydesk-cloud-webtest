package edu.umich.imlc.mydesk.cloud.frontend.app.wemap;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.user.client.rpc.IsSerializable;

public class WeMapObject_GWT implements IsSerializable
{
  protected String objectID;
  protected Point_GWT referencePoint;
  protected int objectColor;

  // ---------------------------------------------------------------------------
  // ---------------------------------------------------------------------------

  public WeMapObject_GWT(String objectID_, Point_GWT referencePoint_,
      int objectColor_)
  {
    objectID = objectID_;
    referencePoint = referencePoint_;
    objectColor = objectColor_;
  }

  // ---------------------------------------------------------------------------

  /**
   * No-arg ctor for serialization
   */
  protected WeMapObject_GWT()
  {
  }

  // ---------------------------------------------------------------------------

  public Point_GWT getReferencePoint()
  {
    return referencePoint;
  }

  // ---------------------------------------------------------------------------

  public void setReferencePoint(Point_GWT referencePoint_)
  {
    referencePoint = referencePoint_;
  }

  // ---------------------------------------------------------------------------

  public String getObjectID()
  {
    return objectID;
  }

  // ---------------------------------------------------------------------------

  public int getObjectColor()
  {
    return objectColor;
  }

  // ---------------------------------------------------------------------------

  public void setObjectColor(int objectColor_)
  {
    objectColor = objectColor_;
  }

  // ---------------------------------------------------------------------------

  public void draw(Context2d context)
  {

  }

  // ---------------------------------------------------------------------------

  @Override
  public int hashCode()
  {
    return objectID.hashCode();
  }

  // ---------------------------------------------------------------------------

  @Override
  public boolean equals(Object obj)
  {
    if( this == obj )
      return true;
    if( obj == null )
      return false;
    if( getClass() != obj.getClass() )
      return false;
    WeMapObject_GWT other = (WeMapObject_GWT) obj;
    if( objectID == null )
    {
      if( other.objectID != null )
        return false;
    }
    else if( !objectID.equals(other.objectID) )
      return false;
    return true;
  }// equals

  // ---------------------------------------------------------------------------

}// class
