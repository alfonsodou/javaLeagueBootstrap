/**
 * 
 */
package org.javahispano.javaleague.server.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author adou
 *
 */
public class BenchMarkServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
	    long t1 = System.nanoTime();

	    int result = 0;
	    for (int i = 0; i < 1000 * 1000; i++) {    // sole loop
	        result += sum();
	    }

	    long t2 = System.nanoTime();
	    
	    PrintWriter out = resp.getWriter();
		out.println("Execution time: " + ((t2 - t1) * 1e-9) +
		        " seconds to compute result = " + result);
		resp.flushBuffer();
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
