/**
 * 
 */
package org.javahispano.javaleague.server;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import org.javahispano.javaleague.client.service.MatchService;
import org.javahispano.javaleague.server.domain.MatchDAO;
import org.javahispano.javaleague.shared.domain.Match;
import org.javahispano.javaleague.shared.domain.TacticUser;

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
		Queue queue = QueueFactory.getQueue("friendly");
		queue.add(TaskOptions.Builder.withUrl("/dispatchMatch").param(
				"tacticID", Long.toString(tacticId)));
	}

	@Override
	public List<Match> getMatchList(Long tacticId) {
		List<Match> matchList = null;

		matchList = matchDAO.findByTactic(tacticId);

		bubbleSort(matchList);

		return matchList;

	}

	private void bubbleSort(List<Match> array) {
		boolean swapped = true;
		int j = 0;
		Match tmp;
		while (swapped) {
			swapped = false;
			j++;
			for (int i = 0; i < array.size() - j; i++) {
				if (array.get(i).getVisualization()
						.compareTo(array.get(i + 1).getVisualization()) > 0) {
					tmp = array.get(i);
					array.set(i, array.get(i + 1));
					array.set(i + 1, tmp);
					swapped = true;
				}
			}
		}
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

	@Override
	public List<Match> getMatchListByState(int state) {
		List<Match> matchList = null;

		matchList = matchDAO.getMatchsState(state);

		return matchList;
	}

}
