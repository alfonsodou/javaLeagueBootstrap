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
public class LeagueDAO {
	static {
		ObjectifyService.register(League.class);
	}

	public LeagueDAO() {

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
				.filter("managerId =", userId).order("-updated").list();

		return leagues;
	}

	public void delete(Long id) {
		ofy().delete().type(League.class).id(id).now();
	}
}
