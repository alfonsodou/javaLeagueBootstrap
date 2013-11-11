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
public class TacticUserDAO extends DAOBase {
	static {
		ObjectifyService.register(TacticUser.class);
	}
	
	public TacticUserDAO() {
		
	}
	
	public TacticUser save(TacticUser tacticUser) {
		ofy().put(tacticUser);
		return tacticUser;
	}
	
	public TacticUser findById(Long id) {
		Key<TacticUser> key = new Key<TacticUser>(TacticUser.class, id);
		return ofy().get(key);
	}	
	
	public List<TacticUser> getTactics(String id) {
		List<TacticUser> list = new ArrayList<TacticUser>();
		Query<TacticUser> q = ofy().query(TacticUser.class);
	
		for (TacticUser fetched : q) {
			list.add(fetched);
		}		
		
		return list;
	}

}
