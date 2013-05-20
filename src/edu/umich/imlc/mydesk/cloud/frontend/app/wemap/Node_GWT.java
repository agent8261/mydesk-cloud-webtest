package edu.umich.imlc.mydesk.cloud.frontend.app.wemap;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.CssColor;
import com.google.gwt.user.client.rpc.IsSerializable;

import edu.umich.imlc.mydesk.cloud.frontend.ColorUtil;

public class Node_GWT extends WeMapObject_GWT implements IsSerializable
{
  private String title;
  private String note;
  private byte imageData[];

  private int height;
  private int width;
  private final int DEFAULT_WIDTH = 150;
  private final int DEFAULT_HEIGHT = 40;
  private final int DEFAULT_ELLIPSE_WIDTH = 5;
  private final int MAX_TITLE_LENGTH = 30;
  private final int MIN_TITLE_LENGTH = 5;
  private final String DEFAULT_FONT = "Helvetica";
  private final String DEFAULT_FONT_SIZE = "35";
  private String node_title = "";

  // ---------------------------------------------------------------------------
  // ---------------------------------------------------------------------------

  public Node_GWT(WeMapObject_GWT object_gwt)
  {
    this(object_gwt.getObjectID(), object_gwt.getReferencePoint(), object_gwt
        .getObjectColor());
    width = DEFAULT_WIDTH;
    height = DEFAULT_HEIGHT;
  }

  // ---------------------------------------------------------------------------

  public Node_GWT(String objectID_, Point_GWT referencePoint_, int objectColor_)
  {
    super(objectID_, referencePoint_, objectColor_);
    width = DEFAULT_WIDTH;
    height = DEFAULT_HEIGHT;
  }

  // ---------------------------------------------------------------------------

  /**
   * No-arg ctor for serialization
   */
  @SuppressWarnings("unused")
  private Node_GWT()
  {

  }

  // ---------------------------------------------------------------------------

  private int centerX()
  {
    return referencePoint.x;
  }

  // ---------------------------------------------------------------------------

  private int centerY()
  {
    return referencePoint.y;

  } 

  // ---------------------------------------------------------------------------

  private void drawEllipse(Context2d context)
  {
    context.setLineWidth(DEFAULT_ELLIPSE_WIDTH);
    context.setStrokeStyle(CssColor.make(ColorUtil.getRedComponent(objectColor),
        ColorUtil.getGreenComponent(objectColor),
        ColorUtil.getBlueComponent(objectColor)));
    
    context.beginPath();
    context.moveTo(centerX(), centerY() - height);
    context.bezierCurveTo(centerX() + width, centerY() - height, centerX()
        + width, centerY() + height, centerX(), centerY() + height);

    context.bezierCurveTo(centerX() - width, centerY() + height, centerX()
        - width, centerY() - height, centerX(), centerY() - height);
    context.stroke();
    handleNotes(context);
  }

  // ---------------------------------------------------------------------------

  public void draw(Context2d context)
  {
    initializeNodeTitle(context);
    drawEllipse(context);
    fillTitle(context);
  }// drawNode()
  
  // ---------------------------------------------------------------------------
  
  private void fillTitle(Context2d context)
  {
    if( !node_title.isEmpty() )
    {   
      if( (objectColor == 0) && (note != null) && !note.isEmpty())
      {
        context.setFillStyle("white");
      }
      else
      {
        context.setFillStyle("black");
      }
      context.setFont(DEFAULT_FONT_SIZE + "px " + DEFAULT_FONT);
      context.setTextAlign(Context2d.TextAlign.CENTER);
      context.fillText(node_title, centerX(), centerY());
    }    
  }
  
  // ---------------------------------------------------------------------------
  
  private void handleNotes(Context2d context)
  {
    if((note == null) || (note.isEmpty()))
    {
      context.setFillStyle("white");
    }
    else
    {
      context.setFillStyle(CssColor.make(ColorUtil.getRedComponent(objectColor),
          ColorUtil.getGreenComponent(objectColor),
          ColorUtil.getBlueComponent(objectColor)));
    }
    context.fill();
  }
  
  // ---------------------------------------------------------------------------
  
  private void initializeNodeTitle(Context2d context)
  {
    context.setFont(DEFAULT_FONT_SIZE + "px " + DEFAULT_FONT);
    if((title != null) && !title.isEmpty())
    {
      if(title.length() > MAX_TITLE_LENGTH)
      {
        node_title = title.substring(0, 26);
        node_title += "...";
        width = (int)context.measureText(node_title).getWidth();
      }
      else if(title.length() > MIN_TITLE_LENGTH)
      {
        node_title = title;
        width = (int)context.measureText(node_title).getWidth();
      }
      else
      {
        node_title = title;
      }
    }
  }

  // ---------------------------------------------------------------------------

  public String getTitle()
  {
    return title;
  }

  // ---------------------------------------------------------------------------

  public void setTitle(String title_)
  {
    title = title_;
  }

  // ---------------------------------------------------------------------------

  public String getNote()
  {
    return note;
  }

  // ---------------------------------------------------------------------------

  public void setNote(String note_)
  {
    note = note_;
  }

  // ---------------------------------------------------------------------------

  public byte[] getImageData()
  {
    return imageData;
  }

  // ---------------------------------------------------------------------------

  public void setImageData(byte[] imageData_)
  {
    imageData = imageData_;
  }

  // ---------------------------------------------------------------------------

  public int getHeight()
  {
    return height;
  }

  // ---------------------------------------------------------------------------

  public void setHeight(int height_)
  {
    height = height_;
  }

  // ---------------------------------------------------------------------------

  public int getWidth()
  {
    return width;
  }

  // ---------------------------------------------------------------------------

  public void setWidth(int width_)
  {
    width = width_;
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
    Node_GWT other = (Node_GWT) obj;
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
