package org.javahispano.javaleague.server.domain;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.util.List;

import com.google.appengine.api.users.User;
import com.googlecode.objectify.ObjectifyService;

public class PartidoStoreDAO {

	static {
		ObjectifyService.register(PartidoStore.class);
	}

	public PartidoStore save(PartidoStore partido) {
		ofy().save().entity(partido).now();
		return partido;
	}

	public PartidoStore findById(Long id) {
		return ofy().load().type(PartidoStore.class).id(id).now();
	}

	public List<PartidoStore> findByUser(User owner) {
		return ofy().load().type(PartidoStore.class).filter("owner", owner)
				.list();
	}
}
