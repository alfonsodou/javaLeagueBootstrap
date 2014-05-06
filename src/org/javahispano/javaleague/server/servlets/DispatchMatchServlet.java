/**
 * 
 */
package org.javahispano.javaleague.server.servlets;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.javahispano.javaleague.server.LoginHelper;
import org.javahispano.javaleague.server.domain.MatchDAO;
import org.javahispano.javaleague.server.domain.TacticUserDAO;
import org.javahispano.javaleague.shared.AppLib;
import org.javahispano.javaleague.shared.domain.Match;
import org.javahispano.javaleague.shared.domain.TacticUser;
import org.javahispano.javaleague.shared.domain.User;

import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.QueueStatistics;
import com.google.appengine.api.taskqueue.TaskOptions;

/**
 * @author adou
 * 
 */
public class DispatchMatchServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger log = Logger
			.getLogger(DispatchMatchServlet.class.getName());
	private MatchDAO matchDao = new MatchDAO();
	private TacticUserDAO tacticUserDAO = new TacticUserDAO();

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		User currentUser = LoginHelper.getLoggedInUser(req.getSession());
		TacticUser tactic = null;
		Long tacticID = Long.parseLong(req.getParameter("tacticID").replace(
				"_", ""));
		try {
			tactic = tacticUserDAO.findById(tacticID);
			List<Match> matchList = matchDao
					.getMatchsState(AppLib.MATCH_FRIENDLY_WAITING);

			if (matchList.size() > 0) {
				Match match = matchList.get(0);

				if (match.getLocal() == null) {
					match.setLocal(tactic);
					match.setNameLocal(tactic.getTeamName());
					match.setNameLocalManager(currentUser.getName());
				} else {
					match.setVisiting(tactic);
					match.setNameForeign(tactic.getTeamName());
					match.setNameVisitingManager(currentUser.getName());
				}
				QueueStatistics queueStatistics = QueueFactory.getQueue(
						AppLib.QUEUE_FRIENDLY).fetchStatistics();

				match.setVisualization(addMinutesToDate(
						new Date(),
						AppLib.MINUTES_EXECUTION_MATCH
								+ (AppLib.MINUTES_EXECUTION_MATCH * queueStatistics
										.getNumTasks())));
			}

			List<TacticUser> results = (List<TacticUser>) tacticUserDAO
					.getTactics(tacticID);

			// De la lista resultante hay que escoger un equipo para el
			// partido
			if (!results.isEmpty()) {

				Match match = new Match();
				TacticUser visitingTactic = results
						.get((int) (Math.random() * results.size()));

				match.setLocal(tactic);
				match.setVisiting(visitingTactic);
				match.setNameLocal(tactic.getTeamName());
				match.setNameForeign(visitingTactic.getTeamName());
				match.setVisualization(addMinutesToDate(new Date(), 5));

				// tactic.setFriendlyMatch(AppLib.FRIENDLY_MATCH_SCHEDULED);
				// visitingTactic
				// .setFriendlyMatch(AppLib.FRIENDLY_MATCH_SCHEDULED);

				matchDao.save(match);

				Queue queue = QueueFactory.getDefaultQueue();

				queue.add(TaskOptions.Builder.withUrl("/playMatch").param(
						"matchID", match.getId().toString()));
			}
			// } else {
			// ... no results ...
			// tactic.setFriendlyMatch(AppLib.FRIENDLY_MATCH_OK);
			// }
			// } // end if AppLib.FRIENDLY_MATCH_NO
		} catch (Exception e) {
			e.printStackTrace();
			log.warning(e.getMessage());

		}
	}

	/**
	 * Agrega o quita minutos a una fecha dada. Para quitar minutos hay que
	 * sumarle valores negativos.
	 * 
	 * @param date
	 * @param minutes
	 * @return
	 */
	public static Date addMinutesToDate(Date date, int minutes) {
		Calendar calendarDate = Calendar.getInstance();
		calendarDate.setTime(date);
		calendarDate.add(Calendar.MINUTE, minutes);
		return calendarDate.getTime();
	}

}
