/**
 * 
 */
package org.javahispano.javaleague.server.domain;

import static org.javahispano.javaleague.server.domain.OfyService.ofy;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.javahispano.javaleague.shared.AppLib;
import org.javahispano.javaleague.shared.domain.Match;

/**
 * @author adou
 * 
 */
public class MatchDAO {
	public MatchDAO() {
		super();
	}

	public Match save(Match partido) {
		partido.setUpdated(new Date());
		ofy().save().entity(partido).now();
		return partido;
	}

	public Match findById(Long id) {
		return ofy().load().type(Match.class).id(id).now();
	}

	public List<Match> findByTactic(Long id) {
		List<Match> list = new ArrayList<Match>();

		getMatchs(id, list, "localTeam");
		getMatchs(id, list, "visitingTeam");

		return list;
	}

	private void getMatchs(Long id, List<Match> partidos, String field) {
		List<Match> m = ofy().load().type(Match.class).filter(field, id).list();

		for (Match fetched : m) {
			partidos.add(fetched);
		}
	}

	public List<Match> getMatchsDate(Date date) {
		List<Match> m = ofy().load().type(Match.class)
				.filter("state", AppLib.MATCH_SCHEDULED)
				.filter("execution <", date).list();

		return m;
	}

	public List<Match> getMatchsState(int state) {
		return ofy().load().type(Match.class).filter("state", state).list();
	}

}
