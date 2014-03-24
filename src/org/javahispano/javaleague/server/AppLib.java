package org.javahispano.javaleague.server;


public class AppLib {
	public static String baseURL = "http://javaleague.appspot.com";
	public static String emailAdmin = "javaleague@gmail.com";
	public static boolean isDevelopment = false;
	public static final String INFONOTFOUND = "<h1>Error #AF31-G</h1><p>Login service credentials missing in appengine-web.xml."
			+ " Please update this file as indicated with OAuth key information and restart the application.</p>"
			+ "<p>If you just want to try the app at once, choose Google authentication.</p>";
	public static final int MATCH_OK = 1; // partido finalizado correctamente
	public static final int MATCH_ERROR = -1; // partido ejecutado con error
	public static final int MATCH_SCHEDULED = 0; // partido pendiente de
													// ejecutar
	public static final int FRIENDLY_MATCH_NO = 0; // tactica no disponible para
													// partido amistoso
	public static final int FRIENDLY_MATCH_OK = 1; // tactica disponible para
													// jugar partido amistoso
	public static final int FRIENDLY_MATCH_SCHEDULED = 2; // tactica con partido
															// amistoso
															// programado
	
	public static final String bucket = "javaleague.appspot.com"; // bucket for Google Cloud Storage



}
