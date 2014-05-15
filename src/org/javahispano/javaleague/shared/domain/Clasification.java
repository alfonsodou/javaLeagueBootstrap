/**
 * 
 */
package org.javahispano.javaleague.shared.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

/**
 * @author adou
 *
 */
@Entity
public class Clasification implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	private Long id;
	
	private Long leagueId;
	
	private HashMap<Long, StatisticsTeam> clasification;
	
	private int numberMatchs;
	
	private Date updated;
	
	public Clasification() {
		super();
		clasification = new HashMap<Long, StatisticsTeam>();
		this.numberMatchs = 0;
		this.updated = new Date();
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
	 * @return the clasification
	 */
	public HashMap<Long, StatisticsTeam> getClasification() {
		return clasification;
	}

	/**
	 * @param clasification the clasification to set
	 */
	public void setClasification(HashMap<Long, StatisticsTeam> clasification) {
		this.clasification = clasification;
	}

	/**
	 * @return the numberMatchs
	 */
	public int getNumberMatchs() {
		return numberMatchs;
	}

	/**
	 * @param numberMatchs the numberMatchs to set
	 */
	public void setNumberMatchs(int numberMatchs) {
		this.numberMatchs = numberMatchs;
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

}
