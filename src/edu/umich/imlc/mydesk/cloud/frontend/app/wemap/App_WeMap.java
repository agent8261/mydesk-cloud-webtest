package edu.umich.imlc.mydesk.cloud.frontend.app.wemap;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import edu.umich.imlc.mydesk.cloud.frontend.BasicPresenterClass;
import edu.umich.imlc.mydesk.cloud.frontend.BasicView;
import edu.umich.imlc.mydesk.cloud.frontend.PresentersView;
import edu.umich.imlc.mydesk.cloud.frontend.app.drawables.DrawableObject;
import edu.umich.imlc.mydesk.cloud.frontend.app.drawables.DrawableObject.DrawingSurface;
import edu.umich.imlc.mydesk.cloud.frontend.app.obj.GWT_Node;
import edu.umich.imlc.mydesk.cloud.frontend.app.obj.GWT_Oval;

/**
 * Contains the current state of the application.
 * Provides functions to alter that state.
 *
 */
public class App_WeMap extends BasicPresenterClass
{
  static final String ID_HEAD = Long.toString((new Date()).getTime());
  static long NEXT_OBJ_ID = 0;
  PresentersView weMapView = new UIController();
  
  DrawingSurface surface = null;
  HashMap<String, GWT_Node> allNodes = new HashMap<String, GWT_Node>();
  ArrayList<Command> undoStack = new ArrayList<Command>();
  ArrayList<Command> redoStack = new ArrayList<Command>();
  
  // ---------------------------------------------------------------------------
  // ---------------------------------------------------------------------------
  
  public App_WeMap()
  {
    weMapView.setPresenter(this);
    createNewNode(30, 30, "Foo", "A Bar of Soap", "#000000");
    surface.draw();
  }
  
  // ---------------------------------------------------------------------------
  
  public void setDrawingSurface(DrawingSurface surface)
  {
    this.surface = surface;
  }
  
  // ---------------------------------------------------------------------------
  
  @Override
  public BasicView getView()
  {
    return weMapView;
  }
  
  // ---------------------------------------------------------------------------
  
  public GWT_Node getNode(String objID)
  {
    return allNodes.get(objID);
  }
  
  // ---------------------------------------------------------------------------
  
  public void editNode(String objID, String title, String note, String color)
  {
    GWT_Node node = allNodes.get(objID);
    if(node == null)
      throw new IllegalStateException();
    String oldT = node.getTitle(), 
        oldN = node.getNote(), oldC = node.getColor();
    Command cmd = new CmdEditNode(objID, oldT, oldN, oldC, title, note, color);
    doCommand(cmd);
  }
  
  // ---------------------------------------------------------------------------
  
  public String getNote(String objID)
  {
    GWT_Node node = allNodes.get(objID);    
    if(node == null)
      throw new IllegalStateException();
    return node.getNote();
  }
  
  // ---------------------------------------------------------------------------
  
  public void createNewNode
    (double centerX, double centerY, String title, String note, String color)
  {
    checkDrawingSurface();
    String objID = ID_HEAD + NEXT_OBJ_ID++;
    Command cmd = new 
        CmdCreateNewNode(centerX, centerY, title, note, color, objID);
    doCommand(cmd);
  }
  
  // ---------------------------------------------------------------------------
  
  public void updateNodeMove
    (String objID, double startX, double startY, double endX, double endY)
  {
    checkDrawingSurface();
    if(objID == null)
      throw new IllegalStateException();
    CmdMoveNode cmd = new CmdMoveNode(objID, startX, startY, endX, endY);
    doCommand(cmd);
  }
  
  // ---------------------------------------------------------------------------
  
  public boolean isUndoable()
  {
    return (undoStack.size() > 0);
  }
  
  // ---------------------------------------------------------------------------
  
  public boolean isRedoable()
  {
    return (redoStack.size() > 0);
  }
  
  // ---------------------------------------------------------------------------
  
  // remove from undo stack, push to redo stack, cmd.undo
  public void undo()
  {
    checkDrawingSurface();
    int last = undoStack.size() - 1;
    if(last < 0)
      return;
    Command cmd = undoStack.remove(last);
    redoStack.add(cmd);
    cmd.undoCommand();
    surface.draw();
  }
  
