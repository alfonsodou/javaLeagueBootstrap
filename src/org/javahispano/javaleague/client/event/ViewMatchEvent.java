/**
 * 
 */
package org.javahispano.javaleague.client.event;

import com.google.gwt.event.shared.GwtEvent;

/**
 * @author adou
 * 
 */
public class ViewMatchEvent extends GwtEvent<ViewMatchEventHandler> {
	public static Type<ViewMatchEventHandler> TYPE = new Type<ViewMatchEventHandler>();

	private final String id;

	public ViewMatchEvent(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	@Override
	public Type<ViewMatchEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(ViewMatchEventHandler handler) {
		handler.onShowMatch(this);

	}

}
