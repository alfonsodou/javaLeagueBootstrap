/**
 * 
 */
package org.javahispano.javaleague.server.domain;

import static org.javahispano.javaleague.server.domain.OfyService.ofy;

import org.javahispano.javaleague.shared.domain.MatchByte;

/**
 * @author adou
 *
 */
public class MatchByteDAO {
	
	public MatchByteDAO() {
		super();
	}
	
	public MatchByte save(MatchByte matchByte) {
		ofy().save().entity(matchByte).now();
		
		return matchByte;
	}
	
	public MatchByte findById(Long id) {
		return ofy().load().type(MatchByte.class).id(id).now();
	}
}
