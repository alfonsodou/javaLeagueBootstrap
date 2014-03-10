package org.javahispano.javaleague.server.servlets;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.javahispano.javaleague.javacup.shared.Agent;
import org.javahispano.javaleague.javacup.shared.MatchShared;
import org.javahispano.javaleague.server.AppLib;
import org.javahispano.javaleague.server.classloader.MyDataStoreClassLoader;
import org.javahispano.javaleague.server.domain.FrameWorkDAO;
import org.javahispano.javaleague.server.domain.MatchDAO;
import org.javahispano.javaleague.server.domain.TacticUserDAO;
import org.javahispano.javaleague.shared.domain.FrameWork;
import org.javahispano.javaleague.shared.domain.Match;
import org.javahispano.javaleague.shared.domain.TacticUser;

import com.google.appengine.api.blobstore.BlobKey;

public class PlayMatchServlet extends HttpServlet {
	private static final Logger log = Logger.getLogger(PlayMatchServlet.class
			.getName());
	private MyDataStoreClassLoader myDataStoreClassLoader;
	private MatchDAO matchDAO = new MatchDAO();
	private TacticUserDAO tacticUserDAO = new TacticUserDAO();
	private FrameWorkDAO frameWorkDAO = new FrameWorkDAO();

	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		// Partido partido = null;

		Match match = null;
		Object lo = null;
		Object vo = null;
		FrameWork frameWork = null;

		long matchID = Long.parseLong(req.getParameter("matchID").replace("_",
				""));

		TacticUser localTactic = null;
		TacticUser visitingTactic = null;

		try {

			match = matchDAO.findById(matchID);
			localTactic = match.getLocal();
			visitingTactic = match.getVisiting();
			frameWork = frameWorkDAO.findByDefaultFrameWork(true);

			myDataStoreClassLoader = new MyDataStoreClassLoader(this.getClass()
					.getClassLoader());

			// Cargamos el framework
			myDataStoreClassLoader.addClassJarFramework(new BlobKey(frameWork
					.getFrameWork()));

			Class<? extends Agent> cz = Class.forName(
					"org.javahispano.javacup.model.engine.AgentPartido", true,
					myDataStoreClassLoader).asSubclass(Agent.class);

			Agent a = cz.newInstance();

			// Cargamos las tácticas
			lo = loadClass(localTactic, a);
			vo = loadClass(visitingTactic, a);

			MatchShared matchShared = a.execute(lo, vo);

			match.setMatch(matchShared.getMatch());
			match.setLocalGoals(matchShared.getGoalsLocal());
			match.setVisitingTeamGoals(matchShared.getGoalsVisiting());
			match.setLocalPossesion(matchShared.getPosessionLocal());

			// actualizamos estadisticas
			localTactic.addGoalsFor(match.getLocalGoals());
			localTactic.addGoalsAgainst(match.getVisitingTeamGoals());
			visitingTactic.addGoalsFor(match.getVisitingTeamGoals());
			visitingTactic.addGoalsAgainst(match.getLocalGoals());
			if (match.getLocalGoals() == match.getVisitingTeamGoals()) {
				localTactic.addMatchTied();
				visitingTactic.addMatchTied();
			} else {
				if (match.getLocalGoals() > match.getVisitingTeamGoals()) {
					localTactic.addMatchWins();
					visitingTactic.addMatchLost();
				} else {
					localTactic.addMatchLost();
					visitingTactic.addMatchWins();
				}
			}
			localTactic.setFriendlyMatch(AppLib.FRIENDLY_MATCH_NO);
			visitingTactic.setFriendlyMatch(AppLib.FRIENDLY_MATCH_NO);

		} catch (Exception e) {
			e.printStackTrace();
			log.warning(e.getClass().getCanonicalName());
			log.warning(e.getClass().getName());
			log.warning(e.getMessage());
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

		byteStream = myDataStoreClassLoader.addClassJar(new BlobKey(tactic.getZipClasses()));

		Iterator it = byteStream.entrySet().iterator();
		while (it.hasNext()) {

			try {
				Map.Entry e = (Map.Entry) it.next();

				String name = new String((String) e.getKey());

				myDataStoreClassLoader.addClass(name, (byte[]) e.getValue());

			} catch (Exception e) {

				log.warning(e.getMessage());
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
			}

		}

		if (result != null)
			return result.newInstance();

		return null;

	}
}
