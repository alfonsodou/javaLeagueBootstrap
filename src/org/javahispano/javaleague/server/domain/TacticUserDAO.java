/**
 * 
 */
package org.javahispano.javaleague.server.domain;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.util.List;

import com.googlecode.objectify.ObjectifyService;

/**
 * @author adou
 *
 */
public class TacticUserDAO {
	static {
		ObjectifyService.register(TacticUser.class);
	}
	
	public TacticUserDAO() {
		
	}
	
	public TacticUser save(TacticUser tacticUser) {
		ofy().save().entity(tacticUser).now();
		return tacticUser;
	}
	
	public TacticUser findById(Long id) {
		return ofy().load().type(TacticUser.class).id(id).now();
	}	
	
	public List<TacticUser> getTactics(Long id) {		
		return ofy().load().type(TacticUser.class).filter("id !=", id).list();
	}

}
