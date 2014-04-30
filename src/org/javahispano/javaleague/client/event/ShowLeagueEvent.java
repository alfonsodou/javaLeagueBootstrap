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
	  private final Long leagueId;
	  
	public ShowLeagueEvent(Long leagueId) {
		this.leagueId = leagueId;
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<ShowLeagueEventHandler> getAssociatedType() {

		return TYPE;
	}
	
	@Override
	protected void dispatch(ShowLeagueEventHandler handler) {
		handler.onShowLeague(this);
		
	}
	
	public Long getLeagueId() {
		return leagueId;
	}

}

