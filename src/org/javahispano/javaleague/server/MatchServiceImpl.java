/**
 * 
 */
package org.javahispano.javaleague.server;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.javahispano.javaleague.client.service.MatchService;
import org.javahispano.javaleague.server.domain.MatchDAO;
import org.javahispano.javaleague.shared.domain.Match;
import org.javahispano.javaleague.shared.domain.TacticUser;
import org.javahispano.javaleague.shared.domain.User;

import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * @author adou
 * 
 */
public class MatchServiceImpl extends RemoteServiceServlet implements
		MatchService {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static Logger logger = Logger.getLogger(MatchServiceImpl.class
			.getName());
	
	private static MatchDAO matchDAO = new MatchDAO();
	
	@Override
	public void dispatchMatch(Long tacticId) {
		Queue queue = QueueFactory.getDefaultQueue();
		queue.add(TaskOptions.Builder.withUrl("/dispatchMatch").param(
				"tacticID", Long.toString(tacticId)));
	}	
	
	@Override
	public List<Match> getMatchList(TacticUser tactic) {
		List<Match> matchList = null;

		matchList = matchDAO.findByTactic(tactic.getId());

		// Falta ordenar la lista devuelta por fecha

		return matchList;

	}


	@Override
	public void setMatchState(Match match, int state) {
		try {
			match.setState(state);
			matchDAO.save(match);
		} catch (Exception e) {
			logger.warning(e.getMessage());
		}

	}

	@Override
	public Match getMatchById(Long id) {
		Match match = null;
		try {
			match = matchDAO.findById(id);
		} catch (Exception e) {
			logger.warning(e.getMessage());
			return null;
		}

		return match;

	}

}
