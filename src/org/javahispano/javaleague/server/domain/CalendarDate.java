/**
 * 
 */
package org.javahispano.javaleague.server.domain;

import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

/**
 * @author adou
 *
 */
@Entity
public class CalendarDate {
	
	/**
	 * 
	 */
	private static final Logger log = Logger.getLogger(CalendarDate.class.getName());
	
	@Id
	private Long id;
	
	private Long seasonId;
	
	private Long leagueId;
	
	private List<Long> matchs;
	
	private Date start;
	
	private Date finish;

	
	public CalendarDate() {
		super();
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
	 * @return the seasonId
	 */
	public Long getSeasonId() {
		return seasonId;
	}

	/**
	 * @param seasonId the seasonId to set
	 */
	public void setSeasonId(Long seasonId) {
		this.seasonId = seasonId;
	}

	/**
	 * @return the matchs
	 */
	public List<Long> getMatchs() {
		return matchs;
	}

	/**
	 * @param matchs the matchs to set
	 */
	public void setMatchs(List<Long> matchs) {
		this.matchs = matchs;
	}

	/**
	 * @return the start
	 */
	public Date getStart() {
		return start;
	}

	/**
	 * @param start the start to set
	 */
	public void setStart(Date start) {
		this.start = start;
	}

	/**
	 * @return the finish
	 */
	public Date getFinish() {
		return finish;
	}

	/**
	 * @param finish the finish to set
	 */
	public void setFinish(Date finish) {
		this.finish = finish;
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
	
}
