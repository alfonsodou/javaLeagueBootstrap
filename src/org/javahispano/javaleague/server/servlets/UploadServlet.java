package org.javahispano.javaleague.server.servlets;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.javahispano.javaleague.server.PMF;
import org.javahispano.javaleague.server.domain.TacticUser;
import org.javahispano.javaleague.server.domain.TacticUserDAO;
import org.javahispano.javaleague.server.utils.BlobstoreUtil;

import com.google.appengine.api.blobstore.BlobKey;

/**
 * 
 */
@SuppressWarnings("serial")
public class UploadServlet extends HttpServlet {

	private static Logger log = Logger.getLogger(UploadServlet.class.getName());
	private static TacticUserDAO tacticDAO = new TacticUserDAO();

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		PersistenceManager pm = PMF.getTxnPm();

		try {
			if (ServletFileUpload.isMultipartContent(req)) {
				String tacticIdField = req.getParameter("tacticId");
				List<String> uploadedKeys = BlobstoreUtil.processRequest(req);
				if (uploadedKeys.size() > 0) {
					pm.currentTransaction().begin();
					TacticUser tactic = tacticDAO.findById(Long
							.valueOf(tacticIdField));
					tactic.setTeamName(req.getParameter("teamName"));
					tactic.setUpdated(new Date());
					for (String b : uploadedKeys) {
						BlobstoreUtil.delete(tactic.getZipClasses()
								.getKeyString());
						tactic.setZipClasses(new BlobKey(b));
					}
					tacticDAO.save(tactic);
					pm.currentTransaction().commit();
				} else {
					log.warning("empty upload");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
				log.warning("did transaction rollback");
			}
			pm.close();
		}

		// List<BlobInfo> blobInfos = BlobstoreUtil.loadBlobInfos(uploadedKeys);
		// Gson gson = new Gson();
		// ChannelUtil.pushMessage(gson.toJson(blobInfos),
		// MessageType.AddEvent); // Also push to sender.

		// Upload URLs are one time use only, need to send a new URL to the
		// client.
		String newBlobstoreUrl = BlobstoreUtil.getUrl();
		res.setHeader("Content-Type", "text/html"); // Browser will wrap
													// text/plain in <pre> tags
		res.getWriter().print(newBlobstoreUrl);
		res.getWriter().flush();
		res.getWriter().close();
		log.info("Returning new blobstore URL " + newBlobstoreUrl);
	}
}