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
public class LeagueDAO extends DAOBase {
	static {
		ObjectifyService.register(League.class);
	}

	public LeagueDAO() {

	}

	public League save(League league) {
		ofy().put(league);
		return league;
	}

	public League findById(Long id) {
		Key<League> key = new Key<League>(League.class, id);
		return ofy().get(key);
	}

	public List<League> findByUser(Long userId) {
		List<League> leagues = new ArrayList<League>();

		Query<League> q = ofy().query(League.class).filter("managerId", userId)
				.order("-updated");
		
		for(League l : q) {
			leagues.add(l);
		}
		
		return leagues;
	}
	
	public void delete(Long id) {
		ofy().delete(new Key<League>(League.class, id));
	}
}
