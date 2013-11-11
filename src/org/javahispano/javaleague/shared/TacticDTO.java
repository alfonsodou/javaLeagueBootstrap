package org.javahispano.javaleague.shared;

import java.io.Serializable;
import java.util.Set;
import java.util.Vector;

/**
 * 
 * @author adou
 * 
 */
public class TacticDTO implements Serializable {

	/**
	 * 
	 */
	private String id;

	/**
	 * 
	 */
	private String teamName;

	/**
	 * 
	 */
	private String creation;

	/**
	 * 
	 */
	private String updated;

	/**
	 * 
	 */
	private Vector<TacticClassDTO> tacticsClass = new Vector<TacticClassDTO>();

	/**
	 * 
	 */
	private boolean isValid;

	private int firendlyMatch;

	private int goalsFor;

	private int goalsAgainst;

	private int matchWins;

	private int matchLost;

	private int matchTied;

	public TacticDTO() {

	}

	public TacticDTO(String id, String teamName, String creation,
			String updated, Vector<TacticClassDTO> tacticsClass, boolean isValid,
			int state, int goalsFor, int goalsAgainst, int matchWins,
			int matchLost, int matchTied) {
		this();
		this.setId(id);
		this.setTeamName(teamName);
		this.setCreation(creation);
		this.setUpdated(updated);
		this.setTacticsClass(tacticsClass);
		this.setValid(isValid);
		this.setFriendlyMatch(state);
		this.setGoalsAgainst(goalsAgainst);
		this.setGoalsFor(goalsFor);
		this.setMatchLost(matchLost);
		this.setMatchTied(matchTied);
		this.setMatchWins(matchWins);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCreation() {
		return creation;
	}

	public void setCreation(String creation) {
		this.creation = creation;
	}

	public String getUpdated() {
		return updated;
	}

	public void setUpdated(String updated) {
		this.updated = updated;
	}

	public Vector<TacticClassDTO> getTacticsClass() {
		return tacticsClass;
	}

	public void setTacticsClass(Vector<TacticClassDTO> tacticsClass) {
		this.tacticsClass = tacticsClass;
	}

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	public boolean isValid() {
		return isValid;
	}

	public void setValid(boolean isValid) {
		this.isValid = isValid;
	}

	public int getFriendlyMatch() {
		return firendlyMatch;
	}

	public void setFriendlyMatch(int friendlyMatch) {
		this.firendlyMatch = friendlyMatch;
	}

	public int getGoalsFor() {
		return goalsFor;
	}

	public void setGoalsFor(int goalsFor) {
		this.goalsFor = goalsFor;
	}

	public int getGoalsAgainst() {
		return goalsAgainst;
	}

	public void setGoalsAgainst(int goalsAgainst) {
		this.goalsAgainst = goalsAgainst;
	}

	public int getMatchWins() {
		return matchWins;
	}

	public void setMatchWins(int matchWins) {
		this.matchWins = matchWins;
	}

	public int getMatchLost() {
		return matchLost;
	}

	public void setMatchLost(int matchLost) {
		this.matchLost = matchLost;
	}

	public int getMatchTied() {
		return matchTied;
	}

	public void setMatchTied(int matchTied) {
		this.matchTied = matchTied;
	}

}
