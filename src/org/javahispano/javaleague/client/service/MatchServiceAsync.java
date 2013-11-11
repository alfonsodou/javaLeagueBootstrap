/**
 * 
 */
package org.javahispano.javaleague.client.service;

import java.util.List;

import org.javahispano.javaleague.shared.MatchDTO;
import org.javahispano.javaleague.shared.TacticDTO;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * @author adou
 *
 */
public interface MatchServiceAsync {

	void dispatchMatch(AsyncCallback<Void> cb);
	
	void getMatchList(TacticDTO tacticDTO, AsyncCallback<List<MatchDTO>> cb);

	void setMatchState(MatchDTO match, int state, AsyncCallback<Void> callback);

	void getMatchById(Long id, AsyncCallback<MatchDTO> callback);

	void getMatchList(AsyncCallback<List<MatchDTO>> callback);

	void dispatchMatch(String tacticId, AsyncCallback<Void> callback);

}
