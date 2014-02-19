package org.javahispano.javaleague.server.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.logging.Logger;

import javax.jdo.JDOCanRetryException;
import javax.jdo.PersistenceManager;
import javax.jdo.Transaction;
import javax.jdo.listener.StoreCallback;
import javax.persistence.Id;

import org.javahispano.javaleague.server.PMF;
import org.javahispano.javaleague.server.utils.cache.CacheSupport;
import org.javahispano.javaleague.server.utils.cache.Cacheable;
import org.javahispano.javaleague.shared.UserDTO;

public class User implements StoreCallback, Serializable, Cacheable {
	private static final int CACHE_EXPIR = 600; // in seconds
	private static final int NUM_RETRIES = 5;
	private static final Logger log = Logger.getLogger(User.class.getName());

	@Id
	private Long id;

	private String name;

	private String emailAddress;

	private String password;

	private Long tacticId;

	private Date lastLoginOn;

	private Date lastActive;

	private String tokenActivate;

	private Date dateTokenActivate;

	private boolean active;

	private String channelId;

	private static UserDAO userDAO = new UserDAO();
	private static TacticUserDAO tacticUserDAO = new TacticUserDAO();

	public User() {
		this.active = false;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the emailAddress
	 */
	public String getEmailAddress() {
		return emailAddress;
	}

	/**
	 * @param emailAddress
	 *            the emailAddress to set
	 */
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	/**
	 * @return the tactic
	 */
	public Long getTactic() {
		return tacticId;
	}

	/**
	 * @param tactic
	 *            the tactic to set
	 */
	public void setTactic(Long tacticId) {
		this.tacticId = tacticId;
	}

	/**
	 * @return the lastLoginOn
	 */
	public Date getLastLoginOn() {
		return lastLoginOn;
	}

	/**
	 * @param lastLoginOn
	 *            the lastLoginOn to set
	 */
	public void setLastLoginOn(Date lastLoginOn) {
		this.lastLoginOn = lastLoginOn;
	}

	/**
	 * @return the lastActive
	 */
	public Date getLastActive() {
		return lastActive;
	}

	public void setLastActive(Date lastActive) {
		this.lastActive = lastActive;
	}

	/**
	 * @return the channelId
	 */
	public String getChannelId() {
		return channelId;
	}

	/**
	 * @param channelId
	 *            the channelId to set
	 */
	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password
	 *            the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the tokenActivate
	 */
	public String getTokenActivate() {
		return tokenActivate;
	}

	/**
	 * @param tokenActivate
	 *            the tokenActivate to set
	 */
	public void setTokenActivate(String tokenActivate) {
		this.tokenActivate = tokenActivate;
	}

	/**
	 * @return the dateTokenActivate
	 */
	public Date getDateTokenActivate() {
		return dateTokenActivate;
	}

	/**
	 * @param dateTokenActivate
	 *            the dateTokenActivate to set
	 */
	public void setDateTokenActivate(Date dateTokenActivate) {
		this.dateTokenActivate = dateTokenActivate;
	}

	/**
	 * @return the active
	 */
	public boolean isActive() {
		return active;
	}

	/**
	 * @param active
	 *            the active to set
	 */
	public void setActive(boolean active) {
		this.active = active;
	}

	public static UserDTO toDTO(User user) {
		if (user == null) {
			return null;
		}

		return new UserDTO(user.getId(), user.getEmailAddress(),
				user.getName(), user.getTactic());
	}

	@Override
	public void addToCache() {
		getTactic();
		CacheSupport.cachePutExp(this.getClass().getName(), id, this,
				CACHE_EXPIR);
	}

	@Override
	public void removeFromCache() {
		CacheSupport.cacheDelete(this.getClass().getName(), id);
	}

	@Override
	public void jdoPreStore() {

	}

}
