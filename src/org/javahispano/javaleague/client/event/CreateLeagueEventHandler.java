/**
 * 
 */
package org.javahispano.javaleague.client.event;

import com.google.gwt.event.shared.EventHandler;


/**
 * @author adou
 *
 */
public interface CreateLeagueEventHandler extends EventHandler {
	void onCreateLeague(CreateLeagueEvent event);
}
