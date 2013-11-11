package org.javahispano.javaleague.server.servlets;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.javahispano.javaleague.server.utils.AuthenticationProvider;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

/**
 * 
 * @author adou
 *
 */
public class LoginGoogleServlet extends LoginSuperServlet {
  private static final long serialVersionUID = -4565961422877273742L;
  private static Logger log = Logger.getLogger(LoginGoogleServlet.class
      .getName());
  
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    String callbackURL = buildCallBackURL(request, AuthenticationProvider.GOOGLE);
    UserService userService = UserServiceFactory.getUserService();
    String googleLoginUrl = userService.createLoginURL(callbackURL);
    log.info("Going to Google login URL: " + googleLoginUrl);
    response.sendRedirect(googleLoginUrl);
    }
  }