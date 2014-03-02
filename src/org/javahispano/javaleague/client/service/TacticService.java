/**
 * 
 */
package org.javahispano.javaleague.client.service;

import java.util.ArrayList;

import org.javahispano.javaleague.shared.TacticClassDTO;
import org.javahispano.javaleague.shared.TacticDTO;
import org.javahispano.javaleague.shared.UserDTO;
import org.javahispano.javaleague.shared.exception.NotLoggedInException;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * @author adou
 * 
 */
@RemoteServiceRelativePath("tacticService")
public interface TacticService extends RemoteService {
	ArrayList<TacticClassDTO> getTacticClass();

	String deleteTactic(String id) throws NotLoggedInException;
	
	TacticDTO getTactic(String id);
	
	TacticDTO updateTactic(TacticDTO userTacticDTO);

	TacticDTO getUserTacticSummary();

	UserDTO getUserAccount();
	
	
}
