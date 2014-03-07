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
public class CreateCalendarLeagueEvent extends GwtEvent<CreateCalendarLeagueEventHandler> {
	public static Type<CreateCalendarLeagueEventHandler> TYPE = new Type<CreateCalendarLeagueEventHandler>();
	private League league;

	public CreateCalendarLeagueEvent() {
		
	}
	
	public CreateCalendarLeagueEvent(League league) {
		this.league = league;
	}
	
	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<CreateCalendarLeagueEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(CreateCalendarLeagueEventHandler handler) {
		handler.onCreateCalendarLeague(this);
		
	}

	/**
	 * @return the league
	 */
	public League getLeague() {
		return league;
	}

	/**
	 * @param league the league to set
	 */
	public void setLeague(League league) {
		this.league = league;
	}

	

}
