package org.javahispano.javaleague.client.service;

import org.javahispano.javaleague.shared.domain.User;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * UserAccountService.
 */
@RemoteServiceRelativePath("userAccountService")
public interface UserAccountService extends RemoteService {
	/**
	 * login function.
	 * 
	 * @param email
	 *            email
	 * @param password
	 *            password
	 * @return UserAccountDTO
	 * @see UserDTO
	 */
	User login(String email, String password);

	User register(User user, String teamName, String msgFrom,
			String msgSubject, String msgBody);

}
