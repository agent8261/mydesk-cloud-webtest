package edu.umich.imlc.mydesk.cloud.frontend.flexi;


import edu.umich.imlc.mydesk.cloud.frontend.flexi.ColorJso.HsvJSO;
import edu.umich.imlc.mydesk.cloud.frontend.flexi.ColorJso.RgbJSO;

public class Color
{
  // ===========================================================================
  // ===========================================================================
  
  public static class Rgb
  {
    public RgbJSO jso = ColorJso.makeRgb(-1, -1, -1);
    
    // .........................................................................
    public void copy(Rgb rgb)
    {
      RgbJSO o = rgb.jso;
      jso.set(o.r(), o.g(), o.b());
    }
    
    // .........................................................................
    public void set(final int r, final int g, final int b)
    {
      jso.set(r, g, b);
    }
    
    // .........................................................................
    public void print()
    {
      System.out.println(toString());
    }
    
    // .........................................................................
    public String toString()
    {
      return toRgbString(jso.r(), jso.g(), jso.b());
    }
    
    // .........................................................................
    public void fromHsv(final Hsv hsv)
    {
      HsvJSO o = hsv.jso;
      ColorJso.convertHsvToRgb(o.h(), o.s(), o.v(), jso);
    }
    
    public int getHue()
    {
      return jso.getHue();
    }
  }

  // ===========================================================================
  // ===========================================================================
  
  public static class Hsv
  {
    public HsvJSO jso = ColorJso.makeHsv(-1, -1, -1);
    
    // .........................................................................
    public void print()
    {
      System.out.print
        ("h: " + jso.h() + " s: " + jso.s() + " v: " + jso.v());
      RgbJSO o = ColorJso.makeRgb(0, 0, 0);
      ColorJso.convertHsvToRgb(jso, o);
      System.out.println(" -- R: " + o.r() + " g: " + o.g() + " b: " + o.b());
    }
    
    // .........................................................................
    public void set(final int h, final int s, final int v)
    {
      jso.set(h, s, v);
    }
    
    // .........................................................................
    public void rgb2hsv(final Rgb rgb)
    {
      RgbJSO o = rgb.jso;
      rgb2hsv(o.r(), o.g(), o.b());
    }
    
    // .........................................................................
    public void rgb2hsv(int r, int g, int b)
    {
      ColorJso.convertRgbToHsv(r, g, b, jso);
    }
    
    // .........................................................................
    public String toString()
    {
      RgbJSO o = ColorJso.makeRgb(0, 0, 0);
      ColorJso.convertHsvToRgb(jso, o);
      return toRgbString(o.r(), o.g(), o.b());
    }
  }
  
  // ===========================================================================
  // ===========================================================================
  
  public static String toRgbString(int r, int g, int b)
  {
    assert( (r >= 0) && (r < 256));
    assert( (g >= 0) && (g < 256));
    assert( (b >= 0) && (b < 256));
    return "rgb(" + r +"," + g + "," + b + ")";
  }
  
  // ----------------------------------------------------------------------------
}
