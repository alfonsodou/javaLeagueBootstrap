/**
 * 
 */
package org.javahispano.javaleague.server;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;

import org.javahispano.javaleague.client.service.MatchService;
import org.javahispano.javaleague.server.domain.Match;
import org.javahispano.javaleague.server.domain.MatchDAO;
import org.javahispano.javaleague.server.domain.User;
import org.javahispano.javaleague.shared.MatchDTO;
import org.javahispano.javaleague.shared.TacticDTO;

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
	private static Logger logger = Logger.getLogger(MatchServiceImpl.class
			.getName());
	private static final int NUM_RETRIES = 5;

	@Override
	public void dispatchMatch() {
		Queue queue = QueueFactory.getDefaultQueue();
		queue.add(TaskOptions.Builder.withUrl("/dispatchMatch").param(
				"tacticID", getUserTacticSummary().getId()));
	}

	@Override
	public void dispatchMatch(String tacticId) {
		Queue queue = QueueFactory.getDefaultQueue();
		queue.add(TaskOptions.Builder.withUrl("/dispatchMatch").param(
				"tacticID", tacticId));
	}	
	
	@Override
	public List<MatchDTO> getMatchList(TacticDTO tacticDTO) {
		List<Match> matchList = null;
		List<MatchDTO> matchDTOList = new ArrayList<MatchDTO>();
		MatchDAO matchDAO = new MatchDAO();

		matchList = matchDAO.findByTactic(tacticDTO.getId());

		for (Match m : matchList) {
			matchDTOList.add(m.toDTO());
		}
		// Falta ordenar la lista devuelta por fecha

		return matchDTOList;

	}

	@Override
	public List<MatchDTO> getMatchList() {
		TacticDTO tacticDTO = getUserTacticSummary(); 
		List<Match> matchList = null;
		List<MatchDTO> matchDTOList = new ArrayList<MatchDTO>();
		MatchDAO matchDAO = new MatchDAO();

		matchList = matchDAO.findByTactic(tacticDTO.getId());

		for (Match m : matchList) {
			matchDTOList.add(m.toDTO());
		}
		// Falta ordenar la lista devuelta por fecha

		return matchDTOList;

	}
	

	@Override
	public void setMatchState(MatchDTO matchDTO, int state) {
		MatchDAO matchDAO = new MatchDAO();
		try {
			Match match = matchDAO.findById(matchDTO.getId());
			match.setState(state);
		} catch (Exception e) {
			logger.warning(e.getMessage());
		}

	}

	@Override
	public MatchDTO getMatchById(Long id) {
		MatchDAO matchDAO = new MatchDAO();
		Match match = null;
		try {
			match = matchDAO.findById(id);
		} catch (Exception e) {
			logger.warning(e.getMessage());
			return null;
		}

		return match.toDTO();

	}
	
	public TacticDTO getUserTacticSummary() {

		TacticDTO userTacticSummary = new TacticDTO();

		try {
			User user = LoginHelper.getLoggedInUser(
					getThreadLocalRequest().getSession());
			if (user == null)
				return null;

			//if (user.getTactic() == null)
				//return null;

			//userTacticSummary = user.getTactic().toDTO();
		} catch (Exception e) {
			logger.warning(e.getMessage());
			return null;
		}

		return userTacticSummary;
	}	

}
