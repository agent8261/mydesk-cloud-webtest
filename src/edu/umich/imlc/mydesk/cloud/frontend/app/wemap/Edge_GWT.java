package edu.umich.imlc.mydesk.cloud.frontend.app.wemap;


import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.CssColor;
import com.google.gwt.user.client.rpc.IsSerializable;

import edu.umich.imlc.mydesk.cloud.frontend.ColorUtil;

public class Edge_GWT extends WeMapObject_GWT implements IsSerializable
{
  private Direction_GWT direction;
  // opposite referencePoint
  // Not used if anchored at node B.
  private Point_GWT endpoint;
  
  private String forwardTitle = "";
  private String reverseTitle = "";
  private String forwardNote = "";
  private String reverseNote = "";
  private String nodeA_ID = "";
  private String nodeB_ID = "";
  
  private final int ARROW_LENGTH = 50;
  private final int ARROW_ANGLE_DEGREES = 30;
  private final double MAGIK_RATIO = 0.75;
  private final int TITLE_BOX_HEIGHT = 50;
  private final int FONT_SIZE = 35;
  private String DEFAULT_FONT = "Helvetica";
  private final int MAX_TITLE_LENGTH = 5;
  
  //used for drawing
  private String localTitle;
  private Point_GWT endpoint_a;
  private Point_GWT endpoint_b;
  private WeMapFile_GWT mapFile;

  // ---------------------------------------------------------------------------
  // ---------------------------------------------------------------------------

  public Edge_GWT(WeMapObject_GWT object_gwt)
  {
    this(object_gwt.getObjectID(), object_gwt.getReferencePoint(), object_gwt
        .getObjectColor());
  }

  // ---------------------------------------------------------------------------

  public Edge_GWT(String objectID_, Point_GWT referencePoint_, int objectColor_)
  {
    super(objectID_, referencePoint_, objectColor_);
  }

  // ---------------------------------------------------------------------------

  /**
   * No-arg ctor for serialization
   */
  @SuppressWarnings("unused")
  private Edge_GWT()
  {
  }
 
  // ---------------------------------------------------------------------------
  
  public void draw(Context2d context)
  {
    drawEdge(context, mapFile);
  }
  
  // ---------------------------------------------------------------------------
  
  public void setFile(WeMapFile_GWT file)
  {
    mapFile = file;
  }
  
  // ---------------------------------------------------------------------------
  
  public void drawEdge(Context2d context, WeMapFile_GWT file)
  {
    context.beginPath();

    endpoint_a = calculateEndpoint(file.getNodeByID(nodeB_ID), 
                                   file.getNodeByID(nodeA_ID));

    endpoint_b = calculateEndpoint(file.getNodeByID(nodeA_ID),
                                   file.getNodeByID(nodeB_ID));

    context.moveTo(endpoint_a.x, endpoint_a.y);
    context.lineTo(endpoint_b.x, endpoint_b.y);
    context.setStrokeStyle(CssColor.make(ColorUtil.getRedComponent(objectColor),
        ColorUtil.getGreenComponent(objectColor),
        ColorUtil.getBlueComponent(objectColor)));
    context.stroke();
    drawTitle(endpoint_a, endpoint_b, context);
    drawArrows(context, file);
  }
  
  //----------------------------------------------------------------------------
  
  private void drawTitle(Point_GWT endpointA, Point_GWT endpointB, Context2d context)
  {
    if(forwardTitle.isEmpty())
    {
      return;
    }
    int midpointX = (int)((double)endpointA.x + (double)endpointB.x)/2;
    int midpointY = (int)((double)endpointA.y + (double)endpointB.y)/2;
    
    if(forwardTitle.length() > MAX_TITLE_LENGTH)
    {
      localTitle = forwardTitle.substring(0, MAX_TITLE_LENGTH-1);
    }
    else
    {
      localTitle = forwardTitle;
    }
    
    double textWidth = context.measureText(localTitle).getWidth();
    context.setFillStyle("black");
    context.fillRect(midpointX, midpointY, textWidth, TITLE_BOX_HEIGHT);
    context.setFillStyle("white");
    context.setFont(FONT_SIZE + "px" + DEFAULT_FONT);
    context.fillText(localTitle, midpointX + (int)(textWidth/2), midpointY + TITLE_BOX_HEIGHT/2);
  }
  
  //----------------------------------------------------------------------------
  
  private Point_GWT calculateEndpoint(Node_GWT startNode, Node_GWT endNode)
  {
    Point_GWT startPoint = startNode.getReferencePoint();
    Point_GWT endPoint = endNode.getReferencePoint();
    double xMin = Math.min(startPoint.x, endPoint.x);
    double xMax = Math.max(startPoint.x, endPoint.x);
    double a = endNode.getWidth()*MAGIK_RATIO;
    double b = endNode.getHeight();
    double xIntersect, yIntersect;
    
    if(endPoint.x == startPoint.x)
    {
      xIntersect = endPoint.x;
      yIntersect = (endPoint.y > startPoint.y ? endPoint.y - b : endPoint.y + b);
    }
    else
    {
      double m = (double)(startPoint.y-endPoint.y)/(double)(startPoint.x-endPoint.x);
      double c = startPoint.y - m * startPoint.x;
      
      xIntersect = MAGIK(endPoint.x, endPoint.y, m, c, a, b, false);
      if(xIntersect < xMin || xIntersect > xMax)
      {
        xIntersect = MAGIK(endPoint.x, endPoint.y, m, c, a, b, true);
      }
      yIntersect = m * xIntersect + c;
    }
    return new Point_GWT((int)xIntersect, (int)yIntersect);
  }
  
