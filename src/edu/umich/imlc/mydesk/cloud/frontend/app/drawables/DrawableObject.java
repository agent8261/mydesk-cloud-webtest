package edu.umich.imlc.mydesk.cloud.frontend.app.drawables;

public interface DrawableObject
{
  // ---------------------------------------------------------------------------
  
  public void setPosition(double x, double y);
  
  // ---------------------------------------------------------------------------
  
  public double getX();
  
  // ---------------------------------------------------------------------------
  
  public double getY();
  
  // ---------------------------------------------------------------------------
  
  public void addTo(DrawingSurface drawingSurface);
  
  // ---------------------------------------------------------------------------
  // ---------------------------------------------------------------------------
  
  /** 
   * Marks a objects as a surface that Drawable can be added to
   * The methods of adding however will be implementation specific
   */
  public static interface DrawingSurface
  {
    void draw();
  }
  
  // ---------------------------------------------------------------------------
}
