/**
 * 
 */
package org.javahispano.javaleague.client.event;

import com.google.gwt.event.shared.GwtEvent;

/**
 * @author adou
 * 
 */
public class TacticEditEvent extends GwtEvent<TacticEditEventHandler> {
	public static Type<TacticEditEventHandler> TYPE = new Type<TacticEditEventHandler>();
	// private final TacticDTO userTactic;
	private final String id;

	public TacticEditEvent(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	@Override
	public Type<TacticEditEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(TacticEditEventHandler handler) {
		handler.onShowTactics(this);
	}

}
