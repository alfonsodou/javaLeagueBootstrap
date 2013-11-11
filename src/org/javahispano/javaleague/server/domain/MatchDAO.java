/**
 * 
 */
package org.javahispano.javaleague.server.domain;

import java.util.ArrayList;
import java.util.List;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.Query;
import com.googlecode.objectify.util.DAOBase;

/**
 * @author adou
 * 
 */
public class MatchDAO extends DAOBase {
	static {
		ObjectifyService.register(Match.class);
	}

	public MatchDAO() {

	}

	public Match save(Match partido) {
		ofy().put(partido);
		return partido;
	}

	public Match findById(Long id) {
		Key<Match> key = new Key<Match>(Match.class, id);
		return ofy().get(key);
	}

	public List<Match> findByTactic(String id) {
		List<Match> list = new ArrayList<Match>();

		getMatchs(id, list, "localTeam");
		getMatchs(id, list, "visitingTeam");

		return list;
	}

	private void getMatchs(String id, List<Match> partidos, String field) {
		Query<Match> q = ofy().query(Match.class).filter(field, id);

		for (Match fetched : q) {
			partidos.add(fetched);
		}
	}
}
