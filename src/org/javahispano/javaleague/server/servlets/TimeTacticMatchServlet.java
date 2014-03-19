/**
 * 
 */
package org.javahispano.javaleague.server.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.javahispano.javaleague.server.domain.MatchByteDAO;
import org.javahispano.javaleague.server.domain.MatchDAO;
import org.javahispano.javaleague.shared.domain.Match;
import org.javahispano.javaleague.shared.domain.MatchByte;

/**
 * @author adou
 * 
 */
public class TimeTacticMatchServlet extends HttpServlet {
	/**
	 * Falta implementar la seguridad.
	 * Solo puede acceder a los datos el usuario propietario de la tactica
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger log = Logger
			.getLogger(TimeTacticMatchServlet.class.getName());

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		MatchDAO matchDAO = new MatchDAO();
		MatchByteDAO matchByteDAO = new MatchByteDAO();
		Match match = null;
		MatchByte matchByte = null;
		long[] time;
		String result = "";
		try {
			match = matchDAO.findById(Long.parseLong(req
					.getParameter("matchID").replace("_", "")));
			matchByte = matchByteDAO.findById(match.getMatchByteId());
		} catch (Exception e) {
			log.warning(e.getMessage());
		}

		res.setContentType("text/plain");
		PrintWriter out = res.getWriter();
		if (req.getParameter("tactic").equals("local")) {
			time = matchByte.getTimeLocal();
		} else {
			time = matchByte.getTimeVisita();
		}
		for(int i = 0; i < time.length; i++) {
			result += time[i] + ",";
		}
		result = result.substring(0, result.length() - 1);
		out.println(result);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		doGet(req, res);
	}
}
