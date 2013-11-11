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
public class SeasonDAO extends DAOBase {
	static {
		ObjectifyService.register(Season.class);
	}
	
	public SeasonDAO() {
		
	}
	
	public Season save(Season season) {
		ofy().put(season);
		return season;
	}
	
	public Season findById(Long id) {
		Key<Season> key = new Key<Season>(Season.class, id);
		return ofy().get(key);
	}	

	public Season findByUniqueId(String uniqueId) {
		Query<Season> q = ofy().query(Season.class).filter("uniqueId", uniqueId);
		
		return q.get();
	}
}
