package org.javahispano.javaleague.shared.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.logging.Logger;

import org.javahispano.javaleague.server.AppLib;

import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Load;

@Entity
public class Match implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final Logger log = Logger.getLogger(Match.class.getName());	

	@Id
	private Long id;

	@Load
	private Ref<TacticUser> localTeam;
	
	private String nameLocalTeam;

	@Load
	private Ref<TacticUser> visitingTeam;
	
	private String nameVisitingTeam;

	private int localTeamGoals;

	private int visitingTeamGoals;

	private double localTeamPossesion;

	private Long matchByteId;

	@Index
	private Date execution;

	private Date visualization;
	
	@Index
	private int state;

	public Match() {
		super();
		this.execution = new Date();
		this.visualization = new Date();
		this.visitingTeam = null;
		this.localTeam = null;
		this.visitingTeamGoals = 0;
		this.localTeamGoals = 0;
		this.localTeamPossesion = 0;
		this.state = AppLib.MATCH_SCHEDULED;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public TacticUser getLocal() {
		return localTeam.get();
	}

	public void setLocal(TacticUser local) {
		this.localTeam = Ref.create(local);
	}

	public TacticUser getVisiting() {
		return visitingTeam.get();
	}

	public void setVisiting(TacticUser visiting) {
		this.visitingTeam = Ref.create(visiting);
	}

	public int getLocalGoals() {
		return localTeamGoals;
	}

	public void setLocalGoals(int localGoals) {
		this.localTeamGoals = localGoals;
	}

	public int getVisitingTeamGoals() {
		return visitingTeamGoals;
	}

	public void setVisitingTeamGoals(int foreignGoals) {
		this.visitingTeamGoals = foreignGoals;
	}

	public double getLocalPossesion() {
		return localTeamPossesion;
	}

	public void setLocalPossesion(double d) {
		this.localTeamPossesion = d;
	}

	public Date getExecution() {
		return execution;
	}

	public void setExecution(Date execution) {
		this.execution = execution;
	}

	public Date getVisualization() {
		return visualization;
	}

	public void setVisualization(Date visualization) {
		this.visualization = visualization;
	}

	public String getNameLocal() {
		return nameLocalTeam;
	}

	public void setNameLocal(String nameLocal) {
		this.nameLocalTeam = nameLocal;
	}

	public String getNameForeign() {
		return nameVisitingTeam;
	}

	public void setNameForeign(String nameForeign) {
		this.nameVisitingTeam = nameForeign;
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

	/**
	 * @return the matchByteId
	 */
	public Long getMatchByteId() {
		return matchByteId;
	}

	/**
	 * @param matchByteId the matchByteId to set
	 */
	public void setMatchByteId(Long matchByteId) {
		this.matchByteId = matchByteId;
	}

	
}
