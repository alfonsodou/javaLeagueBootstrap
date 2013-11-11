package org.javahispano.javaleague.shared.messages;

/**
 * 
 * @author adou
 *
 */
@SuppressWarnings("serial") 
public class ChannelTextMessage extends Message {

  private String msg;

  private ChannelTextMessage() {
    super(Type.TEXT_MESSAGE);
  }

  public ChannelTextMessage(String msg) {
    this();
    this.msg = msg;
  }

  public String get() {
    return msg;
  }
}
