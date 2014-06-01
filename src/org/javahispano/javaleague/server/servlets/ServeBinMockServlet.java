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

import org.javahispano.javaleague.server.domain.MatchDAO;
import org.javahispano.javaleague.shared.AppLib;
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
public class ServeBinMockServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private MatchDAO dao = new MatchDAO();
	private final GcsService gcsService = GcsServiceFactory
			.createGcsService(RetryParams.getDefaultInstance());

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		String pathFileName;
		
		pathFileName = "matchs/league/6133129278390272/5031379704217600.bin";

		GcsFilename filename = new GcsFilename(AppLib.BUCKET_GCS, pathFileName);

		res.setHeader("ETag", "5031379704217600");// Establece header ETag
		res.setHeader("Content-disposition", "inline; filename=5031379704217600.bin");

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
