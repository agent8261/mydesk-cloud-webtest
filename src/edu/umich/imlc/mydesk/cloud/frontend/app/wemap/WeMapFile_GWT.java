package edu.umich.imlc.mydesk.cloud.frontend.app.wemap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.CssColor;

import edu.umich.imlc.mydesk.cloud.frontend.ColorUtil;
import edu.umich.imlc.mydesk.cloud.frontend.canvas.DrawableFile;

public class WeMapFile_GWT implements DrawableFile
{
  private String fileName;
  private String fileID;
  private String fileFormatVersion;
  private final int GRID_LINE_WIDTH = 3;
  private final int THICK_GRID_LINE_WIDTH = 6;
  private final int GRID_SPACING = 40;
  private final int THICK_GRID_LINE_SPACER = 400;
  private final int ALPHA_LINE_COLOR = 50;
  private final int LINE_COLOR = 11583201;

  // This list is for draw order, and hit detection.
  // Draw from beginning to end.
  // Detect hits from end to beginning.
  // When an object is touched or moved, move it to the end of the list.
  private List<WeMapObject_GWT> objects = new ArrayList<WeMapObject_GWT>();

  // ---------------------------------------------------------------------------
  // ---------------------------------------------------------------------------

  public WeMapFile_GWT(String fileName_, String fileID_,
      String fileFormatVersion_, List<Node_GWT> nodes_, List<Edge_GWT> edges_)
  {
    this(fileName_, fileID_, fileFormatVersion_);

    for( Node_GWT node : nodes_ )
    {
      objects.add(node);
    }

    for( Edge_GWT edge : edges_ )
    {
      objects.add(edge);
    }

  }// ctor

  // ---------------------------------------------------------------------------

  public WeMapFile_GWT(String fileName_, String fileID_,
      String fileFormatVersion_)
  {
    fileName = fileName_;
    fileID = fileID_;
    fileFormatVersion = fileFormatVersion_;
  }// ctor

  // ---------------------------------------------------------------------------

  /*
   * No-arg ctor for serialization
   */
  @SuppressWarnings("unused")
  private WeMapFile_GWT()
  {
  }

  // ---------------------------------------------------------------------------

  public Node_GWT getNodeByID(String id)
  {
    for( WeMapObject_GWT object : objects )
    {
      if( object.getObjectID().equals(id) )
      {
        return (Node_GWT) object;
      }
    }// for

    return null;
  }// getNodeByID

  // ---------------------------------------------------------------------------

  public Edge_GWT getEdgeByID(String id)
  {
    for( WeMapObject_GWT object : objects )
    {
      if( object.getObjectID().equals(id) )
      {
        return (Edge_GWT) object;
      }
    }// for

    return null;
  }// getEdgeByID

  // ---------------------------------------------------------------------------

  public Map<String, Node_GWT> getNodes()
  {
    Map<String, Node_GWT> nodes = new HashMap<String, Node_GWT>();

    for( WeMapObject_GWT object : objects )
    {
      if( object instanceof Node_GWT )
      {
        nodes.put(object.getObjectID(), (Node_GWT) object);
      }
    }// for

    return Collections.unmodifiableMap(nodes);
  }// getNodes

  // ---------------------------------------------------------------------------

  public Map<String, Edge_GWT> getEdges()
  {
    Map<String, Edge_GWT> edges = new HashMap<String, Edge_GWT>();

    for( WeMapObject_GWT object : objects )
    {
      if( object instanceof Edge_GWT )
      {
        edges.put(object.getObjectID(), (Edge_GWT) object);
      }
    }// for

    return Collections.unmodifiableMap(edges);
  }// getEdges

  // ---------------------------------------------------------------------------

  public List<WeMapObject_GWT> getWeMapObjects()
  {
    return objects;
  }
  
  // ---------------------------------------------------------------------------
  
