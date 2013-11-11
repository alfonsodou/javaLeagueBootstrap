package org.javahispano.javaleague.shared;

import java.io.Serializable;
import java.util.Date;

public class MatchDTO implements Serializable {

	private long id;
	private String local;
	private String foreign;
	private int localGoals;
	private int foreignGoals;
	private double localPosession;
	private double foreignPosession;
	private String execution;
	private String visualization;
	private String nameLocal;
	private String nameForeign;
	private int state;

	// private SeasonDTO season;
	// private byte[] match;

	public MatchDTO() {

	}

	public MatchDTO(String local, String foreign, int localGoals,
			int foreignGoals, double localPosession, double foreignPosession,
			String execution, String visualization, String nameLocal,
			String nameForeign, int state
	// SeasonDTO season,
	// byte[] match
	) {
		this();
		this.setLocal(local);
		this.setForeign(foreign);
		this.setLocalGoals(localGoals);
		this.setForeignGoals(foreignGoals);
		this.setLocalPosession(localPosession);
		this.setForeignPosession(foreignPosession);
		this.setExecution(execution);
		this.setVisualization(visualization);
		this.setNameLocal(nameLocal);
		this.setNameForeign(nameForeign);
		this.setState(state);

		// this.setSeason(season);
		// this.setMatch(match);
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getLocal() {
		return local;
	}

	public void setLocal(String local) {
		this.local = local;
	}

	public String getForeign() {
		return foreign;
	}

	public void setForeign(String foreign) {
		this.foreign = foreign;
	}

	public int getLocalGoals() {
		return localGoals;
	}

	public void setLocalGoals(int localGoals) {
		this.localGoals = localGoals;
	}

	public int getForeignGoals() {
		return foreignGoals;
	}

	public void setForeignGoals(int foreignGoals) {
		this.foreignGoals = foreignGoals;
	}

	public double getLocalPosession() {
		return localPosession;
	}

	public void setLocalPosession(double localPosession) {
		this.localPosession = localPosession;
	}

	public double getForeignPosession() {
		return foreignPosession;
	}

	public void setForeignPosession(double foreignPosession) {
		this.foreignPosession = foreignPosession;
	}

	public String getExecution() {
		return execution;
	}

	public void setExecution(String execution) {
		this.execution = execution;
	}

	public String getVisualization() {
		return visualization;
	}

	public void setVisualization(String visualization) {
		this.visualization = visualization;
	}

	public String getNameLocal() {
		return nameLocal;
	}

	public void setNameLocal(String nameLocal) {
		this.nameLocal = nameLocal;
	}

	public String getNameForeign() {
		return nameForeign;
	}

	public void setNameForeign(String nameForeign) {
		this.nameForeign = nameForeign;
	}

	/**
	 * @return the state
	 */
	public int getState() {
		return state;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(int state) {
		this.state = state;
	}
	
	

	/*
	 * public SeasonDTO getSeason() { return season; }
	 * 
	 * public void setSeason(SeasonDTO season) { this.season = season; }
	 * 
	 * public byte[] getMatch() { return match; }
	 * 
	 * public void setMatch(byte[] match) { this.match = match; }
	 */

}
