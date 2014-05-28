/**
 * 
 */
package org.javahispano.javaleague.server.servlets;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.javahispano.javaleague.server.domain.ClasificationDAO;
import org.javahispano.javaleague.server.domain.LeagueDAO;
import org.javahispano.javaleague.server.domain.StatisticsTeamDAO;
import org.javahispano.javaleague.shared.AppLib;
import org.javahispano.javaleague.shared.domain.CalendarDate;
import org.javahispano.javaleague.shared.domain.Clasification;
import org.javahispano.javaleague.shared.domain.League;
import org.javahispano.javaleague.shared.domain.Match;
import org.javahispano.javaleague.shared.domain.StatisticsTeam;
import org.javahispano.javaleague.shared.domain.User;

import com.googlecode.objectify.Ref;

/**
 * @author adou
 * 
 */
public class ClasificationLeaguesServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final Logger log = Logger
			.getLogger(ClasificationLeaguesServlet.class.getName());

	private LeagueDAO leagueDAO = new LeagueDAO();
	private ClasificationDAO clasificationDAO = new ClasificationDAO();
	private StatisticsTeamDAO statisticsTeamDAO = new StatisticsTeamDAO();

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		List<League> leagues = leagueDAO
				.getLeaguesState(AppLib.LEAGUE_EXECUTION);

		for (League l : leagues) {
			if ((l.getClasification() != null)
					&& (l.getClasification().get().getClasification() != null)
					&& (l.getClasification().get().getClasification().size() > 0)) {
				for (Ref<StatisticsTeam> st : l.getClasification().get()
						.getClasification()) {
					if (st.get() != null) {
						statisticsTeamDAO.delete(st.get().getId());
					}
				}
				clasificationDAO.delete(l.getClasification().get().getId());
			}
			createClasification(l);

			Clasification clasification = l.getClasification().get();

			if (l.getExecutedMatchs() > l.getClasification().get()
					.getNumberMatchs()) {
				log.warning("** Liga executed matchs: " + l.getExecutedMatchs()
						+ " :: " + l.getClasification().get().getNumberMatchs());
				List<Ref<CalendarDate>> listCalendarDate = l.getMatchs();
				for (Ref<CalendarDate> cd : listCalendarDate) {
					for (Ref<Match> m : cd.get().getMatchs()) {
						if (m.get().getState() == AppLib.MATCH_OK) {
							StatisticsTeam stLocal = l.getClasification().get()
									.getClasification(m.get().getLocalTeamId());
							StatisticsTeam stVis = l
									.getClasification()
									.get()
									.getClasification(
											m.get().getVisitingTeamId());
							if (m.get().getLocalGoals() > m.get()
									.getVisitingTeamGoals()) {
								stLocal.addGoalsAgainst(m.get()
										.getVisitingTeamGoals());
								stLocal.addGoalsFor(m.get().getLocalGoals());
								stLocal.addMatchWins();
								stLocal.addPoints(l.getPointsForWin());
								stLocal.addPosession(m.get()
										.getLocalPossesion());
								stVis.addGoalsAgainst(m.get().getLocalGoals());
								stVis.addGoalsFor(m.get()
										.getVisitingTeamGoals());
								stVis.addMatchLost();
								stVis.addPoints(l.getPointsForLost());
								stVis.addPosession(1 - m.get()
										.getLocalPossesion());
							} else if (m.get().getLocalGoals() < m.get()
									.getVisitingTeamGoals()) {
								stLocal.addGoalsAgainst(m.get()
										.getVisitingTeamGoals());
								stLocal.addGoalsFor(m.get().getLocalGoals());
								stLocal.addMatchLost();
								stLocal.addPoints(l.getPointsForLost());
								stLocal.addPosession(m.get()
										.getLocalPossesion());
								stVis.addGoalsAgainst(m.get().getLocalGoals());
								stVis.addGoalsFor(m.get()
										.getVisitingTeamGoals());
								stVis.addMatchWins();
								stVis.addPoints(l.getPointsForWin());
								stVis.addPosession(1 - m.get()
										.getLocalPossesion());
							} else {
								stLocal.addGoalsAgainst(m.get()
										.getVisitingTeamGoals());
								stLocal.addGoalsFor(m.get().getLocalGoals());
								stLocal.addMatchTied();
								stLocal.addPoints(l.getPointsForTied());
								stLocal.addPosession(m.get()
										.getLocalPossesion());
								stVis.addGoalsAgainst(m.get().getLocalGoals());
								stVis.addGoalsFor(m.get()
										.getVisitingTeamGoals());
								stVis.addMatchTied();
								stVis.addPoints(l.getPointsForTied());
								stVis.addPosession(1 - m.get()
										.getLocalPossesion());
							}
							stLocal = statisticsTeamDAO.save(stLocal);
							stVis = statisticsTeamDAO.save(stVis);

							clasification.addNumberMatchs();
						}
					}
				}
			}

			if (l.getExecutedMatchs() == l.getNumberMatchs()) {
				l.setState(AppLib.LEAGUE_FINISH);
			}

			bubbleSort(clasification.getClasification());

			clasificationDAO.save(clasification);
			leagueDAO.save(l);
		}

	}

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		doGet(req, res);
	}

	private void bubbleSort(List<Ref<StatisticsTeam>> array) {
		boolean swapped = true;
		int j = 0;
		Ref<StatisticsTeam> tmp;
		while (swapped) {
			swapped = false;
			j++;
			for (int i = 0; i < array.size() - j; i++) {
				if (array.get(i).get().getPoints() < array.get(i + 1).get()
						.getPoints()) {
					tmp = array.get(i);
					array.set(i, array.get(i + 1));
					array.set(i + 1, tmp);
					swapped = true;
				}
			}
		}
	}

	private void createClasification(League l) {
		Clasification c = new Clasification();
		for (int f = 0; f < l.getUsers().size(); f++) {
			User u = l.getUsers().get(f).get();
			StatisticsTeam st = new StatisticsTeam();
			st.setTeamName(u.getTactic().getTeamName());
			st.setTacticId(u.getTactic().getId());
			st = statisticsTeamDAO.save(st);
			c.getClasification().add(Ref.create(st));
		}
		c = clasificationDAO.save(c);
		l.setClasification(c);
		l = leagueDAO.save(l);
	}
}
