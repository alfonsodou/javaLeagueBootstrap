/**
 * 
 */
package org.javahispano.javaleague.server.servlets;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.io.StringWriter;
import java.nio.ByteBuffer;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.javahispano.javaleague.server.utils.Compressor;
import org.javahispano.javaleague.server.utils.Serializer;
import org.javahispano.javaleague.shared.AppLib;

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
	private final GcsService gcsService = GcsServiceFactory
			.createGcsService(RetryParams.getDefaultInstance());
	private static final Logger log = Logger
			.getLogger(ServeBinMockServlet.class.getName());

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		String pathFileName;

		// pathFileName = "matchs/league/6133129278390272/5031379704217600.bin";
		pathFileName = req.getParameter("file");
		log.warning("FileName: " + pathFileName);

		GcsFilename filename = new GcsFilename(AppLib.BUCKET_GCS, pathFileName);

		res.setHeader("ETag", "5031379704217600");// Establece header ETag
		res.setHeader(
				"Content-disposition",
				"inline; filename="
						+ pathFileName.substring(pathFileName.lastIndexOf("/"),
								pathFileName.lastIndexOf(".")));

		try {
			byte[] ser = readFromFile(filename);
			res.getOutputStream().write(ser);
			res.getOutputStream().close();
		} catch (ClassNotFoundException e) {
			log.warning(e.getMessage());
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			log.warning("stackTrace -> " + sw.toString());
		}
	}

	private byte[] readFromFile(GcsFilename fileName) throws IOException,
			ClassNotFoundException {
		int fileSize = (int) gcsService.getMetadata(fileName).getLength();
		ByteBuffer result = ByteBuffer.allocate(fileSize);
		try (GcsInputChannel readChannel = gcsService.openReadChannel(fileName,
				0)) {
			readChannel.read(result);
		}
		return result.array();
	}

	private static Serializable fromBytes(byte[] bytes) throws IOException,
			ClassNotFoundException {
		ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
		ObjectInput in = new ObjectInputStream(bis);
		Object o = in.readObject();
		bis.close();
		in.close();
		return (Serializable) o;
	}

	private static byte[] toBytes(Serializable ser) throws IOException {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ObjectOutput out = new ObjectOutputStream(bos);
		out.writeObject(ser);
		out.close();
		bos.close();
		return bos.toByteArray();
	}
}
