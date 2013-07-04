package edu.umich.imlc.mydesk.cloud.frontend.app.web;

import com.google.gwt.core.client.Callback;
import com.google.gwt.core.client.JavaScriptObject;

/**
 * GWT implementation of David Durman's FlexiColorPicker
 * https://github.com/DavidDurman/FlexiColorPicker
 * 
 * MIT License
 */
public class GWTJSOTest extends JavaScriptObject
{
  // ---------------------------------------------------------------------------
  
  static 
  { 
    initFlexiGlobals();
    initSlidePickerMarkup();
  }
  
  // ---------------------------------------------------------------------------
  
  protected GWTJSOTest() {}
  
  // ---------------------------------------------------------------------------
  
  private static native void initFlexiGlobals() 
  /*-{
    $wnd.type = ($wnd.SVGAngle
        || $doc.implementation.hasFeature(
            "http://www.w3.org/TR/SVG11/feature#BasicStructure", "1.1") ? "SVG"
        : "VML");
    $wnd.picker;
    $wnd.slide;
    $wnd.hueOffset = 15;
    $wnd.svgNS = 'http://www.w3.org/2000/svg';
    $wnd.colorpickerHTMLSnippet == [
        '<div class="picker-wrapper">',
        '<div class="picker"></div>',
        '<div class="picker-indicator"></div>',
        '</div>',
        '<div class="slide-wrapper">',
        '<div class="slide"></div>',
        '<div class="slide-indicator"></div>',
        '</div>'].join('');
  }-*/;
  
  // ---------------------------------------------------------------------------
  
  static native void initSlidePickerMarkup()
  /*-{
    
    function cSvg(el, attrs, children) 
    {
      el = $doc.createElementNS($wnd.svgNS, el);
      for (var key in attrs)
        el.setAttribute(key, attrs[key]);
      if (Object.prototype.toString.call(children) != '[object Array]') children = [children];
      var i = 0, len = (children[0] && children.length) || 0;
      for (; i < len; i++)
        el.appendChild(children[i]);
      return el;
    }
    
    if ($wnd.type == 'SVG')
    {
      $wnd.slide = cSvg('svg',
      {
        xmlns : 'http://www.w3.org/2000/svg',
        version : '1.1',
        width : '100%',
        height : '100%'
      }, [ cSvg('defs',
      {}, cSvg('linearGradient',
      {
        id : 'gradient-hsv',
        x1 : '0%',
        y1 : '100%',
        x2 : '0%',
        y2 : '0%'
      }, [ cSvg('stop',
      {
        offset : '0%',
        'stop-color' : '#FF0000',
        'stop-opacity' : '1'
      }), cSvg('stop',
      {
        offset : '13%',
        'stop-color' : '#FF00FF',
        'stop-opacity' : '1'
      }), cSvg('stop',
      {
        offset : '25%',
        'stop-color' : '#8000FF',
        'stop-opacity' : '1'
      }), cSvg('stop',
      {
        offset : '38%',
        'stop-color' : '#0040FF',
        'stop-opacity' : '1'
      }), cSvg('stop',
      {
        offset : '50%',
        'stop-color' : '#00FFFF',
        'stop-opacity' : '1'
      }), cSvg('stop',
      {
        offset : '63%',
        'stop-color' : '#00FF40',
        'stop-opacity' : '1'
      }), cSvg('stop',
      {
        offset : '75%',
        'stop-color' : '#0BED00',
        'stop-opacity' : '1'
      }), cSvg('stop',
      {
        offset : '88%',
        'stop-color' : '#FFFF00',
        'stop-opacity' : '1'
      }), cSvg('stop',
      {
        offset : '100%',
        'stop-color' : '#FF0000',
        'stop-opacity' : '1'
      }) ])), cSvg('rect',
      {
        x : '0',
        y : '0',
        width : '100%',
        height : '100%',
        fill : 'url(#gradient-hsv)'
      }) ]);
  
      $wnd.picker = cSvg('svg',
      {
        xmlns : 'http://www.w3.org/2000/svg',
        version : '1.1',
        width : '100%',
        height : '100%'
      }, [ cSvg('defs',
      {}, [ cSvg('linearGradient',
      {
        id : 'gradient-black',
        x1 : '0%',
        y1 : '100%',
        x2 : '0%',
        y2 : '0%'
      }, [ cSvg('stop',
      {
        offset : '0%',
        'stop-color' : '#000000',
        'stop-opacity' : '1'
      }), cSvg('stop',
      {
        offset : '100%',
        'stop-color' : '#CC9A81',
        'stop-opacity' : '0'
      }) ]), cSvg('linearGradient',
      {
        id : 'gradient-white',
        x1 : '0%',
        y1 : '100%',
        x2 : '100%',
        y2 : '100%'
      }, [ cSvg('stop',
      {
        offset : '0%',
        'stop-color' : '#FFFFFF',
        'stop-opacity' : '1'
      }), cSvg('stop',
      {
        offset : '100%',
        'stop-color' : '#CC9A81',
        'stop-opacity' : '0'
      }) ]) ]), cSvg('rect',
      {
        x : '0',
        y : '0',
        width : '100%',
        height : '100%',
        fill : 'url(#gradient-white)'
      }), cSvg('rect',
      {
        x : '0',
        y : '0',
        width : '100%',
        height : '100%',
        fill : 'url(#gradient-black)'
      }) ]);
    }
    else if ($wnd.type == 'VML')
    {
      $wnd.slide = [
          '<DIV style="position: relative; width: 100%; height: 100%">',
          '<v:rect style="position: absolute; top: 0; left: 0; width: 100%; height: 100%" stroked="f" filled="t">',
          '<v:fill type="gradient" method="none" angle="0" color="red" color2="red" colors="8519f fuchsia;.25 #8000ff;24903f #0040ff;.5 aqua;41287f #00ff40;.75 #0bed00;57671f yellow"></v:fill>',
          '</v:rect>', '</DIV>' ].join('');
  
      $wnd.picker = [
          '<DIV style="position: relative; width: 100%; height: 100%">',
          '<v:rect style="position: absolute; left: -1px; top: -1px; width: 101%; height: 101%" stroked="f" filled="t">',
          '<v:fill type="gradient" method="none" angle="270" color="#FFFFFF" opacity="100%" color2="#CC9A81" o:opacity2="0%"></v:fill>',
          '</v:rect>',
          '<v:rect style="position: absolute; left: 0px; top: 0px; width: 100%; height: 101%" stroked="f" filled="t">',
          '<v:fill type="gradient" method="none" angle="0" color="#000000" opacity="100%" color2="#CC9A81" o:opacity2="0%"></v:fill>',
          '</v:rect>', '</DIV>' ].join('');
  
      if (!$doc.namespaces['v'])
        $doc.namespaces.add('v', 'urn:schemas-microsoft-com:vml','#default#VML');
    }
  }-*/;
  
