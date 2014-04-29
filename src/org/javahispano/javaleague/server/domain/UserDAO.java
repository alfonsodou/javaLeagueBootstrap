/**
 * 
 */
package org.javahispano.javaleague.server.domain;

import static org.javahispano.javaleague.server.domain.OfyService.ofy;

import java.util.Date;

import org.javahispano.javaleague.shared.domain.User;

/**
 * @author adou
 * 
 */
public class UserDAO {
	public UserDAO() {
		super();
	}

	public User save(User user) {
		user.setLastActive(new Date());
		ofy().save().entity(user).now();
		return user;
	}

	public User findById(Long id) {
		return ofy().load().type(User.class).id(id).now();
	}

	public User findByEmail(String emailAddress) {
		return ofy().load().type(User.class)
				.filter("emailAddress", emailAddress).first().now();
	}

	public User findByToken(String token) {
		return ofy().load().type(User.class).filter("tokenActivate", token)
				.first().now();
	}
}
