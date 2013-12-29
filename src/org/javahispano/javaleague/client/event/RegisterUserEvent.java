/**
 * 
 */
package org.javahispano.javaleague.client.event;

import org.javahispano.javaleague.shared.UserDTO;

import com.google.gwt.event.shared.GwtEvent;

/**
 * @author adou
 * 
 */
public class RegisterUserEvent extends GwtEvent<RegisterUserEventHandler> {
	public static Type<RegisterUserEventHandler> TYPE = new Type<RegisterUserEventHandler>();
	private final UserDTO userDTO;

	public RegisterUserEvent(UserDTO userDTO) {
		this.userDTO = userDTO;
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<RegisterUserEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(RegisterUserEventHandler handler) {
		handler.onRegisterUser(this);

	}
	
	public UserDTO getUserDTO() {
		return userDTO;
	}

}
