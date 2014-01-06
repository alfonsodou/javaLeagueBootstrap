package org.javahispano.javaleague.server;

import java.util.Date;
import java.util.logging.Logger;

import javax.jdo.JDOCanRetryException;
import javax.jdo.JDOException;
import javax.jdo.PersistenceManager;
import javax.jdo.Transaction;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.javahispano.javaleague.server.domain.User;
import org.javahispano.javaleague.server.domain.UserDAO;
import org.javahispano.javaleague.server.utils.ServletHelper;
import org.javahispano.javaleague.server.utils.ServletUtils;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * 
 * @author adou
 * 
 */
public class LoginHelper extends RemoteServiceServlet {
	private static final long serialVersionUID = 2888983680310646846L;

	private static Logger logger = Logger
			.getLogger(LoginHelper.class.getName());
	private static final int NUM_RETRIES = 5;
	private static UserDAO userDAO = new UserDAO();	

	static public String getApplicationURL(HttpServletRequest request) {

		if (ServletHelper.isDevelopment(request)) {
			AppLib.isDevelopment = true;
			return "http://127.0.0.1:8888/index.html?gwt.codesvr=127.0.0.1:9997";
		} else {
			return ServletUtils.getBaseUrl(request);
		}

	}

	static public User getLoggedInUser(HttpSession session,
			PersistenceManager pm) {

		boolean localPM = false;

		if (session == null)
			return null; // user not logged in

		String userId = (String) session.getAttribute("userId");
		if (userId == null)
			return null; // user not logged in

		Long id = Long.parseLong(userId.trim());

		if (pm == null) {
			// then create local pm
			pm = PMF.getNonTxnPm();
			localPM = true;
		}

		try {
			User u = userDAO.findById(id);
			u.setLastActive(new Date());
			return u;
		} finally {
			if (localPM) {
				pm.close();
			}
		}

	}

	static public boolean isLoggedIn(HttpServletRequest req) {

		if (req == null)
			return false;
		else {
			HttpSession session = req.getSession();
			if (session == null) {
				logger.info("Session is null...");
				return false;
			} else {
				Boolean isLoggedIn = (Boolean) session.getAttribute("loggedin");
				if (isLoggedIn == null) {
					logger.info("Session found, but did not find loggedin attribute in it: user not logged in");
					return false;
				} else if (isLoggedIn) {
					logger.info("User is logged in...");
					return true;
				} else {
					logger.info("User not logged in");
					return false;
				}
			}
		}
	}

	public User loginStarts(HttpSession session, User user) {
		User aUser = User.findOrCreateUser(user);
		User u = null;

		// update user info under transactional control
		PersistenceManager pm = PMF.getTxnPm();
		Transaction tx = pm.currentTransaction();
		try {
			for (int i = 0; i < NUM_RETRIES; i++) {
				tx = pm.currentTransaction();
				tx.begin();
				u = (User) userDAO.findById(aUser.getId());
				// String channelId =
				// ChannelServer.createChannel(u.getUniqueId());
				// u.setChannelId(channelId);
				u.setLastActive(new Date());
				u.setLastLoginOn(new Date());
				try {
					userDAO.save(u);
					tx.commit();
					// update session if successful
					session.setAttribute("userId", String.valueOf(u.getId()));
					session.setAttribute("loggedin", true);
					break;
				} catch (JDOCanRetryException e1) {
					if (i == (NUM_RETRIES - 1)) {
						throw e1;
					}
				}
			} // end for
		} catch (JDOException e) {
			e.printStackTrace();
			return null;
		} finally {
			if (tx.isActive()) {
				logger.severe("loginStart transaction rollback.");
				tx.rollback();
			}
			pm.close();
		}

		return u;
	}

}
