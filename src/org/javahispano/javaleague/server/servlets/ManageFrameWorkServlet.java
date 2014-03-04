/**
 * 
 */
package org.javahispano.javaleague.server.servlets;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.channels.Channels;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.javahispano.javaleague.server.domain.FrameWorkDAO;
import org.javahispano.javaleague.shared.domain.FrameWork;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.files.AppEngineFile;
import com.google.appengine.api.files.FileService;
import com.google.appengine.api.files.FileServiceFactory;
import com.google.appengine.api.files.FileWriteChannel;

/**
 * @author adou
 * 
 */
public class ManageFrameWorkServlet extends HttpServlet {
	private FrameWorkDAO frameWorkDAO = new FrameWorkDAO();

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		String action = req.getParameter("action");

		if (action.equals("add")) {
			FrameWork frameWork = new FrameWork();

			try {
				frameWork.setName(req.getParameter("name"));
				frameWork.setFrameWork(SaveFile(req.getParameter("url"),
						req.getParameter("url")));
				frameWork.setUrlDownload(req.getParameter("urlSource"));
				
				frameWorkDAO.save(frameWork);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private static String SaveFile(String link, String fileName)
			throws Exception {
		BlobKey result = null;
		URL url = new URL(link);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		try {
			connection.setInstanceFollowRedirects(false);
			connection.setRequestMethod("GET");
			connection.setConnectTimeout(10000);

			FileService fileService = FileServiceFactory.getFileService();

			Integer code = connection.getResponseCode();
			if (code == HttpURLConnection.HTTP_OK) {
				String contentType = connection.getHeaderField("Content-type");
				InputStream is = connection.getInputStream();

				AppEngineFile file = fileService.createNewBlobFile(contentType,
						fileName);
				boolean lock = true;
				FileWriteChannel writeChannel = fileService.openWriteChannel(
						file, lock);
				OutputStream os = Channels.newOutputStream(writeChannel);

				byte[] buf = new byte[4096];
				ByteArrayOutputStream bas = new ByteArrayOutputStream();
				int n;
				while ((n = is.read(buf)) >= 0)
					bas.write(buf, 0, n);
				os.write(bas.toByteArray());
				os.close();
				writeChannel.closeFinally();

				return fileService.getBlobKey(file).getKeyString();
			}
		} finally {
			connection.disconnect();
		}
		return null;
	}

}
