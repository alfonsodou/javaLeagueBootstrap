/**
 * 
 */
package org.javahispano.javaleague.server.servlets;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Date;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;
import org.javahispano.javaleague.server.AppLib;
import org.javahispano.javaleague.server.LoginHelper;
import org.javahispano.javaleague.server.domain.TacticUserDAO;
import org.javahispano.javaleague.server.domain.UserDAO;
import org.javahispano.javaleague.shared.domain.TacticUser;
import org.javahispano.javaleague.shared.domain.User;

import com.google.appengine.tools.cloudstorage.GcsFileOptions;
import com.google.appengine.tools.cloudstorage.GcsFilename;
import com.google.appengine.tools.cloudstorage.GcsOutputChannel;
import com.google.appengine.tools.cloudstorage.GcsService;
import com.google.appengine.tools.cloudstorage.GcsServiceFactory;
import com.google.appengine.tools.cloudstorage.RetryParams;

/**
 * @author adou
 * 
 */
public class UploadBlobServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final GcsService gcsService = GcsServiceFactory
			.createGcsService(RetryParams.getDefaultInstance());
	private static final Logger log = Logger.getLogger(UploadBlobServlet.class
			.getName());
	private static TacticUserDAO tacticDAO = new TacticUserDAO();
	private static UserDAO userDAO = new UserDAO();

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		User currentUser = LoginHelper.getLoggedInUser(req.getSession());
		TacticUser tacticUser = currentUser.getTactic();
		String status = null;
		GcsFilename fileName = null;
		byte[] zipBytes = null;

		log.warning("Dentro de UploadBlob!!");
		try {
			ServletFileUpload upload = new ServletFileUpload();
			FileItemIterator iter = upload.getItemIterator(req);
			while (iter.hasNext()) {
				FileItemStream item = iter.next();
				log.warning("itemFieldName: " + item.getFieldName());
				if (item.isFormField()) {
					if (item.getFieldName().equals("teamName")) {
						tacticUser.setTeamName(IOUtils.toString(item
								.openStream()));
					}
				} else {
					log.warning("itemName: " + item.getName());
					zipBytes = IOUtils.toByteArray(item.openStream());
					fileName = new GcsFilename(AppLib.bucket, "user/"
							+ currentUser.getId().toString() + "/tactic/"
							+ tacticUser.getId().toString() + "/"
							+ item.getName());
					log.warning("fileName: " + fileName);
					writeToFile(fileName, zipBytes);

					if (tacticUser.getFileName() != null) {
						gcsService.delete(new GcsFilename(AppLib.bucket,
								tacticUser.getFileName()));
					}

					tacticUser.setFileName(fileName.toString());
					tacticUser.setBytes(gcsService.getMetadata(fileName)
							.getLength());
				}
			}
			tacticUser.setUpdated(new Date());
			tacticUser = tacticDAO.save(tacticUser);
			currentUser.setTactic(tacticUser);
			currentUser = userDAO.save(currentUser);
		} catch (Exception e) {
			status = e.getMessage();
			log.warning(status);
		}
		// res.getWriter().write(status);

	}

	private void save() {
		/*
		 * User currentUser = LoginHelper
		 * .getLoggedInUser(request.getSession());
		 * 
		 * try { if (item.isFormField()) { log.warning("FormITEM: " +
		 * item.getFieldName() + " :: " + item.getString());
		 * 
		 * } else {
		 * 
		 * BlobstoreFileItem b = (BlobstoreFileItem) item; BlobInfoFactory
		 * infoFactory = new BlobInfoFactory(); BlobInfo blobInfo =
		 * infoFactory.loadBlobInfo(b.getKey()); Date updated = new Date();
		 * DateTimeFormat fmt = DateTimeFormat
		 * .getFormat("dd/MM/yyyy :: HH:mm:ss");
		 * 
		 * out += blobInfo.getFilename() + " :: " + blobInfo.getSize() +
		 * " bytes|" + fmt.format(updated);
		 * 
		 * if (currentUser.getTactic() != null) { // update TacticUser tactic =
		 * currentUser.getTactic(); tactic.setUpdated(updated);
		 * 
		 * if (tactic.getZipClasses() != null) {
		 * BlobstoreUtil.delete(tactic.getZipClasses()); }
		 * tactic.setZipClasses(b.getKeyString());
		 * tactic.setFileName(blobInfo.getFilename());
		 * tactic.setBytes(blobInfo.getSize());
		 * 
		 * tacticDAO.save(tactic); } else { // add TacticUser tactic = new
		 * TacticUser(); tactic.setZipClasses(b.getKeyString());
		 * tactic.setFileName(blobInfo.getFilename());
		 * tactic.setBytes(blobInfo.getSize());
		 * 
		 * tactic = tacticDAO.save(tactic);
		 * 
		 * currentUser.setTactic(tactic);
		 * 
		 * userDAO.save(currentUser); } }
		 * 
		 * } catch (Exception e) { e.printStackTrace(); }
		 */
	}

	private void writeToFile(GcsFilename fileName, byte[] content)
			throws IOException {
		@SuppressWarnings("resource")
		GcsOutputChannel outputChannel = gcsService.createOrReplace(fileName,
				GcsFileOptions.getDefaultInstance());
		outputChannel.write(ByteBuffer.wrap(content));
		outputChannel.close();
	}
}
