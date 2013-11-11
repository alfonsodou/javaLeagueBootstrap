/**
 * 
 */
package org.javahispano.javaleague.client.event;

import org.javahispano.javaleague.shared.TacticDTO;

import com.google.gwt.event.shared.GwtEvent;

/**
 * @author adou
 * 
 */
public class UpdateTacticEvent extends GwtEvent<UpdateTacticEventHandler> {
	public static Type<UpdateTacticEventHandler> TYPE = new Type<UpdateTacticEventHandler>();
	private final TacticDTO userTactic;

	public UpdateTacticEvent(TacticDTO result) {
		this.userTactic = result;
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<UpdateTacticEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(UpdateTacticEventHandler handler) {
		handler.onUpdateTactic(this);

	}

}
