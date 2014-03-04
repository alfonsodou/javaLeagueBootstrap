package org.javahispano.javaleague.server;

import javax.servlet.http.HttpSession;

import org.javahispano.javaleague.client.service.LoginService;
import org.javahispano.javaleague.server.domain.FrameWorkDAO;
import org.javahispano.javaleague.shared.domain.FrameWork;
import org.javahispano.javaleague.shared.domain.User;
import org.javahispano.javaleague.shared.exception.NotLoggedInException;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@SuppressWarnings("serial")
public class LoginServiceImpl extends RemoteServiceServlet implements
		LoginService {
	public final static String CHANNEL_ID = "channel_id";

	@Override
	public User getLoggedInUser() {
		HttpSession session = getThreadLocalRequest().getSession();

		User u = LoginHelper.getLoggedInUser(session);
		if (u == null)
			return null;
		
		return u;
	}

	@Override
	public void logout() throws NotLoggedInException {
		getThreadLocalRequest().getSession().invalidate();
		throw new NotLoggedInException("Logged out");
	}

} // end class