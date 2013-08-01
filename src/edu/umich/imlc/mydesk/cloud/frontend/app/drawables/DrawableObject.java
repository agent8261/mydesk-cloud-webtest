package edu.umich.imlc.mydesk.cloud.frontend.app.drawables;

public interface DrawableObject
{ 
  public void addTo(DrawingSurface drawingSurface);
  
  public void removeFrom(DrawingSurface drawingSurface);
  
  public String getObjectID();
  
  public void setPosition(double x, double y);
  
  
  // ---------------------------------------------------------------------------
  // ---------------------------------------------------------------------------
  
  /** 
   * Marks a objects as a surface that Drawable can be added to
   * The methods of adding however will be implementation specific
   */
  public static interface DrawingSurface
  {
    void draw();
    void remove(DrawableObject obj);
  }
  
  // ---------------------------------------------------------------------------
}
