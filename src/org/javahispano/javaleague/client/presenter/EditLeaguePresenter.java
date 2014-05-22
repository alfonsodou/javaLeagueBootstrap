/**
 * 
 */
package org.javahispano.javaleague.client.presenter;

import org.gwtbootstrap3.client.ui.CheckBox;
import org.gwtbootstrap3.client.ui.FormLabel;
import org.gwtbootstrap3.client.ui.Label;
import org.gwtbootstrap3.client.ui.TextBox;
import org.gwtbootstrap3.extras.datetimepicker.client.ui.DateTimeBox;
import org.gwtbootstrap3.extras.summernote.client.ui.Summernote;
import org.javahispano.javaleague.client.event.ShowMyLeaguesEvent;
import org.javahispano.javaleague.client.helper.RPCCall;
import org.javahispano.javaleague.client.service.LeagueServiceAsync;
import org.javahispano.javaleague.shared.AppLib;
import org.javahispano.javaleague.shared.domain.League;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author adou
 * 
 */
public class EditLeaguePresenter implements Presenter {

	public interface Display {
		Widget asWidget();

		HasClickHandlers getCancelButton();

		HasClickHandlers getEditLeagueButton();

		TextBox getLeagueName();

		Summernote getLeagueDescription();

		CheckBox getLeaguePublic();

		CheckBox getLeaguePrivate();

		TextBox getPasswordLeague();

		DateTimeBox getStartSignIn();

		DateTimeBox getEndSignIn();

		Label getErrorName();

		Label getErrorPassword();

		Label getErrorDate();

		FormLabel getPasswordLeagueLabel();

		Label getErrorType();

		TextBox getPointsForWin();

		TextBox getPointsForTied();

		TextBox getPointsForLost();

		Label getErrorPointsForWin();

		Label getErrorPointsForTied();

		Label getErrorPointsForLost();
	}

	private League league;
	private Long leagueId;

	private final LeagueServiceAsync leagueService;
	private final SimpleEventBus eventBus;
	private final Display display;

	public EditLeaguePresenter(LeagueServiceAsync leagueService,
			SimpleEventBus eventBus, Long leagueId, Display display) {
		this.leagueService = leagueService;
		this.eventBus = eventBus;
		this.display = display;
		this.leagueId = leagueId;

		hideErrorLabel();

		fetchLeague();
	}

	@Override
	public void go(HasWidgets container) {
		container.clear();
		container.add(display.asWidget());

		bind();

	}

