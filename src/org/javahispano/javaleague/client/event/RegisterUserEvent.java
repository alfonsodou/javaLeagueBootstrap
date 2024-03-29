/**
 * 
 */
package org.javahispano.javaleague.client.event;

import org.javahispano.javaleague.shared.domain.User;

import com.google.gwt.event.shared.GwtEvent;

/**
 * @author adou
 * 
 */
public class RegisterUserEvent extends GwtEvent<RegisterUserEventHandler> {
	public static Type<RegisterUserEventHandler> TYPE = new Type<RegisterUserEventHandler>();
	private final User user;

	public RegisterUserEvent(User user) {
		this.user = user;
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<RegisterUserEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(RegisterUserEventHandler handler) {
		handler.onRegisterUser(this);

	}
	
	public User getUser() {
		return user;
	}

}
