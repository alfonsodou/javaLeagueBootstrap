/**
 * 
 */
package org.javahispano.javaleague.client.event;

import com.google.gwt.event.shared.GwtEvent;

/**
 * @author adou
 * 
 */
public class PlayMatchEvent extends GwtEvent<PlayMatchEventHandler> {
	public static Type<PlayMatchEventHandler> TYPE = new Type<PlayMatchEventHandler>();
	// private final TacticDTO userTactic;
	private final String id;

	public PlayMatchEvent(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	@Override
	public Type<PlayMatchEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(PlayMatchEventHandler handler) {
		handler.onPlayMatch(this);
	}

}
