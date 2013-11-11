/**
 * 
 */
package org.javahispano.javaleague.server.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.jdo.PersistenceManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.javahispano.javaleague.server.PMF;
import org.javahispano.javaleague.server.domain.Match;
import org.javahispano.javaleague.server.domain.MatchDAO;

/**
 * @author adou
 * 
 */
public class DaytimeServlet extends HttpServlet {

	public Date getDate() {
		return new Date();
	}

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		PersistenceManager pm = PMF.getNonTxnPm();
		MatchDAO matchDAO = new MatchDAO();
		Match match = null;
		try {
			match = matchDAO.findById(Long.parseLong(req
					.getParameter("matchID").replace("_", "")));
		} finally {
			pm.close();
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
