/**
 * 
 */
package org.javahispano.javaleague.client.presenter;

import java.util.List;

import org.javahispano.javaleague.client.event.CreateLeagueEvent;
import org.javahispano.javaleague.client.helper.RPCCall;
import org.javahispano.javaleague.client.service.LeagueServiceAsync;
import org.javahispano.javaleague.client.service.MatchServiceAsync;
import org.javahispano.javaleague.client.service.TacticServiceAsync;
import org.javahispano.javaleague.shared.LeagueDTO;

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
public class MyLeaguesPresenter implements Presenter {

	public interface Display {
		Widget asWidget();

		HasClickHandlers getCreateLeagueButton();

		void setListMyLeagues(List<LeagueDTO> leaguesDTO);
	}

	private final SimpleEventBus eventBus;
	private final Display display;
	private final TacticServiceAsync tacticService;
	private final MatchServiceAsync matchService;
	private final LeagueServiceAsync leagueService;

	private List<LeagueDTO> leaguesDTO;

	public MyLeaguesPresenter(TacticServiceAsync tacticService,
			MatchServiceAsync matchService, LeagueServiceAsync leagueService,
			SimpleEventBus eventBus, Display display) {
		this.display = display;
		this.eventBus = eventBus;
		this.tacticService = tacticService;
		this.leagueService = leagueService;
		this.matchService = matchService;

	}

	@Override
	public void go(HasWidgets container) {
		container.clear();
		container.add(display.asWidget());

		bind();
	}

	private void fetchLeaguesDTO() {
		new RPCCall<List<LeagueDTO>>() {

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Error fetching leagues: "
						+ caught.getMessage());
			}

			@Override
			public void onSuccess(List<LeagueDTO> result) {
				if (result.size() > 0) {
					leaguesDTO = result;
					
					display.setListMyLeagues(leaguesDTO);
				}
				
			}

			@Override
			protected void callService(AsyncCallback<List<LeagueDTO>> cb) {
				leagueService.getMyLeagues(cb);
			}
			
		}.retry(3);
	}

	private void bind() {
		this.display.getCreateLeagueButton().addClickHandler(
				new ClickHandler() {
					public void onClick(ClickEvent event) {
						GWT.log("MyLeaguesPresenter: firing CreateLeagueEvent");
						eventBus.fireEvent(new CreateLeagueEvent());
					}
				});

	}

}
