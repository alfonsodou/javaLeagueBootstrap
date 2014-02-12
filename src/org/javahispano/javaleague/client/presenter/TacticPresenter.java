/**
 * 
 */
package org.javahispano.javaleague.client.presenter;

import gwtupload.client.IUploadStatus.Status;
import gwtupload.client.IUploader;
import gwtupload.client.SingleUploader;

import org.gwtbootstrap3.client.ui.Badge;
import org.gwtbootstrap3.client.ui.Form;
import org.gwtbootstrap3.client.ui.Label;
import org.gwtbootstrap3.client.ui.TextBox;
import org.javahispano.javaleague.client.event.PlayMatchEvent;
import org.javahispano.javaleague.client.event.UpdateTacticEvent;
import org.javahispano.javaleague.client.helper.RPCCall;
import org.javahispano.javaleague.client.resources.messages.JavaLeagueMessages;
import org.javahispano.javaleague.client.service.MatchServiceAsync;
import org.javahispano.javaleague.client.service.TacticServiceAsync;
import org.javahispano.javaleague.client.service.UserFileServiceAsync;
import org.javahispano.javaleague.shared.TacticDTO;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FormPanel;
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

		void setVisibleUpdateButton(boolean visible);

		Label getErrorTeamName();

		TextBox getTeamName();

		FormPanel getFormPanelTactic();

		Form getFormTactic();

		SingleUploader getUploader();

		Badge getFileName();

		Label getUpdatedTactic();

	}

	private TacticDTO tacticDTO;
	private final SimpleEventBus eventBus;
	private final Display display;
	private final TacticServiceAsync tacticService;
	private final MatchServiceAsync matchService;
	private final UserFileServiceAsync userFileService;

	private JavaLeagueMessages javaLeagueMessages = GWT
			.create(JavaLeagueMessages.class);

	public TacticPresenter(TacticServiceAsync tacticService,
			MatchServiceAsync matchService,
			UserFileServiceAsync userFileService, SimpleEventBus eventBus,
			Display display) {
		this.display = display;
		this.eventBus = eventBus;
		this.tacticService = tacticService;
		this.matchService = matchService;
		this.userFileService = userFileService;

		hideErrorLabel();

		bind();
	}

	public void bind() {

		display.getUploader().addOnFinishUploadHandler(onFinishUploaderHandler);

		display.getUpdateButton().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				GWT.log("TacticPresenter: Firing UpdateTacticEvent");
				eventBus.fireEvent(new UpdateTacticEvent(tacticDTO));

				doUpdateTactic();
			}
		});

		display.getPlayMatchButton().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				GWT.log("TacticPresenter: Firing PlayMatchEvent");
				eventBus.fireEvent(new PlayMatchEvent(tacticDTO.getId()));

				doPlayMatch();
			}
		});
	}

	private IUploader.OnFinishUploaderHandler onFinishUploaderHandler = new IUploader.OnFinishUploaderHandler() {
		public void onFinish(IUploader uploader) {
			if (uploader.getStatus() == Status.SUCCESS) {
				GWT.log("TacticPresenter: Firing UpdateTacticEvent");
				eventBus.fireEvent(new UpdateTacticEvent(tacticDTO));

				String[] parts = uploader.getServerMessage().getMessage().split("#"); 
				display.getFileName().setText(parts[0]);
				display.getUpdatedTactic().setText(parts[1]);
			}
		}
	};

	private void doUpdateTactic() {
		boolean error = false;

		hideErrorLabel();

		if (display.getTeamName().getValue().isEmpty()) {
			display.getErrorTeamName().setVisible(true);
			error = true;
		}

		if (!error) {

			tacticDTO.setTeamName(display.getTeamName().getValue());

			new RPCCall<TacticDTO>() {
				@Override
				protected void callService(AsyncCallback<TacticDTO> cb) {
					tacticService.updateTactic(tacticDTO, cb);
				}

				@Override
				public void onFailure(Throwable caught) {
					Window.alert("Error fetching tactic summary: "
							+ caught.getMessage());

				}

				@Override
				public void onSuccess(TacticDTO result) {
					if (result != null) {
						tacticDTO = result;

						display.setVisibleUpdateButton(true);
						display.getUpdatedTactic().setText(result.getUpdated());
						display.setTeamName(result.getTeamName());
						display.setGoalsAgainst(Integer.toString(result
								.getGoalsAgainst()));
						display.setGoalsFor(Integer.toString(result
								.getGoalsFor()));
						display.setMatchLost(Integer.toString(result
								.getMatchLost()));
						display.setMatchTied(Integer.toString(result
								.getMatchTied()));
						display.setMatchWins(Integer.toString(result
								.getMatchWins()));

						if (result.getFileName() != null) {
							display.getFileName().setText(
									result.getFileName() + " :: "
											+ result.getBytes() + " bytes");
						} else {
							display.getFileName().setText(
									javaLeagueMessages.emptyUserTactic());
						}

					} else {
						display.setVisibleUpdateButton(false);
					}
				}

			}.retry(3);

		}
	}

	private void doPlayMatch() {
		new RPCCall() {

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Error dispatching match for Tactic "
						+ tacticDTO.getId() + ": " + caught.getMessage());
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

	@Override
	public void go(final HasWidgets container) {
		container.clear();
		container.add(display.asWidget());
		fetchTacticSummaryDTO();
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
				if (result != null) {
					tacticDTO = result;
					display.setVisibleUpdateButton(true);
					display.getUpdatedTactic().setText(result.getUpdated());
					display.setTeamName(result.getTeamName());
					display.setGoalsAgainst(Integer.toString(result
							.getGoalsAgainst()));
					display.setGoalsFor(Integer.toString(result.getGoalsFor()));
					display.setMatchLost(Integer.toString(result.getMatchLost()));
					display.setMatchTied(Integer.toString(result.getMatchTied()));
					display.setMatchWins(Integer.toString(result.getMatchWins()));

					if (result.getFileName() != null) {
						display.getFileName().setText(
								result.getFileName() + " :: "
										+ result.getBytes() + " bytes");
					} else {
						display.getFileName().setText(
								javaLeagueMessages.emptyUserTactic());
					}
				} else {
					display.setVisibleUpdateButton(false);
				}
			}

		}.retry(3);

	}

	private void hideErrorLabel() {
		display.getErrorTeamName().setVisible(false);
	}
}
