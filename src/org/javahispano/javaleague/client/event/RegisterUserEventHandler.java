/**
 * 
 */
package org.javahispano.javaleague.client.event;

import com.google.gwt.event.shared.EventHandler;

/**
 * @author adou
 *
 */
public interface RegisterUserEventHandler extends EventHandler {
	void onRegisterUser(RegisterUserEvent event);

}
