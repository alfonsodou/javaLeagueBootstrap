/**
 * 
 */
package org.javahispano.javaleague.client.event;

import com.google.gwt.event.shared.GwtEvent;

/**
 * @author adou
 * 
 */
public class ShowHomeEvent extends GwtEvent<ShowHomeEventHandler> {
	public static Type<ShowHomeEventHandler> TYPE = new Type<ShowHomeEventHandler>();

	@Override
	public Type<ShowHomeEventHandler> getAssociatedType() {

		return TYPE;
	}

	@Override
	protected void dispatch(ShowHomeEventHandler handler) {

		handler.onShowHome(this);
	}

}
