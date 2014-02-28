package org.javahispano.javaleague.server.domain;

import java.util.ArrayList;

import com.google.appengine.api.users.User;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyService;


public class PartidoStoreDAO extends DAOBase {
   
	static {
        ObjectifyService.register(PartidoStore.class);
    }

    public PartidoStore save(PartidoStore partido) {
        ofy().put(partido);
        return partido;
    }
    
    public PartidoStore findById(Long id) {
        Key<PartidoStore> key = new Key<PartidoStore>(PartidoStore.class, id);
        return ofy().get(key);
    }
    
    public ArrayList<PartidoStore> findByUser(User owner) {
    	Query<PartidoStore> q = ofy().query(PartidoStore.class).filter("owner", owner);
    	
    	ArrayList<PartidoStore> partidos = new ArrayList<PartidoStore>();
    	
    	for(PartidoStore fetched : q) {
    		partidos.add(fetched);
    	}
    	
    	return partidos;
    }
}
