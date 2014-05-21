/**
 * 
 */
package org.javahispano.javaleague.server.domain;

import org.javahispano.javaleague.shared.domain.StatisticsTeam;
import static org.javahispano.javaleague.server.domain.OfyService.ofy;

/**
 * @author adou
 *
 */
public class StatisticsTeamDAO {
	public StatisticsTeamDAO() {
		super();
	}
	
	public StatisticsTeam save(StatisticsTeam statisticsTeam) {
		ofy().save().entity(statisticsTeam).now();
		return statisticsTeam;
	}

	public void delete(Long id) {
		ofy().delete().type(StatisticsTeam.class).id(id).now();
	}
}
