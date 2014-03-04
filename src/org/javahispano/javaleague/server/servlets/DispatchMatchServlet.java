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

import org.javahispano.javaleague.server.domain.MatchDAO;
import org.javahispano.javaleague.server.domain.TacticUserDAO;
import org.javahispano.javaleague.shared.domain.Match;
import org.javahispano.javaleague.shared.domain.TacticUser;

import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;

/**
 * @author adou
 * 
 */
public class DispatchMatchServlet extends HttpServlet {
	private static final Logger log = Logger
			.getLogger(DispatchMatchServlet.class.getName());
	private MatchDAO dao = new MatchDAO();
	private TacticUserDAO tacticUserDAO = new TacticUserDAO();

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		TacticUser tactic = null;
		Long tacticID = Long.parseLong(req.getParameter("tacticID").replace(
				"_", ""));
		try {
			tactic = tacticUserDAO.findById(tacticID);

			List<TacticUser> results = (List<TacticUser>) tacticUserDAO
					.getTactics(tacticID);

			// De la lista resultante hay que escoger un equipo para el
			// partido
			if (!results.isEmpty()) {

				Match match = new Match();
				TacticUser visitingTactic = results
						.get((int) (Math.random() * results.size()));

				match.setLocal(tacticID.toString());
				match.setVisiting(visitingTactic.getId().toString());
				match.setNameLocal(tactic.getTeamName());
				match.setNameForeign(visitingTactic.getTeamName());
				match.setVisualization(addMinutesToDate(new Date(), 5));

				// tactic.setFriendlyMatch(AppLib.FRIENDLY_MATCH_SCHEDULED);
				// visitingTactic
				// .setFriendlyMatch(AppLib.FRIENDLY_MATCH_SCHEDULED);

				dao.save(match);

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
