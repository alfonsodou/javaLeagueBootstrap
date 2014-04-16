/**
 * 
 */
package org.javahispano.javaleague.server.servlets;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ar.com.hjg.pngj.IImageLine;
import ar.com.hjg.pngj.ImageLineInt;
import ar.com.hjg.pngj.PngReader;
import ar.com.hjg.pngj.PngWriter;
import ar.com.hjg.pngj.chunks.ChunkCopyBehaviour;

/**
 * 
 * 
 */
public class ImageServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void processRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String file = request.getParameter("file");
		String path = request.getParameter("path");


			String filename = path.substring(path.lastIndexOf("/") + 1);
			ServletContext cntx = getServletContext();
			String mime = cntx.getMimeType(filename);
			if (!"image/png".equals(mime)) {
				response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				return;
			}
			response.setContentType(mime);

		OutputStream out = response.getOutputStream();

		String toto = request.getParameter("to");

		final int CACHE_DURATION_IN_SECOND = 60 * 60 * 24 * 356; // 1 year
		final long CACHE_DURATION_IN_MS = CACHE_DURATION_IN_SECOND * 1000;
		long now = System.currentTimeMillis();
		response.setDateHeader("Expires", now + CACHE_DURATION_IN_MS);
		response.setHeader("ETag", path);// Estalblece header ETag

		String[] to = toto.split(",");
		String[] from = request.getParameter("from").split(",");
		String[] error = request.getParameter("error").split(",");
		float[] _from = new float[from.length];
		int[][] _to = new int[to.length][];
		float[] _error = new float[error.length];
		if (_from.length != _to.length || _error.length != _to.length) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			return;
		}
		for (int i = 0; i < from.length; i++) {
			_from[i] = Float.parseFloat(from[i]);
		}
		for (int i = 0; i < error.length; i++) {
			_error[i] = Float.parseFloat(error[i]);
		}
		for (int i = 0; i < to.length; i++) {
			String[] rgb = to[i].split("\\.");
			_to[i] = new int[] { Integer.parseInt(rgb[0]),
					Integer.parseInt(rgb[1]), Integer.parseInt(rgb[2]) };
		}
		ServletContext context = getServletContext();
		InputStream is = context.getResourceAsStream(path);
		PngReader pngr = new PngReader(is);
		int channels = pngr.imgInfo.channels;
		if (channels < 3 || pngr.imgInfo.bitDepth != 8) {
			throw new RuntimeException("This method is for RGB8/RGBA8 images");
		}
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PngWriter pngw = new PngWriter(baos, pngr.imgInfo);
		pngw.copyChunksFrom(pngr.getChunksList(),
				ChunkCopyBehaviour.COPY_ALL_SAFE);
		for (int row = 0; row < pngr.imgInfo.rows; row++) { // also:
															// while(pngr.hasMoreRows())
			IImageLine l1 = pngr.readRow();
			int[] scanline = ((ImageLineInt) l1).getScanline(); // to save
																// typing
			for (int j = 0; j < pngr.imgInfo.cols; j++) {
				int red = scanline[j * channels];
				int green = scanline[j * channels + 1];
				int blue = scanline[j * channels + 2];
				int[] resp = transform(_from, _to, _error, red, green, blue);
				scanline[j * channels] = resp[0];
				scanline[j * channels + 1] = resp[1];
				scanline[j * channels + 2] = resp[2];
			}
			pngw.writeRow(l1);
		}
		pngr.end(); // it's recommended to end the reader first, in case there
					// are trailing chunks to read
		pngw.end();
		byte[] ser = baos.toByteArray();

		out.write(ser);
		out.close();
	}

	public static float[] RGBtoHSB(int r, int g, int b) {
		float hue, saturation, brightness;
		float[] hsbvals = new float[3];
		int cmax = (r > g) ? r : g;
		if (b > cmax) {
			cmax = b;
		}
		int cmin = (r < g) ? r : g;
		if (b < cmin) {
			cmin = b;
		}

		brightness = ((float) cmax) / 255.0f;
		if (cmax != 0) {
			saturation = ((float) (cmax - cmin)) / ((float) cmax);
		} else {
			saturation = 0;
		}
		if (saturation == 0) {
			hue = 0;
		} else {
			float redc = ((float) (cmax - r)) / ((float) (cmax - cmin));
			float greenc = ((float) (cmax - g)) / ((float) (cmax - cmin));
			float bluec = ((float) (cmax - b)) / ((float) (cmax - cmin));
			if (r == cmax) {
				hue = bluec - greenc;
			} else if (g == cmax) {
				hue = 2.0f + redc - bluec;
			} else {
				hue = 4.0f + greenc - redc;
			}
			hue = hue / 6.0f;
			if (hue < 0) {
				hue = hue + 1.0f;
			}
		}
		hsbvals[0] = hue;
		hsbvals[1] = saturation;
		hsbvals[2] = brightness;
		return hsbvals;
	}

	public static int HSBtoRGB(float hue, float saturation, float brightness) {
		int r = 0, g = 0, b = 0;
		if (saturation == 0) {
			r = g = b = (int) (brightness * 255.0f + 0.5f);
		} else {
			float h = (hue - (float) Math.floor(hue)) * 6.0f;
			float f = h - (float) java.lang.Math.floor(h);
			float p = brightness * (1.0f - saturation);
			float q = brightness * (1.0f - saturation * f);
			float t = brightness * (1.0f - (saturation * (1.0f - f)));
			switch ((int) h) {
			case 0:
				r = (int) (brightness * 255.0f + 0.5f);
				g = (int) (t * 255.0f + 0.5f);
				b = (int) (p * 255.0f + 0.5f);
				break;
			case 1:
				r = (int) (q * 255.0f + 0.5f);
				g = (int) (brightness * 255.0f + 0.5f);
				b = (int) (p * 255.0f + 0.5f);
				break;
			case 2:
				r = (int) (p * 255.0f + 0.5f);
				g = (int) (brightness * 255.0f + 0.5f);
				b = (int) (t * 255.0f + 0.5f);
				break;
			case 3:
				r = (int) (p * 255.0f + 0.5f);
				g = (int) (q * 255.0f + 0.5f);
				b = (int) (brightness * 255.0f + 0.5f);
				break;
			case 4:
				r = (int) (t * 255.0f + 0.5f);
				g = (int) (p * 255.0f + 0.5f);
				b = (int) (brightness * 255.0f + 0.5f);
				break;
			case 5:
				r = (int) (brightness * 255.0f + 0.5f);
				g = (int) (p * 255.0f + 0.5f);
				b = (int) (q * 255.0f + 0.5f);
				break;
			}
		}
		return 0xff000000 | (r << 16) | (g << 8) | (b);
	}

	int[] transform(float hueFrom[], int[][] colorTo, float[] error, int r,
			int g, int b) {
		float[] v0 = RGBtoHSB(r, g, b);
		for (int i = 0; i < hueFrom.length; i++) {
			if (Math.abs(v0[0] - hueFrom[i]) < error[i]
					|| Math.abs(v0[0] + 1 - hueFrom[i]) < error[i]
					|| Math.abs(v0[0] - 1 - hueFrom[i]) < error[i]) {
				float[] v1 = RGBtoHSB(colorTo[i][0], colorTo[i][1],
						colorTo[i][2]);
				int rgb = HSBtoRGB(v1[0], v1[1], v0[2] * v1[2]);
				r = (rgb >> 16) & 0xFF;
				g = (rgb >> 8) & 0xFF;
				b = (rgb) & 0xFF;
				return new int[] { r, g, b };
			}
		}
		return new int[] { r, g, b };
	}

	// <editor-fold defaultstate="collapsed"
	// desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
	/**
	 * Handles the HTTP <code>GET</code> method.
	 * 
	 * @param request
	 *            servlet request
	 * @param response
	 *            servlet response
	 * @throws ServletException
	 *             if a servlet-specific error occurs
	 * @throws IOException
	 *             if an I/O error occurs
	 */
	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	/**
	 * Handles the HTTP <code>POST</code> method.
	 * 
	 * @param request
	 *            servlet request
	 * @param response
	 *            servlet response
	 * @throws ServletException
	 *             if a servlet-specific error occurs
	 * @throws IOException
	 *             if an I/O error occurs
	 */
	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	/**
	 * Returns a short description of the servlet.
	 * 
	 * @return a String containing servlet description
	 */
	@Override
	public String getServletInfo() {
		return "Short description";
	}// </editor-fold>
}
