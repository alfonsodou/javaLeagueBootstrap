/**
 * 
 */
package org.javahispano.javaleague.client.presenter;

import org.gwtbootstrap3.client.ui.CheckBox;
import org.gwtbootstrap3.client.ui.FormLabel;
import org.gwtbootstrap3.client.ui.Label;
import org.gwtbootstrap3.client.ui.TextArea;
import org.gwtbootstrap3.client.ui.TextBox;
import org.gwtbootstrap3.extras.datetimepicker.client.ui.DateTimeBox;
import org.javahispano.javaleague.client.helper.RPCCall;
import org.javahispano.javaleague.client.service.LeagueServiceAsync;
import org.javahispano.javaleague.shared.LeagueDTO;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author adou
 * 
 */
public class CreateLeaguePresenter implements Presenter {

	public interface Display {
		Widget asWidget();

		HasClickHandlers getCancelButton();

		HasClickHandlers getCreateLeagueButton();

		HasValue<String> getLeagueName();

		TextArea getLeagueDescription();

		CheckBox getLeaguePublic();

		CheckBox getLeaguePrivate();

		TextBox getPasswordLeague();

		DateTimeBox getStartSignIn();

		DateTimeBox getEndSignIn();

		Label getErrorName();

		Label getErrorPassword();

		Label getErrorDate();
		
		FormLabel getPasswordLeagueLabel();

	}

	private LeagueDTO leagueDTO;

	private final LeagueServiceAsync leagueService;
	private final SimpleEventBus eventBus;
	private final Display display;

	public CreateLeaguePresenter(LeagueServiceAsync leagueService,
			SimpleEventBus eventBus, Display display) {
		this.leagueService = leagueService;
		this.eventBus = eventBus;
		this.display = display;

		leagueDTO = new LeagueDTO();

		this.display.getLeaguePublic().setActive(true);
		this.display.getLeaguePrivate().setActive(false);
		this.display.getPasswordLeagueLabel().setVisible(false);
		this.display.getPasswordLeague().setVisible(false);
		
		hideErrorLabel();

	}

	@Override
	public void go(HasWidgets container) {
		container.clear();
		container.add(display.asWidget());

		bind();
	}

	private void bind() {
		this.display.getCreateLeagueButton().addClickHandler(
				new ClickHandler() {
					public void onClick(ClickEvent event) {
						doSave();
					}
				});

		this.display.getLeaguePrivate().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				display.getLeaguePublic().setActive(false);
				display.getPasswordLeagueLabel().setVisible(true);
				display.getPasswordLeague().setValue("");
				display.getPasswordLeague().setVisible(true);
			}
		});
		
		this.display.getLeaguePublic().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				display.getLeaguePrivate().setActive(false);
				display.getPasswordLeagueLabel().setVisible(false);
				display.getPasswordLeague().setVisible(false);
			}
		});

	}

	private void doSave() {
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

		if (!error) {
			leagueDTO.setLeagueName(display.getLeagueName().getValue().trim());

			new RPCCall<Void>() {
				@Override
				protected void callService(AsyncCallback<Void> cb) {
					leagueService.createLeague(leagueDTO, cb);
				}

				@Override
				public void onFailure(Throwable caught) {
					Window.alert("Error creating league...");
				}

				@Override
				public void onSuccess(Void result) {
					// TODO Auto-generated method stub

				}
			}.retry(3);
		}
	}

	private void hideErrorLabel() {
		this.display.getErrorDate().setVisible(false);
		this.display.getErrorName().setVisible(false);
		this.display.getErrorPassword().setVisible(false);
	}
}
