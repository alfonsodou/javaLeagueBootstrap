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
public class ShowLeagueEvent extends GwtEvent<ShowLeagueEventHandler> {
	  public static Type<ShowLeagueEventHandler> TYPE = new Type<ShowLeagueEventHandler>();
	  private final League league;
	  
	public ShowLeagueEvent(League league) {
		this.league = league;
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<ShowLeagueEventHandler> getAssociatedType() {

		return TYPE;
	}
	
	@Override
	protected void dispatch(ShowLeagueEventHandler handler) {
		handler.onShowLeague(this);
		
	}
	
	public League getLeague() {
		return league;
	}

}

