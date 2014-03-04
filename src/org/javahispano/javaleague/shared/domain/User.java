package org.javahispano.javaleague.shared.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

@Entity
public class User implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final Logger log = Logger.getLogger(User.class.getName());

	@Id
	private Long id;

	private String name;

	@Index 
	private String emailAddress;

	private String password;

	private Long tacticId;

	private Date lastLoginOn;

	private Date lastActive;

	@Index 
	private String tokenActivate;

	private Date dateTokenActivate;

	private boolean active;

	private String channelId;
	
	private List<Long> leagues;

	public User() {
		super();
		this.active = false;
		this.leagues = new ArrayList<Long>();
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
	public Long getTacticId() {
		return tacticId;
	}

	/**
	 * @param tactic
	 *            the tactic to set
	 */
	public void setTacticId(Long tacticId) {
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

	/**
	 * @return the leagues
	 */
	public List<Long> getLeagues() {
		return leagues;
	}

	/**
	 * @param leagues the leagues to set
	 */
	public void setLeagues(List<Long> leagues) {
		this.leagues = leagues;
	}

	public boolean isJoinLeague(Long id2) {
		// TODO Auto-generated method stub
		return false;
	}



}