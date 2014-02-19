/**
 * 
 */
package org.javahispano.javaleague.client.service;

import org.javahispano.javaleague.shared.LeagueDTO;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * @author adou
 *
 */
public interface LeagueServiceAsync {
	void createLeague(LeagueDTO league, AsyncCallback<LeagueDTO> cb);

}
