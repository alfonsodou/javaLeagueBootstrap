package org.javahispano.javaleague.shared.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.logging.Logger;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

/**
 * 
 * @author adou
 *
 */
@Entity
public class LeagueSummary implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private static final Logger log = Logger.getLogger(LeagueSummary.class.getName());
			  
	@Id
	private Long id;
	
	@Index
	private Long  leagueId;
	
	private String name;
	
	private String nameManager;
	
	private Date creation;
	
	private Date updated;
	
	private Date startSignIn;
	
	private Date endSignIn;
	
	public LeagueSummary() {
		super();
		this.creation = new Date();
		this.updated = new Date();		
	}
		
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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
	 * @return the startSignIn
	 */
	public Date getStartSignIn() {
		return startSignIn;
	}

	/**
	 * @param startSignIn the startSignIn to set
	 */
	public void setStartSignIn(Date startSignIn) {
		this.startSignIn = startSignIn;
	}

	/**
	 * @return the endSignIn
	 */
	public Date getEndSignIn() {
		return endSignIn;
	}

	/**
	 * @param endSignIn the endSignIn to set
	 */
	public void setEndSignIn(Date endSignIn) {
		this.endSignIn = endSignIn;
	}

	/**
	 * @return the nameManager
	 */
	public String getNameManager() {
		return nameManager;
	}

	/**
	 * @param nameManager the nameManager to set
	 */
	public void setNameManager(String nameManager) {
		this.nameManager = nameManager;
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
	
	

}
