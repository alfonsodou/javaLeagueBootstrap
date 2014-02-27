/**
 * 
 */
package org.javahispano.javaleague.client.service;

import java.util.List;

import org.javahispano.javaleague.shared.LeagueDTO;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * @author adou
 *
 */
public interface LeagueServiceAsync {
	void createLeague(LeagueDTO league, AsyncCallback<LeagueDTO> cb);
	
	void getMyLeagues(AsyncCallback<List<LeagueDTO>> cb);

	void getLeague(Long id, AsyncCallback<LeagueDTO> callback);

	void dropLeague(Long id, AsyncCallback<Void> cb);

	void joinLeague(Long id, AsyncCallback<Void> callback);

}