  //----------------------------------------------------------------------------
  
  private void drawArrows(Context2d context, WeMapFile_GWT file)
  {
    if(direction == Direction_GWT.NONDIRECTIONAL)
    {
      return;
    }
    else if(direction == Direction_GWT.FORWARD)
    {
      doDraw(file.getNodeByID(nodeB_ID).getReferencePoint(), endpoint_b, context);
    }
    else if(direction == Direction_GWT.REVERSE)
    {
      doDraw(file.getNodeByID(nodeA_ID).getReferencePoint(), endpoint_a, context);
    }
    else if(direction == Direction_GWT.BIDIRECTIONAL)
    {
      doDraw(file.getNodeByID(nodeB_ID).getReferencePoint(), endpoint_b, context);
      doDraw(file.getNodeByID(nodeA_ID).getReferencePoint(), endpoint_a, context);
    }
  }
  
  //----------------------------------------------------------------------------
  
  private void doDraw(Point_GWT destination, Point_GWT intersect, Context2d context)
  {
    double anchorX = destination.x - intersect.x;
    double anchorY = destination.y - intersect.y;
    double angleFromHorizontalDegrees = toDegrees(Math.atan2(anchorY, anchorX) + Math.PI);
    
    double offsetArmX = ARROW_LENGTH * Math.cos(toRadians(angleFromHorizontalDegrees + ARROW_ANGLE_DEGREES));
    double offsetArmY = ARROW_LENGTH * Math.sin(toRadians(angleFromHorizontalDegrees + ARROW_ANGLE_DEGREES));
    
    context.moveTo(intersect.x, intersect.y);
    context.lineTo(intersect.x + offsetArmX, intersect.y + offsetArmY);
    context.stroke();
    context.fill();
    
    offsetArmX = ARROW_LENGTH * Math.cos(toRadians(angleFromHorizontalDegrees - ARROW_ANGLE_DEGREES));
    offsetArmY = ARROW_LENGTH * Math.sin(toRadians(angleFromHorizontalDegrees - ARROW_ANGLE_DEGREES));
    
    context.moveTo(intersect.x, intersect.y);
    context.lineTo(intersect.x + offsetArmX, intersect.y + offsetArmY);
    context.stroke();
    context.fill();
  }
  
  // ---------------------------------------------------------------------------
  
  private double toRadians(double degrees)
  {
      return  (degrees * ((2*Math.PI)/360));
  }
  
  //----------------------------------------------------------------------------

  private double toDegrees(double radians)
  {
      return  ((radians * 360) / (2 * Math.PI));
  }
  
  //----------------------------------------------------------------------------
  
  // Solved quadratic without having to use sqrt

  double MAGIK(double a, double b, double m, double c, double q, double w,

      boolean neg)

  {

    // F THE POLICE

    double one, two, three;

    one = m * m * q * q + w * w;

    two = q

        * q

        * w

        * w

        * ((-(a * a) * m * m + 2 * a * b * m - 2 * a * c * m - b * b + 2 * b

            * c - c * c + m * m * q * q + w * w));

    two = Math.sqrt(two);

    three = a * w * w + b * m * q * q - c * m * q * q;

    if( neg )

      return (three-two) / one;

    return (two + three) / one;

  }// MAGIK

  // ---------------------------------------------------------------------------

  public Direction_GWT getDirection()
  {
    return direction;
  }

  // ---------------------------------------------------------------------------

  public void setDirection(Direction_GWT direction_)
  {
    direction = direction_;
  }

  // ---------------------------------------------------------------------------

  public Point_GWT getEndpoint()
  {
    return endpoint;
  }

  // ---------------------------------------------------------------------------

  public void setEndpoint(Point_GWT endpoint_)
  {
    endpoint = endpoint_;
  }

  // ---------------------------------------------------------------------------

  public String getForwardTitle()
  {
    return forwardTitle;
  }

  // ---------------------------------------------------------------------------

  public void setForwardTitle(String forwardTitle_)
  {
    forwardTitle = forwardTitle_;
  }

  // ---------------------------------------------------------------------------

  public String getReverseTitle()
  {
    return reverseTitle;
  }

  // ---------------------------------------------------------------------------

  public void setReverseTitle(String reverseTitle_)
  {
    reverseTitle = reverseTitle_;
  }

  // ---------------------------------------------------------------------------

  public String getForwardNote()
  {
    return forwardNote;
  }

  // ---------------------------------------------------------------------------

  public void setForwardNote(String forwardNote_)
  {
    forwardNote = forwardNote_;
  }

  // ---------------------------------------------------------------------------

  public String getReverseNote()
  {
    return reverseNote;
  }

  // ---------------------------------------------------------------------------

  public void setReverseNote(String reverseNote_)
  {
    reverseNote = reverseNote_;
  }

  // ---------------------------------------------------------------------------

  public String getNodeA_ID()
  {
    return nodeA_ID;
  }

  // ---------------------------------------------------------------------------

  public void setNodeA_ID(String nodeA_ID_)
  {
    nodeA_ID = nodeA_ID_;
  }

  // ---------------------------------------------------------------------------

  public String getNodeB_ID()
  {
    return nodeB_ID;
  }

  // ---------------------------------------------------------------------------

  public void setNodeB_ID(String nodeB_ID_)
  {
    nodeB_ID = nodeB_ID_;
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
    Edge_GWT other = (Edge_GWT) obj;
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
