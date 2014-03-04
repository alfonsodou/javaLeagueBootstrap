/**
 * 
 */
package org.javahispano.javaleague.server;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.javahispano.javaleague.client.service.LeagueService;
import org.javahispano.javaleague.shared.domain.League;
import org.javahispano.javaleague.shared.domain.LeagueDAO;
import org.javahispano.javaleague.shared.domain.User;
import org.javahispano.javaleague.shared.domain.UserDAO;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * @author adou
 * 
 */
public class LeagueServiceImpl extends RemoteServiceServlet implements
		LeagueService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static Logger logger = Logger.getLogger(TacticServiceImpl.class
			.getName());

	private static LeagueDAO leagueDAO = new LeagueDAO();
	private static UserDAO userDAO = new UserDAO();

	public LeagueServiceImpl() {

	}

	@Override
	public League createLeague(League league) {
		try {
			User user = LoginHelper.getLoggedInUser(getThreadLocalRequest()
					.getSession());
			league.setManagerId(user.getId());
			league = leagueDAO.save(league);
			user.getLeagues().add(league.getId());
			user = userDAO.save(user);
		} catch (Exception e) {
			e.printStackTrace();
			logger.warning(e.getMessage());
		}

		return league;
	}

	@Override
	public List<League> getMyLeagues() {
		List<League> leagues = null;

		try {
			User user = LoginHelper.getLoggedInUser(getThreadLocalRequest()
					.getSession());
			leagues = leagueDAO.findByUser(user.getId());


		} catch (Exception e) {
			e.printStackTrace();
			logger.warning(e.getMessage());
		}

		return leagues;
	}

	@Override
	public League getLeague(Long id) {
		League league = null;

		try {
			league = leagueDAO.findById(id);
		} catch (Exception e) {
			e.printStackTrace();
			logger.warning(e.getMessage());
		}

		return league;
	}

	@Override
	public void dropLeague(Long id) {
		try {
			User user = LoginHelper.getLoggedInUser(getThreadLocalRequest()
					.getSession());

			leagueDAO.delete(id);
			user.getLeagues().remove(id);
			userDAO.save(user);
		} catch (Exception e) {
			e.printStackTrace();
			logger.warning(e.getMessage());
		}
	}

	@Override
	public void joinLeague(Long id) {
		try {
			User user = LoginHelper.getLoggedInUser(getThreadLocalRequest()
					.getSession());

			user.getLeagues().add(id);
			userDAO.save(user);
		} catch (Exception e) {
			e.printStackTrace();
			logger.warning(e.getMessage());
		} 

	}

}
