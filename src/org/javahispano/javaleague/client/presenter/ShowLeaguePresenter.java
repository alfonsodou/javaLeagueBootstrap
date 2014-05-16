/**
 * 
 */
package org.javahispano.javaleague.client.presenter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.HashMap;

import org.gwtbootstrap3.client.ui.Anchor;
import org.gwtbootstrap3.client.ui.Button;
import org.gwtbootstrap3.client.ui.Column;
import org.gwtbootstrap3.client.ui.DescriptionData;
import org.gwtbootstrap3.client.ui.Heading;
import org.gwtbootstrap3.client.ui.Italics;
import org.gwtbootstrap3.client.ui.ListItem;
import org.gwtbootstrap3.client.ui.Pagination;
import org.gwtbootstrap3.client.ui.Paragraph;
import org.gwtbootstrap3.client.ui.Row;
import org.gwtbootstrap3.client.ui.Small;
import org.gwtbootstrap3.client.ui.TabListItem;
import org.gwtbootstrap3.client.ui.TabPane;
import org.gwtbootstrap3.client.ui.constants.Alignment;
import org.gwtbootstrap3.client.ui.constants.ColumnSize;
import org.gwtbootstrap3.client.ui.constants.IconType;
import org.gwtbootstrap3.extras.bootbox.client.Bootbox;
import org.gwtbootstrap3.extras.bootbox.client.callback.ConfirmCallback;
import org.javahispano.javaleague.client.event.CreateCalendarLeagueEvent;
import org.javahispano.javaleague.client.event.EditLeagueEvent;
import org.javahispano.javaleague.client.event.ShowMyLeaguesEvent;
import org.javahispano.javaleague.client.helper.MyClickHandlerLeague;
import org.javahispano.javaleague.client.helper.RPCCall;
import org.javahispano.javaleague.client.resources.messages.JavaLeagueMessages;
import org.javahispano.javaleague.client.service.LeagueServiceAsync;
import org.javahispano.javaleague.client.service.TacticServiceAsync;
import org.javahispano.javaleague.client.service.UserAccountServiceAsync;
import org.javahispano.javaleague.shared.AppLib;
import org.javahispano.javaleague.shared.domain.CalendarDate;
import org.javahispano.javaleague.shared.domain.League;
import org.javahispano.javaleague.shared.domain.Match;
import org.javahispano.javaleague.shared.domain.StatisticsTeam;
import org.javahispano.javaleague.shared.domain.TacticUser;
import org.javahispano.javaleague.shared.domain.User;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.DateTimeFormat.PredefinedFormat;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;
import com.googlecode.objectify.Ref;

/**
 * @author adou
 * 
 */
public class ShowLeaguePresenter implements Presenter {
	public interface Display {
		Widget asWidget();

		Button getJoinLeagueButton();

		Button getDropLeagueButton();

		Button getEditLeagueButton();

		Button getCreateCalendarLeagueButton();

		Paragraph getDescriptionLeague();

		Paragraph getParagraphRoundDate();

		Paragraph getParagraphRoundClasification();

		Heading getNameLeague();

		TabPane getTabPaneDate();

		TabPane getTabPaneClasification();

		TabPane getTabPaneInformation();

		Pagination getPaginationRounds();

		DescriptionData getNameLeagueDescription();

		DescriptionData getNameManagerDescription();

		DescriptionData getPointsForWinDescription();

		DescriptionData getPointsForTiedDescription();

		DescriptionData getPointsForLostDescription();

		DescriptionData getJoinTeamsDescription();

		DescriptionData getTypeLeagueDescription();

		TabListItem getTabListItemInformation();

		TabListItem getTabListItemDate();

		TabListItem getTabListItemClasification();

	}

	private final SimpleEventBus eventBus;
	private final Display display;
	private final LeagueServiceAsync leagueService;
	private final TacticServiceAsync tacticService;
	private final UserAccountServiceAsync userAccountService;

	private Long leagueId;
	private League league;
	private TacticUser tacticUser;
	private User user;
	private int round;
	private Date now;

	private JavaLeagueMessages javaLeagueMessages = GWT
			.create(JavaLeagueMessages.class);

	public ShowLeaguePresenter(LeagueServiceAsync leagueService,
			TacticServiceAsync tacticService,
			UserAccountServiceAsync userAccountService, Long leagueId,
			User user, SimpleEventBus eventBus, Display display) {
		this.leagueService = leagueService;
		this.tacticService = tacticService;
		this.userAccountService = userAccountService;
		this.leagueId = leagueId;
		this.user = user;
		this.eventBus = eventBus;
		this.display = display;
		this.round = 0;

		fetchDate();
	}

