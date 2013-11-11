package org.javahispano.javaleague.server.domain;

import java.io.Serializable;
import java.util.Date;

import javax.jdo.listener.StoreCallback;
import javax.persistence.Id;

import org.javahispano.javaleague.server.AppLib;
import org.javahispano.javaleague.server.utils.cache.CacheSupport;
import org.javahispano.javaleague.server.utils.cache.Cacheable;
import org.javahispano.javaleague.shared.MatchDTO;

//@PersistenceCapable(identityType = IdentityType.APPLICATION, detachable = "true")
public class Match implements StoreCallback, Serializable, Cacheable {

	private static final int CACHE_EXPIR = 600; // in seconds

	/*
	 * @PrimaryKey
	 * 
	 * @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	 * 
	 * @Extension(vendorName = "datanucleus", key = "gae.encoded-pk", value =
	 * "true") private String id;
	 */
	@Id
	private Long id;

	// @Persistent
	private String localTeam;
	
	private String nameLocalTeam;

	// @Persistent
	private String visitingTeam;
	
	private String nameVisitingTeam;

	// @Persistent
	private int localTeamGoals;

	// @Persistent
	private int visitingTeamGoals;

	// @Persistent
	private double localTeamPossesion;

	// @Persistent
	private byte[] match;

	// @Persistent
	private Date execution;

	// @Persistent
	private Date visualization;
	
	// @Persistent
	private int state;

	/*
	 * @Persistent private Season season;
	 */

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
		// this.season = null;
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

	/*
	 * public Season getSeason() { return season; }
	 * 
	 * public void setSeason(Season season) { this.season = season; }
	 */

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

	@Override
	public void addToCache() {

		getLocal();
		getVisiting();
		// getSeason();

		CacheSupport.cachePutExp(this.getClass().getName(), id, this,
				CACHE_EXPIR);
	}

	@Override
	public void removeFromCache() {

		CacheSupport.cacheDelete(this.getClass().getName(), id);
	}

	@Override
	public void jdoPreStore() {

	}

}
