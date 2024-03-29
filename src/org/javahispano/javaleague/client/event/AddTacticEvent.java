/**
 * 
 */
package org.javahispano.javaleague.client.event;

import com.google.gwt.event.shared.GwtEvent;

/**
 * @author adou
 * 
 */
public class AddTacticEvent extends GwtEvent<AddTacticEventHandler> {
	public static Type<AddTacticEventHandler> TYPE = new Type<AddTacticEventHandler>();
	private final Long id;
	
	public AddTacticEvent(Long id) {
		this.id = id;
	}
	
	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<AddTacticEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(AddTacticEventHandler handler) {
		handler.onAddTacticEvent(this);
	}

	public Long getId() {
		return id;
	}


	
}
