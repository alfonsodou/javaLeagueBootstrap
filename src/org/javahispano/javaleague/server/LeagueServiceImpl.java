/**
 * 
 */
package org.javahispano.javaleague.server;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.javahispano.javaleague.client.service.LeagueService;
import org.javahispano.javaleague.server.domain.League;
import org.javahispano.javaleague.server.domain.LeagueDAO;
import org.javahispano.javaleague.server.domain.User;
import org.javahispano.javaleague.server.domain.UserDAO;
import org.javahispano.javaleague.shared.LeagueDTO;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * @author adou
 * 
 */
public class LeagueServiceImpl extends RemoteServiceServlet implements
		LeagueService {

	private static Logger logger = Logger.getLogger(TacticServiceImpl.class
			.getName());

	private static LeagueDAO leagueDAO = new LeagueDAO();
	private static UserDAO userDAO = new UserDAO();

	public LeagueServiceImpl() {

	}

	@Override
	public LeagueDTO createLeague(LeagueDTO leagueDTO) {
		League league = new League(leagueDTO);

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

		return league.toDTO();
	}

	@Override
	public List<LeagueDTO> getMyLeagues() {
		List<LeagueDTO> leaguesDTO = new ArrayList<LeagueDTO>();
		List<League> leagues;
		LeagueDTO leagueDTO;

		try {
			User user = LoginHelper.getLoggedInUser(getThreadLocalRequest()
					.getSession());
			leagues = leagueDAO.findByUser(user.getId());

			for (League l : leagues) {
				leagueDTO = l.toDTO();
				user = userDAO.findById(l.getManagerId());
				leagueDTO.setNameManager(user.getName());
				leaguesDTO.add(leagueDTO);
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.warning(e.getMessage());
		}

		return leaguesDTO;
	}

	@Override
	public LeagueDTO getLeague(Long id) {
		LeagueDTO leagueDTO = null;

		try {
			leagueDTO = leagueDAO.findById(id).toDTO();
		} catch (Exception e) {
			e.printStackTrace();
			logger.warning(e.getMessage());
		}

		return leagueDTO;
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
