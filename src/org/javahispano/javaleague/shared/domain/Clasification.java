/**
 * 
 */
package org.javahispano.javaleague.shared.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

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
	
	private List<StatisticsTeam> clasification;
	
	private int numberMatchs;
	
	private Date updated;
	
	public Clasification() {
		super();
		clasification = new ArrayList<StatisticsTeam>();
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
	public List<StatisticsTeam> getClasification() {
		return clasification;
	}

	public StatisticsTeam getClasification(Long id) {
		StatisticsTeam result = null;
		for(StatisticsTeam st : clasification) {
			if (st.getTacticId().equals(id)) {
				result = st;
				break;
			}
		}
	
		clasification.remove(result);
		
		return result;
	}
	
	/**
	 * @param clasification the clasification to set
	 */
	public void setClasification(List<StatisticsTeam> clasification) {
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
