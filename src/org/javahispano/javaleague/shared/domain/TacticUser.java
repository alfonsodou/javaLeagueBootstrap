package org.javahispano.javaleague.shared.domain;

import java.io.Serializable;
import java.util.Date;

import org.javahispano.javaleague.server.AppLib;

import com.google.appengine.api.blobstore.BlobKey;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

/**
 * 
 * @author adou
 * 
 */

@Entity
public class TacticUser implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@Id
	private Long id;

	/**
	 * Team's Name
	 */
	private String teamName;

	/**
	 * Is tactic validated ?.
	 */
	private boolean valid;

	/**
	 * Date/Time first upload.
	 */
	private Date creation;

	/**
	 * Date/Time last updated.
	 */
	private Date updated;

	private int friendlyMatch;

	private int goalsFor;

	private int goalsAgainst;

	private int matchWins;

	private int matchLost;

	private int matchTied;

	/**
	 * Blob key for zip
	 */
	private String zipClasses;

	public TacticUser() {
		super();
		this.creation = new Date();
		this.updated = new Date();
		this.valid = true;
		this.teamName = "javaLeague";
		this.friendlyMatch = AppLib.FRIENDLY_MATCH_NO;
		this.goalsAgainst = 0;
		this.goalsFor = 0;
		this.matchLost = 0;
		this.matchTied = 0;
		this.matchWins = 0;
		this.zipClasses = null;
	}

	/**
	 * Get id for this object.
	 * 
	 * @return id
	 */
	public Long getId() {
		return id;
	}

	public boolean isValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}

	public Date getCreation() {
		return creation;
	}

	public void setCreation(Date creation) {
		this.creation = creation;
	}

	public Date getUpdated() {
		return updated;
	}

	public void setUpdated(Date updated) {
		this.updated = updated;
	}

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	/**
	 * @return the friendlyMatch
	 */
	public int getFriendlyMatch() {
		return friendlyMatch;
	}

	/**
	 * @param friendlyMatch
	 *            the friendlyMatch to set
	 */
	public void setFriendlyMatch(int friendlyMatch) {
		this.friendlyMatch = friendlyMatch;
	}

	public void addGoalsFor(int goals) {
		this.goalsFor += goals;
	}

	public void addGoalsAgainst(int goals) {
		this.goalsAgainst += goals;
	}

	public void addMatchWins() {
		this.matchWins++;
	}

	public void addMatchLost() {
		this.matchLost++;
	}

	public void addMatchTied() {
		this.matchTied++;
	}

	/**
	 * @return the goalsFor
	 */
	public int getGoalsFor() {
		return goalsFor;
	}

	/**
	 * @param goalsFor
	 *            the goalsFor to set
	 */
	public void setGoalsFor(int goalsFor) {
		this.goalsFor = goalsFor;
	}

	/**
	 * @return the goalsAgainst
	 */
	public int getGoalsAgainst() {
		return goalsAgainst;
	}

	/**
	 * @param goalsAgainst
	 *            the goalsAgainst to set
	 */
	public void setGoalsAgainst(int goalsAgainst) {
		this.goalsAgainst = goalsAgainst;
	}

	/**
	 * @return the matchWins
	 */
	public int getMatchWins() {
		return matchWins;
	}

	/**
	 * @param matchWins
	 *            the matchWins to set
	 */
	public void setMatchWins(int matchWins) {
		this.matchWins = matchWins;
	}

	/**
	 * @return the matchLost
	 */
	public int getMatchLost() {
		return matchLost;
	}

	/**
	 * @param matchLost
	 *            the matchLost to set
	 */
	public void setMatchLost(int matchLost) {
		this.matchLost = matchLost;
	}

	/**
	 * @return the matchTied
	 */
	public int getMatchTied() {
		return matchTied;
	}

	/**
	 * @param matchTied
	 *            the matchTied to set
	 */
	public void setMatchTied(int matchTied) {
		this.matchTied = matchTied;
	}

	/**
	 * @return the zipClasses
	 */
	public String getZipClasses() {
		return zipClasses;
	}

	/**
	 * @param zipClasses
	 *            the zipClasses to set
	 */
	public void setZipClasses(String zipClasses) {
		this.zipClasses = zipClasses;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	public void setFileName(String substring) {
		// TODO Auto-generated method stub
		
	}

	public Object getFileName() {
		// TODO Auto-generated method stub
		return null;
	}

	public Object getBytes() {
		// TODO Auto-generated method stub
		return null;
	}

}