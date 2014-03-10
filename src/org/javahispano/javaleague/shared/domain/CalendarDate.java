/**
 * 
 */
package org.javahispano.javaleague.shared.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Load;

/**
 * @author adou
 *
 */
@Entity
public class CalendarDate implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private static final Logger log = Logger.getLogger(CalendarDate.class.getName());
	
	@Id
	private Long id;
	
	private Long leagueId;
	
	@Load
	private List<Ref<Match>> matchs;
	
	private Date start;
	
	private Date finish;
	
	private Clasification clasification;

	
	public CalendarDate() {
		super();
		matchs = new ArrayList<Ref<Match>>();
		clasification = new Clasification();
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
	 * @return the matchs
	 */
	public List<Ref<Match>> getMatchs() {
		return matchs;
	}

	/**
	 * @param matchs the matchs to set
	 */
	public void setMatchs(List<Match> value) {
		matchs.clear();
		for(Match m : value) {
			matchs.add(Ref.create(m));
		}
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

	/**
	 * @return the clasification
	 */
	public Clasification getClasification() {
		return clasification;
	}

	/**
	 * @param clasification the clasification to set
	 */
	public void setClasification(Clasification clasification) {
		this.clasification = clasification;
	}
	
	public void addMatch(Match m) {
		matchs.add(Ref.create(m));
	}
}
