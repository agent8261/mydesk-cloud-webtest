package edu.umich.imlc.mydesk.cloud.frontend;

public class ColorUtil
{
  // ---------------------------------------------------------------------------

  public static int getBlueComponent(int color)
  {
    return (color & 0x000000ff);
  }

  // ---------------------------------------------------------------------------

  public static int getGreenComponent(int color)
  {
    return (color & 0x0000ff00) >> 8;
  }

  // ---------------------------------------------------------------------------

  public static int getRedComponent(int color)
  {
    return (color & 0x00ff0000) >> 16;
  }

  // ---------------------------------------------------------------------------

  public static int getAlphaComponent(int color)
  {
    return (color & 0xff000000) >> 24;
  }

}
