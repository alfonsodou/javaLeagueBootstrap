package org.javahispano.javaleague.shared.domain;

import static org.javahispano.javaleague.shared.domain.OfyService.ofy;

import java.util.List;

import com.google.appengine.api.users.User;

public class PartidoStoreDAO {
	public PartidoStoreDAO() {
		super();
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
