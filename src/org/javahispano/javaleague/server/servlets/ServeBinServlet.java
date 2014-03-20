/**
 * 
 */
package org.javahispano.javaleague.server.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.javahispano.javaleague.server.domain.MatchByteBinDAO;
import org.javahispano.javaleague.server.domain.MatchDAO;
import org.javahispano.javaleague.shared.domain.Match;
import org.javahispano.javaleague.shared.domain.MatchByteBin;

/**
 * @author adou
 * 
 */
public class ServeBinServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private MatchDAO dao = new MatchDAO();
	private MatchByteBinDAO matchByteBinDAO = new MatchByteBinDAO();

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		long id = Long.parseLong(req.getParameter("id").replace("_", ""));
		Match p = dao.findById(id);
		MatchByteBin mb = matchByteBinDAO.findById(p.getMatchByteBinId());

		res.getOutputStream().write(mb.getBin());
		res.flushBuffer();
	}
}
