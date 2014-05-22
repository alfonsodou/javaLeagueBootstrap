/**
 * 
 */
package org.javahispano.javaleague.server.domain;

import static org.javahispano.javaleague.server.domain.OfyService.ofy;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.javahispano.javaleague.shared.domain.LeagueSummary;

import com.googlecode.objectify.Ref;

/**
 * @author adou
 * 
 */
public class LeagueSummaryDAO {
	public LeagueSummaryDAO() {
		super();
	}

	public LeagueSummary save(LeagueSummary leagueSummary) {
		leagueSummary.setUpdated(new Date());
		ofy().save().entity(leagueSummary).now();
		return leagueSummary;
	}

	public LeagueSummary findById(Long id) {
		return ofy().load().type(LeagueSummary.class).id(id).now();
	}

	public List<LeagueSummary> findByLeague(Long leagueId) {
		List<LeagueSummary> leaguesSummary = ofy().load()
				.type(LeagueSummary.class).filter("leagueId", leagueId)
				.order("-updated").list();

		return leaguesSummary;
	}

	public void delete(Long id) {
		ofy().delete().type(LeagueSummary.class).id(id).now();
	}

	public List<Ref<LeagueSummary>> getAllLeaguesSummary() {
		List<LeagueSummary> leaguesSummary = ofy().load()
				.type(LeagueSummary.class).list();
		List<Ref<LeagueSummary>> refLeaguesSummary = new ArrayList<Ref<LeagueSummary>>();

		for (LeagueSummary ls : leaguesSummary) {
			refLeaguesSummary.add(Ref.create(ls));
		}

		return refLeaguesSummary;
	}

	public List<Ref<LeagueSummary>> getManagerLeaguesSummary(Long managerId) {
		List<LeagueSummary> leaguesSummary = ofy().load()
				.type(LeagueSummary.class).filter("managerId", managerId)
				.order("-updated").list();
		List<Ref<LeagueSummary>> refLeaguesSummary = new ArrayList<Ref<LeagueSummary>>();

		for (LeagueSummary ls : leaguesSummary) {
			refLeaguesSummary.add(Ref.create(ls));
		}

		return refLeaguesSummary;
	}
	

}
