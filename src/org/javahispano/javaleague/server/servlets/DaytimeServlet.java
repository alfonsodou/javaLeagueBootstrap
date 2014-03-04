/**
 * 
 */
package org.javahispano.javaleague.server.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.javahispano.javaleague.server.domain.MatchDAO;
import org.javahispano.javaleague.shared.domain.Match;

/**
 * @author adou
 * 
 */
public class DaytimeServlet extends HttpServlet {
	private static final Logger log = Logger
			.getLogger(DaytimeServlet.class.getName());
	
	public Date getDate() {
		return new Date();
	}

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		MatchDAO matchDAO = new MatchDAO();
		Match match = null;
		try {
			match = matchDAO.findById(Long.parseLong(req
					.getParameter("matchID").replace("_", "")));
		} catch (Exception e) {
			log.warning(e.getMessage());
		}

		res.setContentType("text/plain");
		PrintWriter out = res.getWriter();
		out.println(match.getVisualization().getTime() / 1000);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		doGet(req, res);
	}
}
