/**
 * 
 */
package org.javahispano.javaleague.server.domain;

import static org.javahispano.javaleague.server.domain.OfyService.ofy;

import java.util.List;

import org.javahispano.javaleague.shared.domain.TacticUser;

/**
 * @author adou
 *
 */
public class TacticUserDAO {
	public TacticUserDAO() {
		super();
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
