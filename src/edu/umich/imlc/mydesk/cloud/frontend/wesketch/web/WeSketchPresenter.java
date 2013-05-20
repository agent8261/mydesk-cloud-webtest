package edu.umich.imlc.mydesk.cloud.frontend.wesketch.web;


import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

import edu.umich.imlc.mydesk.cloud.frontend.BasicPresenterClass;
import edu.umich.imlc.mydesk.cloud.frontend.BasicView;
import edu.umich.imlc.mydesk.cloud.frontend.app.wesketch.DrawRectangleEvent_GWT;
import edu.umich.imlc.mydesk.cloud.frontend.app.wesketch.Point_GWT;
import edu.umich.imlc.mydesk.cloud.frontend.app.wesketch.Slide_GWT;
import edu.umich.imlc.mydesk.cloud.frontend.app.wesketch.WeSketchBaseEvent_GWT;
import edu.umich.imlc.mydesk.cloud.frontend.app.wesketch.WeSketchFile_GWT;
import edu.umich.imlc.mydesk.cloud.frontend.history.HistoryTokenParser;
import edu.umich.imlc.mydesk.cloud.frontend.rpc.RPCServiceAsync;
import edu.umich.imlc.mydesk.cloud.frontend.utilities.FrontendUtil;

public class WeSketchPresenter extends BasicPresenterClass
{
  
  private RPCServiceAsync rpc;
  private WeSketchFile_GWT file = null;
  @SuppressWarnings("unused")
  private String fileID;
  private WeSketchView view;
  private int currentSlideIndex = 0;

  // ---------------------------------------------------------------------------
  // ---------------------------------------------------------------------------

  /**
   * @param fileID_
   * @param rpc_
   */
  public WeSketchPresenter(RPCServiceAsync rpc_)
  {
    FrontendUtil.printMethodName();
    assert (rpc_ != null);
    rpc = rpc_;
    view = new WeSketchView();
    file = makeDefaultFile();
    //bind();
  }// ctor

  // ---------------------------------------------------------------------------

  /**
   * @param file_
   * @param rpc_
   */
  public WeSketchPresenter(WeSketchFile_GWT file_, RPCServiceAsync rpc_)
  {
    FrontendUtil.printMethodName();
    assert (file_ != null);
    assert (rpc_ != null);

    file = file_;
    fileID = file.getFileID();
    rpc = rpc_;
    view = new WeSketchView();
    //bind();
  }// ctor

  // ---------------------------------------------------------------------------
  
  /** 
   * @see edu.umich.imlc.mydesk.cloud.frontend.BasicPresenter#getView()
   */
  @Override
  public BasicView getView()
  {
    return view;
  } // getView

  // ---------------------------------------------------------------------------
  
  /** 
   * @see edu.umich.imlc.mydesk.cloud.frontend.BasicPresenter#onPostLoad(HistoryTokenParser)
   */
  @Override
  public void onPostLoad(HistoryTokenParser tokenParser)
  {
    FrontendUtil.printMethodName();
    drawSlide();
  }
  
  // ---------------------------------------------------------------------------
  
  @Override
  public void onStateChange(HistoryTokenParser tokenParser)
  {
    FrontendUtil.printMethodName();
    drawSlide();
  }
  
  // ---------------------------------------------------------------------------

  /**
   * Attach clickHandlers, etc., to objects/widgets in the view.
   */
  private void bind()
  {
    FrontendUtil.printMethodName();
    if(file.getAllSlides().size() == 1)
    {
      view.getPreviousSlideButton().setEnabled(false);
      view.getNextSlideButton().setEnabled(false);
    }
    else
    {
      view.getPreviousSlideButton().setEnabled(false);
    }

    // Add Handler for Save button
    view.getBackButton().addClickHandler(new DoBackHandler());
    view.getCancelButton().addClickHandler(new DoCancelHandler());
    view.getNextSlideButton().addClickHandler(new DoNextSlideHandler());
    view.getPreviousSlideButton().addClickHandler(new DoPrevSlideHandler());   
    view.getTitleLabel().setText(file.getFileName());
    view.getCurrentSlideLabel().setText(String.valueOf(currentSlideIndex));
  }// bind

  // ---------------------------------------------------------------------------

