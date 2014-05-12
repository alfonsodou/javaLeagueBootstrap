package org.javahispano.javaleague.client.service;

import java.util.List;

import org.javahispano.javaleague.shared.domain.Match;
import org.javahispano.javaleague.shared.domain.TacticUser;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("matchService")
public interface MatchService extends RemoteService {


	List<Match> getMatchList(Long tacticId);

	void setMatchState(Match match, int state);

	Match getMatchById(Long id);

	void dispatchMatch(Long tacticId);
	
	List<Match> getMatchListByState(int state);

}
