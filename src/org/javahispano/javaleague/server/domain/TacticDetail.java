package org.javahispano.javaleague.server.domain;

import java.io.Serializable;

import javax.jdo.annotations.Extension;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import javax.jdo.listener.StoreCallback;

import org.javahispano.javaleague.server.utils.cache.Cacheable;

/**
 * 
 * @author adou
 *
 */
public class TacticDetail implements StoreCallback, 
										Serializable, 
										Cacheable {

	/**
	 * 
	 */
	private static final int CACHE_EXPIR = 600;  // in seconds
	
	/**
	 * 
	 */
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	@Extension(vendorName = "datanucleus", 
				key = "gae.encoded-pk", 
				value = "true")
	private String id;
	
	/**
	 * Tactic's size in bytes
	 */
	@Persistent
	private int size;
	
	@Override
	public void addToCache() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeFromCache() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void jdoPreStore() {
		// TODO Auto-generated method stub
		
	}

}
