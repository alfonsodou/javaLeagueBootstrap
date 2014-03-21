/**
 * 
 */
package org.javahispano.javaleague.client.event;

import com.google.gwt.event.shared.GwtEvent;

/**
 * @author adou
 * 
 */
public class SearchLeagueEvent extends GwtEvent<SearchLeagueEventHandler> {
	public static Type<SearchLeagueEventHandler> TYPE = new Type<SearchLeagueEventHandler>();
	private final String search;
	
	public SearchLeagueEvent(String search) {
		this.search = search;
	}
	
	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<SearchLeagueEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(SearchLeagueEventHandler handler) {
		handler.onSearchLeagueEvent(this);
	}

	public String getSearch() {
		return search;
	}


	
}
