/**
 * 
 */
package org.javahispano.javaleague.client.service;

import java.util.List;

import org.javahispano.javaleague.shared.LeagueDTO;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * @author adou
 * 
 */
@RemoteServiceRelativePath("leagueService")
public interface LeagueService extends RemoteService {

	LeagueDTO createLeague(LeagueDTO league);

	List<LeagueDTO> getMyLeagues();

	LeagueDTO getLeague(Long id);

	void dropLeague(Long id);

}
