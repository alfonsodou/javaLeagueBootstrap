package org.javahispano.javaleague.shared.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.logging.Logger;

import org.javahispano.javaleague.shared.AppLib;

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

	private String nameLocalManager;

	@Load
	private Ref<TacticUser> visitingTeam;

	private String nameVisitingTeam;

	private String nameVisitingManager;

	private int localTeamGoals;

	private int visitingTeamGoals;

	private double localTeamPossesion;

	private Long leagueId;

	@Index
	private Date execution;

	private Date visualization;

	private Date updated;
	
	@Index
	private int state;

	private long[] timeLocal;

	private long[] timeVisita;

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
		this.leagueId = 0L;
		this.timeLocal = null;
		this.timeVisita = null;

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
	 * @param state
	 *            the state to set
	 */
	public void setState(int state) {
		this.state = state;
	}

	/**
	 * @return the leagueId
	 */
	public Long getLeagueId() {
		return leagueId;
	}

	/**
	 * @param leagueId
	 *            the leagueId to set
	 */
	public void setLeagueId(Long leagueId) {
		this.leagueId = leagueId;
	}

	/**
	 * @return the timeLocal
	 */
	public long[] getTimeLocal() {
		return timeLocal;
	}

	/**
	 * @param timeLocal
	 *            the timeLocal to set
	 */
	public void setTimeLocal(long[] timeLocal) {
		this.timeLocal = timeLocal;
	}

	/**
	 * @return the timeVisita
	 */
	public long[] getTimeVisita() {
		return timeVisita;
	}

	/**
	 * @param timeVisita
	 *            the timeVisita to set
	 */
	public void setTimeVisita(long[] timeVisita) {
		this.timeVisita = timeVisita;
	}

	/**
	 * @return the nameLocalManager
	 */
	public String getNameLocalManager() {
		return nameLocalManager;
	}

	/**
	 * @param nameLocalManager
	 *            the nameLocalManager to set
	 */
	public void setNameLocalManager(String nameLocalManager) {
		this.nameLocalManager = nameLocalManager;
	}

	/**
	 * @return the nameVisitingManager
	 */
	public String getNameVisitingManager() {
		return nameVisitingManager;
	}

	/**
	 * @param nameVisitingManager
	 *            the nameVisitingManager to set
	 */
	public void setNameVisitingManager(String nameVisitingManager) {
		this.nameVisitingManager = nameVisitingManager;
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
