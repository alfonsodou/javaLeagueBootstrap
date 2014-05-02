/**
 * 
 */
package org.javahispano.javaleague.client.event;

import com.google.gwt.event.shared.GwtEvent;

/**
 * @author adou
 *
 */
public class EditLeagueEvent extends GwtEvent<EditLeagueEventHandler> {
	public static Type<EditLeagueEventHandler> TYPE = new Type<EditLeagueEventHandler>();
	private Long leagueId;

	public EditLeagueEvent() {
		
	}
	
	public EditLeagueEvent(Long leagueId) {
		this.leagueId = leagueId;
	}
	
	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<EditLeagueEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(EditLeagueEventHandler handler) {
		handler.onEditLeague(this);
		
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
