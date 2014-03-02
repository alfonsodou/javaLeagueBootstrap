package org.javahispano.javaleague.server.domain;

import java.util.Date;
import java.util.logging.Logger;

import org.javahispano.javaleague.server.AppLib;
import org.javahispano.javaleague.shared.MatchDTO;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

@Entity
public class Match {

	private static final Logger log = Logger.getLogger(Match.class.getName());	

	@Id
	private Long id;

	private String localTeam;
	
	private String nameLocalTeam;

	private String visitingTeam;
	
	private String nameVisitingTeam;

	private int localTeamGoals;

	private int visitingTeamGoals;

	private double localTeamPossesion;

	private byte[] match;

	private Date execution;

	private Date visualization;
	
	private int state;

	public Match() {
		this.execution = new Date();
		this.visualization = new Date();
		this.visitingTeam = null;
		this.localTeam = null;
		this.visitingTeamGoals = 0;
		this.localTeamGoals = 0;
		this.localTeamPossesion = 0;
		this.match = null;
		this.state = AppLib.MATCH_SCHEDULED;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLocal() {
		return localTeam;
	}

	public void setLocal(String local) {
		this.localTeam = local;
	}

	public String getVisiting() {
		return visitingTeam;
	}

	public void setVisiting(String visiting) {
		this.visitingTeam = visiting;
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

	public byte[] getMatch() {
		return match;
	}

	public void setMatch(byte[] match) {
		this.match = match;
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

	public MatchDTO toDTO() {
		MatchDTO matchDTO = new MatchDTO();
		
		matchDTO.setId(this.getId());
		matchDTO.setLocal(this.getLocal());
		matchDTO.setForeign(this.getVisiting());
		matchDTO.setNameLocal(this.getNameLocal());
		matchDTO.setNameForeign(this.getNameForeign());
		matchDTO.setLocalGoals(this.getLocalGoals());
		matchDTO.setForeignGoals(this.getVisitingTeamGoals());
		matchDTO.setLocalPosession(this.getLocalPossesion());
		matchDTO.setForeignPosession(this.getVisitingTeamGoals());
		matchDTO.setExecution(this.getExecution().toString());
		matchDTO.setVisualization(this.getVisualization().toString());
		matchDTO.setState(this.getState());
		
		return matchDTO;
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

}
