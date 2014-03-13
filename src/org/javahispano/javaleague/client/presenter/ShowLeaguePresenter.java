/**
 * 
 */
package org.javahispano.javaleague.client.presenter;

import java.util.Date;

import org.gwtbootstrap3.client.ui.Button;
import org.gwtbootstrap3.client.ui.Heading;
import org.gwtbootstrap3.client.ui.ListGroup;
import org.gwtbootstrap3.client.ui.ListGroupItem;
import org.gwtbootstrap3.client.ui.Paragraph;
import org.gwtbootstrap3.extras.bootbox.client.Bootbox;
import org.gwtbootstrap3.extras.bootbox.client.callback.ConfirmCallback;
import org.javahispano.javaleague.client.event.CreateCalendarLeagueEvent;
import org.javahispano.javaleague.client.event.ShowMyLeaguesEvent;
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
		
		ListGroup getHomeTeams();
		
		ListGroup getVisitingTeams();
		
		ListGroup getResultMatch();

		Heading getNameLeague();

	}

	private final SimpleEventBus eventBus;
	private final Display display;
	private final LeagueServiceAsync leagueService;

	private League league;
	private User user;

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
		Date now = new Date();
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
		for(Ref<CalendarDate> cd : league.getMatchs()) {
			for(Ref<Match> m : cd.get().getMatchs()) {
				text += "<p>" + m.get().getNameLocal();
				if (m.get().getState() == 0) {
					text += m.get().getLocalGoals() + " - " + m.get().getVisitingTeamGoals();
				} else {
					text += " N/A ";
				}
				text += m.get().getNameForeign() + "</p>";
			}
		}
		display.getParagraphDate().setText(text);

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
						doCreateCalendarLeague();
					}
				});
	}

	private void doCreateCalendarLeague() {
		
		new RPCCall<League>() {

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Error createCalendarLeague: " + caught.getMessage());
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
