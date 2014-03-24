/**
 * 
 */
package org.javahispano.javaleague.server.servlets;

import java.io.IOException;
import java.nio.ByteBuffer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.javahispano.javaleague.server.AppLib;
import org.javahispano.javaleague.server.domain.MatchDAO;
import org.javahispano.javaleague.shared.domain.Match;

import com.google.appengine.tools.cloudstorage.GcsFilename;
import com.google.appengine.tools.cloudstorage.GcsInputChannel;
import com.google.appengine.tools.cloudstorage.GcsService;
import com.google.appengine.tools.cloudstorage.GcsServiceFactory;
import com.google.appengine.tools.cloudstorage.RetryParams;

/**
 * @author adou
 * 
 */
public class ServeBinServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private MatchDAO dao = new MatchDAO();
	private final GcsService gcsService = GcsServiceFactory
			.createGcsService(RetryParams.getDefaultInstance());

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		long id = Long.parseLong(req.getParameter("id").replace("_", ""));
		Match p = dao.findById(id);
		GcsFilename filename = new GcsFilename(AppLib.bucket, p.getLeagueId()
				.toString() + "/" + p.getId().toString() + ".bin");
		res.getOutputStream().write(readFromFile(filename));
		res.flushBuffer();
	}

	private byte[] readFromFile(GcsFilename fileName) throws IOException {
		int fileSize = (int) gcsService.getMetadata(fileName).getLength();
		ByteBuffer result = ByteBuffer.allocate(fileSize);
		try (GcsInputChannel readChannel = gcsService.openReadChannel(fileName,
				0)) {
			readChannel.read(result);
		}
		return result.array();
	}

}
