/**
 * 
 */
package org.javahispano.javaleague.server.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.javahispano.javaleague.server.domain.ClasificationDAO;
import org.javahispano.javaleague.server.domain.LeagueDAO;
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

	private LeagueDAO leagueDAO = new LeagueDAO();
	private ClasificationDAO clasificationDAO = new ClasificationDAO();

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		List<League> leagues = leagueDAO
				.getLeaguesState(AppLib.LEAGUE_EXECUTION);

		for (League l : leagues) {
			if (l.getClasification() != null) {
				clasificationDAO.delete(l.getClasification().get().getId());
			}
			createClasification(l);

			Clasification clasification = l.getClasification().get();

			if (l.getExecutedMatchs() > l.getClasification().get()
					.getNumberMatchs()) {
				List<Ref<CalendarDate>> listCalendarDate = l.getMatchs();
				for (Ref<CalendarDate> cd : listCalendarDate) {
					for (Ref<Match> m : cd.get().getMatchs()) {
						if (m.get().getState() == AppLib.MATCH_OK) {
							StatisticsTeam stLocal = l.getClasification().get()
									.getClasification()
									.get(m.get().getLocalTeamId());
							StatisticsTeam stVis = l.getClasification().get()
									.getClasification()
									.get(m.get().getVisitingTeamId());
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
							clasification.getClasification().put(
									m.get().getLocalTeamId(), stLocal);
							clasification.getClasification().put(
									m.get().getVisitingTeamId(), stVis);
						}
					}
				}
			}

			if (l.getExecutedMatchs() == l.getNumberMatchs()) {
				l.setState(AppLib.LEAGUE_FINISH);
			}

			clasificationDAO.save(clasification);
			leagueDAO.save(l);
		}

	}

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		doGet(req, res);
	}

	private void createClasification(League l) {
		Clasification c = new Clasification();
		for (int f = 0; f < l.getUsers().size(); f++) {
			User u = l.getUsers().get(f).get();
			StatisticsTeam st = new StatisticsTeam();
			st.setTeamName(u.getTactic().getTeamName());
			c.getClasification().put(u.getTactic().getId(), st);
		}
		c = clasificationDAO.save(c);
		l.setClasification(c);
		l = leagueDAO.save(l);
	}
}