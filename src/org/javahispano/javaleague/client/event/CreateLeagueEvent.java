/**
 * 
 */
package org.javahispano.javaleague.client.event;

import com.google.gwt.event.shared.GwtEvent;

/**
 * @author adou
 *
 */
public class CreateLeagueEvent extends GwtEvent<CreateLeagueEventHandler> {
	public static Type<CreateLeagueEventHandler> TYPE = new Type<CreateLeagueEventHandler>();

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<CreateLeagueEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(CreateLeagueEventHandler handler) {
		handler.onCreateLeague(this);
	}

}
