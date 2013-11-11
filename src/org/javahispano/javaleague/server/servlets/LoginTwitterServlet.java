package org.javahispano.javaleague.server.servlets;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.javahispano.javaleague.server.AppLib;
import org.javahispano.javaleague.server.utils.AuthenticationProvider;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.RequestToken;

/**
 * 
 * @author adou
 *
 */
@SuppressWarnings("serial") 
public class LoginTwitterServlet extends LoginSuperServlet {
  private static Logger log = Logger.getLogger(LoginTwitterServlet.class
      .getName());

  public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws IOException {

    Twitter twitter = new TwitterFactory().getInstance();
    // get auth info from system properties
    String key = AuthenticationProvider.getProp("twitter-consumer-key");
    String secret = AuthenticationProvider.getProp("twitter-consumer-secret");
    
    if(key == null || secret == null){
      response.setContentType("text/html");
      response.getWriter().print(AppLib.INFONOTFOUND);
      return;
    }

    try {
      twitter.setOAuthConsumer(key, secret);
      String callbackURL = buildCallBackURL(request, AuthenticationProvider.TWITTER);
      RequestToken token = twitter.getOAuthRequestToken(callbackURL);
      request.getSession().setAttribute("requestToken", token);
      String loginURL = token.getAuthenticationURL() + "&force_login=true";
      log.info("Redirecting to: " + loginURL);
      response.sendRedirect(loginURL);

    } catch (TwitterException e) {
      response.setContentType("text/html");
      response.getWriter().print("<p>" + e.getMessage() + "</p>");
      response.getWriter().print(AppLib.INFONOTFOUND);
      return;
      // e.printStackTrace();
    }

  }
}
