package org.javahispano.javaleague.client.channel;

/**
 * 
 * @author adou
 *
 */
public interface SocketListener {
    void onOpen();
    void onMessage(String message);
}
