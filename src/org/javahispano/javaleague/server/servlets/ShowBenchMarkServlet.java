/**
 * 
 */
package org.javahispano.javaleague.server.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.javahispano.javaleague.server.domain.BenchMarkDAO;
import org.javahispano.javaleague.shared.domain.BenchMark;

/**
 * @author adou
 * 
 */
public class ShowBenchMarkServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private BenchMarkDAO benchMarkDAO = new BenchMarkDAO();

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		resp.setHeader("Content-disposition",
				"attachment; filename=benchMark.csv");

		PrintWriter out = resp.getWriter();
		List<BenchMark> listBenchMark = benchMarkDAO.getAllBenchMark();
		for (BenchMark benchMark : listBenchMark) {
			out.println(benchMark.getDate().toString() + ","
					+ benchMark.getTime());
		}

		resp.flushBuffer();
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		doGet(req, res);
	}

}