  // ---------------------------------------------------------------------------
  
  public static native FlexiType makeFlexiJSO()
  /*-{
    return {
      type : ($wnd.SVGAngle || $doc.implementation.hasFeature
          ("http://www.w3.org/TR/SVG11/feature#BasicStructure", "1.1") ? "SVG": "VML"),
      picker : null,
      slide : null, 
      hueOffset : 15, 
      svgNS : 'http://www.w3.org/2000/svg'
    };
  }-*/;
  
  // ===========================================================================
  // ===========================================================================
  
  public static class FlexiType extends JavaScriptObject
  {
    protected FlexiType(){}
    
    // -------------------------------------------------------------------------
    public final native String getType()
    /*-{
      return this.type;
    }-*/;
    
    // -------------------------------------------------------------------------
    public final native String getSvgNS()
    /*-{
      return this.svgNS;
    }-*/;
    
    // -------------------------------------------------------------------------
    public final native int getHueOffset()
    /*-{
      return this.hueOffset;
    }-*/;
    
    // -------------------------------------------------------------------------
    public final native void doCallback()
    /*-{
      $wnd.pickfunc();
    }-*/;
    
    // -------------------------------------------------------------------------
    // a callback that has a string for either success or failure
    public final native void addCallback(Callback<String, String> callback) 
    /*-{
      $wnd.pickfunc = function()
      {
        callback.@com.google.gwt.core.client.Callback::onSuccess(Ljava/lang/Object;)("success!");
      };
    }-*/;
  }
  
  // ---------------------------------------------------------------------------  
  // ---------------------------------------------------------------------------
  
  public static native String getType()
  /*-{
    return $wnd.type;
  }-*/;
  
  // ---------------------------------------------------------------------------
  
  public static native int getHueOffset()
  /*-{
    return $wnd.hueOffset;
  }-*/;
  


  // ---------------------------------------------------------------------------
  
  public static native void XXXX()
  /*-{
   }-*/;
  
  // ---------------------------------------------------------------------------
}
