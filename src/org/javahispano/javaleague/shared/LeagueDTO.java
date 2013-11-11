/**
 * 
 */
package org.javahispano.javaleague.shared;

import java.io.Serializable;

/**
 * @author adou
 *
 */
public class LeagueDTO implements Serializable {
	private String id;
	private String leagueName;
	private String managerId;
	private String creation;
	private String updated;
	private String activeSeason;
	
	public LeagueDTO() {
		
	}
	
	public LeagueDTO(String id, String leagueName, String managerId, String creation, String updated, String activeSeason) {
		this.id = id;
		this.leagueName = leagueName;
		this.managerId = managerId;
		this.creation = creation;
		this.updated = updated;
		this.activeSeason = activeSeason;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the leagueName
	 */
	public String getLeagueName() {
		return leagueName;
	}

	/**
	 * @param leagueName the leagueName to set
	 */
	public void setLeagueName(String leagueName) {
		this.leagueName = leagueName;
	}

	/**
	 * @return the managerId
	 */
	public String getManagerId() {
		return managerId;
	}

	/**
	 * @param managerId the managerId to set
	 */
	public void setManagerId(String managerId) {
		this.managerId = managerId;
	}

	/**
	 * @return the creation
	 */
	public String getCreation() {
		return creation;
	}

	/**
	 * @param creation the creation to set
	 */
	public void setCreation(String creation) {
		this.creation = creation;
	}

	/**
	 * @return the updated
	 */
	public String getUpdated() {
		return updated;
	}

	/**
	 * @param updated the updated to set
	 */
	public void setUpdated(String updated) {
		this.updated = updated;
	}

	/**
	 * @return the activeSeason
	 */
	public String getActiveSeason() {
		return activeSeason;
	}

	/**
	 * @param activeSeason the activeSeason to set
	 */
	public void setActiveSeason(String activeSeason) {
		this.activeSeason = activeSeason;
	}

	
	
}
