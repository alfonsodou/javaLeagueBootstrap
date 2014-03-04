/**
 * 
 */
package org.javahispano.javaleague.server;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.javahispano.javaleague.client.service.FrameWorkService;
import org.javahispano.javaleague.shared.domain.FrameWork;
import org.javahispano.javaleague.shared.domain.FrameWorkDAO;

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
	private static final int NUM_RETRIES = 5;
	
	@Override
	public List<FrameWork> getFrameWorks() {
		FrameWorkDAO frameWorkDAO = new FrameWorkDAO();
		List<FrameWork> frameWorks = frameWorkDAO.findAllFrameWorks();
		List<FrameWork> frameWorksDTO = new ArrayList<FrameWork>();
		
		for(FrameWork f : frameWorks) {
			frameWorksDTO.add(f);
		}
		
		return frameWorksDTO;
	}

}
