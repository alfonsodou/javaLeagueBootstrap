/**
 * 
 */
package org.javahispano.javaleague.client.service;

import java.util.List;

import org.javahispano.javaleague.shared.domain.Match;
import org.javahispano.javaleague.shared.domain.TacticUser;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * @author adou
 *
 */
public interface MatchServiceAsync {

	
	void getMatchList(TacticUser tactic, AsyncCallback<List<Match>> cb);

	void setMatchState(Match match, int state, AsyncCallback<Void> callback);

	void getMatchById(Long id, AsyncCallback<Match> callback);

	void dispatchMatch(Long tacticId, AsyncCallback<Void> callback);

}
