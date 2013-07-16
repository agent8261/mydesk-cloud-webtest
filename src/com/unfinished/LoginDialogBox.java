package com.unfinished;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.SpanElement;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiFactory;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class LoginDialogBox extends FlowPanel
{
  // <RootTypeUsedInXmlFile, ClassWeWantToBindTemplateTo>
  @UiTemplate("LoginDialogBox.ui.xml")
  interface MyBinder extends UiBinder<Widget, LoginDialogBox>{}
  
  private static MyBinder uiBinder = GWT.create(MyBinder.class);
  
  @UiField Button btnLogin;
  @UiField TextBox txtEmail;
  @UiField SpanElement eEmailErrorText;
  @UiField SpanElement ePassErrorText;
  @UiField Element eEmailError;
  @UiField Element ePassError;
  @UiField(provided=true) TextBox txtPassword;
  
  // ---------------------------------------------------------------------------
  // ---------------------------------------------------------------------------
  
  public LoginDialogBox()
  {
    setStyleName("");
    txtPassword = new PasswordTextBox();
    add(uiBinder.createAndBindUi(this));
  }
  
  // ---------------------------------------------------------------------------
  
  @UiFactory
  Button createLoginButton()
  {
    Button button = new Button();
    button.setTitle("Submit the login form");
    return button;
  }
  // ---------------------------------------------------------------------------
  
  @UiHandler("btnLogin")
  void submitLoginForm(ClickEvent event)
  {
    Window.alert("Loggin in..");
  }
  // ---------------------------------------------------------------------------
}
