/**
 * 
 */
package org.javahispano.javaleague.client.presenter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.gwtbootstrap3.client.ui.Button;
import org.gwtbootstrap3.client.ui.CheckBox;
import org.gwtbootstrap3.client.ui.DescriptionData;
import org.gwtbootstrap3.client.ui.Label;
import org.gwtbootstrap3.client.ui.TextBox;
import org.gwtbootstrap3.extras.datetimepicker.client.ui.DateTimeBox;
import org.javahispano.javaleague.client.event.ShowLeagueEvent;
import org.javahispano.javaleague.client.helper.RPCCall;
import org.javahispano.javaleague.client.resources.messages.JavaLeagueMessages;
import org.javahispano.javaleague.client.service.LeagueServiceAsync;
import org.javahispano.javaleague.shared.AppLib;
import org.javahispano.javaleague.shared.domain.League;

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

/**
 * @author adou
 * 
 */
public class CreateCalendarLeaguePresenter implements Presenter {

	public interface Display {
		DescriptionData getLeagueName();

		DescriptionData getLeagueType();

		DescriptionData getPointsForWin();

		DescriptionData getPointsForTied();

		DescriptionData getPointsForLost();

		TextBox getNumberRounds();

		Label getErrorNumberRounds();

		DateTimeBox getStartFirstRound();

		CheckBox getMondayCheckBox();

		CheckBox getTuesdayCheckBox();

		CheckBox getWednesdayCheckBox();

		CheckBox getThursdayCheckBox();

		CheckBox getFridayCheckBox();

		CheckBox getSaturdayCheckBox();

		CheckBox getSundayCheckBox();

		Label getErrorDayRound();

		Button getCreateCalendarLeagueButton();

		Button getCancelCalendarLeagueButton();

		Widget asWidget();
	}

	private final Display display;
	private final LeagueServiceAsync leagueService;
	private final SimpleEventBus eventBus;
	private League league;
	private Long leagueId;
	List<Integer> days = new ArrayList<Integer>();
	private JavaLeagueMessages javaLeagueMessages = GWT
			.create(JavaLeagueMessages.class);

	public CreateCalendarLeaguePresenter(LeagueServiceAsync leagueService,
			SimpleEventBus eventBus, Long leagueId, Display display) {
		this.leagueService = leagueService;
		this.eventBus = eventBus;
		this.leagueId = leagueId;
		this.display = display;

		hideErrorLabels();

		fetchLeague();
	}

	@Override
	public void go(HasWidgets container) {
		container.clear();
		container.add(display.asWidget());

		bind();
	}

	private void ShowLeague() {
		this.display.getSaturdayCheckBox().setValue(true);
		this.display.getLeagueName().setText(league.getName());
		if (league.getType() == AppLib.LEAGUE_PRIVATE) {
			this.display.getLeagueType().setText(
					javaLeagueMessages.privateLeague());
		} else {
			this.display.getLeagueType().setText(
					javaLeagueMessages.publicLeague());
		}
		this.display.getNumberRounds().setText(
				league.getNumberRounds().toString());
		this.display.getPointsForWin().setText(
				league.getPointsForWin().toString());
		this.display.getPointsForTied().setText(
				league.getPointsForTied().toString());
		this.display.getPointsForLost().setText(
				league.getPointsForLost().toString());
		/*
		 * this.display.getStartFirstRound().setFormat(
		 * DateTimeFormat.getFormat(PredefinedFormat.DATE_TIME_FULL)
		 * .getPattern());
		 */
	}

	private void bind() {
		display.getCreateCalendarLeagueButton().addClickHandler(
				new ClickHandler() {
					public void onClick(ClickEvent event) {
						doCreateCalendarLeague();
					}
				});

		display.getCancelCalendarLeagueButton().addClickHandler(
				new ClickHandler() {
					public void onClick(ClickEvent event) {
						GWT.log("CreateCalendarLeaguePresenter: firing ShowLeagueEvent. LeagueId:  "
								+ league.getId());
						eventBus.fireEvent(new ShowLeagueEvent(league.getId()));
					}
				});

	}

	private void doCreateCalendarLeague() {
		boolean result = true;

		hideErrorLabels();

		try {
			if (Integer.parseInt(display.getNumberRounds().getValue()) < 1) {
				result = false;
				display.getErrorNumberRounds().setVisible(true);
			}
		} catch (Exception e) {
			result = false;
			display.getErrorNumberRounds().setVisible(true);
		}

		if (!((display.getMondayCheckBox().getValue())
				|| (display.getTuesdayCheckBox().getValue())
				|| (display.getWednesdayCheckBox().getValue())
				|| (display.getThursdayCheckBox().getValue())
				|| (display.getFridayCheckBox().getValue())
				|| (display.getSaturdayCheckBox().getValue()) || (display
					.getSundayCheckBox().getValue()))) {
			result = false;
			display.getErrorDayRound().setVisible(true);
		}

		if (result) {
			league.setNumberRounds(Integer.parseInt(display.getNumberRounds()
					.getValue()));
			if (display.getMondayCheckBox().getValue()) {
				days.add(Calendar.MONDAY);
			}
			if (display.getTuesdayCheckBox().getValue()) {
				days.add(Calendar.TUESDAY);
			}
			if (display.getWednesdayCheckBox().getValue()) {
				days.add(Calendar.WEDNESDAY);
			}
			if (display.getThursdayCheckBox().getValue()) {
				days.add(Calendar.THURSDAY);
			}
			if (display.getFridayCheckBox().getValue()) {
				days.add(Calendar.FRIDAY);
			}
			if (display.getSaturdayCheckBox().getValue()) {
				days.add(Calendar.SATURDAY);
			}
			if (display.getSundayCheckBox().getValue()) {
				days.add(Calendar.SUNDAY);
			}

			new RPCCall<League>() {

				@Override
				public void onFailure(Throwable caught) {
					Window.alert("Error createCalendarLeague: "
							+ caught.getMessage());
				}

				@Override
				public void onSuccess(League result) {
					league = result;
					GWT.log("CreataCalendarLeaguePresenter: firing ShowLeagueLeagueEvent. LeagueId: "
							+ league.getId());
					eventBus.fireEvent(new ShowLeagueEvent(league.getId()));
				}

				@Override
				protected void callService(AsyncCallback<League> cb) {
					display.getCreateCalendarLeagueButton().setEnabled(false);
					leagueService.createCalendarLeague(league, display
							.getStartFirstRound().getValue(), days, cb);
				}

			}.retry(3);
		}
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

	private void hideErrorLabels() {
		display.getErrorDayRound().setVisible(false);
		display.getErrorNumberRounds().setVisible(false);
	}
}
