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
	private final Long id;

	public TacticEditEvent(Long id) {
		this.id = id;
	}

	public Long getId() {
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
