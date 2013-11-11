package org.javahispano.javaleague.shared.messages;

/**
 * 
 * @author adou
 *
 */
@SuppressWarnings("serial") 
public class ContentAvailableMessage extends Message {

  public ContentAvailableMessage() {
    super(Type.NEW_CONTENT_AVAILABLE);
  }

}
