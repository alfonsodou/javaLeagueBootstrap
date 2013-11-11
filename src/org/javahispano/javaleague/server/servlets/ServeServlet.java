/**
 * 
 */
package org.javahispano.javaleague.server.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.javahispano.javaleague.server.domain.Match;
import org.javahispano.javaleague.server.domain.MatchDAO;

/**
 * @author adou
 * 
 */
public class ServeServlet extends HttpServlet {
	private MatchDAO dao = new MatchDAO();

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		long id = Long.parseLong(req.getParameter("id").replace("_", ""));
		Match p = dao.findById(id);

		res.getOutputStream().write(p.getMatch());
		res.flushBuffer();
	}
}
