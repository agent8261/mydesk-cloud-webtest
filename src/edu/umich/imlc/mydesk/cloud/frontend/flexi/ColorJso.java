package edu.umich.imlc.mydesk.cloud.frontend.flexi;

import com.google.gwt.core.client.JavaScriptObject;

public class ColorJso
{
  // ===========================================================================
  // ===========================================================================
  
  public static class RgbJSO extends JavaScriptObject
  {
    protected RgbJSO(){};
    
    public native final int r()
    /*-{
      return this.r;
    }-*/;
    
    public native final int g()
    /*-{
      return this.g;
    }-*/;
    
    public native final int b()
    /*-{
      return this.b;
    }-*/;
    
    public native final void set(int red, int green, int blue)
    /*-{
      this.r = red; this.g = green; this.b = blue;
    }-*/;
    
    public native final void setR(int red)
    /*-{
      this.r = r;
    }-*/;
    
    public native final void setG(int green)
    /*-{
      this.g = green;
    }-*/;
    
    public native final void setB(int blue)
    /*-{
      this.b = blue;
    }-*/;
    
    public native final int getHue()
    /*-{
      var max = Math.max(this.r, this.g, this.b);
      var min = Math.min(this.r, this.g, this.b);
      var c = max - min;      
      var h = 0;
      if(max != 0)
      {
        if(max == this.r)
          h = (this.g - this.b) / c;
        else if (max == this.g)
          h = (this.b - this.r) / c + 2
        else
          h = (this.r - this.g) / c + 4
        h = Math.min(Math.round(h * 60), 360);
        h = (h < 0) ? h + 360 : h;
      }
      return Math.round(h);
    }-*/;
  }
  
  // ===========================================================================
  // ===========================================================================
  
  public static class HsvJSO extends JavaScriptObject
  {
    protected HsvJSO(){};
    
    public native final int h()
    /*-{
      return this.h;
    }-*/;
    
    public native final int s()
    /*-{
      return this.s;
    }-*/;
    
    public native final int v()
    /*-{
      return this.v;
    }-*/;
    
    public native final void set(int hue, int saturation, int value)
    /*-{
      this.h = hue; this.s = saturation; this.v = value;
    }-*/;
    
    public native final void setH(int hue)
    /*-{
      this.h = hue;
    }-*/;
    
    public native final void setS(int saturation)
    /*-{
      this.s = saturation;
    }-*/;
    
    public native final void setV(int value)
    /*-{
      this.v = value;
    }-*/;
  }
  
  // ===========================================================================
  // ===========================================================================
  
  public static native RgbJSO makeRgb(int red, int green, int blue)
  /*-{
    return {
      r : red,
      g : green,
      b : blue
    };
  }-*/;
  
  // ---------------------------------------------------------------------------
  
  public static native HsvJSO makeHsv(int hue, int saturation, int value)
  /*-{
    return {
      h : hue,
      s : saturation,
      v : value
    };
  }-*/;
    
  // ---------------------------------------------------------------------------
  // HSV not HSL
  public static native void convertRgbToHsv
    (int r, int g, int b, HsvJSO hsv)
  /*-{
    var max = Math.max(r, g, b);
    var delta = max - Math.min(r, g, b);
    var h = 0, s = 0;
    var l = Math.round(max * 100 / 255); // V = M
    if(max != 0)
    {
      s = Math.round(delta * 100 / max);
      if(max == r)
      { h = (g - b) / delta; }
      else if (max == g)
      { h = (b - r) / delta + 2 }
      else
      { h = (r - g) / delta + 4 }
      h = Math.min(Math.round(h * 60), 360);
      h = (h < 0) ? h + 360 : h;
    }
    hsv.h = Math.round(h);
    hsv.s = Math.round(s);
    hsv.l = l;
  }-*/;
  
  // ---------------------------------------------------------------------------
  
  public static void convertHsvToRgb
    (int hue, int saturation, int value, RgbJSO rgb)
  {
    HsvJSO hsv = ColorJso.makeHsv(hue, saturation, value);
    convertHsvToRgb(hsv, rgb);
    int r = rgb.r(), g = rgb.g(), b = rgb.b();
    assert( (r >= 0) && (r < 256));
    assert( (g >= 0) && (g < 256));
    assert( (b >= 0) && (b < 256));
  }
  
  // ---------------------------------------------------------------------------
  
  public static native void convertHsvToRgb(HsvJSO hsl, RgbJSO rgb)
  /*-{
    var h = hsl.h / 360;
    var s = hsl.s / 100;
    var l = hsl.v / 100;

    if(s == 0)
    {
      l = Math.round(l * 255.0);
      rgb.r = rgb.g = rgb.b = l;
      return; 
    }
    if(s < 0)
    {
      rgb.r = rgb.g = rgb.b = 0;
      return;
    }
    
    h = (h >= 1) ? 0 : (h * 6);
    var f = h - Math.floor(h);
    var a = Math.round(l * 255.0 * (1.0 - s));
    var b = Math.round(l * 255.0 * (1.0 - (s * f)));
    var c = Math.round(l * 255.0 * (1.0 - (s * (1.0 - f))));
    l = Math.round(l * 255.0);
    
    h = Math.floor(h);
    rgb.r = Math.round([l, b, a, a, c, l][h]);
    rgb.g = Math.round([c, l, l, b, a, a][h]);
    rgb.b = Math.round([a, a, c, l ,l, b][h]);
  }-*/;
  
  // ---------------------------------------------------------------------------
}












