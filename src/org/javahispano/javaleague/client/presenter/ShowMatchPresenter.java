package org.javahispano.javaleague.client.presenter;

import org.javahispano.javaleague.client.helper.RPCCall;
import org.javahispano.javaleague.client.service.MatchServiceAsync;
import org.javahispano.javaleague.shared.MatchDTO;

import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;

public class ShowMatchPresenter implements Presenter {

	public interface Display {
		Widget asWidget();

		void setMatchID(String matchID);

		void setDate(String date);

		void setDateMatch(String dateMatch);

	}

	private final SimpleEventBus eventBus;
	private final Display display;
	private final MatchServiceAsync matchService;
	private final String matchID;
	private String date;
	private String dateMatch;

	private MatchDTO matchDTO;

	public ShowMatchPresenter(final MatchServiceAsync matchService,
			SimpleEventBus eventBus, Display display, final String matchID,
			final String date) {
		this.matchService = matchService;
		this.eventBus = eventBus;
		this.display = display;
		this.matchID = matchID;
		this.date = date;

		bind();
	}

	public void bind() {

	}

	@Override
	public void go(final HasWidgets container) {
		//fetchMatchSummaryDTO();
		container.clear();
		container.add(display.asWidget());
		
		display.setMatchID(matchID);
		display.setDate(date);	

	}

	private void fetchMatchSummaryDTO() {
		new RPCCall<MatchDTO>() {

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Error fetching match summary: "
						+ caught.getMessage());
			}

			@Override
			public void onSuccess(MatchDTO result) {
				matchDTO = result;
				display.setDateMatch(matchDTO.getVisualization());
				display.setMatchID(matchID);
				display.setDate(date);				
			}

			@Override
			protected void callService(AsyncCallback<MatchDTO> cb) {
				matchService.getMatchById(Long.parseLong(matchID), cb);

			}

		}.retry(3);
		
	}
	
}