  // ---------------------------------------------------------------------------
  
  // remove from redo stack, push to undo stack cmd.do
  public void redo()
  {
    checkDrawingSurface();
    int last = redoStack.size() - 1;
    if(last < 0)
      return;
    Command cmd = redoStack.remove(last);
    undoStack.add(cmd);
    cmd.doCommand();
    surface.draw();
  }
  
  // ---------------------------------------------------------------------------
  
  // Used when the first time a command is executed
  // Push command to undo stack, clear redo stack. 
  void doCommand(Command cmd)
  {
    undoStack.add(cmd);
    redoStack.clear();
    cmd.doCommand();
    surface.draw();
  }
  
  // ---------------------------------------------------------------------------
  
  GWT_Node getNodeChecked(String objID)
  {
    GWT_Node node = allNodes.get(objID);
    if(node == null)
      throw new IllegalStateException();
    return node;
  }
  
  // ---------------------------------------------------------------------------
  
  void checkDrawingSurface()
  {
    if(surface == null)
      throw new IllegalStateException();
  }

  // ===========================================================================
  // ===========================================================================
  
  static interface Command
  {
    public void doCommand();
    public void undoCommand();
  }
  
  // ---------------------------------------------------------------------------
  // ---------------------------------------------------------------------------
  
  class CmdEditNode implements Command
  {
    final String objID;
    final String oTitle, oNote, oColor;
    final String nTitle, nNote, nColor;
    
    CmdEditNode(String objID, String oldtitle, String oldNote, String oldColor,
        String newTitle, String newNote, String newColor)
    {
      this.objID = objID; oTitle = oldtitle; oNote = oldNote;
      oColor = oldColor; nTitle = newTitle; nNote = newNote; nColor = newColor;
    }

    @Override
    public void doCommand()
    {
      setNode(nTitle, nNote, nColor);
    }

    @Override
    public void undoCommand()
    {
      setNode(oTitle, oNote, oColor);
    }
    
    void setNode(String t, String n, String c)
    {
      GWT_Node node = getNodeChecked(objID);
      node.setColor(c);
      node.setTitle(t);
      node.setNote(n);
      surface.draw();
    }
  }
  
  // ---------------------------------------------------------------------------
  // ---------------------------------------------------------------------------
  
  class CmdMoveNode implements Command
  {
    final String objID;
    final double startX, startY, endX, endY;
    
    CmdMoveNode
      (String objID, double startX, double startY, double endX, double endY)
    {
      this.objID = objID; this.startX = startX; this.startY = startY;
      this.endX = endX; this.endY = endY;
    }
    
    void move(double x, double y)
    {
      GWT_Node node = getNodeChecked(objID);
      node.setPosition(x, y);
    }
    
    @Override
    public void doCommand()
    {
      move(endX, endY);
    }

    @Override
    public void undoCommand()
    {
      move(startX, startY);
    }
  } // CmdMoveNode
  
  // ---------------------------------------------------------------------------
  // ---------------------------------------------------------------------------
  
  class CmdCreateNewNode implements Command
  {
    final double centerX, centerY;
    final String title, note, color, objID;
    
    // -------------------------------------------------------------------------
    // -------------------------------------------------------------------------
    
    CmdCreateNewNode(double centerX, double centerY, String title, 
        String note, String color, String objID)
    {
      this.centerX = centerX; this.centerY = centerY; this.title = title;
      this.note = new String(note); this.color = new String(color);
      this.objID = new String(objID); 
    }
    
    // -------------------------------------------------------------------------
    
    @Override
    public void doCommand()
    {
      GWT_Oval node = 
          new GWT_Oval(objID, centerX, centerY, title, note, color, surface);
      allNodes.put(objID, node);
    }

    // -------------------------------------------------------------------------
    
    @Override
    public void undoCommand()
    {
      GWT_Node node = getNodeChecked(objID);
      DrawableObject dObj = node.getDrawnObject();
      dObj.removeFrom(surface);
      allNodes.remove(objID);
    }
  } // CmdCreateNewNode
  
  // -------------------------------------------------------------------------
}







