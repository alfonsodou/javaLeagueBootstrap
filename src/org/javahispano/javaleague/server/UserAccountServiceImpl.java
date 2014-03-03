/**
 * 
 */
package org.javahispano.javaleague.server;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;
import java.util.logging.Logger;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpSession;

import org.javahispano.javaleague.client.service.UserAccountService;
import org.javahispano.javaleague.server.domain.TacticUser;
import org.javahispano.javaleague.server.domain.TacticUserDAO;
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
	private static Logger logger = Logger.getLogger(UserAccountServiceImpl.class
			.getName());
	
	private static UserDAO userDAO = new UserDAO();
	private static TacticUserDAO tacticUserDAO = new TacticUserDAO();

	@Override
	public UserDTO login(String email, String password) {
		User user = userDAO.findByEmail(email);

		if (user == null) {
			logger.warning("User == null");
		}
		
		if ((user != null) && (user.getPassword().equals(password))) {
			HttpSession session = getThreadLocalRequest().getSession();
			// update session if successful
			session.setAttribute("userId", String.valueOf(user.getId()));
			session.setAttribute("loggedin", true);

			user.setLastActive(new Date());
			user.setLastLoginOn(new Date());

			userDAO.save(user);

			UserDTO userDTO = User.toDTO(user);

			return userDTO;
		}

		return null;
	}

	@Override
	public UserDTO register(UserDTO userDTO, String teamName, String msgFrom,
			String msgSubject, String msgBody) {
		User user = userDAO.findByEmail(userDTO.getEmailAddress());

		// Ya está registrada esa dirección de correo
		// Falta comprobar que se intente registrar una dirección de correo
		// que aún no ha sido autenticada
		if (user != null) {
			return null;
		}

		SessionIdentifierGenerator userTokenGenerator = new SessionIdentifierGenerator();
		TacticUser tacticUser = new TacticUser();
		tacticUser.setTeamName(teamName);
		tacticUser = tacticUserDAO.save(tacticUser);
		
		user = new User();
		user.setDateTokenActivate(new Date());
		user.setTokenActivate(userTokenGenerator.nextSessionId());
		user.setEmailAddress(userDTO.getEmailAddress());
		user.setName(userDTO.getName());
		// Falta guardar la contraseña encriptada
		user.setPassword(userDTO.getPassword());
		user.setTactic(tacticUser.getId());

		Properties props = new Properties();
		Session session = Session.getDefaultInstance(props, null);

		msgBody = msgBody.replace("#0#", user.getName());
		msgBody = msgBody.replace("#1#", AppLib.baseURL
				+ "/authenticateUser?token=" + user.getTokenActivate());
		msgBody = msgBody.replace("#2#", AppLib.emailAdmin);
		msgBody = msgBody.replace("#3#", msgFrom);

		try {
			Message msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress(AppLib.emailAdmin, msgFrom));
			msg.addRecipient(Message.RecipientType.TO,
					new InternetAddress(user.getEmailAddress(), user.getName()));
			msg.setSubject(msgSubject);
			msg.setText(msgBody);
			Transport.send(msg);

		} catch (AddressException e) {
			return null;
		} catch (MessagingException e) {
			return null;
		} catch (UnsupportedEncodingException e) {
			return null;
		}

		userDAO.save(user);

		userDTO.setId(user.getId());

		return userDTO;
	}
}
