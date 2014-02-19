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
public class AddLeagueEvent extends GwtEvent<AddLeagueEventHandler> {
	public static Type<AddLeagueEventHandler> TYPE = new Type<AddLeagueEventHandler>();
	private final LeagueDTO leagueDTO;
	
	public AddLeagueEvent(LeagueDTO leagueDTO) {
		this.leagueDTO = leagueDTO;
	}
	
	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<AddLeagueEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(AddLeagueEventHandler handler) {
		handler.onAddLeagueEvent(this);
	}

	public LeagueDTO getLeagueDTO() {
		return leagueDTO;
	}


	
}