  /**
   * @param index
   */
  public void drawSlide()
  {
    FrontendUtil.printMethodName();
    for( WeSketchBaseEvent_GWT event : file.getSlide(currentSlideIndex).getAllEvents() )
    {
      //file.getSlide(currentSlideIndex).draw(view.getFrontCanvas());
      //event.draw(view.getFrontCanvas());
    }// for
  }// drawSlide
  
  // ---------------------------------------------------------------------------
  // ---------------------------------------------------------------------------

  /**
   * DoBackHandler
   */
  private class DoBackHandler implements ClickHandler
  {
    @Override
    public void onClick(ClickEvent event)
    {
      FrontendUtil.printMethodName();
      // TODO: RPC call to server with new file.
    }
  }

  // ---------------------------------------------------------------------------
  // ---------------------------------------------------------------------------
  
  /**
   * DoCancelHandler
   */
  private class DoCancelHandler implements ClickHandler
  {
    @Override
    public void onClick(ClickEvent event)
    {
      FrontendUtil.printMethodName();
    }
  }
  
  // ---------------------------------------------------------------------------
  // ---------------------------------------------------------------------------
  
  /**
   * DoNextSlideHandler
   */
  private class DoNextSlideHandler implements ClickHandler
  {
    @Override
    public void onClick(ClickEvent event)
    {
      FrontendUtil.printMethodName();
      currentSlideIndex++;
      view.getCurrentSlideLabel().setText(String.valueOf(currentSlideIndex));
      if(currentSlideIndex == file.getAllSlides().size()-1)
      {
        view.getNextSlideButton().setEnabled(false);
      }
      view.getPreviousSlideButton().setEnabled(true);
      view.clearFrontCanvas();
      drawSlide();
    }
  }
  
  // ---------------------------------------------------------------------------
  // ---------------------------------------------------------------------------
  
  /**
   * DoPrevSlideHandler
   */
  private class DoPrevSlideHandler implements ClickHandler
  {
    @Override
    public void onClick(ClickEvent event)
    {
      FrontendUtil.printMethodName();
      currentSlideIndex--;
      view.getCurrentSlideLabel().setText(String.valueOf(currentSlideIndex));
      if(currentSlideIndex == 0)
      {
        view.getPreviousSlideButton().setEnabled(false);
      }
      view.getNextSlideButton().setEnabled(true);
      view.clearFrontCanvas();
      drawSlide();
    }
  }

  // ---------------------------------------------------------------------------
  // ---------------------------------------------------------------------------

  /**
   * This callback's methods are called upon completion (success or failure) of
   * a call to a file from the server.
   */
  @SuppressWarnings("unused")
  private class Callback_getWeSketchFile implements
      AsyncCallback<WeSketchFile_GWT>
  {

    @Override
    public void onFailure(Throwable caught)
    {
      FrontendUtil.printMethodName();
      caught.printStackTrace();
      Window.alert("Could not retrieve file.");
    }// onFailure

    // -------------------------------------------------------------------------

    @Override
    public void onSuccess(WeSketchFile_GWT result)
    {
      FrontendUtil.printMethodName();
      file = result;
      // TODO: Show file in view.
    }// onSuccess

  }// inner class

  // ---------------------------------------------------------------------------
  
  public void loadFile(String fileID)
  {
    FrontendUtil.printMethodName();
    rpc.getWeSketchFile(fileID, new AsyncCallback<WeSketchFile_GWT>()
    {
      @Override
      public void onFailure(Throwable caught)
      {
        Window.alert("load failed: " + caught.getMessage());
      }
      @Override
      public void onSuccess(WeSketchFile_GWT result)
      {
        file = result;
        bind();
        drawSlide();
        view.resizeCanvas();
      }
    });
  }
  
  
  // ---------------------------------------------------------------------------
  
  private static WeSketchFile_GWT makeDefaultFile()
  {
    FrontendUtil.printMethodName();
    WeSketchFile_GWT file = new WeSketchFile_GWT("");
    file.setFileName("Default");
    Slide_GWT slide = new Slide_GWT("1a0a04de1-8680-4f74-a4e6-f94c238e5833");

    // Event 4
    DrawRectangleEvent_GWT rectEvent = new DrawRectangleEvent_GWT(4);
    rectEvent.color = -7260208;
    rectEvent.topLeft = new Point_GWT(396, 307);
    rectEvent.bottomRight = new Point_GWT(651, 759);
    slide.add(rectEvent);
    
    file.addSlide(slide);
    return file;
  }// makeDummyFile  
  // ---------------------------------------------------------------------------

}// class