	private void ShowLeague() {
		display.getJoinLeagueButton().setEnabled(true);

		if (league.getEndSignIn().before(now)) {
			display.getJoinLeagueButton().setEnabled(false);
		}
		if (isJoinLeague(user.getId())) {
			display.getJoinLeagueButton().setEnabled(false);
		}
		if (user.getTactic().getFileName().equals(AppLib.NO_FILE)) {
			display.getJoinLeagueButton().setEnabled(false);
		}

		if (league.getManagerId().equals(user.getId())) {
			display.getDropLeagueButton().setVisible(true);
			display.getEditLeagueButton().setVisible(true);
			display.getCreateCalendarLeagueButton().setVisible(true);
			if (league.getUsers().size() < 2) {
				display.getCreateCalendarLeagueButton().setEnabled(false);
			} else {
				display.getCreateCalendarLeagueButton().setEnabled(true);
			}
		} else {
			display.getDropLeagueButton().setVisible(false);
			display.getEditLeagueButton().setVisible(false);
			display.getCreateCalendarLeagueButton().setVisible(false);
		}
		display.getDescriptionLeague().setHTML(league.getDescription());
		display.getNameLeague().setText(league.getName());

		display.getNameLeagueDescription().setText(league.getName());
		display.getNameManagerDescription().setText(league.getNameManager());
		if (league.getType() == League.PUBLIC) {
			display.getTypeLeagueDescription().setText(
					javaLeagueMessages.publicLeague());
		} else {
			display.getTypeLeagueDescription().setText(
					javaLeagueMessages.privateLeague());
		}
		display.getPointsForLostDescription().setText(
				Integer.toString(league.getPointsForLost()));
		display.getPointsForTiedDescription().setText(
				Integer.toString(league.getPointsForTied()));
		display.getPointsForWinDescription().setText(
				Integer.toString(league.getPointsForWin()));
		display.getJoinTeamsDescription().setText(
				Integer.toString(league.getUsers().size()));

		round = getRoundActual();

		doDisplayRound(round);

		ListItem previousLink = display.getPaginationRounds().addPreviousLink();
		previousLink.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if (league.getMatchs().size() > 0) {
					if (round > 0) {
						round--;
						doDisplayRound(round);
					}
				}
			}
		});
		ListItem nextLink = display.getPaginationRounds().addNextLink();
		nextLink.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if (league.getMatchs().size() > 0) {
					if (round < league.getMatchs().size() - 1) {
						round++;
						doDisplayRound(round);
					}
				}
			}
		});
	}

	private boolean isJoinLeague(Long userId) {
		boolean result = false;
		for(Ref<User> u : league.getUsers()) {
			if (u.get().getId().equals(userId)) {
				result = true;
				break;
			}
		}
		
		return result;
	}
	
	private int getRoundActual() {
		int result = 0;

		for (Ref<CalendarDate> cd : league.getMatchs()) {
			if (cd.get().getFinish().before(now)) {
				result++;
			} else {
				break;
			}
		}

		return result;
	}

	private void doDisplayRound(int index) {
		if (league.getMatchs().size() > 0) {
			DateTimeFormat date = DateTimeFormat
					.getFormat(PredefinedFormat.DATE_TIME_MEDIUM);

			doDisplayClasification(index);

			Ref<CalendarDate> cd = league.getMatchs().get(index);
			display.getTabPaneDate().clear();
			display.getParagraphRoundDate().setText(
					javaLeagueMessages.round() + " "
							+ Integer.toString(index + 1) + " / "
							+ Integer.toString(league.getMatchs().size())
							+ " :: " + date.format(cd.get().getStart()));

			for (Ref<Match> m : cd.get().getMatchs()) {

				Row row = new Row();

				row.add(addType(m.get().getLeagueId()));
				row.add(addTeam(m.get().getLocal().getId(), m.get()
						.getNameLocal(), m.get().getNameLocalManager()));
				row.add(addResult(m.get().getLocalGoals(), m.get()
						.getVisitingTeamGoals(), m.get().getLocalPossesion(), m
						.get().getState(), m.get().getId(), m.get()
						.getVisualization()));
				row.add(addTeam(m.get().getVisiting().getId(), m.get()
						.getNameForeign(), m.get().getNameVisitingManager()));
				row.add(addLinks(m.get().getId(), m.get().getLocal()
						.getUserId(), m.get().getVisiting().getUserId(), m
						.get().getState()));

				display.getTabPaneDate().add(row);
			}
		}
	}

	private void doDisplayClasification(int index) {
		display.getParagraphRoundClasification().clear();
		HashMap<Long, StatisticsTeam> hashMapST = new HashMap<Long, StatisticsTeam>();
		for (int i = 0; i < league.getUsers().size(); i++) {
			StatisticsTeam st = new StatisticsTeam();
			st.setTeamName(league.getUsers().get(i).get().getTactic()
					.getTeamName());
			hashMapST.put(league.getUsers().get(i).get().getTactic().getId(),
					st);
		}
		
		for (int f = 0; f <= index; f++) {
			Ref<CalendarDate> cd = league.getMatchs().get(index);
			for(Ref<Match> m : cd.get().getMatchs()) {
				
			}
		}
	}

	private Column addType(Long leagueId) {
		Column column = new Column();

		column.setSize(ColumnSize.MD_2);
		if (leagueId != 0) {
			Anchor anchor = new Anchor();
			anchor.setText(javaLeagueMessages.league());
			MyClickHandlerLeague myClickHandler = new MyClickHandlerLeague(
					leagueId, eventBus);
			anchor.addClickHandler(myClickHandler);
			column.add(anchor);
		} else {
			Italics italics = new Italics();
			italics.setText(javaLeagueMessages.friendly());
			column.add(italics);
		}

		return column;
	}

	private Column addResult(int localGoals, int visitingTeamGoals,
			double localPossesion, int state, Long id, Date d) {
		Column column = new Column();
		column.setSize(ColumnSize.MD_3);
		Paragraph p = new Paragraph();
		p.setAlignment(Alignment.CENTER);
		Paragraph result = new Paragraph();
		Small possesion = new Small();
		DateTimeFormat date = DateTimeFormat
				.getFormat(PredefinedFormat.TIME_MEDIUM);

		Anchor anchor = new Anchor();

		switch (state) {
		case AppLib.MATCH_ERROR:
			anchor.setText(javaLeagueMessages.matchError());
			break;
		case AppLib.MATCH_SCHEDULED:
			anchor.setText(date.format(d));
			break;
		case AppLib.MATCH_OK:
			if (now.before(addMinutesToDate(d,
					-AppLib.MINUTES_BEFORE_LIVE_MATCH))) {
				anchor.setText(date.format(d));
			} else {
				if (now.after(addMinutesToDate(d,
						-AppLib.MINUTES_BEFORE_LIVE_MATCH))
						&& now.before(addMinutesToDate(d,
								AppLib.MINUTES_AFTER_LIVE_MATCH))) {
					anchor.setText(javaLeagueMessages.live());

				} else {
					anchor.setText(localGoals + " - " + visitingTeamGoals);
					possesion.setText(round(localPossesion * 100d, 2) + " - "
							+ round((1d - localPossesion) * 100d, 2));
				}
				/*
				 * De momento el enlace descarga el partido Falta arreglar el
				 * visor para ver el partido en directo
				 */
				anchor.setHref(AppLib.baseURL + "/serve?id="
						+ Long.toString(id));

				/*
				 * MyClickHandlerMatch myClickHandler = new MyClickHandlerMatch(
				 * id, eventBus); anchor.addClickHandler(myClickHandler);
				 */
			}
			break;
		}

		result.add(anchor);

		p.add(result);
		p.add(possesion);

		column.add(p);

		return column;
	}

	private Column addTeam(Long id, String name, String nameManager) {
		Column column = new Column();
		column.setSize(ColumnSize.MD_4);
		Paragraph p = new Paragraph();
		p.setAlignment(Alignment.CENTER);
		Paragraph teamName = new Paragraph();
		Small managerName = new Small();

		teamName.setText(name);
		managerName.setText(nameManager);

		p.add(teamName);
		p.add(managerName);

		column.add(p);

		return column;
	}

	private Column addLinks(Long id, Long localId, Long visitingId, int state) {
		Column column = new Column();
		column.setSize(ColumnSize.MD_1);

		if (state == AppLib.MATCH_OK) {
			Paragraph p = new Paragraph();
			p.setAlignment(Alignment.CENTER);
			Anchor match = new Anchor();
			match.setIcon(IconType.DOWNLOAD);
			match.setHref(AppLib.baseURL + "/serve?id=" + Long.toString(id));
			p.add(match);

			if (user.getId() == localId) {
				Anchor csv = new Anchor();
				csv.setIcon(IconType.CALENDAR);
				csv.setHref(AppLib.baseURL + "/timeTacticMatch?id="
						+ Long.toString(id) + "&tactic=local");

				p.add(csv);

			} else if (user.getId() == visitingId) {
				Anchor csv = new Anchor();
				csv.setIcon(IconType.CALENDAR);
				csv.setHref(AppLib.baseURL + "/timeTacticMatch?id="
						+ Long.toString(id) + "&tactic=vis");

				p.add(csv);
			}

			column.add(p);
		}

		return column;
	}

	private static double round(double value, int places) {
		if (places < 0)
			throw new IllegalArgumentException();

		BigDecimal bd = new BigDecimal(value);
		bd = bd.setScale(places, RoundingMode.HALF_UP);
		return bd.doubleValue();
	}

	@Override
	public void go(HasWidgets container) {
		container.clear();
		container.add(display.asWidget());

		bind();
	}

	private void bind() {
		display.getDropLeagueButton().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				Bootbox.confirm(javaLeagueMessages.confirm(),
						new ConfirmCallback() {
							@Override
							public void callback(boolean result) {
								if (result) {
									doDropLeague();
								}
							}
						});
			}
		});

		display.getJoinLeagueButton().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				doJoinLeague();
			}
		});

		display.getCreateCalendarLeagueButton().addClickHandler(
				new ClickHandler() {
					public void onClick(ClickEvent event) {
						GWT.log("ShowLeaguePresenter: firing CreateCalenderLeagueEvent. LeagueId: "
								+ league.getId());
						eventBus.fireEvent(new CreateCalendarLeagueEvent(league
								.getId()));
					}
				});

		display.getEditLeagueButton().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				GWT.log("ShowLeaguePresenter: firing EditLeagueEvent. LeagueId: "
						+ league.getId());
				eventBus.fireEvent(new EditLeagueEvent(league.getId()));
			}
		});
	}

	private void fetchUser() {
		new RPCCall<User>() {

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Error fetching user ID: " + user.getId() + " :: "
						+ caught.getMessage());
			}

			@Override
			public void onSuccess(User result) {
				user = result;
				fetchLeague();
			}

			@Override
			protected void callService(AsyncCallback<User> cb) {
				userAccountService.getUser(user.getId(), cb);
			}

		}.retry(3);
	}

	private void fetchTactic() {
		new RPCCall<TacticUser>() {

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Error fetching tacticUser ID: "
						+ user.getTactic().getId() + " :: "
						+ caught.getMessage());
			}

			@Override
			public void onSuccess(TacticUser result) {
				tacticUser = result;
			}

			@Override
			protected void callService(AsyncCallback<TacticUser> cb) {
				tacticService.getTactic(user.getTactic().getId(), cb);
			}

		}.retry(3);
	}

	private void fetchLeague() {
		new RPCCall<League>() {

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Error fetching leagueID: " + leagueId + " :: "
						+ caught.getMessage());
			}

			@Override
			public void onSuccess(League result) {
				league = result;
				ShowLeague();
			}

			@Override
			protected void callService(AsyncCallback<League> cb) {
				leagueService.getLeague(leagueId, cb);
			}

		}.retry(3);
	}

	private void fetchDate() {
		new RPCCall<Date>() {

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Error fetching dateNow. LeagueId: " + leagueId
						+ " :: " + caught.getMessage());
			}

			@Override
			public void onSuccess(Date result) {
				now = result;
				fetchUser();
			}

			@Override
			protected void callService(AsyncCallback<Date> cb) {
				leagueService.getDateNow(cb);
			}

		}.retry(3);
	}

	private void doJoinLeague() {
		new RPCCall<Void>() {

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Error join league: " + caught.getMessage());
			}

			@Override
			public void onSuccess(Void result) {
				GWT.log("ShowLeaguePresenter: firing ShowMyLeaguesEvent");
				eventBus.fireEvent(new ShowMyLeaguesEvent());
			}

			@Override
			protected void callService(AsyncCallback<Void> cb) {
				leagueService.joinLeague(league.getId(), cb);
			}

		}.retry(3);
	}

	private void doDropLeague() {
		new RPCCall<Void>() {

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Error drop league: " + caught.getMessage());
			}

			@Override
			public void onSuccess(Void result) {
				GWT.log("ShowLeaguePresenter: firing ShowMyLeaguesEvent");
				eventBus.fireEvent(new ShowMyLeaguesEvent());
			}

			@Override
			protected void callService(AsyncCallback<Void> cb) {
				leagueService.dropLeague(league.getId(), cb);
			}

		}.retry(3);
	}

	/**
	 * Agrega o quita minutos a una fecha dada. Para quitar minutos hay que
	 * sumarle valores negativos.
	 * 
	 * @param date
	 * @param minutes
	 * @return
	 */
	private Date addMinutesToDate(Date date, int minutes) {
		return new Date(date.getTime() + (minutes * 60 * 1000));
	}

}
