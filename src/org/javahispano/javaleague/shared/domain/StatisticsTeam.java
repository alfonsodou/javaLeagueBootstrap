/**
 * 
 */
package org.javahispano.javaleague.shared.domain;

import java.io.Serializable;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;


/**
 * @author adou
 *
 */

@Entity
public class StatisticsTeam implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	private Long id;
	
	private Long tacticId;
	
	private String teamName;
	
	private int goalsFor;

	private int goalsAgainst;

	private int matchWins;

	private int matchLost;

	private int matchTied;
	
	private int points;
	
	private double posession;
	
	public StatisticsTeam() {
		super();
		this.tacticId = 0L;
		this.goalsFor = 0;
		this.goalsAgainst = 0;
		this.matchLost = 0;
		this.matchTied = 0;
		this.matchWins = 0;
		this.points = 0;
		this.posession = 0.0;
	}

	/**
	 * @return the teamName
	 */
	public String getTeamName() {
		return teamName;
	}

	/**
	 * @param teamName the teamName to set
	 */
	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	/**
	 * @return the goalsFor
	 */
	public int getGoalsFor() {
		return goalsFor;
	}

	/**
	 * @param goalsFor the goalsFor to set
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
	 * @param goalsAgainst the goalsAgainst to set
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
	 * @param matchWins the matchWins to set
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
	 * @param matchLost the matchLost to set
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
	 * @param matchTied the matchTied to set
	 */
	public void setMatchTied(int matchTied) {
		this.matchTied = matchTied;
	}

	/**
	 * @return the points
	 */
	public int getPoints() {
		return points;
	}

	/**
	 * @param points the points to set
	 */
	public void setPoints(int points) {
		this.points = points;
	}

	/**
	 * @return the posession
	 */
	public double getPosession() {
		return posession;
	}

	/**
	 * @param posession the posession to set
	 */
	public void setPosession(double posession) {
		this.posession = posession;
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
	
	public void addPoints(int points) {
		this.points += points;
	}
	
	public void addPosession(double posession) {
		this.posession += posession;
	}

	/**
	 * @return the tacticId
	 */
	public Long getTacticId() {
		return tacticId;
	}

	/**
	 * @param tacticId the tacticId to set
	 */
	public void setTacticId(Long tacticId) {
		this.tacticId = tacticId;
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
	
	
}
