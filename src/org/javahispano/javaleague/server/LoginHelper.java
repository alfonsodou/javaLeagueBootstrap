package org.javahispano.javaleague.server;

import java.util.Date;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.javahispano.javaleague.server.domain.UserDAO;
import org.javahispano.javaleague.server.utils.ServletHelper;
import org.javahispano.javaleague.server.utils.ServletUtils;
import org.javahispano.javaleague.shared.domain.User;

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

	static public User getLoggedInUser(HttpSession session) {

		if (session == null)
			return null; // user not logged in

		String userId = (String) session.getAttribute("userId");
		if (userId == null)
			return null; // user not logged in

		Long id = Long.parseLong(userId.trim());

		try {
			User u = userDAO.findById(id);
			u.setLastActive(new Date());
			userDAO.save(u);
			return u;
		} catch (Exception e) {
			return null;
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


}
