/**
 * 
 */
package org.javahispano.javaleague.client.event;

import org.javahispano.javaleague.shared.domain.League;

import com.google.gwt.event.shared.GwtEvent;

/**
 * @author adou
 * 
 */
public class AddLeagueEvent extends GwtEvent<AddLeagueEventHandler> {
	public static Type<AddLeagueEventHandler> TYPE = new Type<AddLeagueEventHandler>();
	private final League league;
	
	public AddLeagueEvent(League league) {
		this.league = league;
	}
	
	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<AddLeagueEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(AddLeagueEventHandler handler) {
		handler.onAddLeagueEvent(this);
	}

	public League getLeague() {
		return league;
	}


	
}
