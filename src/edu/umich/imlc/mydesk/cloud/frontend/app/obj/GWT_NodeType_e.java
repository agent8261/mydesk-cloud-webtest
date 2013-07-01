package edu.umich.imlc.mydesk.cloud.frontend.app.obj;

public enum GWT_NodeType_e
{
  OVAL("oval"),
  RECTANGLE("rectangle"),
  DIAMOND("diamond"),
  STAR("star");
  
  // ---------------------------------------------------------------------------
  // ---------------------------------------------------------------------------
  
  private final String nodeName;
  
  // ---------------------------------------------------------------------------
  
  GWT_NodeType_e(String nodeName)
  {
    this.nodeName = nodeName;
  }
  
  // ---------------------------------------------------------------------------
  
  public String toString()
  {
    return nodeName;
  }
  
  // ---------------------------------------------------------------------------
  
  public static GWT_NodeType_e fromString(String nodeType)
  {
    if(nodeType == null)
      throw new IllegalArgumentException();
    
    if(nodeType.equalsIgnoreCase(STAR.nodeName))
      return STAR;
    else if(nodeType.equalsIgnoreCase(RECTANGLE.nodeName))
      return RECTANGLE;
    else if(nodeType.equalsIgnoreCase(DIAMOND.nodeName))
      return DIAMOND;
    return OVAL;
  }
  
  // ---------------------------------------------------------------------------
}
