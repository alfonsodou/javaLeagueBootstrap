/**
 * 
 */
package org.javahispano.javaleague.shared.domain;

import static org.javahispano.javaleague.shared.domain.OfyService.ofy;

/**
 * @author adou
 *
 */
public class CalendarDateDAO {
	
	public CalendarDateDAO() {
		super();
	}

	public CalendarDate save(CalendarDate calendarDate) {
		ofy().save().entity(calendarDate).now();
		return calendarDate;
	}
	
	public CalendarDate findById(Long id) {
		return ofy().load().type(CalendarDate.class).id(id).now();
	}	
}
