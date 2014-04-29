/**
 * 
 */
package org.javahispano.javaleague.server.domain;

import org.javahispano.javaleague.shared.domain.CalendarDate;
import org.javahispano.javaleague.shared.domain.FrameWork;
import org.javahispano.javaleague.shared.domain.League;
import org.javahispano.javaleague.shared.domain.LeagueSummary;
import org.javahispano.javaleague.shared.domain.Match;
import org.javahispano.javaleague.shared.domain.TacticUser;
import org.javahispano.javaleague.shared.domain.User;

import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyFactory;
import com.googlecode.objectify.ObjectifyService;

/**
 * @author adou
 * 
 */
public class OfyService {
	static {
		factory().register(CalendarDate.class);
		factory().register(FrameWork.class);
		factory().register(League.class);
		factory().register(Match.class);
		factory().register(TacticUser.class);
		factory().register(User.class);
		factory().register(LeagueSummary.class);
	}

	public static Objectify ofy() {
		return ObjectifyService.ofy();
	}

	public static ObjectifyFactory factory() {
		return ObjectifyService.factory();
	}
}
