package org.javahispano.javaleague.shared.messages;

import java.io.Serializable;


/**
 * 
 * @author adou
 *
 */
public abstract class Message implements Serializable {

  public enum Type {
      NEW_CONTENT_AVAILABLE,
      TEXT_MESSAGE,
    }
  
  private Type type;

  @SuppressWarnings("unused") private Message() {
  }

  protected Message(Type type) {
    this.type = type;
  }

  public Type getType() {
    return type;
  }
}

