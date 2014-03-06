/**
 * 
 */
package org.javahispano.javaleague.server;

import java.util.List;
import java.util.logging.Logger;

import org.javahispano.javaleague.client.service.LeagueService;
import org.javahispano.javaleague.server.domain.LeagueDAO;
import org.javahispano.javaleague.server.domain.UserDAO;
import org.javahispano.javaleague.shared.domain.League;
import org.javahispano.javaleague.shared.domain.User;

import com.google.common.collect.Lists;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.googlecode.objectify.Ref;

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
			league.setNameManager(user.getName());
			league = leagueDAO.save(league);
			user.addLeague(league);
			user = userDAO.save(user);
		} catch (Exception e) {
			e.printStackTrace();
			logger.warning(e.getMessage());
		}

		return league;
	}

	@Override
	public List<Ref<League>> getMyLeagues() {
		List<Ref<League>> leagues = null;

		try {
			User user = LoginHelper.getLoggedInUser(getThreadLocalRequest()
					.getSession());
			// leagues = leagueDAO.findByUser(user.getId());
			leagues = Lists.newArrayList(user.getLeagues());

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
			League l = leagueDAO.findById(id);

			// Borramos la liga de la lista de ligas del manager
			User user = LoginHelper.getLoggedInUser(getThreadLocalRequest()
					.getSession());
			user.deleteLeague(l);
			userDAO.save(user);

			List<Ref<User>> users = l.getUsers();
			if (users != null) {
				for (Ref<User> u : users) {
					u.get().deleteLeague(l);
					userDAO.save(u.get());
				}
			}
			leagueDAO.delete(id);
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
			League l = leagueDAO.findById(id);
			if (l.getManagerId() != user.getId()) {
				user.addLeague(l);
				userDAO.save(user);
			}
			l.addUser(user);
			leagueDAO.save(l);
		} catch (Exception e) {
			e.printStackTrace();
			logger.warning(e.getMessage());
		}

	}

}
