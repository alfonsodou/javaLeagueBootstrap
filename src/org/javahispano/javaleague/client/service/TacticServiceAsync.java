/**
 * 
 */
package org.javahispano.javaleague.client.service;

import java.util.Date;

import org.javahispano.javaleague.shared.domain.TacticUser;
import org.javahispano.javaleague.shared.domain.User;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * @author adou
 * 
 */
public interface TacticServiceAsync {

	void deleteTactic(Long id, AsyncCallback<String> callback);

	void getTactic(Long id, AsyncCallback<TacticUser> callback);

	void updateTactic(TacticUser userTactic, AsyncCallback<TacticUser> callback);

	void getUserAccount(AsyncCallback<User> callback);

	void getTacticUserLogin(AsyncCallback<TacticUser> callback);

	void getDateNow(AsyncCallback<Date> callback);

}
