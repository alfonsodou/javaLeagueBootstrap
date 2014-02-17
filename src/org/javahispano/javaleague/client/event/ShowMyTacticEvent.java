/**
 * 
 */
package org.javahispano.javaleague.client.event;

import com.google.gwt.event.shared.GwtEvent;

/**
 * @author adou
 * 
 */
public class ShowMyTacticEvent extends GwtEvent<ShowMyTacticEventHandler> {
	public static Type<ShowMyTacticEventHandler> TYPE = new Type<ShowMyTacticEventHandler>();

	private final String id;

	public ShowMyTacticEvent(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	@Override
	public Type<ShowMyTacticEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(ShowMyTacticEventHandler handler) {
		handler.onShowMyTactic(this);

	}

}
