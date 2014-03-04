package org.javahispano.javaleague.client.event;

import org.javahispano.javaleague.shared.domain.User;

import com.google.gwt.event.shared.GwtEvent;

/**
 * 
 * @author adou
 *
 */
public class LoginEvent extends GwtEvent<LoginEventHandler> {
  public static Type<LoginEventHandler> TYPE = new Type<LoginEventHandler>();
  private final User user;

  public LoginEvent(User user) {
    this.user = user;
  }

  public User getUser() {
    return user;
  }

  @Override public Type<LoginEventHandler> getAssociatedType() {
    return TYPE;
  }

  @Override protected void dispatch(LoginEventHandler handler) {
    handler.onLogin(this);
  }
}
