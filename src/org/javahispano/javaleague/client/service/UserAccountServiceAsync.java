package org.javahispano.javaleague.client.service;




import org.javahispano.javaleague.shared.domain.User;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * UserAccountServiceAsync.
 */
public interface UserAccountServiceAsync {

	/**
	 * login.
	 * 
	 * @param email
	 *            email
	 * @param password
	 *            password
	 * @param callback
	 *            AsynCallback<UserAccountDTO>
	 * @see UserDTO
	 */
	void login(String email, String password, AsyncCallback<User> callback);

	void register(User user, String teamName, String msgFrom, String msgSubject,
			String msgBody, AsyncCallback<User> callback);

	void getUser(Long id, AsyncCallback<User> callback);

}
