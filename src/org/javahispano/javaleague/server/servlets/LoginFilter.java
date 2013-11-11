package org.javahispano.javaleague.server.servlets;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.javahispano.javaleague.server.LoginHelper;
import org.javahispano.javaleague.shared.SharedConstants;

/**
 * 
 * @author adou
 *
 */
public final class LoginFilter implements Filter {

  private static Logger logger = Logger.getLogger(LoginFilter.class.getName());

  @Override public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
      ServletException {

    logger.info("in LoginFilter.doFilter");
    try {
      HttpServletRequest req = (HttpServletRequest) request;

      
      if (LoginHelper.isLoggedIn(req)) {
        logger.info("User is logged in...");
        chain.doFilter(request, response);
      } else {
        logger.warning("User is not logged in...");
        if (request.getContentType().contains("x-gwt-rpc")){
          // GWT requests
          HttpServletResponse resp = (HttpServletResponse) response;
          resp.setHeader("content-type", request.getContentType());
          resp.getWriter().print(SharedConstants.LOGGED_OUT);
        } else {
          HttpServletResponse resp = (HttpServletResponse) response;
          resp.sendRedirect("/");
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }

  }

  @Override public void destroy() {
    //  Auto-generated method stub

  }

  @Override public void init(FilterConfig arg0) throws ServletException {
    //  Auto-generated method stub

  }

} // end class
