package edu.umich.imlc.mydesk.cloud.frontend;

import com.google.gwt.user.client.ui.HasWidgets;


/**
 * 
 * When the master app's controller receives a "value change" event. The
 * following sequence of events happen:
 * 
 * The controller parses the history string and checks if the BasePageName has
 * changed. If the name is the same it will assume that some state internal to
 * that page has changed and call BasicPresenter.onStateChange()
 * 
 * If the name is different then it will then construct the corresponding
 * presenter, load it’s view and then call BasicPresenter.onPostLoad();
 * 
 */
public interface BasicPresenter
{
  public BasicView getView();

  // Implementing class add it's view to the to given viewContainer
  public void go(HasWidgets viewContainer);
  
}// interface
