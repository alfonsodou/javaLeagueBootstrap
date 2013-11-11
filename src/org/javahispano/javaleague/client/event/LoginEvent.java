package org.javahispano.javaleague.client.event;

import org.javahispano.javaleague.shared.UserDTO;

import com.google.gwt.event.shared.GwtEvent;

/**
 * 
 * @author adou
 *
 */
public class LoginEvent extends GwtEvent<LoginEventHandler> {
  public static Type<LoginEventHandler> TYPE = new Type<LoginEventHandler>();
  private final UserDTO user;

  public LoginEvent(UserDTO user) {
    this.user = user;
  }

  public UserDTO getUser() {
    return user;
  }

  @Override public Type<LoginEventHandler> getAssociatedType() {
    return TYPE;
  }

  @Override protected void dispatch(LoginEventHandler handler) {
    handler.onLogin(this);
  }
}
