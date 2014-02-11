package org.javahispano.javaleague.server;

import java.util.ArrayList;
import java.util.logging.Logger;

import javax.jdo.JDOCanRetryException;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import org.javahispano.javaleague.client.service.TacticService;
import org.javahispano.javaleague.server.domain.TacticClass;
import org.javahispano.javaleague.server.domain.TacticUser;
import org.javahispano.javaleague.server.domain.TacticUserDAO;
import org.javahispano.javaleague.server.domain.User;
import org.javahispano.javaleague.server.domain.UserDAO;
import org.javahispano.javaleague.server.utils.cache.CacheSupport;
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

	/*
	 * @Override public TacticDTO getTactic(String id) {
	 * 
	 * PersistenceManager pm = PMF.getNonTxnPm(); TacticUser detached = null;
	 * 
	 * try { detached = getTacticViaCache(id, pm); } finally { pm.close(); } //
	 * logger.fine("in getTactic- class are: " + detached.getClass()); return
	 * detached.toDTO(); }
	 */
	private TacticUser getTacticViaCache(String id, PersistenceManager pm) {
		TacticUser dsTactic = null, detached = null;

		// check cache first
		Object o = null;
		o = CacheSupport.cacheGet(TacticUser.class.getName(), id);
		if (o != null && o instanceof TacticUser) {
			detached = (TacticUser) o;
			logger.fine("cache hit for Tactic " + detached);
		} else {
			dsTactic = pm.getObjectById(TacticUser.class, id); // the get adds
			// to cache

			detached = pm.detachCopy(dsTactic);

			// String[] loadedFieldNames =
			// NucleusJDOHelper.getLoadedFields(detached, pm);
			// String[] dirtyFieldNames =
			// NucleusJDOHelper.getDirtyFields(detached, pm);
		}
		return detached;
	}

	@Override
	public UserDTO getUserAccount() {
		PersistenceManager pm = PMF.getTxnPm();
		User currentUser = LoginHelper.getLoggedInUser(getThreadLocalRequest()
				.getSession(), pm);

		return currentUser.toDTO(currentUser);
	}

	@Override
	public TacticDTO updateTactic(TacticDTO userTacticDTO) {
		PersistenceManager pm = PMF.getTxnPm();
		if (userTacticDTO.getId() == null) { // create new Tactic
			TacticUser newTactic = addTactic(userTacticDTO);
			return newTactic.toDTO();
		}

		TacticUser tactic = null;
		try {
			for (int i = 0; i < NUM_RETRIES; i++) {
				pm.currentTransaction().begin();
				tactic = tacticDAO
						.findById(Long.valueOf(userTacticDTO.getId()));

				tactic.updatedFromDTO(userTacticDTO);

				tacticDAO.save(tactic);
				try {
					logger.fine("about to start commit");
					pm.currentTransaction().commit();
					logger.info("in updateTactic, did successful commit");
					break;
				} catch (JDOCanRetryException e1) {
					logger.warning(e1.getMessage());
					if (i == (NUM_RETRIES - 1)) {
						throw e1;
					}
				}
			} // end for
		} catch (Exception e) {
			e.printStackTrace();
			logger.warning(e.getMessage());
			userTacticDTO = null;
		} finally {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
				logger.warning("did transaction rollback");
				userTacticDTO = null;
			}
			pm.close();
		}

		return userTacticDTO;
	}

	private TacticUser addTactic(TacticDTO userTacticDTO) {
		PersistenceManager pm = PMF.getTxnPm();
		TacticUser userTactic = null;
		String uid = null;
		try {
			// do this operation under transactional control
			for (int i = 0; i < NUM_RETRIES; i++) {
				pm.currentTransaction().begin();

				User currentUser = LoginHelper.getLoggedInUser(
						getThreadLocalRequest().getSession(), pm);

				userTactic = new TacticUser(userTacticDTO);
				currentUser.setTactic(userTactic.getId().toString());
				// pm.makePersistent(currentUser);
				userDAO.save(currentUser);
				uid = userTactic.getId().toString();

				try {
					logger.fine("starting commit");
					pm.currentTransaction().commit();
					logger.fine("commit was successful");
					break;
				} catch (JDOCanRetryException e1) {
					if (i == (NUM_RETRIES - 1)) {
						throw e1;
					}
				}
			}
		} catch (Exception e) {
			logger.warning(e.getMessage());
			userTactic = null;
		} finally {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
				userTactic = null;
				logger.warning("did transaction rollback");
			}
			pm.close();
		}
		return userTactic;
	}

	public TacticDTO getUserTacticSummary() {

		TacticDTO userTacticSummary = null;
		TacticUser userTactic;

		PersistenceManager pm = PMF.getNonTxnPm();

		try {
			User user = LoginHelper.getLoggedInUser(getThreadLocalRequest()
					.getSession(), pm);
			if (user == null)
				return null;

			if (user.getTactic() == null)
				return null;

			userTactic = tacticDAO.findById(Long.valueOf(Long.valueOf(user
					.getTactic())));

			userTacticSummary = userTactic.toDTO();
		} finally {
			pm.close();
		}

		return userTacticSummary;
	}

	/*
	 * @Override public String deleteTacticClass(String name) { String[] names =
	 * name.split("-", 2); String packageName = names[0].trim(); String
	 * className = names[1].trim();
	 * 
	 * PersistenceManager pm = PMF.getNonTxnPm(); Query q =
	 * pm.newQuery(TacticClass.class, "className == :cn && packageName == :pn");
	 * try { q.setUnique(true); TacticClass tc = (TacticClass)
	 * q.execute(className, packageName); BlobstoreService blobstoreService =
	 * BlobstoreServiceFactory .getBlobstoreService();
	 * blobstoreService.delete(tc.getBlob()); pm.deletePersistent(tc); } finally
	 * { q.closeAll(); pm.close(); } return className; }
	 */

	@Override
	public void deleteTacticClass(String tacticClassId) {
		PersistenceManager pm = PMF.getNonTxnPm();
		Query q = pm.newQuery(TacticClass.class, "id == :tacticClassId");
		try {
			q.setUnique(true);
			TacticClass tc = (TacticClass) q.execute(tacticClassId);
			BlobstoreService blobstoreService = BlobstoreServiceFactory
					.getBlobstoreService();
			blobstoreService.delete(tc.getBlob());
			pm.deletePersistent(tc);
		} finally {
			q.closeAll();
			pm.close();
		}

	}
}
