/**
 * 
 */
package org.javahispano.javaleague.client.presenter;

import org.gwtbootstrap3.client.ui.Button;
import org.gwtbootstrap3.client.ui.CheckBox;
import org.gwtbootstrap3.client.ui.DescriptionData;
import org.gwtbootstrap3.client.ui.Label;
import org.gwtbootstrap3.client.ui.TextBox;
import org.gwtbootstrap3.extras.datetimepicker.client.ui.DateTimeBox;
import org.javahispano.javaleague.client.event.CreateCalendarLeagueEvent;
import org.javahispano.javaleague.client.helper.RPCCall;
import org.javahispano.javaleague.client.service.LeagueServiceAsync;
import org.javahispano.javaleague.shared.domain.League;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.SimpleEventBus;
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

		DateTimeBox getDateFirstRound();

		CheckBox getMondayCheckBox();

		CheckBox getTuesdayCheckBox();

		CheckBox getWednesdayCheckBox();

		CheckBox getThursdayCheckBox();

		CheckBox getFridayCheckBox();

		CheckBox getSaturdayCheckBox();

		CheckBox getSundayCheckBox();

		Label getErrorDaysRound();

		Button getCreateCalendarLeagueButton();

		Button getCancelCalendarLeagueButton();

		Widget asWidget();
	}

	private final Display display;
	private final LeagueServiceAsync leagueService;
	private final SimpleEventBus eventBus;
	private League league;

	public CreateCalendarLeaguePresenter(LeagueServiceAsync leagueService,
			SimpleEventBus eventBus, League league, Display display) {
		this.leagueService = leagueService;
		this.eventBus = eventBus;
		this.league = league;
		this.display = display;

		hideErrorLabels();

		this.display.getSaturdayCheckBox().setEnabled(true);
		this.display.getLeagueName().setText(league.getName());
		this.display.getLeagueType().setText(league.getType().toString());
		this.display.getNumberRounds().setText(
				league.getNumberRounds().toString());
		this.display.getPointsForWin().setText(
				league.getPointsForWin().toString());
		this.display.getPointsForTied().setText(
				league.getPointsForTied().toString());
		this.display.getPointsForLost().setText(
				league.getPointsForLost().toString());
	}

	@Override
	public void go(HasWidgets container) {
		container.clear();
		container.add(display.asWidget());

		bind();
	}

	private void bind() {
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
				Window.alert("Error createCalendarLeague: "
						+ caught.getMessage());
			}

			@Override
			public void onSuccess(League result) {
				league = result;
				GWT.log("ShowLeaguePresenter: firing CreateCalenderLeagueEvent. LeagueId: "
						+ league.getId());
			}

			@Override
			protected void callService(AsyncCallback<League> cb) {
				leagueService.createCalendarLeague(league, cb);
			}

		}.retry(3);
	}

	private void hideErrorLabels() {
		display.getErrorDaysRound().setVisible(false);
		display.getErrorNumberRounds().setVisible(false);
	}
}
