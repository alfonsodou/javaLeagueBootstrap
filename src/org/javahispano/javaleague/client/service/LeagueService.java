/**
 * 
 */
package org.javahispano.javaleague.client.service;

import org.javahispano.javaleague.shared.LeagueDTO;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * @author adou
 *
 */
@RemoteServiceRelativePath("leagueService")
public interface LeagueService extends RemoteService {

	void createLeague(LeagueDTO league);

}