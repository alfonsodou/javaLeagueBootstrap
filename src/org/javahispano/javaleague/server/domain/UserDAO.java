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
public class UserDAO extends DAOBase {
	static {
		ObjectifyService.register(User.class);
	}
	
	public UserDAO() {
		
	}
	
	public User save(User user) {
		ofy().put(user);
		return user;
	}
	
	public User findById(Long id) {
		Key<User> key = new Key<User>(User.class, id);
		return ofy().get(key);
	}	

	public User findByUniqueId(String uniqueId) {
		Query<User> q = ofy().query(User.class).filter("uniqueId", uniqueId);
		
		return q.get();
	}
	
	public User findByEmail(String emailAddress) {
		Query<User> q = ofy().query(User.class).filter("emailAddress", emailAddress);
		
		return q.get();
	}

	public User findByToken(String token) {
		Query<User> q = ofy().query(User.class).filter("tokenActivate", token);
		
		return q.get();
	}
}
