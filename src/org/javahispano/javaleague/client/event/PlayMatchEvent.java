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
	private final Long id;

	public PlayMatchEvent(Long id) {
		this.id = id;
	}

	public Long getId() {
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
