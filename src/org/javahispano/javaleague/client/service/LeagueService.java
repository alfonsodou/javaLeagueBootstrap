/**
 * 
 */
package org.javahispano.javaleague.client.service;

import java.util.List;

import org.javahispano.javaleague.shared.domain.League;

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

	League getLeague(Long id);

	void dropLeague(Long id);
	
	void joinLeague(Long id);
	
	League createCalendarLeague(League league);

}
