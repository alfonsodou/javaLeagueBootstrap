/**
 * 
 */
package org.javahispano.javaleague.client.service;

import java.util.Date;

import org.javahispano.javaleague.shared.domain.TacticUser;
import org.javahispano.javaleague.shared.domain.User;
import org.javahispano.javaleague.shared.exception.NotLoggedInException;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * @author adou
 * 
 */
@RemoteServiceRelativePath("tacticService")
public interface TacticService extends RemoteService {

	String deleteTactic(Long id) throws NotLoggedInException;
	
	TacticUser getTactic(Long id);
	
	TacticUser updateTactic(TacticUser userTactic);

	User getUserAccount();

	TacticUser getTacticUserLogin();
	
	Date getDateNow();
}
