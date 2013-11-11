package org.javahispano.javaleague.server.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.jdo.listener.StoreCallback;
import javax.persistence.Id;

import org.javahispano.javaleague.server.utils.cache.CacheSupport;
import org.javahispano.javaleague.server.utils.cache.Cacheable;
import org.javahispano.javaleague.shared.LeagueDTO;

/**
 * 
 * @author adou
 *
 */
public class League implements StoreCallback, Serializable, Cacheable {
	/**
	 * 
	 */
	private static final int CACHE_EXPIR = 600;  // in seconds
	private static final Logger log = Logger.getLogger(League.class.getName());
	  
	@Id
	private Long id;
	
	private Long managerId;
	
	private String name;
	
	private String description;
	
	private List<Long> users;
	
	private List<Long> matchs;
	
	private Date creation;
	
	private Date updated;
	
	private Long activeSeason;
	
	public League() {
		
	}
	
	public League(LeagueDTO leagueDTO) {
		//this.id = Long.parseLong(leagueDTO.getId());
		this.name = leagueDTO.getLeagueName();
		this.creation = new Date();
		this.updated = new Date();
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getManager() {
		return managerId;
	}

	public void setManager(Long manager) {
		this.managerId = manager;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	
	
	/**
	 * @return the managerId
	 */
	public Long getManagerId() {
		return managerId;
	}

	/**
	 * @param managerId the managerId to set
	 */
	public void setManagerId(Long managerId) {
		this.managerId = managerId;
	}

	/**
	 * @return the users
	 */
	public List<Long> getUsers() {
		return users;
	}

	/**
	 * @param users the users to set
	 */
	public void setUsers(List<Long> users) {
		this.users = users;
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
	 * @return the activeSeason
	 */
	public Long getActiveSeason() {
		return activeSeason;
	}

	/**
	 * @param activeSeason the activeSeason to set
	 */
	public void setActiveSeason(Long activeSeason) {
		this.activeSeason = activeSeason;
	}		

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the matchs
	 */
	public List<Long> getMatchs() {
		return matchs;
	}

	/**
	 * @param matchs the matchs to set
	 */
	public void setMatchs(List<Long> matchs) {
		this.matchs = matchs;
	}

	@Override
	public void addToCache() {
		
		getManager();
		
		CacheSupport.cachePutExp(this.getClass().getName(), 
								id, 
								this, 
								CACHE_EXPIR);				
	}

	@Override
	public void removeFromCache() {
		
		CacheSupport.cacheDelete(this.getClass().getName(), 
								id);				
	}

	@Override
	public void jdoPreStore() {
		
		
	}

}
