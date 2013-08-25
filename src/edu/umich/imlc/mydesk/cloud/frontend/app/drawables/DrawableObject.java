package edu.umich.imlc.mydesk.cloud.frontend.app.drawables;

public interface DrawableObject
{ 
  public void addTo(DrawingSurface drawingSurface);
  
  public void removeFrom(DrawingSurface drawingSurface);
  
  public String getObjectID();
  
  public void setPosition(double x, double y);
  
  public void setColor(String color);
  
  public String getColor();
  
  public void setTitle(String title);
  
  public String getTitle();
  
  // ---------------------------------------------------------------------------
  // ---------------------------------------------------------------------------
  
  /** 
   * Marks a objects as a surface that DrawableObjects can be added to
   * The methods of adding however will be implementation specific
   */
  public static interface DrawingSurface
  {
    void draw();
    void remove(DrawableObject obj);
  }
  
  // ---------------------------------------------------------------------------
}
