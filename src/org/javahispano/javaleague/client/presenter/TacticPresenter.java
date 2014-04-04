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
import org.javahispano.javaleague.client.service.BlobstoreServiceAsync;
import org.javahispano.javaleague.client.service.MatchServiceAsync;
import org.javahispano.javaleague.client.service.TacticServiceAsync;
import org.javahispano.javaleague.client.service.UserFileServiceAsync;
import org.javahispano.javaleague.shared.domain.TacticUser;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.i18n.client.DateTimeFormat;
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

		FileUpload getFileUpload();

	}

	private TacticUser tactic;
	private final SimpleEventBus eventBus;
	private final Display display;
	private final TacticServiceAsync tacticService;
	private final MatchServiceAsync matchService;
	private final UserFileServiceAsync userFileService;
	private final BlobstoreServiceAsync blobstoreService;

	private JavaLeagueMessages javaLeagueMessages = GWT
			.create(JavaLeagueMessages.class);

	public TacticPresenter(TacticServiceAsync tacticService,
			MatchServiceAsync matchService,
			UserFileServiceAsync userFileService,
			BlobstoreServiceAsync blobstoreService, SimpleEventBus eventBus,
			Display display) {
		this.display = display;
		this.eventBus = eventBus;
		this.tacticService = tacticService;
		this.matchService = matchService;
		this.userFileService = userFileService;
		this.blobstoreService = blobstoreService;

		hideErrorLabel();

		display.getFormPanelTactic().setEncoding(FormPanel.ENCODING_MULTIPART);
		display.getFormPanelTactic().setMethod(FormPanel.METHOD_POST);
		display.getFileUpload().setName("fileUpload");

		startNewBlobstoreSession();

		display.getFormPanelTactic().addSubmitCompleteHandler(
				new FormPanel.SubmitCompleteHandler() {

					@Override
					public void onSubmitComplete(SubmitCompleteEvent event) {
						startNewBlobstoreSession();
					}
				});
		bind();
	}

	private void startNewBlobstoreSession() {
		blobstoreService.getUploadGCSURL(new AsyncCallback<String>() {
			public void onFailure(Throwable error) {
				GWT.log("TacticPresenter: Error blobstoreService.getUploadGCSURL");
			}

			public void onSuccess(String url) {
				GWT.log("URL: " + url);
				display.getFormPanelTactic().setAction(url);
			}
		});

	}

	public void bind() {

		display.getUploader().addOnFinishUploadHandler(onFinishUploaderHandler);

		display.getUpdateButton().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				GWT.log("TacticPresenter: Firing UpdateTacticEvent");
				eventBus.fireEvent(new UpdateTacticEvent(tactic));

				display.getFormPanelTactic().submit();

				//doUpdateTactic();
			}
		});

		display.getPlayMatchButton().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				GWT.log("TacticPresenter: Firing PlayMatchEvent");
				eventBus.fireEvent(new PlayMatchEvent(tactic.getId()));

				doPlayMatch();
			}
		});
	}

	private IUploader.OnFinishUploaderHandler onFinishUploaderHandler = new IUploader.OnFinishUploaderHandler() {
		public void onFinish(IUploader uploader) {
			if (uploader.getStatus() == Status.SUCCESS) {
				GWT.log("TacticPresenter: Firing UpdateTacticEvent");

				String msg = uploader.getServerMessage().getMessage();

				display.getFileName().setText(
						msg.substring(0, msg.indexOf('|')));
				display.getUpdatedTactic().setText(
						msg.substring(msg.indexOf('|') + 1));

				eventBus.fireEvent(new UpdateTacticEvent(tactic));
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

			tactic.setTeamName(display.getTeamName().getValue());

			new RPCCall<TacticUser>() {
				@Override
				protected void callService(AsyncCallback<TacticUser> cb) {
					tacticService.updateTactic(tactic, cb);
				}

				@Override
				public void onFailure(Throwable caught) {
					Window.alert("Error fetching tactic summary: "
							+ caught.getMessage());

				}

				@Override
				public void onSuccess(TacticUser result) {
					if (result != null) {
						tactic = result;

						DateTimeFormat fmt = DateTimeFormat
								.getFormat("dd/MM/yyyy :: HH:mm:ss");

						display.setVisibleUpdateButton(true);
						display.getUpdatedTactic().setText(
								fmt.format(result.getUpdated()));
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
						+ tactic.getId() + ": " + caught.getMessage());
			}

			@Override
			public void onSuccess(Object result) {
				GWT.log("Dispathing Match for Tactic: " + tactic.getId());

			}

			@Override
			protected void callService(AsyncCallback cb) {
				matchService.dispatchMatch(tactic.getId(), cb);
			}

		}.retry(3);

	}

	@Override
	public void go(final HasWidgets container) {
		container.clear();
		container.add(display.asWidget());
		fetchTacticSummary();
	}

	private void fetchTacticSummary() {

		new RPCCall<TacticUser>() {
			@Override
			protected void callService(AsyncCallback<TacticUser> cb) {
				tacticService.getTacticUserLogin(cb);
			}

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Error fetching tactic summary: "
						+ caught.getMessage());

			}

			@Override
			public void onSuccess(TacticUser result) {
				if (result != null) {
					tactic = result;

					DateTimeFormat fmt = DateTimeFormat
							.getFormat("dd/MM/yyyy :: HH:mm:ss");

					display.setVisibleUpdateButton(true);
					display.getUpdatedTactic().setText(
							fmt.format(result.getUpdated()));
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
