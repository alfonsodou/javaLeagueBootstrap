package org.javahispano.javaleague.client.event;

import com.google.gwt.event.shared.GwtEvent;

/**
 * 
 * @author adou
 *
 */
public class ContentAvailableEvent extends GwtEvent<ContentAvailableEventHandler> {
  public static Type<ContentAvailableEventHandler> TYPE = new Type<ContentAvailableEventHandler>();

  public ContentAvailableEvent() {
  }


  @Override public Type<ContentAvailableEventHandler> getAssociatedType() {
    return TYPE;
  }

  @Override protected void dispatch(ContentAvailableEventHandler handler) {
    handler.onContentAvailable(this);
  }
}
