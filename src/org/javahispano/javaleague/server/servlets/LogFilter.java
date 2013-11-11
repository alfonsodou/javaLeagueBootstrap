package org.javahispano.javaleague.server.servlets;

import java.io.IOException;
import java.util.Enumeration;
import java.util.logging.Logger;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * This demo class shows a simple filter at work.  It logs information 
 * about the request that it is filtering.
 * This filter is not required by Connectr.
 */
public final class LogFilter implements Filter {

  private static Logger logger = Logger.getLogger(LogFilter.class.getName());


  @Override
  public void doFilter(ServletRequest request,
      ServletResponse response, FilterChain chain) 
  throws IOException, ServletException {

    try {
      Enumeration<?> pnames = request.getParameterNames();
      String p;
      String pval = null;
      String reqUrl = ((HttpServletRequest)(request)).getRequestURL().toString();
      logger.info("request URL: " + reqUrl);

      while(pnames.hasMoreElements()) {
        p = (String)pnames.nextElement();

        pval = request.getParameter(p);
        logger.info("request parameter " + p + " has value " + pval);
      }
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    chain.doFilter(request, response);

  }

  @Override
  public void destroy() {
    // Auto-generated method stub
  }


  @Override
  public void init(FilterConfig arg0) throws ServletException {
    // Auto-generated method stub

  }

} // end class
