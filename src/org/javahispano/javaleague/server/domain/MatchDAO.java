/**
 * 
 */
package org.javahispano.javaleague.server.domain;

import static org.javahispano.javaleague.server.domain.OfyService.ofy;

import java.util.ArrayList;
import java.util.List;

/**
 * @author adou
 * 
 */
public class MatchDAO {
	public MatchDAO() {
		super();
	}

	public Match save(Match partido) {
		ofy().save().entity(partido).now();
		return partido;
	}

	public Match findById(Long id) {
		return ofy().load().type(Match.class).id(id).now();
	}

	public List<Match> findByTactic(String id) {
		List<Match> list = new ArrayList<Match>();

		getMatchs(id, list, "localTeam");
		getMatchs(id, list, "visitingTeam");

		return list;
	}

	private void getMatchs(String id, List<Match> partidos, String field) {
		List<Match> m = ofy().load().type(Match.class).filter(field, id).list();

		for (Match fetched : m) {
			partidos.add(fetched);
		}
	}
}
