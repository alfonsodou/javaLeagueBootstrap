/**
 * 
 */
package org.javahispano.javaleague.server;

import java.util.Date;

import org.javahispano.javaleague.client.service.UserAccountService;
import org.javahispano.javaleague.server.domain.User;
import org.javahispano.javaleague.server.domain.UserDAO;
import org.javahispano.javaleague.server.utils.SessionIdentifierGenerator;
import org.javahispano.javaleague.shared.UserDTO;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * @author adou
 * 
 */
public class UserAccountServiceImpl extends RemoteServiceServlet implements
		UserAccountService {
	
	private static UserDAO userDAO = new UserDAO();
	
	@Override
	public UserDTO login(String email, String password) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserDTO register(UserDTO userDTO) {
		User user = userDAO.findByEmail(userDTO.getEmailAddress()); 
		
		// Ya está registrada esa dirección de correo
		// Falta comprobar que se intente registrar una dirección de correo
		// que aún no ha sido autenticada
		if (user != null) { 
			return null;
		}
		
		SessionIdentifierGenerator userTokenGenerator = new SessionIdentifierGenerator();
		user.setDateTokenActivate(new Date());
		user.setTokenActivate(userTokenGenerator.nextSessionId());
		user.setEmailAddress(userDTO.getEmailAddress());
		user.setName(userDTO.getName());
		user.setPassword(userDTO.getPassword());
		
		return userDTO;
	}

}
