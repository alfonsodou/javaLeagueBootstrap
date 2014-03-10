/**
 * 
 */
package org.javahispano.javaleague.shared.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Id;

/**
 * @author adou
 *
 */
public class Clasification implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	private Long id;
	
	private Long calendarDateId;
	
	private List<Ref<StatisticsTeam>> statisticsTeam;
	
	public Clasification() {
		super();
		statisticsTeam = new ArrayList<Ref<StatisticsTeam>>();
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
	 * @return the calendarDateId
	 */
	public Long getCalendarDateId() {
		return calendarDateId;
	}

	/**
	 * @param calendarDateId the calendarDateId to set
	 */
	public void setCalendarDateId(Long calendarDateId) {
		this.calendarDateId = calendarDateId;
	}

	/**
	 * @return the statisticsTeam
	 */
	public List<Ref<StatisticsTeam>> getStatisticsTeam() {
		return statisticsTeam;
	}

	/**
	 * @param statisticsTeam the statisticsTeam to set
	 */
	public void setStatisticsTeam(List<Ref<StatisticsTeam>> statisticsTeam) {
		this.statisticsTeam = statisticsTeam;
	}

}
