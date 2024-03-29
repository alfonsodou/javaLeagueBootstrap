/**
 * 
 */
package org.javahispano.javaleague.server.servlets;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.javahispano.javaleague.server.domain.MatchDAO;
import org.javahispano.javaleague.shared.AppLib;
import org.javahispano.javaleague.shared.domain.Match;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;

/**
 * @author adou
 * 
 */
public class ScheduleMatchsServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger log = Logger
			.getLogger(ScheduleMatchsServlet.class.getName());
	private static Queue queue = QueueFactory.getQueue("league");
	private static MatchDAO matchDAO = new MatchDAO();

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		List<Match> matchs = matchDAO.getMatchsDate(new Date());

		for (Match m : matchs) {
			try {

				queue.add(TaskOptions.Builder.withUrl("/playMatch").param(
						"matchID", m.getId().toString()));
				
				m.setState(AppLib.MATCH_QUEUE);
				matchDAO.save(m);

			} catch (Exception e) {
				log.warning(e.getMessage());
			}

		}

	}

}
