/**
 * 
 */
package org.javahispano.javaleague.server.domain;

import static com.googlecode.objectify.ObjectifyService.ofy;

import com.googlecode.objectify.ObjectifyService;

/**
 * @author adou
 * 
 */
public class UserDAO {
	static {
		ObjectifyService.register(User.class);
	}

	public UserDAO() {

	}

	public User save(User user) {
		ofy().save().entity(user).now();
		return user;
	}

	public User findById(Long id) {
		return ofy().load().type(User.class).id(id).now();
	}

	public User findByEmail(String emailAddress) {
		return ofy().load().type(User.class)
				.filter("emailAddress =", emailAddress).first().now();
	}

	public User findByToken(String token) {
		return ofy().load().type(User.class).filter("tokenActivate =", token)
				.first().now();
	}
}
