/**
 * 
 */
package org.javahispano.javaleague.shared.domain;

import static org.javahispano.javaleague.shared.domain.OfyService.ofy;

/**
 * @author adou
 * 
 */
public class UserDAO {
	public UserDAO() {
		super();
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
				.filter("emailAddress", emailAddress).first().now();
	}

	public User findByToken(String token) {
		return ofy().load().type(User.class).filter("tokenActivate", token)
				.first().now();
	}
}
