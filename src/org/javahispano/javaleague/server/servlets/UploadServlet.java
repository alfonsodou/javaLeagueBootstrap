package org.javahispano.javaleague.server.servlets;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.javahispano.javaleague.server.LoginHelper;
import org.javahispano.javaleague.server.PMF;
import org.javahispano.javaleague.server.domain.TacticUser;
import org.javahispano.javaleague.server.domain.TacticUserDAO;
import org.javahispano.javaleague.server.domain.User;
import org.javahispano.javaleague.server.domain.UserDAO;
import org.javahispano.javaleague.server.utils.BlobstoreUtil;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;

/**
 * 
 */
@SuppressWarnings("serial")
public class UploadServlet extends HttpServlet {

	private static Logger log = Logger.getLogger(UploadServlet.class.getName());
	private static TacticUserDAO tacticDAO = new TacticUserDAO();
	private static UserDAO userDAO = new UserDAO();

	private BlobstoreService blobstoreService = BlobstoreServiceFactory
			.getBlobstoreService();

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		log.warning("Dentro de uploadServlet!");    
		
		PersistenceManager pm = PMF.getTxnPm();
		User currentUser = LoginHelper.getLoggedInUser(req.getSession(), pm);

		Map<String, List<BlobKey>> blobs = blobstoreService.getUploads(req);
		List<BlobKey> blobKeys = blobs.get("fileUpload");

		if ((blobKeys != null) && (blobKeys.size() > 0)) {
			
			log.warning("Dentro de blobKeys. Count: " + blobKeys.size());
			
			pm.currentTransaction().begin();
			if (currentUser.getTactic() != null) { // update
				Long tacticIdField = currentUser.getTactic();


				TacticUser tactic = tacticDAO.findById(Long
						.valueOf(tacticIdField));
				tactic.setTeamName(req.getParameter("teamName"));
				tactic.setUpdated(new Date());

				BlobstoreUtil.delete(tactic.getZipClasses().getKeyString());
				tactic.setZipClasses(blobKeys.get(0));

				tacticDAO.save(tactic);
			} else { // add
				
				log.warning("dentro de add. TeamName: " + req.getParameter("teamName"));
				
				TacticUser tactic = new TacticUser();
				tactic.setTeamName(req.getParameter("teamName"));
				tactic.setZipClasses(blobKeys.get(0));
				
				tactic = tacticDAO.save(tactic);
				
				currentUser.setTactic(tactic.getId());
				
				userDAO.save(currentUser);
			}
			pm.currentTransaction().commit();
		}

		if (pm.currentTransaction().isActive()) {
			pm.currentTransaction().rollback();
			log.warning("did transaction rollback");
		}
		pm.close();

	}
}