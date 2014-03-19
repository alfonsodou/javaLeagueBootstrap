package org.javahispano.javaleague.server.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
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
import org.javahispano.javaleague.server.domain.MatchByteBinDAO;
import org.javahispano.javaleague.server.domain.MatchByteDAO;
import org.javahispano.javaleague.server.domain.MatchDAO;
import org.javahispano.javaleague.server.domain.TacticUserDAO;
import org.javahispano.javaleague.shared.domain.FrameWork;
import org.javahispano.javaleague.shared.domain.Match;
import org.javahispano.javaleague.shared.domain.MatchByte;
import org.javahispano.javaleague.shared.domain.MatchByteBin;
import org.javahispano.javaleague.shared.domain.TacticUser;

import com.google.appengine.api.blobstore.BlobKey;

public class PlayMatchServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger log = Logger.getLogger(PlayMatchServlet.class
			.getName());
	private MyDataStoreClassLoader myDataStoreClassLoader;
	private MatchDAO matchDAO = new MatchDAO();
	private MatchByteDAO matchByteDAO = new MatchByteDAO();
	private MatchByteBinDAO matchByteBinDAO = new MatchByteBinDAO();
	private TacticUserDAO tacticUserDAO = new TacticUserDAO();
	private FrameWorkDAO frameWorkDAO = new FrameWorkDAO();

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
			//frameWork = frameWorkDAO.findByDefaultFrameWork(true);
			frameWork = frameWorkDAO.findById(5108624154165248L);
			log.warning("FrameWork: " + frameWork.getName());

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
			MatchByte matchByte = new MatchByte();
			matchByte.setJvc(matchShared.getMatch());
			matchByte.setTimeLocal(matchShared.getTimeLocal());
			matchByte.setTimeVisita(matchShared.getTimeVisita());
			matchByte = matchByteDAO.save(matchByte);
			MatchByteBin matchByteBin = new MatchByteBin();
			matchByteBin.setBin(matchShared.getMatchBin());
			matchByteBin = matchByteBinDAO.save(matchByteBin);
			
			
			match.setMatchByteId(matchByte.getId());
			match.setLocalGoals(matchShared.getGoalsLocal());
			match.setVisitingTeamGoals(matchShared.getGoalsVisiting());
			match.setLocalPossesion(matchShared.getPosessionLocal());
			match.setState(1);

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

		byteStream = myDataStoreClassLoader.addClassJar(new BlobKey(tactic.getZipClasses()));

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
}
