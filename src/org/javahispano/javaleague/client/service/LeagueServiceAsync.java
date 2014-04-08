/**
 * 
 */
package org.javahispano.javaleague.client.service;

import java.util.List;

import org.javahispano.javaleague.shared.domain.League;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.googlecode.objectify.Ref;

/**
 * @author adou
 *
 */
public interface LeagueServiceAsync {
	void createLeague(League league, AsyncCallback<League> cb);
	
	void getMyLeagues(AsyncCallback<List<Ref<League>>> cb);

	void getLeague(Long id, AsyncCallback<League> callback);

	void dropLeague(Long id, AsyncCallback<Void> cb);

	void joinLeague(Long id, AsyncCallback<Void> callback);

	void createCalendarLeague(League league, AsyncCallback<League> callback);

	void getLeagues(String textToSearch,
			AsyncCallback<List<Ref<League>>> callback);

}
