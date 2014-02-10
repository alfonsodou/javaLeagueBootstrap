/**
 * 
 */
package org.javahispano.javaleague.server.servlets;

import gwtupload.server.exceptions.UploadActionException;
import gwtupload.server.gae.BlobstoreFileItemFactory.BlobstoreFileItem;
import gwtupload.server.gae.BlobstoreUploadAction;

import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.javahispano.javaleague.server.LoginHelper;
import org.javahispano.javaleague.server.PMF;
import org.javahispano.javaleague.server.domain.TacticUser;
import org.javahispano.javaleague.server.domain.TacticUserDAO;
import org.javahispano.javaleague.server.domain.User;
import org.javahispano.javaleague.server.domain.UserDAO;
import org.javahispano.javaleague.server.utils.BlobstoreUtil;

/**
 * @author adou
 * 
 */
public class MyCustomizedUploadServlet extends BlobstoreUploadAction {

	private static Logger log = Logger
			.getLogger(MyCustomizedUploadServlet.class.getName());
	private static TacticUserDAO tacticDAO = new TacticUserDAO();
	private static UserDAO userDAO = new UserDAO();

	@Override
	public String executeAction(HttpServletRequest request,
			List<FileItem> sessionFiles) throws UploadActionException {
		String out = super.executeAction(request, sessionFiles);

		for (FileItem imgItem : sessionFiles) {
			PersistenceManager pm = PMF.getTxnPm();
			User currentUser = LoginHelper.getLoggedInUser(
					request.getSession(), pm);

			try {
				// Start the transaction
				pm.currentTransaction().begin();

				BlobstoreFileItem b = (BlobstoreFileItem) imgItem;

				log.warning(currentUser.getName() + " :: " + b.getName()
						+ " :: " + b.getSize() + " :: " + b.getKeyString());

				// Commit the transaction, flushing the object to the datastore

				if (currentUser.getTactic() != null) { // update
					String tacticIdField = currentUser.getTactic();

					TacticUser tactic = tacticDAO.findById(Long
							.valueOf(tacticIdField));
					tactic.setTeamName(request.getParameter("teamName"));
					tactic.setUpdated(new Date());

					BlobstoreUtil.delete(tactic.getZipClasses().getKeyString());
					tactic.setZipClasses(b.getKey());

					tacticDAO.save(tactic);
				} else { // add

					log.warning("dentro de add. TeamName: "
							+ request.getParameter("teamName"));

					TacticUser tactic = new TacticUser();
					tactic.setTeamName(request.getParameter("teamName"));
					tactic.setZipClasses(b.getKey());

					tactic = tacticDAO.save(tactic);

					currentUser.setTactic(tactic.getId().toString());

					userDAO.save(currentUser);
				}

				pm.currentTransaction().commit();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (pm.currentTransaction().isActive()) {
					pm.currentTransaction().rollback();
				}
				pm.close();
			}
		}
		return out;
	}

}
