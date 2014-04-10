/**
 * 
 */
package org.javahispano.javaleague.server;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.javahispano.javaleague.client.service.FrameWorkService;
import org.javahispano.javaleague.server.domain.FrameWorkDAO;
import org.javahispano.javaleague.shared.AppLib;
import org.javahispano.javaleague.shared.domain.FrameWork;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * @author adou
 * 
 */
public class FrameWorkServiceImpl extends RemoteServiceServlet implements
		FrameWorkService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(FrameWorkServiceImpl.class
			.getName());

	@Override
	public List<FrameWork> getFrameWorks() {
		List<FrameWork> frameWorks;
		try {
			FrameWorkDAO frameWorkDAO = new FrameWorkDAO();
			frameWorks = frameWorkDAO.findAllFrameWorks();
			logger.log(Level.INFO, "FrameWorkServiceImpl: getFrameWorks OK!");
		} catch (Exception e) {
			frameWorks = null;
			logger.warning("FrameWorkServiceImpl: getFrameWorks error :: "
					+ e.getMessage());
		}

		return frameWorks;
	}

	@Override
	public FrameWork getDefaultFrameWork() {
		FrameWork frameWork;
		try {
			FrameWorkDAO frameWorkDAO = new FrameWorkDAO();
			//frameWork = frameWorkDAO.findDefaultFrameWork();
			frameWork = frameWorkDAO.findById(AppLib.DEFAULT_FRAMEWORK_ID);
			logger.warning("FrameWorkServiceImpl: getDefaultFrameWork OK!");
		} catch (Exception e) {
			frameWork = null;
			logger.warning("FrameWorkServiceImpl: getDefaultFrameWork error :: "
					+ e.getMessage());
		}

		return frameWork;
	}

}
