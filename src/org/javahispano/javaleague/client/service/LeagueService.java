/**
 * 
 */
package org.javahispano.javaleague.client.service;

import java.util.Date;
import java.util.List;

import org.javahispano.javaleague.shared.domain.League;
import org.javahispano.javaleague.shared.domain.LeagueSummary;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.googlecode.objectify.Ref;

/**
 * @author adou
 * 
 */
@RemoteServiceRelativePath("leagueService")
public interface LeagueService extends RemoteService {

	League createLeague(League league);

	List<Ref<League>> getMyLeagues();

	List<Ref<League>> getLeagues(String textToSearch);
	
	List<Ref<LeagueSummary>> getLeaguesSummary();
	
	List<Ref<LeagueSummary>> getManagerLeaguesSummary();
	
	List<Ref<LeagueSummary>> getMyLeaguesSummary();

	League getLeague(Long id);

	void dropLeague(Long id);

	Boolean joinLeague(Long id, String password);

	League createCalendarLeague(League league, Date init, List<Integer> days);
	
	Date getDateNow();
	
	League editLeague(League league);

}
