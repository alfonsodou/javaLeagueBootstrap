package org.javahispano.javaleague.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class CancelUpdateTacticEvent extends
		GwtEvent<CancelUpdateTacticEventHandler> {
	public static Type<CancelUpdateTacticEventHandler> TYPE = new Type<CancelUpdateTacticEventHandler>();

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<CancelUpdateTacticEventHandler> getAssociatedType() {

		return TYPE;
	}

	@Override
	protected void dispatch(CancelUpdateTacticEventHandler handler) {
		handler.onCancelUpdateTactic(this);

	}

}
