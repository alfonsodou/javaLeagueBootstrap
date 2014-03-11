/**
 * 
 */
package org.javahispano.javaleague.server.domain;

import static org.javahispano.javaleague.server.domain.OfyService.ofy;

import java.util.ArrayList;
import java.util.List;

import org.javahispano.javaleague.shared.domain.League;

import com.googlecode.objectify.Ref;

/**
 * @author adou
 * 
 */
public class LeagueDAO {
	public LeagueDAO() {
		super();
	}

	public League save(League league) {
		ofy().save().entity(league).now();
		return league;
	}

	public League findById(Long id) {
		return ofy().load().type(League.class).id(id).now();
	}

	public List<League> findByUser(Long userId) {
		List<League> leagues = ofy().load().type(League.class)
				.filter("managerId", userId).order("-updated").list();

		return leagues;
	}

	public void delete(Long id) {
		ofy().delete().type(League.class).id(id).now();
	}
	
	public List<Ref<League>> getAllLeagues() {
		List<League> leagues = ofy().load().type(League.class).list();
		List<Ref<League>> refLeagues = new ArrayList<Ref<League>>();
		
		for(League l : leagues) {
			refLeagues.add(Ref.create(l));
		}
		
		return refLeagues;
	}
}
