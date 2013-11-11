package org.javahispano.javaleague.client.service;

import java.util.List;

import org.javahispano.javaleague.shared.MatchDTO;
import org.javahispano.javaleague.shared.TacticDTO;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("matchService")
public interface MatchService extends RemoteService {

	void dispatchMatch();

	List<MatchDTO> getMatchList(TacticDTO tacticDTO);

	void setMatchState(MatchDTO match, int state);

	MatchDTO getMatchById(Long id);

	List<MatchDTO> getMatchList();

	void dispatchMatch(String tacticId);

}
