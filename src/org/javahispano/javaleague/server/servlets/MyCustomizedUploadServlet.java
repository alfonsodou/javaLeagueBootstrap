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

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.javahispano.javaleague.server.LoginHelper;
import org.javahispano.javaleague.server.domain.TacticUserDAO;
import org.javahispano.javaleague.server.domain.UserDAO;
import org.javahispano.javaleague.server.utils.BlobstoreUtil;
import org.javahispano.javaleague.shared.domain.TacticUser;
import org.javahispano.javaleague.shared.domain.User;

import com.google.appengine.api.blobstore.BlobInfo;
import com.google.appengine.api.blobstore.BlobInfoFactory;
import com.google.gwt.i18n.client.DateTimeFormat;

/**
 * @author adou
 * 
 */
public class MyCustomizedUploadServlet extends BlobstoreUploadAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static Logger log = Logger
			.getLogger(MyCustomizedUploadServlet.class.getName());
	private static TacticUserDAO tacticDAO = new TacticUserDAO();
	private static UserDAO userDAO = new UserDAO();

	@Override
	public String executeAction(HttpServletRequest request,
			List<FileItem> sessionFiles) throws UploadActionException {
		String out = "";

		super.executeAction(request, sessionFiles);

		for (FileItem item : sessionFiles) {
			User currentUser = LoginHelper
					.getLoggedInUser(request.getSession());

			try {
				if (item.isFormField()) {
					log.warning("FormITEM: " + item.getFieldName() + " :: "
							+ item.getString());

				} else {

					BlobstoreFileItem b = (BlobstoreFileItem) item;
					BlobInfoFactory infoFactory = new BlobInfoFactory();
					BlobInfo blobInfo = infoFactory.loadBlobInfo(b.getKey());
					Date updated = new Date();
					DateTimeFormat fmt = DateTimeFormat
							.getFormat("dd/MM/yyyy :: HH:mm:ss");

/*					out += blobInfo.getFilename() + " :: " + blobInfo.getSize()
							+ " bytes|" + fmt.format(updated);*/
					out += blobInfo.getFilename() + " :: " + blobInfo.getSize()
							+ " bytes|" + updated.toString();
					
					if (currentUser.getTactic() != null) { // update
						TacticUser tactic = currentUser.getTactic();
						tactic.setUpdated(updated);

						if (tactic.getZipClasses() != null) {
							BlobstoreUtil.delete(tactic.getZipClasses());
						}
						tactic.setZipClasses(b.getKeyString());
						tactic.setFileName(blobInfo.getFilename());
						tactic.setBytes(blobInfo.getSize());

						tacticDAO.save(tactic);
					} else { // add
						TacticUser tactic = new TacticUser();
						tactic.setZipClasses(b.getKeyString());
						tactic.setFileName(blobInfo.getFilename());
						tactic.setBytes(blobInfo.getSize());

						tactic = tacticDAO.save(tactic);

						currentUser.setTactic(tactic);

						userDAO.save(currentUser);
					}
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return out;
	}

}
