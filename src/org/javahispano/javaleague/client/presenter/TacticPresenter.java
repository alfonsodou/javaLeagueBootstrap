/**
 * 
 */
package org.javahispano.javaleague.client.presenter;

import gwtupload.client.IUploadStatus.Status;
import gwtupload.client.IUploader;
import gwtupload.client.IUploader.UploadedInfo;
import gwtupload.client.SingleUploader;

import org.gwtbootstrap3.client.ui.Form;
import org.gwtbootstrap3.client.ui.Label;
import org.gwtbootstrap3.client.ui.TextBox;
import org.javahispano.javaleague.client.event.AddTacticEvent;
import org.javahispano.javaleague.client.event.PlayMatchEvent;
import org.javahispano.javaleague.client.event.TacticEditEvent;
import org.javahispano.javaleague.client.event.UpdateTacticEvent;
import org.javahispano.javaleague.client.helper.RPCCall;
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
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteEvent;
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

		HasClickHandlers getAddButton();

		void setTeamName(String teamName);

		void setMatchWins(String matchWins);

		void setMatchLost(String matchLost);

		void setMatchTied(String matchTied);

		void setGoalsFor(String goalsFor);

		void setGoalsAgainst(String goalsAgainst);

		HasClickHandlers getPlayMatchButton();

		void setVisibleUpdateButton(boolean visible);

		void setVisibleAddButton(boolean visible);

		Label getErrorTeamName();

		TextBox getTeamName();

		Label getErrorFileUpload();

		FormPanel getFormPanelTactic();

		Form getFormTactic();

		SingleUploader getUploader();
		
		Label getFileName();

	}

	private TacticDTO tacticDTO;
	private final SimpleEventBus eventBus;
	private final Display display;
	private final TacticServiceAsync tacticService;
	private final MatchServiceAsync matchService;
	private final UserFileServiceAsync userFileService;

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

		// startNewBlobstoreSession();

	}

	public void bind() {
		/*
		 * display.getFileUpload().addChangeHandler(new ChangeHandler() {
		 * 
		 * @Override public void onChange(ChangeEvent event) {
		 * startNewBlobstoreSession(); display.getFormPanelTactic().submit(); }
		 * });
		 */

		TextBox teamNameTextBox = new TextBox();
		Label teamNameLabel = new Label();

		teamNameLabel.setText("Nombre del Equipo:");
		display.getUploader().add(teamNameLabel, 0);
		display.getUploader().add(teamNameTextBox, 1);

		display.getUploader().addOnFinishUploadHandler(onFinishUploaderHandler);

		display.getFormPanelTactic().addSubmitCompleteHandler(
				new FormPanel.SubmitCompleteHandler() {
					@Override
					public void onSubmitComplete(SubmitCompleteEvent event) {
						Window.alert("Submit Complete!");

						GWT.log("TacticPresenter: Firing UpdateTacticEvent");
						eventBus.fireEvent(new UpdateTacticEvent(tacticDTO));

						// startNewBlobstoreSession();
					}
				});

		display.getUpdateButton().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				eventBus.fireEvent(new TacticEditEvent(tacticDTO.getId()));

				doUpdateTactic();
			}
		});

		display.getAddButton().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				doAddTactic();

				eventBus.fireEvent(new AddTacticEvent(tacticDTO.getId()));
			}
		});

		display.getPlayMatchButton().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				eventBus.fireEvent(new PlayMatchEvent(tacticDTO.getId()));

				doPlayMatch();
			}
		});

	}

	private IUploader.OnFinishUploaderHandler onFinishUploaderHandler = new IUploader.OnFinishUploaderHandler() {
		public void onFinish(IUploader uploader) {
			if (uploader.getStatus() == Status.SUCCESS) {
				UploadedInfo info = uploader.getServerInfo();
				
				display.getFileName().setText(info.name + " :: " + info.size);
				
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

/*		if (display.getFileUpload().getFilename().isEmpty()) {
			display.getErrorFileUpload().setVisible(true);
			error = true;
		}*/

		if (!error) {
			tacticDTO = new TacticDTO();
			tacticDTO.setTeamName(display.getTeamName().getValue());

			display.getFormPanelTactic().submit();

		}
	}

	private void doAddTactic() {
		boolean error = false;

		hideErrorLabel();

		if (display.getTeamName().getValue().isEmpty()) {
			display.getErrorTeamName().setVisible(true);
			error = true;
		}

/*		if (display.getFileUpload().getFilename().isEmpty()) {
			display.getErrorFileUpload().setVisible(true);
			error = true;
		}*/

		if (!error) {
			tacticDTO = new TacticDTO();
			tacticDTO.setTeamName(display.getTeamName().getValue());

			// Window.alert("doAddTactic: " +
			// display.getFormPanelTactic().getAction());
			startNewBlobstoreSession();

			display.getFormPanelTactic().submit();

			/*
			 * new RPCCall<TacticDTO>() {
			 * 
			 * @Override protected void callService(AsyncCallback<TacticDTO> cb)
			 * { tacticService.updateTactic(tacticDTO, cb); }
			 * 
			 * @Override public void onSuccess(TacticDTO result) {
			 * GWT.log("TacticEditPresenter: Firing UpdateTacticEvent");
			 * eventBus.fireEvent(new UpdateTacticEvent(result)); }
			 * 
			 * @Override public void onFailure(Throwable caught) {
			 * Window.alert("Error retrieving tactic..."); } }.retry(3);
			 */
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
					display.setVisibleAddButton(false);
					display.setTeamName(result.getTeamName());
					display.setGoalsAgainst(Integer.toString(result
							.getGoalsAgainst()));
					display.setGoalsFor(Integer.toString(result.getGoalsFor()));
					display.setMatchLost(Integer.toString(result.getMatchLost()));
					display.setMatchTied(Integer.toString(result.getMatchTied()));
					display.setMatchWins(Integer.toString(result.getMatchWins()));
				} else {
					display.setVisibleUpdateButton(false);
					display.setVisibleAddButton(true);
				}
			}

		}.retry(3);

	}

	private void startNewBlobstoreSession() {

		new RPCCall<String>() {

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Error uploading jar file...");

			}

			@Override
			public void onSuccess(String result) {
				GWT.log("TacticPresenter -> setAction: " + result);

				// Window.alert("Action: " + result);

				display.getFormPanelTactic().setAction(result);
				display.getFormPanelTactic().setEncoding(
						FormPanel.ENCODING_MULTIPART);
				display.getFormPanelTactic().setMethod(FormPanel.METHOD_POST);
			}

			@Override
			protected void callService(AsyncCallback<String> cb) {
				userFileService.getBlobstoreUploadUrl(cb);
			}

		}.retry(3);

	}

	private void hideErrorLabel() {
		display.getErrorTeamName().setVisible(false);
		display.getErrorFileUpload().setVisible(false);
	}
}
