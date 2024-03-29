package org.javahispano.javaleague.client.view;

import org.javahispano.javaleague.client.presenter.UserBadgePresenter;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

/**
 * 
 * @author adou
 *
 */
public class UserBadgeView extends Composite implements UserBadgePresenter.Display {
  @UiField
  Label usernameLabel;
  @UiField
  Anchor logoutLink;
  
  private static UserBadgeUiBinder uiBinder = GWT
      .create(UserBadgeUiBinder.class);

  interface UserBadgeUiBinder extends UiBinder<Widget, UserBadgeView> {
  }

  public UserBadgeView() {
    initWidget(uiBinder.createAndBindUi(this));
  }

  @Override
  public Widget asWidget() {
    return this;
  }

  @Override
  public HasClickHandlers getLogoutLink() {
    return logoutLink;
  }

  @Override
  public HasText getUsernameLabel() {
    return usernameLabel;
  }

}
