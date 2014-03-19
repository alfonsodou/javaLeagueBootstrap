/**
 * 
 */
package org.javahispano.javaleague.server.domain;

import static org.javahispano.javaleague.server.domain.OfyService.ofy;

import org.javahispano.javaleague.shared.domain.MatchByteBin;

/**
 * @author adou
 *
 */
public class MatchByteBinDAO {
	
	public MatchByteBinDAO() {
		super();
	}
	
	public MatchByteBin save(MatchByteBin matchByteBin) {
		ofy().save().entity(matchByteBin).now();
		
		return matchByteBin;
	}
	
	public MatchByteBin findById(Long id) {
		return ofy().load().type(MatchByteBin.class).id(id).now();
	}
}
