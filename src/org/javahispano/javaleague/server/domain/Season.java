package org.javahispano.javaleague.server.domain;

import java.io.Serializable;
import java.util.Date;

import javax.jdo.listener.StoreCallback;
import javax.persistence.Id;

import org.javahispano.javaleague.server.utils.cache.CacheSupport;
import org.javahispano.javaleague.server.utils.cache.Cacheable;

/**
 * 
 * @author adou
 *
 */
public class Season implements StoreCallback, Serializable, Cacheable {
	/**
	 * 
	 */
	private static final int CACHE_EXPIR = 600;  // in seconds
	  
	@Id
	private Long id;

	private Long leagueId;
	
	private Date creation;
	
	private Date updated;
	
	private Date start;
	
	private Date finish;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getLeague() {
		return leagueId;
	}

	public void setLeague(Long league) {
		this.leagueId = league;
	}
	
	

	/**
	 * @return the leagueId
	 */
	public Long getLeagueId() {
		return leagueId;
	}

	/**
	 * @param leagueId the leagueId to set
	 */
	public void setLeagueId(Long leagueId) {
		this.leagueId = leagueId;
	}

	/**
	 * @return the creation
	 */
	public Date getCreation() {
		return creation;
	}

	/**
	 * @param creation the creation to set
	 */
	public void setCreation(Date creation) {
		this.creation = creation;
	}

	/**
	 * @return the updated
	 */
	public Date getUpdated() {
		return updated;
	}

	/**
	 * @param updated the updated to set
	 */
	public void setUpdated(Date updated) {
		this.updated = updated;
	}

	/**
	 * @return the start
	 */
	public Date getStart() {
		return start;
	}

	/**
	 * @param start the start to set
	 */
	public void setStart(Date start) {
		this.start = start;
	}

	/**
	 * @return the finish
	 */
	public Date getFinish() {
		return finish;
	}

	/**
	 * @param finish the finish to set
	 */
	public void setFinish(Date finish) {
		this.finish = finish;
	}

	@Override
	public void addToCache() {
		// TODO Auto-generated method stub
		getLeague();
		
		CacheSupport.cachePutExp(this.getClass().getName(), 
								id, 
								this, 
								CACHE_EXPIR);		
	}

	@Override
	public void removeFromCache() {
		// TODO Auto-generated method stub
		CacheSupport.cacheDelete(this.getClass().getName(), 
								id);				
	}

	@Override
	public void jdoPreStore() {
		// TODO Auto-generated method stub
		
	}

}
