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

	private final Long id;

	public ShowMyTacticEvent(Long id) {
		this.id = id;
	}

	public Long getId() {
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
