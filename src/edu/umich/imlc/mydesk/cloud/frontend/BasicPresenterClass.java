package edu.umich.imlc.mydesk.cloud.frontend;

import com.google.gwt.user.client.ui.HasWidgets;

public abstract class BasicPresenterClass implements BasicPresenter
{
  // ---------------------------------------------------------------------------
  // ---------------------------------------------------------------------------

  @Override
  public void go(HasWidgets viewContainer_)
  {
    viewContainer_.clear();
    viewContainer_.add(getView().asWidget());
  }
  
  // ---------------------------------------------------------------------------
  
}// class
