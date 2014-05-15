/**
 * 
 */
package org.javahispano.javaleague.server.domain;

import static org.javahispano.javaleague.server.domain.OfyService.ofy;

import java.util.Date;

import org.javahispano.javaleague.shared.domain.Clasification;

/**
 * @author adou
 *
 */
public class ClasificationDAO {
	public ClasificationDAO() {
		super();
	}
	
	public Clasification save(Clasification clasification) {
		clasification.setUpdated(new Date());
		ofy().save().entity(clasification).now();
		return clasification;
	}
	
	public Clasification findById(Long id) {
		return ofy().load().type(Clasification.class).id(id).now();
	}
	
	public void delete(Long id) {
		ofy().delete().type(Clasification.class).id(id).now();
	}

}
