package org.javahispano.javaleague.server;

import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Logger;

import org.javahispano.javaleague.client.service.TacticService;
import org.javahispano.javaleague.server.domain.TacticUser;
import org.javahispano.javaleague.server.domain.TacticUserDAO;
import org.javahispano.javaleague.server.domain.User;
import org.javahispano.javaleague.server.domain.UserDAO;
import org.javahispano.javaleague.shared.TacticClassDTO;
import org.javahispano.javaleague.shared.TacticDTO;
import org.javahispano.javaleague.shared.UserDTO;
import org.javahispano.javaleague.shared.exception.NotLoggedInException;

import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class TacticServiceImpl extends RemoteServiceServlet implements
		TacticService {

	private static Logger logger = Logger.getLogger(TacticServiceImpl.class
			.getName());
	private static final int NUM_RETRIES = 5;

	private static UserDAO userDAO = new UserDAO();
	private static TacticUserDAO tacticDAO = new TacticUserDAO();

	private BlobstoreService blobstoreService = BlobstoreServiceFactory
			.getBlobstoreService();

	public TacticServiceImpl() {

	}

	@Override
	public ArrayList<TacticClassDTO> getTacticClass() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String deleteTactic(String id) throws NotLoggedInException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TacticDTO getTactic(String id) {
		TacticUser tacticUser = tacticDAO.findById(Long.valueOf(id));

		if (tacticUser != null) {
			return tacticUser.toDTO();
		}

		return null;
	}

	@Override
	public UserDTO getUserAccount() {
		User currentUser = LoginHelper.getLoggedInUser(getThreadLocalRequest()
				.getSession());

		return currentUser.toDTO(currentUser);
	}

	@Override
	public TacticDTO updateTactic(TacticDTO userTacticDTO) {
		if (userTacticDTO.getId() == null) { // create new Tactic
			TacticUser newTactic = addTactic(userTacticDTO);
			return newTactic.toDTO();
		}

		TacticUser tactic = null;
		try {
			tactic = tacticDAO.findById(Long.valueOf(userTacticDTO.getId()));

			tactic.updatedFromDTO(userTacticDTO);
			tactic.setUpdated(new Date());
			userTacticDTO.setUpdated(tactic.getUpdated().toString());
			tacticDAO.save(tactic);
		} catch (Exception e) {
			e.printStackTrace();
			logger.warning(e.getMessage());
			userTacticDTO = null;
		}

		return userTacticDTO;
	}

	private TacticUser addTactic(TacticDTO userTacticDTO) {
		TacticUser userTactic = null;
		String uid = null;
		try {
			User currentUser = LoginHelper
					.getLoggedInUser(getThreadLocalRequest().getSession());

			userTactic = new TacticUser(userTacticDTO);
			currentUser.setTactic(userTactic.getId());
			userDAO.save(currentUser);
			uid = userTactic.getId().toString();
		} catch (Exception e) {
			logger.warning(e.getMessage());
			userTactic = null;
		}
		return userTactic;
	}

	public TacticDTO getUserTacticSummary() {

		TacticDTO userTacticSummary = null;
		TacticUser userTactic;

		try {
			User user = LoginHelper.getLoggedInUser(getThreadLocalRequest()
					.getSession());
			if (user == null)
				return null;

			if (user.getTactic() == null)
				return null;

			userTactic = tacticDAO.findById(Long.valueOf(user.getTactic()));

			userTacticSummary = userTactic.toDTO();
		} catch (Exception e) {
			logger.warning(e.getMessage());
			userTacticSummary = null;
		}

		return userTacticSummary;
	}

}
