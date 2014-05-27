/**
 * 
 */
package org.javahispano.javaleague.server.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

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
public class BenchMarkServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private BenchMarkDAO benchMarkDAO = new BenchMarkDAO();
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
	    long t1 = System.nanoTime();

	    int result = 0;
	    for (int i = 0; i < 1000 * 1000; i++) {    // sole loop
	        result += sum();
	    }

	    long t2 = System.nanoTime();
	    
	    BenchMark benchMark = new BenchMark();
	    benchMark.setDate(new Date());
	    benchMark.setTime(((t2 - t1) * 1e-9));
	    benchMark.setResult(result);
	    benchMarkDAO.save(benchMark);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		doGet(req, res);
	}
	
	private static int sum() {
	    int sum = 0;
	    for (int j = 0; j < 10 * 1000; j++) {
	        sum += j;
	    }
	    return sum;
	}	

}
