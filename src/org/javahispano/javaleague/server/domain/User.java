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

	private String tacticId;

	private Date lastLoginOn;

	private Date lastActive;
	
	private String tokenActivate;
	
	private Date dateTokenActivate;

	private boolean active;
	/**
	 * loginId and loginProvider form a unique key. E.g.: loginId = supercobra,
	 * loginProvider = LoginProvider.TWITTER
	 */
	private String uniqueId;

	private String channelId;
	
	private static UserDAO userDAO = new UserDAO();
	private static TacticUserDAO tacticUserDAO = new TacticUserDAO();
	
	public User() {
		this.active = false;
	}

	public User(String loginId, Integer loginProvider) {
		this();
		this.setUniqueId(loginId + "-" + loginProvider);
		this.setName(loginId);

	}	
	
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
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
	 * @param name the name to set
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
	 * @param emailAddress the emailAddress to set
	 */
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	/**
	 * @return the tactic
	 */
	public String getTactic() {
		return tacticId;
	}

	/**
	 * @param tactic the tactic to set
	 */
	public void setTactic(String tacticId) {
		this.tacticId = tacticId;
	}

	/**
	 * @return the lastLoginOn
	 */
	public Date getLastLoginOn() {
		return lastLoginOn;
	}

	/**
	 * @param lastLoginOn the lastLoginOn to set
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
	 * @return the uniqueId
	 */
	public String getUniqueId() {
		return uniqueId;
	}

	/**
	 * @param uniqueId the uniqueId to set
	 */
	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}

	/**
	 * @return the channelId
	 */
	public String getChannelId() {
		return channelId;
	}

	/**
	 * @param channelId the channelId to set
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
	 * @param password the password to set
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
	 * @param tokenActivate the tokenActivate to set
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
	 * @param dateTokenActivate the dateTokenActivate to set
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
	 * @param active the active to set
	 */
	public void setActive(boolean active) {
		this.active = active;
	}

	public static UserDTO toDTO(User user) {
		if (user == null) {
			return null;
		}
		
		return new UserDTO(user.getEmailAddress(), user.getName(),
				user.getUniqueId(), user.getTactic());
	}
	
	public static User findOrCreateUser(User user) {

		PersistenceManager pm = PMF.getTxnPm();
		Transaction tx = null;
		User oneResult = null, detached = null;

		String uniqueId = user.getUniqueId();	

		// perform the query and creation under transactional control,
		// to prevent another process from creating an acct with the same id.
		try {
			for (int i = 0; i < NUM_RETRIES; i++) {
				tx = pm.currentTransaction();
				tx.begin();
				oneResult = userDAO.findByUniqueId(uniqueId);
				if (oneResult != null) {
					log.info("User uniqueId already exists: " + uniqueId);
					detached = oneResult;
					//detached = pm.detachCopy(oneResult);
				} else {
					log.info("UserAccount " + uniqueId
							+ " does not exist, creating...");
					TacticUser tacticUser = new TacticUser();
					tacticUser.addSampleTacticClass();
					tacticUserDAO.save(tacticUser);
					user.setTactic(tacticUser.getId().toString());
					//AppLib.addTactic(user);
					userDAO.save(user);
					detached = user;
					//detached = pm.detachCopy(user);
				}
				try {
					tx.commit();
					break;
				} catch (JDOCanRetryException e1) {
					if (i == (NUM_RETRIES - 1)) {
						throw e1;
					}
				}
			} // end for
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (tx.isActive()) {
				tx.rollback();
			}
			pm.close();
		}

		return detached;
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
