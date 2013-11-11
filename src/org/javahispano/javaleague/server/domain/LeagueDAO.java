/**
 * 
 */
package org.javahispano.javaleague.server.domain;

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

	public League findByUniqueId(String uniqueId) {
		Query<League> q = ofy().query(League.class).filter("uniqueId", uniqueId);
		
		return q.get();
	}
}
