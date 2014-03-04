/**
 * 
 */
package org.javahispano.javaleague.server.domain;

import static org.javahispano.javaleague.server.domain.OfyService.ofy;

import java.util.List;

import org.javahispano.javaleague.shared.domain.FrameWork;

import com.googlecode.objectify.ObjectifyService;

/**
 * @author adou
 * 
 */
public class FrameWorkDAO {
	public FrameWorkDAO() {
		super();
	}

	public FrameWork save(FrameWork framework) {
		ofy().save().entity(framework).now();
		return framework;
	}

	public FrameWork findById(Long id) {
		return ofy().load().type(FrameWork.class).id(id).now();
	}

	public FrameWork findByDefaultFrameWork(Boolean defaultFrameWork) {
		FrameWork frameWork = ofy().load().type(FrameWork.class)
				.filter("defaultFrameWork", true).first().now();

		return frameWork;
	}

	public List<FrameWork> findAllFrameWorks() {
		List<FrameWork> frameWorks = ofy().load().type(FrameWork.class)
				.filter("active", true).order("defaultFrameWork")
				.order("-updated").list();

		return frameWorks;
	}
}
