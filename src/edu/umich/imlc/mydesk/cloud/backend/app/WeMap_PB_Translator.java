package edu.umich.imlc.mydesk.cloud.backend.app;

import edu.umich.imlc.mydesk.app.wemap.WeMapProtocolBuffer.WeMapFile_PB;
import edu.umich.imlc.mydesk.cloud.frontend.app.obj.GWT_WeMapFile;

/**
 * Converts between a GWT WeMap File and PB WeMap file
 */
public class WeMap_PB_Translator
{
  // ---------------------------------------------------------------------------
  // ---------------------------------------------------------------------------
  
  public static GWT_WeMapFile makeGWTFile(WeMapFile_PB pbFile)
  {
    WeMap_PB_Translator codec = new WeMap_PB_Translator();
    return codec.readPb();
  }
  
  // ---------------------------------------------------------------------------
  
  public static WeMapFile_PB makePBFile(GWT_WeMapFile gwtFile)
  {
    WeMap_PB_Translator codec = new WeMap_PB_Translator();
    return codec.readGWT();
  }
  
  // ---------------------------------------------------------------------------
  
  GWT_WeMapFile readPb()
  {
    return null;
  }
  
  // ---------------------------------------------------------------------------
  
  WeMapFile_PB readGWT()
  {
    return null;
  }
  
  // ---------------------------------------------------------------------------
}
