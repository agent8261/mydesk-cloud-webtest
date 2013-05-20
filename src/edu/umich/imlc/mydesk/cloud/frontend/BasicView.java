package edu.umich.imlc.mydesk.cloud.frontend;

import com.google.gwt.user.client.ui.RequiresResize;
import com.google.gwt.user.client.ui.Widget;

/**
 * A BasicView is driven by a BasicPresenter and will typically extend some
 * Layout or Widget class. The view does not contain any business logic. All
 * changes to a view should be made by its associated presenter.
 */
public interface BasicView extends RequiresResize
{
  Widget asWidget();
}// interface
