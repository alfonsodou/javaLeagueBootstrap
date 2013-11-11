package org.javahispano.javaleague.server.servlets;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

/**
 * 
 * @author adou
 *
 */
public abstract class LoginSuperServlet extends HttpServlet {

  private static final long serialVersionUID = 1956419323667629932L;

  public LoginSuperServlet() {
    super();
  }

  protected String buildCallBackURL(HttpServletRequest request, Integer provider) {
    StringBuffer requestURL = request.getRequestURL();
    String callbackURL = requestURL.toString();
    callbackURL += "callback";
    // System.out.println("callback url: " + callbackURL);
    return callbackURL;
  }

}