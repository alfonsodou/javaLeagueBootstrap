/**
 * 
 */
package org.javahispano.javaleague.server.servlets;

import java.io.IOException;
import java.io.PrintWriter;
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

		try {
			ServletFileUpload upload = new ServletFileUpload();
			FileItemIterator iter = upload.getItemIterator(req);
			while (iter.hasNext()) {
				FileItemStream item = iter.next();
				if (item.isFormField()) {
					if (item.getFieldName().equals("teamName")) {
						tacticUser.setTeamName(IOUtils.toString(item
								.openStream()));
					}
				} else {
					if (!item.getName().isEmpty()) {
						zipBytes = IOUtils.toByteArray(item.openStream());
						if (zipBytes != null) {
							fileName = new GcsFilename(AppLib.bucket, "tactic/"
									+ tacticUser.getId().toString() + "/"
									+ item.getName());
							writeToFile(fileName, zipBytes);

							if (tacticUser.getFileName() != null) {
								gcsService.delete(new GcsFilename(
										AppLib.bucket, "tactic/"
												+ tacticUser.getId().toString()
												+ "/"
												+ tacticUser.getFileName()));
							}

							tacticUser.setFileName(item.getName());
							tacticUser.setBytes(gcsService
									.getMetadata(fileName).getLength());
						}
					}
				}
			}
			tacticUser.setUpdated(new Date());
			tacticUser = tacticDAO.save(tacticUser);
			currentUser.setTactic(tacticUser);
			currentUser = userDAO.save(currentUser);

			PrintWriter out = res.getWriter();
			StringBuffer sb = new StringBuffer();
			sb.append("<?xml version='1.0' encoding='ISO-8859-1'?>\n");
			sb.append("<root>");
			sb.append("<tactic>");
			sb.append("<teamname>" + tacticUser.getTeamName() + "</teamname>\n");
			sb.append("<filename>" + tacticUser.getFileName() + "</filename>\n");
			sb.append("<bytes>" + tacticUser.getBytes().toString()
					+ "</bytes>\n");
			sb.append("<updated>" + tacticUser.getUpdated().toString()
					+ "</updated>\n");
			sb.append("</tactic>");
			sb.append("</root>");
			log.warning("XML: " + sb.toString());
			out.println(sb.toString());
			out.flush();

		} catch (Exception e) {
			status = e.getMessage();
			log.warning("ERROR: " + status);
		}

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