  public void drawGrid(Canvas canvas)
  {
    Context2d context = canvas.getContext2d();
    //don't know if i need to save this but i will
    double alphaHolder = context.getGlobalAlpha();
    context.setGlobalAlpha(ALPHA_LINE_COLOR);
    context.setStrokeStyle(CssColor.make(ColorUtil.getRedComponent(LINE_COLOR), 
        ColorUtil.getGreenComponent(LINE_COLOR), 
        ColorUtil.getBlueComponent(LINE_COLOR)));
    
    for(int i=0; i<canvas.getCoordinateSpaceWidth(); i += GRID_SPACING)
    {
      context.setLineWidth(setGridLineWidth(i));
      context.beginPath();
      context.moveTo(i, 0);
      context.lineTo(i, canvas.getCoordinateSpaceHeight());
      context.stroke();
    }
    
    for(int i=0; i<canvas.getCoordinateSpaceHeight(); i+= GRID_SPACING)
    {
      context.setLineWidth(setGridLineWidth(i));
      context.beginPath();
      context.moveTo(0,i);
      context.lineTo(canvas.getCoordinateSpaceWidth(), i);
      context.stroke();
    }
    //don't know if i need to restore this but I will
    context.setGlobalAlpha(alphaHolder);
  }

  // ---------------------------------------------------------------------------
  // ---------------------------------------------------------------------------
  
  private int setGridLineWidth (int line)
  {
    if(line%THICK_GRID_LINE_SPACER == 0)
    {
      return THICK_GRID_LINE_WIDTH;
    }
    else
    {
      return GRID_LINE_WIDTH;
    }
  }
  
  // ---------------------------------------------------------------------------

  public static WeMapFile_GWT makeDummyFile(String name_)
  {
    WeMapFile_GWT file = new WeMapFile_GWT(name_, "id", "version");
    Node_GWT nodeA;
    Node_GWT nodeB;
    WeMapObject_GWT base;
    Point_GWT point;
    Edge_GWT edge;
    
    point = new Point_GWT(2500, 2864);
    base = new WeMapObject_GWT("fcf3efcb-97ef-4090-9f60-766ace344336", point, 0);
    nodeA = new Node_GWT(base);
    nodeA.setTitle("Hello Wurld longer string!!!111111111");
    file.getWeMapObjects().add(nodeA);
    point = new Point_GWT(2934, 2762);
    base = new WeMapObject_GWT("3be34c42-c8cb-4c59-b379-cb25a874d190", point, 0);
    nodeB = new Node_GWT(base);
    file.getWeMapObjects().add(nodeB);
    base = new WeMapObject_GWT("asdfadsf", point , 0);
    edge = new Edge_GWT(base);
    edge.setDirection(Direction_GWT.FORWARD);
    edge.setNodeA_ID("fcf3efcb-97ef-4090-9f60-766ace344336");
    edge.setNodeB_ID("3be34c42-c8cb-4c59-b379-cb25a874d190");
    edge.setFile(file);
    file.getWeMapObjects().add(edge);
    return file;
  }
  
  // ---------------------------------------------------------------------------

  public String getFileName()
  {
    return fileName;
  }

  // ---------------------------------------------------------------------------

  public void setFileName(String fileName_)
  {
    fileName = fileName_;
  }

  // ---------------------------------------------------------------------------

  public String getFileID()
  {
    return fileID;
  }

  // ---------------------------------------------------------------------------

  public void setFileID(String fileID_)
  {
    fileID = fileID_;
  }

  // ---------------------------------------------------------------------------

  public String getFileFormatVersion()
  {
    return fileFormatVersion;
  }

  // ---------------------------------------------------------------------------

  public void setFileFormatVersion(String fileFormatVersion_)
  {
    fileFormatVersion = fileFormatVersion_;
  }

  @Override
  public void draw(Context2d context)
  {
    // TODO Auto-generated method stub
    
  }

  // ---------------------------------------------------------------------------

}// class
