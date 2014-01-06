package org.javahispano.javaleague.server.servlets;

import java.io.IOException;
import java.security.Principal;
import java.util.logging.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.javahispano.javaleague.server.LoginHelper;
import org.javahispano.javaleague.server.domain.User;
import org.javahispano.javaleague.server.utils.AuthenticationProvider;

/**
 * 
 * @author adou
 *
 */
@SuppressWarnings("serial") 
public class LoginGoogleCallbackServlet extends HttpServlet {
  private static Logger log = Logger.getLogger(LoginGoogleCallbackServlet.class
      .getName());

  public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws IOException {
    Principal googleUser = request.getUserPrincipal();
    if (googleUser != null) {
      // update or create user
      User u = new User(googleUser.getName(),  AuthenticationProvider.GOOGLE);
      u.setName(googleUser.getName());
      User connectr = new LoginHelper().loginStarts(request.getSession(), u);
  
      log.info("User id:" + connectr.getId().toString() + " " + request.getUserPrincipal().getName());
    }
    response.sendRedirect(LoginHelper.getApplicationURL(request));
  }
}
