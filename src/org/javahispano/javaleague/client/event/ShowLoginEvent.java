/**
 * 
 */
package org.javahispano.javaleague.client.event;

import com.google.gwt.event.shared.GwtEvent;

/**
 * @author adou
 * 
 */
public class ShowLoginEvent extends GwtEvent<ShowLoginEventHandler> {
	public static Type<ShowLoginEventHandler> TYPE = new Type<ShowLoginEventHandler>();

	@Override
	public Type<ShowLoginEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(ShowLoginEventHandler handler) {
		handler.onShowLogin(this);
	}

}
