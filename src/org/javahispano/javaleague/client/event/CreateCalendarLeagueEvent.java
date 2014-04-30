/**
 * 
 */
package org.javahispano.javaleague.client.event;

import com.google.gwt.event.shared.GwtEvent;

/**
 * @author adou
 *
 */
public class CreateCalendarLeagueEvent extends GwtEvent<CreateCalendarLeagueEventHandler> {
	public static Type<CreateCalendarLeagueEventHandler> TYPE = new Type<CreateCalendarLeagueEventHandler>();
	private Long leagueId;

	public CreateCalendarLeagueEvent() {
		
	}
	
	public CreateCalendarLeagueEvent(Long leagueId) {
		this.leagueId = leagueId;
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
	public Long getLeagueId() {
		return leagueId;
	}

	/**
	 * @param league the league to set
	 */
	public void setLeagueId(Long leagueId) {
		this.leagueId = leagueId;
	}

	

}
