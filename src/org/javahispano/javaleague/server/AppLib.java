package org.javahispano.javaleague.server;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.channels.Channels;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.javahispano.javaleague.server.domain.TacticClass;
import org.javahispano.javaleague.server.domain.TacticUser;
import org.javahispano.javaleague.server.domain.User;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.files.AppEngineFile;
import com.google.appengine.api.files.FileService;
import com.google.appengine.api.files.FileServiceFactory;
import com.google.appengine.api.files.FileWriteChannel;

public class AppLib {
	public static String baseURL = "http://javaleague.appspot.com";
	public static String emailAdmin = "javaleague@gmail.com";
	public static boolean isDevelopment = false;
	public static final String INFONOTFOUND = "<h1>Error #AF31-G</h1><p>Login service credentials missing in appengine-web.xml."
			+ " Please update this file as indicated with OAuth key information and restart the application.</p>"
			+ "<p>If you just want to try the app at once, choose Google authentication.</p>";
	public static final int MATCH_OK = 1; // partido finalizado correctamente
	public static final int MATCH_ERROR = -1; // partido ejecutado con error
	public static final int MATCH_SCHEDULED = 0; // partido pendiente de
													// ejecutar
	public static final int FRIENDLY_MATCH_NO = 0; // tactica no disponible para
													// partido amistoso
	public static final int FRIENDLY_MATCH_OK = 1; // tactica disponible para
													// jugar partido amistoso
	public static final int FRIENDLY_MATCH_SCHEDULED = 2; // tactica con partido
															// amistoso
															// programado


	public static void addTactic(User user) {
		TacticUser tactic = new TacticUser();
		addSampleTacticClass(tactic);
		tactic.addSampleTacticClass();
		user.setTactic(tactic.getId());
	}	

	public static TacticClass addClass(String url, boolean isTacticClass,
			String className, String packageName) {
		try {
			TacticClass tc = new TacticClass();
			tc.setBlob(SaveFile(url, className));
			tc.setTacticClass(isTacticClass);
			tc.setClassName(className);
			tc.setPackageName(packageName);

			return tc;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public static void addSampleTacticClass(TacticUser tactic) {
		//if (isDevelopment) {
			try {
				tactic.setZipClasses(SaveFile(
						"http://localhost:8888/tactic/samples/TacticaEjemplo.zip",
						"TacticaEjemplo.zip"));
			} catch (Exception e) {
				e.printStackTrace();
			}
		/*} else {
			try {
				tactic.setZipClasses(SaveFile(
						"http://javaleague.appspot.com/tactic/samples/TacticaEjemplo.zip",
						"TacticaEjemplo.zip"));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}*/
	}

	private static BlobKey SaveFile(String link, String fileName)
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

				return fileService.getBlobKey(file);
			}
		} finally {
			connection.disconnect();
		}
		return result;
	}

}
