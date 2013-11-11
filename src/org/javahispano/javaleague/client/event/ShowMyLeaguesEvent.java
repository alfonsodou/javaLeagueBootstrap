/**
 * 
 */
package org.javahispano.javaleague.client.event;

import org.javahispano.javaleague.shared.UserDTO;

import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.GwtEvent.Type;

/**
 * @author adou
 *
 */
public class ShowMyLeaguesEvent extends GwtEvent<ShowMyLeaguesEventHandler> {
	  public static Type<ShowMyLeaguesEventHandler> TYPE = new Type<ShowMyLeaguesEventHandler>();
	  private final UserDTO user;
	  
	public ShowMyLeaguesEvent(UserDTO user) {
		this.user = user;
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

