package org.javahispano.javaleague.server;

import java.util.Date;
import java.util.logging.Logger;

import org.javahispano.javaleague.client.service.TacticService;
import org.javahispano.javaleague.server.domain.TacticUserDAO;
import org.javahispano.javaleague.server.domain.UserDAO;
import org.javahispano.javaleague.shared.domain.TacticUser;
import org.javahispano.javaleague.shared.domain.User;
import org.javahispano.javaleague.shared.exception.NotLoggedInException;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class TacticServiceImpl extends RemoteServiceServlet implements
		TacticService {

	private static Logger logger = Logger.getLogger(TacticServiceImpl.class
			.getName());
	private static final int NUM_RETRIES = 5;

	private static UserDAO userDAO = new UserDAO();
	private static TacticUserDAO tacticDAO = new TacticUserDAO();

	public TacticServiceImpl() {

	}

	@Override
	public String deleteTactic(Long id) throws NotLoggedInException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TacticUser getTactic(Long id) {
		TacticUser tacticUser = tacticDAO.findById(id);

		if (tacticUser != null) {
			return tacticUser;
		}

		return null;
	}
	
	@Override
	public TacticUser getTacticUserLogin() {
		User currentUser = LoginHelper.getLoggedInUser(getThreadLocalRequest()
				.getSession());
		return tacticDAO.findById(currentUser.getTactic().getId());
	}

	@Override
	public User getUserAccount() {
		User currentUser = LoginHelper.getLoggedInUser(getThreadLocalRequest()
				.getSession());

		return currentUser;
	}

	@Override
	public TacticUser updateTactic(TacticUser userTactic) {
		try {
			userTactic.setUpdated(new Date());
			userTactic = tacticDAO.save(userTactic);
		} catch (Exception e) {
			e.printStackTrace();
			logger.warning(e.getMessage());
		}

		return userTactic;
	}

	@Override
	public Date getDateNow() {
		return new Date();
	}
}
