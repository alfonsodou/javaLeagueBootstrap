package org.javahispano.javaleague.client.service;

import org.javahispano.javaleague.shared.UserDTO;

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
	void login(String email, String password, AsyncCallback<UserDTO> callback);

	void register(UserDTO userDTO, String msgFrom, String msgSubject,
			String msgBody, AsyncCallback<UserDTO> callback);

}
