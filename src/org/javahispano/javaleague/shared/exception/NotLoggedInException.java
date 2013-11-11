package org.javahispano.javaleague.shared.exception;

import java.io.Serializable;

/**
 * 
 * @author adou
 *
 */
@SuppressWarnings("serial")
public class NotLoggedInException extends Exception implements Serializable {
  
  public NotLoggedInException() {
  }

  public NotLoggedInException(String message) {
    super(message);
  }

  public NotLoggedInException(String message, Throwable cause) {
    super(message, cause);
  }

  public NotLoggedInException(Throwable cause) {
    super(cause);
  }

}
