/**
 * 
 */
package org.javahispano.javaleague.server;

import java.util.Date;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;

import org.javahispano.javaleague.client.service.LeagueService;
import org.javahispano.javaleague.server.domain.League;
import org.javahispano.javaleague.server.domain.LeagueDAO;
import org.javahispano.javaleague.server.domain.User;
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

	public LeagueServiceImpl() {

	}

	@Override
	public LeagueDTO createLeague(LeagueDTO leagueDTO) {
		PersistenceManager pm = PMF.getTxnPm();
		League league = new League(leagueDTO);
		
		try {
			pm.currentTransaction().begin();
			User user = LoginHelper.getLoggedInUser(getThreadLocalRequest()
					.getSession(), pm);
			league.setManagerId(user.getId());
			leagueDAO.save(league);
			pm.currentTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			logger.warning(e.getMessage());
		} finally {
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
				logger.warning("did transaction rollback");
			}
			pm.close();
		}
		
		return league.toDTO();
	}

}
