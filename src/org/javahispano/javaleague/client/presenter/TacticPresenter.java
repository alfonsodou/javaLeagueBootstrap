/**
 * 
 */
package org.javahispano.javaleague.client.presenter;

import org.javahispano.javaleague.client.event.PlayMatchEvent;
import org.javahispano.javaleague.client.event.TacticEditEvent;
import org.javahispano.javaleague.client.helper.RPCCall;
import org.javahispano.javaleague.client.service.MatchServiceAsync;
import org.javahispano.javaleague.client.service.TacticServiceAsync;
import org.javahispano.javaleague.shared.TacticDTO;

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
public class TacticPresenter implements Presenter {

	public interface Display {
		Widget asWidget();

		HasClickHandlers getUpdateButton();

		void setTeamName(String teamName);
		
		void setMatchWins(String matchWins);
		
		void setMatchLost(String matchLost);
		
		void setMatchTied(String matchTied);
		
		void setGoalsFor(String goalsFor);
		
		void setGoalsAgainst(String goalsAgainst);

		HasClickHandlers getPlayMatchButton();
	}

	private TacticDTO tacticDTO;
	private final SimpleEventBus eventBus;
	private final Display display;
	private final TacticServiceAsync tacticService;
	private final MatchServiceAsync matchService;

	public TacticPresenter(TacticServiceAsync tacticService, MatchServiceAsync matchService,
			SimpleEventBus eventBus, Display display) {
		this.display = display;
		this.eventBus = eventBus;
		this.tacticService = tacticService;
		this.matchService = matchService;

		bind();
	}

	public void bind() {
		this.display.getUpdateButton().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				eventBus.fireEvent(new TacticEditEvent(tacticDTO.getId()));
			}
		});
		
		this.display.getPlayMatchButton().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				eventBus.fireEvent(new PlayMatchEvent(tacticDTO.getId()));
				
				new RPCCall() {

					@Override
					public void onFailure(Throwable caught) {
						Window.alert("Error dispatching match: "
								+ caught.getMessage());
					}

					@Override
					public void onSuccess(Object result) {
						GWT.log("Dispathing Match for Tactic: " + tacticDTO.getId());
						
					}

					@Override
					protected void callService(AsyncCallback cb) {					
						matchService.dispatchMatch(tacticDTO.getId(), cb);
					}
					
				}.retry(3);
			}
		});
		
	}

	@Override
	public void go(final HasWidgets container) {
		container.clear();
		container.add(display.asWidget());
		// fetchTacticSummaryDTO();
	}

	private void fetchTacticSummaryDTO() {

		new RPCCall<TacticDTO>() {
			@Override
			protected void callService(AsyncCallback<TacticDTO> cb) {
				tacticService.getUserTacticSummary(cb);
			}

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Error fetching tactic summary: "
						+ caught.getMessage());

			}

			@Override
			public void onSuccess(TacticDTO result) {
				tacticDTO = result;
				display.setTeamName(result.getTeamName());
				display.setGoalsAgainst(Integer.toString(result.getGoalsAgainst()));
				display.setGoalsFor(Integer.toString(result.getGoalsFor()));
				display.setMatchLost(Integer.toString(result.getMatchLost()));
				display.setMatchTied(Integer.toString(result.getMatchTied()));
				display.setMatchWins(Integer.toString(result.getMatchWins()));
			}

		}.retry(3);

	}

}
