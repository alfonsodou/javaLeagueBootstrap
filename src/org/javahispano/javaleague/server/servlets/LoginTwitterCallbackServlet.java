package org.javahispano.javaleague.server.servlets;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.javahispano.javaleague.server.LoginHelper;
import org.javahispano.javaleague.server.utils.AuthenticationProvider;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.auth.RequestToken;


@SuppressWarnings("serial") 
public class LoginTwitterCallbackServlet extends HttpServlet {
  private static Logger log = Logger.getLogger(LoginTwitterCallbackServlet.class.getName());

  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

    Twitter twitter =    new TwitterFactory().getInstance();
    String key = AuthenticationProvider.getProp("twitter-consumer-key");
    String secret = AuthenticationProvider.getProp("twitter-consumer-secret");
    

    RequestToken token = (RequestToken) request.getSession().getAttribute("requestToken");
    String verifier = request.getParameter("oauth_verifier");
    twitter.setOAuthConsumer(key, secret);

    try {
      twitter.getOAuthAccessToken(token, verifier);
      User user = twitter.verifyCredentials();
      log.info("Twitter user found:" + user.getName());
      request.getSession().removeAttribute("requestToken");
      //String sid = ((Integer) user.getId()).toString();
      String sid = String.valueOf(user.getId());

      org.javahispano.javaleague.server.domain.User u = new org.javahispano.javaleague.server.domain.User(sid, AuthenticationProvider.TWITTER);
      // use screen name for uid
      u.setName(user.getScreenName());
      org.javahispano.javaleague.server.domain.User connectr = new LoginHelper().loginStarts(request.getSession(), u);
      log.info("User id:" + connectr.getId().toString());

    } catch (TwitterException e) {
      e.printStackTrace();
    }

    response.sendRedirect(LoginHelper.getApplicationURL(request));
  }
}
