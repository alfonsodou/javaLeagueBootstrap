/**
 * 
 */
package org.javahispano.javaleague.client.presenter;

import org.gwtbootstrap3.client.ui.Heading;
import org.gwtbootstrap3.client.ui.Paragraph;
import org.gwtbootstrap3.extras.bootbox.client.Bootbox;
import org.gwtbootstrap3.extras.bootbox.client.callback.ConfirmCallback;
import org.javahispano.javaleague.client.event.ShowMyLeaguesEvent;
import org.javahispano.javaleague.client.helper.RPCCall;
import org.javahispano.javaleague.client.resources.messages.JavaLeagueMessages;
import org.javahispano.javaleague.client.service.LeagueServiceAsync;
import org.javahispano.javaleague.shared.LeagueDTO;
import org.javahispano.javaleague.shared.UserDTO;

import com.google.gwt.core.shared.GWT;
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
public class ShowLeaguePresenter implements Presenter {
	public interface Display {
		Widget asWidget();

		HasClickHandlers getJoinLeagueButton();

		HasClickHandlers getDropLeagueButton();

		HasClickHandlers getEditLeagueButton();

		Paragraph getDescriptionLeague();

		Heading getNameLeague();

	}

	private final SimpleEventBus eventBus;
	private final Display display;
	private final LeagueServiceAsync leagueService;

	private LeagueDTO leagueDTO;

	private JavaLeagueMessages javaLeagueMessages = GWT
			.create(JavaLeagueMessages.class);

	public ShowLeaguePresenter(LeagueServiceAsync leagueService,
			LeagueDTO leagueDTO, SimpleEventBus eventBus, Display display) {
		this.leagueService = leagueService;
		this.leagueDTO = leagueDTO;
		this.eventBus = eventBus;
		this.display = display;

		ShowLeague();
	}

	private void ShowLeague() {
		display.getDescriptionLeague().setHTML(leagueDTO.getDescription());
		display.getNameLeague().setText(leagueDTO.getName());
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
				leagueService.dropLeague(leagueDTO.getId(), cb);
			}

		}.retry(3);
	}
}
