package org.javahispano.javaleague.server.servlets;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.javahispano.javaleague.server.LoginHelper;
import org.javahispano.javaleague.server.domain.User;
import org.javahispano.javaleague.server.utils.AuthenticationProvider;
import org.javahispano.javaleague.server.utils.UrlFetcher;
import org.json.JSONException;
import org.json.JSONObject;


@SuppressWarnings("serial") 
public class LoginFacebookCallbackServlet extends HttpServlet {
  private static Logger log = Logger
      .getLogger(LoginFacebookCallbackServlet.class.getName());

  public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws IOException {

    String code = request.getParameter("code");
    String callbackURL = "http://javaleague.appspot.com/loginfacebook";

    if (code != null && !code.isEmpty()) {
      // -------------------------------------------------- step 2
      log.info("STEP 2: got code=" + code);
      String step2 = "https://graph.facebook.com/oauth/access_token"
          + "?client_id=zzzzzzzzzzzzzzz" + "&redirect_uri="
          + callbackURL
          + "&client_secret=sssssssssssssssssssssssss"
          + "&code=" + code;

      /*
       * Get access token
       */
      log.info("requesting access token url=" + step2);
      String resp = UrlFetcher.get(step2);
      log.info("Response = " + resp);
      int beginIndex = "access_token=".length();
      String token = resp.substring(beginIndex);
      log.info("Extracted token = " + token);

      /*
       * Get user info
       */
      String url = "https://graph.facebook.com/me?access_token=" + token;
      log.info("requesting user info: " + url);
      resp = UrlFetcher.get(url);
      log.info("Response: " + resp);
      User connectr = extractUserInfo(resp);
      connectr = new LoginHelper()
          .loginStarts(request.getSession(), connectr);
      log.info("User id is logged in:" + connectr.getId().toString());

      /*
       * All done. Let's go home.
       */
      response.sendRedirect(LoginHelper.getApplicationURL(request));

    }
  }

  private User extractUserInfo(String resp) {
    log.info("Extracting user info");
    JSONObject j;
    User u = null;
    try {
      j = new JSONObject(resp);
      String first = j.getString("first_name");
      String last = j.getString("last_name");
      String id = j.getString("id");
      log.info("User info from JSON: " + first + " " + last + " id = "
          + id);
      u = new User(id, AuthenticationProvider.FACEBOOK);
      u.setName(first + " " + last);
    } catch (JSONException e) {
      e.printStackTrace();
    }
    return u;
  }
}
