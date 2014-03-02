/**
 * 
 */
package org.javahispano.javaleague.server.domain;

import static com.googlecode.objectify.ObjectifyService.ofy;

import com.googlecode.objectify.ObjectifyService;


/**
 * @author adou
 *
 */
public class CalendarDateDAO {
	static {
		ObjectifyService.register(CalendarDate.class);
	}
	
	public CalendarDateDAO() {
		
	}

	public CalendarDate save(CalendarDate calendarDate) {
		ofy().save().entity(calendarDate).now();
		return calendarDate;
	}
	
	public CalendarDate findById(Long id) {
		return ofy().load().type(CalendarDate.class).id(id).now();
	}	
}
