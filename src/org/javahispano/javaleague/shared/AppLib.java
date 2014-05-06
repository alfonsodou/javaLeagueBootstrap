package org.javahispano.javaleague.shared;

public class AppLib {
	public static String baseURL = "http://javaleague.appspot.com";
	public static String emailAdmin = "javaleague@gmail.com";

	public static String QUEUE_LEAGUE = "league";
	public static String QUEUE_FRIENDLY = "friendly";

	public static final int MATCH_OK = 1; // partido finalizado correctamente
	public static final int MATCH_ERROR = -1; // partido ejecutado con error
	public static final int MATCH_SCHEDULED = 0; // partido pendiente de
													// ejecutar
	public static final int MATCH_QUEUE = 2; // partido metido en la cola
												// pendiente de ejecución
	public static final int MATCH_FRIENDLY_WAITING = 3; // partido amistoso
														// pendiende de rival
	public static final int FRIENDLY_MATCH_NO = 0; // tactica no disponible para
													// partido amistoso
	public static final int FRIENDLY_MATCH_OK = 1; // tactica disponible para
													// jugar partido amistoso
	public static final int FRIENDLY_MATCH_SCHEDULED = 2; // tactica con partido
															// amistoso
															// programado

	public static final String BUCKET_GCS = "javaleague.appspot.com"; // bucket
																		// for
																		// Google
																		// Cloud
																		// Storage

	public static final long DEFAULT_FRAMEWORK_ID = 5846033531666432L;
	public static final String PATH_PACKAGE = "org.javahispano.javaleague.ID_";
	
	public static final String PATH_MATCH = "matchs/";
	public static final String PATH_FRIENDLY_MATCH = "friendly/";
	public static final String PATH_LEAGUE_MATCH = "league/";
	public static final String PATH_USER = "user/";

	public static final String NO_FILE = "NO_FILE";

	public static int POINTS_FOR_WIN = 3;
	public static int POINTS_FOR_TIED = 1;
	public static int POINTS_FOR_LOST = 0;

	public static int DEFAULT_NUMBER_ROUNDS = 2;

	public static int MINUTES_BEFORE_LIVE_MATCH = 5;

	public static int MINUTES_AFTER_LIVE_MATCH = 10;

	public static int MINUTES_EXECUTION_MATCH = 5; // minutos previstos para la
													// ejecución de cada partido
	
	public static String CLASS_AGENT = "org.javahispano.javacup.model.engine.AgentPartido";

}
