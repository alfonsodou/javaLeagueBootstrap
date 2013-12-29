/**
 * 
 */
package org.javahispano.javaleague.client.event;

import com.google.gwt.event.shared.GwtEvent;

/**
 * @author adou
 * 
 */
public class ShowRegisterUserEvent extends
		GwtEvent<ShowRegisterUserEventHandler> {
	public static Type<ShowRegisterUserEventHandler> TYPE = new Type<ShowRegisterUserEventHandler>();

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<ShowRegisterUserEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(ShowRegisterUserEventHandler handler) {
		handler.onShowRegisterUser(this);

	}

}
