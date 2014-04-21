/**
 * 
 */
package org.javahispano.javaleague.client.presenter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;

import org.gwtbootstrap3.client.ui.Button;
import org.gwtbootstrap3.client.ui.Heading;
import org.gwtbootstrap3.client.ui.LinkedGroup;
import org.gwtbootstrap3.client.ui.LinkedGroupItem;
import org.gwtbootstrap3.client.ui.LinkedGroupItemHeading;
import org.gwtbootstrap3.client.ui.LinkedGroupItemText;
import org.gwtbootstrap3.client.ui.Paragraph;
import org.gwtbootstrap3.extras.bootbox.client.Bootbox;
import org.gwtbootstrap3.extras.bootbox.client.callback.ConfirmCallback;
import org.javahispano.javaleague.client.event.CreateCalendarLeagueEvent;
import org.javahispano.javaleague.client.event.ShowMyLeaguesEvent;
import org.javahispano.javaleague.client.event.ViewMatchEvent;
import org.javahispano.javaleague.client.helper.RPCCall;
import org.javahispano.javaleague.client.resources.messages.JavaLeagueMessages;
import org.javahispano.javaleague.client.service.LeagueServiceAsync;
import org.javahispano.javaleague.shared.domain.CalendarDate;
import org.javahispano.javaleague.shared.domain.League;
import org.javahispano.javaleague.shared.domain.Match;
import org.javahispano.javaleague.shared.domain.User;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.SimpleEventBus;
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

		Paragraph getParagraphDate();

		LinkedGroup getHomeTeams();

		LinkedGroup getVisitingTeams();

		LinkedGroup getResultMatch();

		Heading getNameLeague();

	}

	private final SimpleEventBus eventBus;
	private final Display display;
	private final LeagueServiceAsync leagueService;

	private League league;
	private User user;
	private Long matchId;

	private JavaLeagueMessages javaLeagueMessages = GWT
			.create(JavaLeagueMessages.class);

	public ShowLeaguePresenter(LeagueServiceAsync leagueService, League league,
			User user, SimpleEventBus eventBus, Display display) {
		this.leagueService = leagueService;
		this.league = league;
		this.user = user;
		this.eventBus = eventBus;
		this.display = display;

		ShowLeague();
	}

	private void ShowLeague() {
		Date now = new Date(); // La fecha hay que solicitarla al servidor !!!!
		if ((league.getEndSignIn().before(now))
				|| (user.isJoinLeague(league.getId()))) {
			display.getJoinLeagueButton().setEnabled(false);
		} else {
			display.getJoinLeagueButton().setEnabled(true);
		}
		display.getDescriptionLeague().setHTML(league.getDescription());
		display.getNameLeague().setText(league.getName());

		String text;
		text = "<p>" + league.getCreation().toString() + "</p>";
		if (league.getMatchs() != null) {
			for (Ref<CalendarDate> cd : league.getMatchs()) {
				for (Ref<Match> m : cd.get().getMatchs()) {

					if (m.get().getState() == 1) {
						text += "<p><a href='/serve?id=" + m.get().getId()
								+ "'>" + m.get().getNameLocal();
						text += " " + m.get().getLocalGoals() + " - "
								+ m.get().getVisitingTeamGoals() + " ";
						text += m.get().getNameForeign() + "</a></p>";
					} else {
						text += "<p>" + m.get().getNameLocal() + " N/A "
								+ m.get().getNameForeign() + "</p>";
					}

					display.getHomeTeams().add(
							addTeam(m.get().getLocal().getId(), m.get()
									.getNameLocal(), m.get().getNameLocal())); // Falta
																				// el
																				// nombre
																				// del
																				// entrenador
					display.getResultMatch().add(
							addResult(m.get().getLocalGoals(), m.get()
									.getVisitingTeamGoals(), m.get()
									.getLocalPossesion(), m.get().getState(), m
									.get().getId()));
					display.getVisitingTeams()
							.add(addTeam(m.get().getVisiting().getId(), m.get()
									.getNameForeign(), m.get().getNameForeign())); // Falta
																					// el
																					// nombre
																					// del
																					// entrenador

				}
			}
		}
		// display.getParagraphDate().setHTML(text);

	}

	private LinkedGroupItem addResult(int localGoals, int visitingTeamGoals,
			double localPossesion, int state, Long id) {
		LinkedGroupItem item = new LinkedGroupItem();
		LinkedGroupItemHeading itemHeading = new LinkedGroupItemHeading(1);
		LinkedGroupItemText itemText = new LinkedGroupItemText();

		matchId = id;
		if (state == 1) {
			itemHeading.setText(localGoals + " - " + visitingTeamGoals);
			itemText.setText(round(localPossesion * 100d, 2) + " - "
					+ round((1d - localPossesion) * 100d, 2));
		} else {
			itemHeading.setText("N / A");
		}
		item.add(itemHeading);
		item.add(itemText);

		item.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				GWT.log("ShowLeaguePresenter: firing ViewMatchEvent. MatchId: "
						+ matchId);
				eventBus.fireEvent(new ViewMatchEvent(matchId));
			}
		});

		return item;
	}

	private LinkedGroupItem addTeam(Long id, String name, String nameManager) {
		LinkedGroupItem item = new LinkedGroupItem();
		LinkedGroupItemHeading itemHeading = new LinkedGroupItemHeading(1);
		LinkedGroupItemText itemText = new LinkedGroupItemText();

		itemHeading.setText(name);
		itemText.setText(nameManager);

		item.add(itemHeading);
		item.add(itemText);

		return item;
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
						// doCreateCalendarLeague();
						GWT.log("ShowLeaguePresenter: firing CreateCalenderLeagueEvent. LeagueId: "
								+ league.getId());
						eventBus.fireEvent(new CreateCalendarLeagueEvent(league));
					}
				});
	}

	private void doCreateCalendarLeague() {

		new RPCCall<League>() {

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Error createCalendarLeague: "
						+ caught.getMessage());
			}

			@Override
			public void onSuccess(League result) {
				league = result;
				GWT.log("ShowLeaguePresenter: firing CreateCalenderLeagueEvent. LeagueId: "
						+ league.getId());
				eventBus.fireEvent(new CreateCalendarLeagueEvent(league));
			}

			@Override
			protected void callService(AsyncCallback<League> cb) {
				leagueService.createCalendarLeague(league, cb);
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
}
