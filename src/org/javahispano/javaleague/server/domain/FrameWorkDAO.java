/**
 * 
 */
package org.javahispano.javaleague.server.domain;

import java.util.ArrayList;
import java.util.List;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyService;

/**
 * @author adou
 * 
 */
public class FrameWorkDAO extends DAOBase {
	static {
		ObjectifyService.register(FrameWork.class);
	}

	public FrameWorkDAO() {

	}

	public FrameWork save(FrameWork framework) {
		ofy().put(framework);
		return framework;
	}

	public FrameWork findById(Long id) {
		Key<FrameWork> key = new Key<FrameWork>(FrameWork.class, id);
		return ofy().get(key);
	}

	public FrameWork findByDefaultFrameWork(Boolean defaultFrameWork) {
		Query<FrameWork> q = ofy().query(FrameWork.class).filter(
				"defaultFrameWork", defaultFrameWork);

		return q.get();
	}

	public List<FrameWork> findAllFrameWorks() {
		List<FrameWork> frameWorks = new ArrayList<FrameWork>();

		Query<FrameWork> q = ofy().query(FrameWork.class)
				.filter("active", true).order("defaultFrameWork")
				.order("-updated");

		for (FrameWork fetched : q) {
			frameWorks.add(fetched);
		}

		return frameWorks;
	}
}
