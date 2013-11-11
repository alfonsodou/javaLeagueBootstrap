/**
 * 
 */
package org.javahispano.javaleague.server.domain;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.Query;
import com.googlecode.objectify.util.DAOBase;

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
		Query<FrameWork> q = ofy().query(FrameWork.class).filter("defaultFrameWork", defaultFrameWork);
		
		return q.get();
	}	
}
