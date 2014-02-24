/**
 * 
 */
package org.javahispano.javaleague.client.event;

import org.javahispano.javaleague.shared.LeagueDTO;

import com.google.gwt.event.shared.GwtEvent;

/**
 * @author adou
 *
 */
public class ShowLeagueEvent extends GwtEvent<ShowLeagueEventHandler> {
	  public static Type<ShowLeagueEventHandler> TYPE = new Type<ShowLeagueEventHandler>();
	  private final LeagueDTO leagueDTO;
	  
	public ShowLeagueEvent(LeagueDTO leagueDTO) {
		this.leagueDTO = leagueDTO;
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<ShowLeagueEventHandler> getAssociatedType() {

		return TYPE;
	}
	
	@Override
	protected void dispatch(ShowLeagueEventHandler handler) {
		handler.onShowLeague(this);
		
	}
	
	public LeagueDTO getLeague() {
		return leagueDTO;
	}

}

