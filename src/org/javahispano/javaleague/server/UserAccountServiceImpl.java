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
import org.javahispano.javaleague.server.domain.TacticUserDAO;
import org.javahispano.javaleague.server.domain.UserDAO;
import org.javahispano.javaleague.server.utils.SessionIdentifierGenerator;
import org.javahispano.javaleague.shared.AppLib;
import org.javahispano.javaleague.shared.domain.TacticUser;
import org.javahispano.javaleague.shared.domain.User;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * @author adou
 * 
 */
public class UserAccountServiceImpl extends RemoteServiceServlet implements
		UserAccountService {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static Logger logger = Logger
			.getLogger(UserAccountServiceImpl.class.getName());

	private static UserDAO userDAO = new UserDAO();
	private static TacticUserDAO tacticUserDAO = new TacticUserDAO();

	@Override
	public User login(String email, String password) {
		User user = userDAO.findByEmail(email);

		if (user == null) {
			logger.warning("User == null :: email = " + email
					+ " :: password = " + password);
		}

		if ((user != null) && (user.getPassword().equals(password))) {
			HttpSession session = getThreadLocalRequest().getSession();
			// update session if successful
			session.setAttribute("userId", String.valueOf(user.getId()));
			session.setAttribute("loggedin", true);

			user.setLastActive(new Date());
			user.setLastLoginOn(new Date());

			userDAO.save(user);

			return user;
		}

		return null;
	}

	@Override
	public User register(User user, String teamName, String msgFrom,
			String msgSubject, String msgBody) {
		// Ya está registrada esa dirección de correo
		// Falta comprobar que se intente registrar una dirección de correo
		// que aún no ha sido autenticada
		if (userDAO.findByEmail(user.getEmailAddress()) != null) {
			return null;
		}

		SessionIdentifierGenerator userTokenGenerator = new SessionIdentifierGenerator();
		TacticUser tacticUser = new TacticUser();
		tacticUser.setTeamName(teamName);
		tacticUser = tacticUserDAO.save(tacticUser);

		user.setDateTokenActivate(new Date());
		user.setTokenActivate(userTokenGenerator.nextSessionId());
		user.setTactic(tacticUser);

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
			msg.setContent(msgBody, "text/html; charset=utf-8");
			msg.setSentDate(new Date());
			Transport.send(msg);

		} catch (AddressException e) {
			return null;
		} catch (MessagingException e) {
			return null;
		} catch (UnsupportedEncodingException e) {
			return null;
		}

		user = userDAO.save(user);

		return user;
	}
}