	private void bind() {
		this.display.getEditLeagueButton().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				doUpdate();
			}

		});

		this.display.getCancelButton().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				GWT.log("ShowLeaguePresenter: firing ShowMyLeaguesEvent");
				eventBus.fireEvent(new ShowMyLeaguesEvent());
			}
		});

		this.display.getLeaguePrivate().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				display.getLeaguePublic().setActive(false);
				display.getPasswordLeagueLabel().setVisible(true);
				display.getPasswordLeague().setValue("");
				display.getPasswordLeague().setVisible(true);
			}
		});

		this.display.getLeaguePublic().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				display.getLeaguePrivate().setActive(false);
				display.getPasswordLeagueLabel().setVisible(false);
				display.getPasswordLeague().setVisible(false);
			}
		});
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

	private void ShowLeague() {
		display.getLeagueName().setFocus(true);
		display.getEndSignIn().setValue(league.getEndSignIn());
		display.getLeagueDescription().setCode(league.getDescription());
		display.getLeagueDescription().reconfigure();
		display.getLeagueName().setValue(league.getName());
		if (league.getType() == AppLib.LEAGUE_PRIVATE) {
			display.getLeaguePrivate().setActive(true);
			display.getLeaguePublic().setActive(false);
			display.getPasswordLeague().setVisible(true);
			display.getPasswordLeague().setValue(league.getPassword());
		} else {
			display.getLeaguePrivate().setActive(false);
			display.getLeaguePublic().setActive(true);
			display.getPasswordLeague().setVisible(false);
		}
		display.getPointsForLost().setValue(
				Integer.toString(league.getPointsForLost()));
		display.getPointsForTied().setValue(
				Integer.toString(league.getPointsForTied()));
		display.getPointsForWin().setValue(
				Integer.toString(league.getPointsForWin()));
		display.getStartSignIn().setValue(league.getStartSignIn());
	}

	private void hideErrorLabel() {
		this.display.getErrorDate().setVisible(false);
		this.display.getErrorName().setVisible(false);
		this.display.getErrorPassword().setVisible(false);
		this.display.getErrorType().setVisible(false);
		this.display.getErrorPointsForWin().setVisible(false);
		this.display.getErrorPointsForTied().setVisible(false);
		this.display.getErrorPointsForLost().setVisible(false);
	}

	private void doUpdate() {
		boolean error = false;

		hideErrorLabel();

		if (this.display.getLeagueName().getValue().isEmpty()) {
			this.display.getErrorName().setVisible(true);
			error = true;
		}

		if (this.display.getEndSignIn().getValue()
				.before(this.display.getStartSignIn().getValue())) {
			this.display.getErrorDate().setVisible(true);
			error = true;
		}

		if (this.display.getLeaguePrivate().getValue()
				&& (this.display.getPasswordLeague().getValue().isEmpty())) {
			this.display.getErrorPassword().setVisible(true);
			error = true;
		}

		if ((!this.display.getLeaguePrivate().getValue())
				&& (!this.display.getLeaguePublic().getValue())) {
			this.display.getErrorType().setVisible(true);
			error = true;
		}

		try {
			if (Integer.parseInt(this.display.getPointsForWin().getValue()) < 0) {
				this.display.getErrorPointsForWin().setVisible(true);
				error = true;
			}
		} catch (NumberFormatException e) {
			this.display.getErrorPointsForWin().setVisible(true);
			error = true;
		}

		try {
			if (Integer.parseInt(this.display.getPointsForTied().getValue()) < 0) {
				this.display.getErrorPointsForTied().setVisible(true);
				error = true;
			}
		} catch (NumberFormatException e) {
			this.display.getErrorPointsForTied().setVisible(true);
			error = true;
		}

		try {
			if (Integer.parseInt(this.display.getPointsForLost().getValue()) < 0) {
				this.display.getErrorPointsForLost().setVisible(true);
				error = true;
			}
		} catch (NumberFormatException e) {
			this.display.getErrorPointsForLost().setVisible(true);
			error = true;
		}

		if (!error) {
			league.setName(display.getLeagueName().getValue().trim());
			league.setDescription(display.getLeagueDescription().getCode());
			league.setStartSignIn(display.getStartSignIn().getValue());
			league.setEndSignIn(display.getEndSignIn().getValue());
			league.setPassword(display.getPasswordLeague().getValue());
			league.setPointsForWin(Integer.parseInt(display.getPointsForWin()
					.getValue()));
			league.setPointsForTied(Integer.parseInt(display.getPointsForTied()
					.getValue()));
			league.setPointsForLost(Integer.parseInt(display.getPointsForLost()
					.getValue()));
			if (display.getLeaguePrivate().getValue()) {
				league.setType(AppLib.LEAGUE_PRIVATE);
			} else {
				league.setType(AppLib.LEAGUE_PUBLIC);
			}
			new RPCCall<League>() {

				@Override
				public void onFailure(Throwable caught) {
					Window.alert("Error updating leagueID: " + league.getId()
							+ " :: " + caught.getMessage());
				}

				@Override
				public void onSuccess(League result) {
					league = result;
					GWT.log("ShowLeaguePresenter: firing ShowMyLeaguesEvent");
					eventBus.fireEvent(new ShowMyLeaguesEvent());

				}

				@Override
				protected void callService(AsyncCallback<League> cb) {
					leagueService.editLeague(league, cb);
				}

			}.retry(3);
		}
	}
}
