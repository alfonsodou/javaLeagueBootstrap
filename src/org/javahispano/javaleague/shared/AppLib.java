package org.javahispano.javaleague.shared;


public class AppLib {
	public static String baseURL = "http://javaleague.appspot.com";
	public static String emailAdmin = "javaleague@gmail.com";
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
	
	public static final String BUCKET_GCS = "javaleague.appspot.com"; // bucket for Google Cloud Storage
	
	public static final long DEFAULT_FRAMEWORK_ID = 5846033531666432L;
	public static final String PATH_PACKAGE = "org.javahispano.javaleague.ID_";

	public static final String NO_FILE = "NO_FILE";

	public static int POINTS_FOR_WIN = 3;
	public static int POINTS_FOR_TIED = 1;
	public static int POINTS_FOR_LOST = 0;
	
	public static int DEFAULT_NUMBER_ROUNDS = 2;
	
	public static int MINUTES_BEFORE_LIVE_MATCH = 5;
	
	public static int MINUTES_AFTER_LIVE_MATCH = 10;

}
