/**
 * 
 */
package org.javahispano.javaleague.client.event;

import com.google.gwt.event.shared.GwtEvent;


/**
 * @author adou
 *
 */
public class ShowFrameWorkEvent extends GwtEvent<ShowFrameWorkEventHandler> {
	
	public static Type<ShowFrameWorkEventHandler> TYPE = new Type<ShowFrameWorkEventHandler>();


	@Override
	public Type<ShowFrameWorkEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(ShowFrameWorkEventHandler handler) {
		handler.onShowFrameWork(this);
	}

}
