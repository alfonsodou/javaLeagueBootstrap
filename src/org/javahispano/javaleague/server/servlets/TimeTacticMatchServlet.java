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

import org.javahispano.javaleague.server.LoginHelper;
import org.javahispano.javaleague.server.domain.MatchDAO;
import org.javahispano.javaleague.shared.domain.Match;
import org.javahispano.javaleague.shared.domain.User;

/**
 * @author adou
 * 
 */
public class TimeTacticMatchServlet extends HttpServlet {
	/**
	 * Falta implementar la seguridad. Solo puede acceder a los datos el usuario
	 * propietario de la tactica
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger log = Logger
			.getLogger(TimeTacticMatchServlet.class.getName());

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		User currentUser = LoginHelper.getLoggedInUser(req.getSession());
		MatchDAO matchDAO = new MatchDAO();
		Match match = null;
		long[] time;
		String result = "";

		try {
			match = matchDAO.findById(Long.parseLong(req
					.getParameter("matchID").replace("_", "")));

			if ((!currentUser.getTactic().getId()
					.equals(match.getLocal().getId()))
					&& (!currentUser.getTactic().getId()
							.equals(match.getVisiting().getId()))) {
				return;

			}
		} catch (Exception e) {
			log.warning(e.getMessage());
		}

		res.setContentType("text/plain");
		res.setHeader("ETag", match.getId().toString());// Establece header ETag

		PrintWriter out = res.getWriter();
		if (req.getParameter("tactic").equals("local")) {
			time = match.getTimeLocal();
			res.setHeader("Content-disposition", "attachment; filename="
					+ match.getId().toString() + "_" + match.getNameLocal()
					+ ".csv");
		} else {
			time = match.getTimeVisita();
			res.setHeader("Content-disposition", "attachment; filename="
					+ match.getId().toString() + "_" + match.getNameForeign()
					+ ".csv");
		}

		for (int i = 0; i < time.length; i++) {
			result += time[i] + ",";
		}
		result = result.substring(0, result.length() - 1);
		out.println(result);
		res.flushBuffer();
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		doGet(req, res);
	}
}
