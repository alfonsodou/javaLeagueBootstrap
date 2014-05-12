/**
 * 
 */
package org.javahispano.javaleague.client.helper;

import org.javahispano.javaleague.client.event.ShowLeagueEvent;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.SimpleEventBus;

/**
 * @author adou
 *
 */
public class MyClickHandlerLeague implements ClickHandler {
	private final Long index;
	private final SimpleEventBus eventBus;
	
	public MyClickHandlerLeague(Long index, SimpleEventBus eventBus) {
		this.index = index;
		this.eventBus = eventBus;
	}

	@Override
	public void onClick(ClickEvent event) {
		eventBus.fireEvent(new ShowLeagueEvent(index));
	}

}
