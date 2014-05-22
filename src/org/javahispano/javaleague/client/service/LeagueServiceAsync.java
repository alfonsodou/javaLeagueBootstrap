/**
 * 
 */
package org.javahispano.javaleague.client.service;

import java.util.Date;
import java.util.List;

import org.javahispano.javaleague.shared.domain.League;
import org.javahispano.javaleague.shared.domain.LeagueSummary;

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

	void joinLeague(Long id, String password, AsyncCallback<Boolean> callback);

	void createCalendarLeague(League league, Date init, List<Integer> days,
			AsyncCallback<League> callback);

	void getLeagues(String textToSearch,
			AsyncCallback<List<Ref<League>>> callback);

	void getLeaguesSummary(AsyncCallback<List<Ref<LeagueSummary>>> callback);

	void getDateNow(AsyncCallback<Date> callback);

	void getManagerLeaguesSummary(
			AsyncCallback<List<Ref<LeagueSummary>>> callback);

	void editLeague(League league, AsyncCallback<League> callback);

	void getMyLeaguesSummary(AsyncCallback<List<Ref<LeagueSummary>>> callback);

}
