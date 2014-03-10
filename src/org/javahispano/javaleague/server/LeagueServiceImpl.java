/**
 * 
 */
package org.javahispano.javaleague.server;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import org.javahispano.javaleague.client.service.LeagueService;
import org.javahispano.javaleague.server.domain.CalendarDateDAO;
import org.javahispano.javaleague.server.domain.LeagueDAO;
import org.javahispano.javaleague.server.domain.MatchDAO;
import org.javahispano.javaleague.server.domain.UserDAO;
import org.javahispano.javaleague.shared.domain.CalendarDate;
import org.javahispano.javaleague.shared.domain.League;
import org.javahispano.javaleague.shared.domain.Match;
import org.javahispano.javaleague.shared.domain.User;

import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;
import com.google.common.collect.Lists;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.googlecode.objectify.Ref;

/**
 * @author adou
 * 
 */
public class LeagueServiceImpl extends RemoteServiceServlet implements
		LeagueService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static Logger logger = Logger.getLogger(TacticServiceImpl.class
			.getName());

	private static LeagueDAO leagueDAO = new LeagueDAO();
	private static UserDAO userDAO = new UserDAO();
	private static CalendarDateDAO calendarDateDAO = new CalendarDateDAO();
	private static MatchDAO matchDAO = new MatchDAO();

	public LeagueServiceImpl() {

	}

	@Override
	public League createLeague(League league) {
		try {
			User user = LoginHelper.getLoggedInUser(getThreadLocalRequest()
					.getSession());
			league.setManagerId(user.getId());
			league.setNameManager(user.getName());
			league = leagueDAO.save(league);
			user.addLeague(league);
			user = userDAO.save(user);
		} catch (Exception e) {
			e.printStackTrace();
			logger.warning(e.getMessage());
		}

		return league;
	}

	@Override
	public List<Ref<League>> getMyLeagues() {
		List<Ref<League>> leagues = null;

		try {
			User user = LoginHelper.getLoggedInUser(getThreadLocalRequest()
					.getSession());
			// leagues = leagueDAO.findByUser(user.getId());
			leagues = Lists.newArrayList(user.getLeagues());

		} catch (Exception e) {
			e.printStackTrace();
			logger.warning(e.getMessage());
		}

		return leagues;
	}

	@Override
	public League getLeague(Long id) {
		League league = null;

		try {
			league = leagueDAO.findById(id);
		} catch (Exception e) {
			e.printStackTrace();
			logger.warning(e.getMessage());
		}

		return league;
	}

	@Override
	public void dropLeague(Long id) {
		try {
			League l = leagueDAO.findById(id);

			// Borramos la liga de la lista de ligas del manager
			User user = LoginHelper.getLoggedInUser(getThreadLocalRequest()
					.getSession());
			user.deleteLeague(l);
			userDAO.save(user);

			List<Ref<User>> users = l.getUsers();
			if (users != null) {
				for (Ref<User> u : users) {
					u.get().deleteLeague(l);
					userDAO.save(u.get());
				}
			}
			leagueDAO.delete(id);
		} catch (Exception e) {
			e.printStackTrace();
			logger.warning(e.getMessage());
		}
	}

	@Override
	public void joinLeague(Long id) {
		try {
			User user = LoginHelper.getLoggedInUser(getThreadLocalRequest()
					.getSession());
			League l = leagueDAO.findById(id);
			if (l.getManagerId().compareTo(user.getId()) != 0) {
				user.addLeague(l);
				userDAO.save(user);
			}
			l.addUser(user);
			leagueDAO.save(l);
		} catch (Exception e) {
			e.printStackTrace();
			logger.warning(e.getMessage());
		}

	}

	@Override
	public League createCalendarLeague(League league) {
		int n = league.getUsers().size();
		int[][][] temp = crearLiguilla(n);
        int partidos = n * (n - 1) / 2;
        int fechas = partidos / (n / 2);
        int partidosPorFecha = partidos / fechas;
        CalendarDate calendarDate;
        Match match;
        Date start = new Date();
		Queue queue = QueueFactory.getDefaultQueue();

		for(int i = 0; i < fechas; i++) {
			calendarDate = new CalendarDate();
			calendarDate.setStart(start);
			calendarDate.setFinish(start);
			calendarDate.setLeagueId(league.getId());
			for(int j = 0; j < partidosPorFecha; j++) {
				match = new Match();
				int found[] = new int[]{temp[i][j][0], temp[i][j][1]};
				match.setExecution(start);
				match.setVisualization(start);
				match.setLocal(league.getUsers().get(found[0]).get().getTactic());
				match.setVisiting(league.getUsers().get(found[1]).get().getTactic());
				match.setNameLocal(league.getUsers().get(found[0]).get().getTactic().getTeamName());
				match.setNameForeign(league.getUsers().get(found[1]).get().getTactic().getTeamName());
				match = matchDAO.save(match);
				calendarDate.addMatch(match);

				queue.add(TaskOptions.Builder.withUrl("/playMatch").param(
						"matchID", match.getId().toString()));
				
			}
			calendarDate = calendarDateDAO.save(calendarDate);
			league.addCalendarDate(calendarDate);
			start = addMinutesToDate(start, 5);
		}
		league = leagueDAO.save(league);
		
		for(int i = 0; i < league.getNumberRounds(); i++) {
			
		}
		return league;
	}
	
    /**
     * Metodo practico para crear liguillas todos contra todos, se debe indicar 'n' como la cantidad de equipos. Retorna un array donde: el primer indice representa la fecha el segundo representa el
     * partido de la fecha y el tercer indice 0: equipo local y 1: la visita
     *
     * Nota: No son partidos de ida y vuelta
     *
     * @param n
     * @return
     */	
    public static int[][][] crearLiguilla(int n) {
        boolean impar = n % 2 == 1;
        if (impar) {
            n++;
        }
        int ppf = n / 2;//partidos por fecha
        int p = n * (n - 1) / 2;//partidos total
        int f = p / ppf;//fechas
        int tmp[][][] = new int[f][impar ? ppf - 1 : ppf][2];
        int subTmp[][] = new int[ppf][2];
        int[][] camino = new int[2 * ppf - 1][2];
        int x = 1;
        int y = 0;
        boolean avanza = true;
        for (int i = 0; i < camino.length; i++) {
            camino[i][0] = x;
            camino[i][1] = y;
            if (avanza) {
                x++;
                if (x >= ppf) {
                    x--;
                    y++;
                    avanza = false;
                }
            } else {
                x--;
            }
        }
        for (int i = 0; i < f; i++) {
            for (int j = 0; j < ppf; j++) {
                subTmp[j][0] = -1;
                subTmp[j][1] = -1;
            }
            subTmp[0][0] = 0;
            for (int j = 0; j < camino.length; j++) {
                x = camino[j][0];
                y = camino[j][1];
                subTmp[x][y] = (j + i) % (n - 1) + 1;
            }
            int k = 0;
            for (int j = 0; j < ppf; j++) {
                if (!impar || (impar && subTmp[j][0] != n - 1 && subTmp[j][1] != n - 1)) {
                    if (((subTmp[j][0] + subTmp[j][1]) % 2) == 0) {
                        int temp = subTmp[j][0];
                        subTmp[j][0] = subTmp[j][1];
                        subTmp[j][1] = temp;
                    }
                    int l = subTmp[j][0];
                    int v = subTmp[j][1];
                    boolean invertir = false;
                    if (l >= v) {
                        invertir = (l + v) % 2 == 0;
                    } else {
                        invertir = (l + v + 1) % 2 == 0;
                    }
                    if (invertir) {
                        tmp[i][k][0] = v;
                        tmp[i][k][1] = l;
                    } else {
                        tmp[i][k][0] = l;
                        tmp[i][k][1] = v;
                    }
                    k++;
                }
            }
        }
        return tmp;
    }	
    
	/**
	 * Agrega o quita minutos a una fecha dada. Para quitar minutos hay que
	 * sumarle valores negativos.
	 * 
	 * @param date
	 * @param minutes
	 * @return
	 */
	public static Date addMinutesToDate(Date date, int minutes) {
		Calendar calendarDate = Calendar.getInstance();
		calendarDate.setTime(date);
		calendarDate.add(Calendar.MINUTE, minutes);
		return calendarDate.getTime();
	}
    

}
