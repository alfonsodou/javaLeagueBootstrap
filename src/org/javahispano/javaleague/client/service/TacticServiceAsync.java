/**
 * 
 */
package org.javahispano.javaleague.client.service;

import java.util.ArrayList;

import org.javahispano.javaleague.shared.TacticClassDTO;
import org.javahispano.javaleague.shared.TacticDTO;
import org.javahispano.javaleague.shared.UserDTO;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * @author adou
 * 
 */
public interface TacticServiceAsync {

	void getTacticClass(AsyncCallback<ArrayList<TacticClassDTO>> callback);

	void deleteTactic(String id, AsyncCallback<String> callback);

	void getTactic(String id, AsyncCallback<TacticDTO> callback);

	void updateTactic(TacticDTO userTacticDTO,
			AsyncCallback<TacticDTO> callback);

	void getUserAccount(AsyncCallback<UserDTO> callback);

	void getUserTacticSummary(AsyncCallback<TacticDTO> callback);

}
