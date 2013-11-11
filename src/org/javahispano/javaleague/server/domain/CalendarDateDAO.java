/**
 * 
 */
package org.javahispano.javaleague.server.domain;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.Query;
import com.googlecode.objectify.util.DAOBase;

/**
 * @author adou
 *
 */
public class CalendarDateDAO extends DAOBase {
	static {
		ObjectifyService.register(CalendarDate.class);
	}
	
	public CalendarDateDAO() {
		
	}
	
	public CalendarDate save(CalendarDate calendarDate) {
		ofy().put(calendarDate);
		return calendarDate;
	}
	
	public CalendarDate findById(Long id) {
		Key<CalendarDate> key = new Key<CalendarDate>(CalendarDate.class, id);
		return ofy().get(key);
	}	

	public CalendarDate findByUniqueId(String uniqueId) {
		Query<CalendarDate> q = ofy().query(CalendarDate.class).filter("uniqueId", uniqueId);
		
		return q.get();
	}
}
