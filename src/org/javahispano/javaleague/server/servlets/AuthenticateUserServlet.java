/**
 * 
 */
package org.javahispano.javaleague.server.servlets;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.javahispano.javaleague.server.LoginHelper;
import org.javahispano.javaleague.server.domain.User;
import org.javahispano.javaleague.server.domain.UserDAO;

/**
 * @author adou
 * 
 */
public class AuthenticateUserServlet extends HttpServlet {
	private static final Logger log = Logger
			.getLogger(AuthenticateUserServlet.class.getName());

	private UserDAO userDAO = new UserDAO();

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {

		User user = null;
		Date now = new Date();

		try {
			user = userDAO.findByToken(req.getParameter("token"));
			if ((user != null)
					&& (now.compareTo(addMinutesToDate(user.getDateTokenActivate(), 3600)) < 0)) {
				user.setActive(true);
				user.setLastActive(new Date());
				user.setLastLoginOn(new Date());
				
				userDAO.save(user);
				
				/*
				 * All done. 
				 */	
				resp.sendRedirect(LoginHelper.getApplicationURL(req) + "/authuser.jsp");
				
			} else {
				resp.sendRedirect(LoginHelper.getApplicationURL(req));				
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.warning(e.getMessage());
		}


	}

	/**
	 * Agrega o quita minutos a una fecha dada. Para quitar minutos hay que
	 * sumarle valores negativos.
	 * 
	 * @param date
	 * @param minutes
	 * @return
	 */
	public static Date addMinutesToDate(Date date, int minutes) {
		Calendar calendarDate = Calendar.getInstance();
		calendarDate.setTime(date);
		calendarDate.add(Calendar.MINUTE, minutes);
		return calendarDate.getTime();
	}
}
