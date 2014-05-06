package org.javahispano.javaleague.server.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.ByteBuffer;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.javahispano.javaleague.javacup.shared.Agent;
import org.javahispano.javaleague.javacup.shared.MatchShared;
import org.javahispano.javaleague.server.classloader.MyDataStoreClassLoader;
import org.javahispano.javaleague.server.domain.FrameWorkDAO;
import org.javahispano.javaleague.server.domain.MatchDAO;
import org.javahispano.javaleague.server.domain.TacticUserDAO;
import org.javahispano.javaleague.shared.AppLib;
import org.javahispano.javaleague.shared.domain.FrameWork;
import org.javahispano.javaleague.shared.domain.Match;
import org.javahispano.javaleague.shared.domain.TacticUser;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.tools.cloudstorage.GcsFileOptions;
import com.google.appengine.tools.cloudstorage.GcsFilename;
import com.google.appengine.tools.cloudstorage.GcsInputChannel;
import com.google.appengine.tools.cloudstorage.GcsOutputChannel;
import com.google.appengine.tools.cloudstorage.GcsService;
import com.google.appengine.tools.cloudstorage.GcsServiceFactory;
import com.google.appengine.tools.cloudstorage.RetryParams;

public class PlayMatchFriendlyServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger log = Logger
			.getLogger(PlayMatchFriendlyServlet.class.getName());
	private MyDataStoreClassLoader myDataStoreClassLoader;
	private MatchDAO matchDAO = new MatchDAO();
	private TacticUserDAO tacticUserDAO = new TacticUserDAO();
	private FrameWorkDAO frameWorkDAO = new FrameWorkDAO();

	private final GcsService gcsService = GcsServiceFactory
			.createGcsService(RetryParams.getDefaultInstance());

	private GcsFilename filename;

	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		// Partido partido = null;

		Match match = null;
		Object lo = null;
		Object vo = null;
		FrameWork frameWork = null;

		Long matchID = Long.parseLong(req.getParameter("matchID").replace("_",
				""));
		log.warning("matchID: " + matchID);

		TacticUser localTactic = null;
		TacticUser visitingTactic = null;

		try {

			match = matchDAO.findById(matchID);
			log.warning("Match: " + match.getVisualization());

			localTactic = match.getLocal();
			log.warning("Local: " + localTactic.getTeamName());
			visitingTactic = match.getVisiting();
			log.warning("Visitante: " + visitingTactic.getTeamName());
			frameWork = frameWorkDAO.findById(match.getFrameWorkId());
			log.warning("FrameWork: " + frameWork.getName());

			myDataStoreClassLoader = new MyDataStoreClassLoader(this.getClass()
					.getClassLoader());

			// Cargamos el framework
			myDataStoreClassLoader.addClassJarFramework(new BlobKey(frameWork
					.getFrameWork()));

			Class<? extends Agent> cz = Class.forName(AppLib.CLASS_AGENT, true,
					myDataStoreClassLoader).asSubclass(Agent.class);

			Agent a = cz.newInstance();

			// Cargamos las tácticas
			lo = loadClass(localTactic, a);
			vo = loadClass(visitingTactic, a);

			MatchShared matchShared = a.execute(lo, vo);

			filename = new GcsFilename(AppLib.BUCKET_GCS, AppLib.PATH_MATCH
					+ AppLib.PATH_FRIENDLY_MATCH + match.getId().toString()
					+ ".jvc");
			writeToFile(filename, matchShared.getMatch());

			filename = new GcsFilename(AppLib.BUCKET_GCS, AppLib.PATH_MATCH
					+ AppLib.PATH_FRIENDLY_MATCH + match.getId().toString()
					+ ".bin");
			writeToFile(filename, matchShared.getMatchBin());

			match.setLocalGoals(matchShared.getGoalsLocal());
			match.setVisitingTeamGoals(matchShared.getGoalsVisiting());
			match.setLocalPossesion(matchShared.getPosessionLocal());
			match.setState(AppLib.MATCH_OK);
			match.setTimeLocal(matchShared.getTimeLocal());
			match.setTimeVisita(matchShared.getTimeVisita());

			localTactic.setFriendlyMatch(AppLib.FRIENDLY_MATCH_NO);
			visitingTactic.setFriendlyMatch(AppLib.FRIENDLY_MATCH_NO);

		} catch (Exception e) {

			log.warning(e.getClass().getCanonicalName());
			log.warning(e.getClass().getName());
			log.warning(e.getMessage());

			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			log.warning("stackTrace -> " + sw.toString());

			match.setState(AppLib.MATCH_ERROR);
			localTactic.setFriendlyMatch(AppLib.FRIENDLY_MATCH_NO);
			visitingTactic.setFriendlyMatch(AppLib.FRIENDLY_MATCH_NO);
		} finally {
			matchDAO.save(match);
			tacticUserDAO.save(localTactic);
			tacticUserDAO.save(visitingTactic);
		}

	}

	private Object loadClass(TacticUser tactic, Agent a) throws IOException,
			ClassNotFoundException, InstantiationException,
			IllegalAccessException {
		Class<?> cz = null;
		Class<?> result = null;
		Map<String, byte[]> byteStream;

		GcsFilename fileName = new GcsFilename(AppLib.BUCKET_GCS,
				AppLib.PATH_USER + tactic.getUserId().toString() + "/"
						+ tactic.getId().toString() + "/"
						+ tactic.getFileName());

		byteStream = myDataStoreClassLoader.addClassJar(readFile(fileName));

		Iterator it = byteStream.entrySet().iterator();
		while (it.hasNext()) {

			try {
				Map.Entry e = (Map.Entry) it.next();

				String name = new String((String) e.getKey());

				myDataStoreClassLoader.addClass(name, (byte[]) e.getValue());

			} catch (Exception e) {

				log.warning(e.getMessage());
				StringWriter sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				log.warning("stackTrace -> " + sw.toString());
			}

		}

		Iterator it1 = byteStream.entrySet().iterator();
		while (it1.hasNext()) {

			try {
				Map.Entry e = (Map.Entry) it1.next();

				String name = new String((String) e.getKey());

				cz = myDataStoreClassLoader.loadClass(name);

				if (a.isTactic(cz)) {
					result = cz;
				}
			} catch (Exception e) {

				log.warning(e.getMessage());
				StringWriter sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				log.warning("stackTrace -> " + sw.toString());
			}

		}

		if (result != null)
			return result.newInstance();

		return null;

	}

	private void writeToFile(GcsFilename fileName, byte[] content)
			throws IOException {
		GcsOutputChannel outputChannel = gcsService.createOrReplace(fileName,
				GcsFileOptions.getDefaultInstance());
		outputChannel.write(ByteBuffer.wrap(content));
		outputChannel.close();
	}

	private byte[] readFile(GcsFilename fileName) {
		int fileSize;
		ByteBuffer result = null;
		try {
			fileSize = (int) gcsService.getMetadata(fileName).getLength();
			result = ByteBuffer.allocate(fileSize);
			try (GcsInputChannel readChannel = gcsService.openReadChannel(
					fileName, 0)) {
				readChannel.read(result);
			}
		} catch (Exception e) {
			log.warning(e.getMessage());
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			log.warning("stackTrace -> " + sw.toString());

		}

		return result.array();
	}

}
