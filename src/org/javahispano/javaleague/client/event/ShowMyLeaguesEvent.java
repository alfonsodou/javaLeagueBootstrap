/**
 * 
 */
package org.javahispano.javaleague.client.event;

import com.google.gwt.event.shared.GwtEvent;

/**
 * @author adou
 *
 */
public class ShowMyLeaguesEvent extends GwtEvent<ShowMyLeaguesEventHandler> {
	  public static Type<ShowMyLeaguesEventHandler> TYPE = new Type<ShowMyLeaguesEventHandler>();
	  
	public ShowMyLeaguesEvent() {

	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<ShowMyLeaguesEventHandler> getAssociatedType() {

		return TYPE;
	}
	@Override
	protected void dispatch(ShowMyLeaguesEventHandler handler) {
		handler.onShowMyLeagues(this);
		
	}

}

