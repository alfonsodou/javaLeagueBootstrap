package org.javahispano.javaleague.client.presenter;

import org.gwtbootstrap3.client.ui.Form;
import org.javahispano.javaleague.client.event.CancelUpdateTacticEvent;
import org.javahispano.javaleague.client.event.UpdateTacticEvent;
import org.javahispano.javaleague.client.helper.RPCCall;
import org.javahispano.javaleague.client.service.TacticServiceAsync;
import org.javahispano.javaleague.client.service.UserFileServiceAsync;
import org.javahispano.javaleague.shared.TacticDTO;
import org.javahispano.javaleague.shared.UserDTO;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;


public class TacticEditPresenter implements Presenter {

	public interface Display {
		Widget asWidget();

		// HasClickHandlers getUploadButton();

		HasClickHandlers getCancelButton();

		HasClickHandlers getUpdateButton();

		// HasValue<String> getPackageName();

		HasValue<String> getTeamName();

		// FormPanel getFormPanel();

		FileUpload getFileUpload();

		Form getFormPanel();

		void setTacticId(String Id);

		HasValue<String> getTacticId();

		// HasClickHandlers getList();

		// String getDeletedClass(ClickEvent event);

		// int getIndexDeletedClass(ClickEvent event);

		// void setData(Vector<TacticClassDTO> classes);

		// void setTacticId(String tacticId);
	}

	private TacticDTO userTactic;
	private String userTacticID;
	private UserDTO currentUser;
	private final TacticServiceAsync tacticService;
	private final UserFileServiceAsync userFileService;
	private final SimpleEventBus eventBus;
	private final Display display;

	public TacticEditPresenter(TacticServiceAsync tacticService,
			UserFileServiceAsync userFileService, SimpleEventBus eventBus,
			Display display) {
		this.eventBus = eventBus;
		this.tacticService = tacticService;
		this.userFileService = userFileService;
		this.display = display;
		this.userTactic = new TacticDTO();
		bind();
	}

	public TacticEditPresenter(final TacticServiceAsync rpcService,
			final UserFileServiceAsync userFileService,
			SimpleEventBus eventBus, Display display, final String id) {
		this(rpcService, userFileService, eventBus, display);

		if (id == null) {
			return;
		}

		userTacticID = id;

		new RPCCall<TacticDTO>() {
			@Override
			protected void callService(AsyncCallback<TacticDTO> cb) {
				rpcService.getTactic(id, cb);
			}

			@Override
			public void onSuccess(TacticDTO result) {
				userTactic = result;

				TacticEditPresenter.this.display.getTeamName().setValue(
						userTactic.getTeamName());
				TacticEditPresenter.this.display.getTacticId().setValue(id);
	
			}

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Error fetching user tactic: "
						+ caught.getMessage());
			}
		}.retry(3);

		startNewBlobstoreSession();

		/*
		TacticEditPresenter.this.display.getFormPanel()
				.addSubmitCompleteHandler(new Form.SubmitCompleteHandler() {

					@Override
					public void onSubmitComplete(
							com.svenjacobs.gwtbootstrap3.client.ui.Form.SubmitCompleteEvent event) {
						TacticEditPresenter.this.display.getFormPanel().reset();
					}
				});
*/
	}

	private void startNewBlobstoreSession() {
		new RPCCall() {

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Error uploading class...");

			}

			@Override
			public void onSuccess(Object result) {
				/*
				TacticEditPresenter.this.display.getFormPanel().setAction(
						(String) result);
				GWT.log("TacticEditPresenter -> setAction: " + result);
				*/
			}

			@Override
			protected void callService(AsyncCallback cb) {
				userFileService.getBlobstoreUploadUrl(cb);
			}

		}.retry(3);

	}

	public void bind() {

		this.display.getUpdateButton().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				doSave();
			}
		});

		this.display.getCancelButton().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				GWT.log("TacticEditPresenter: Firing CancelUpdateTacticEvent");
				eventBus.fireEvent(new CancelUpdateTacticEvent());
			}
		});
	}

	private void doSave() {
		//TacticEditPresenter.this.display.getFormPanel().submit();
		userTactic.setTeamName(display.getTeamName().getValue().trim());

		new RPCCall<TacticDTO>() {
			@Override
			protected void callService(AsyncCallback<TacticDTO> cb) {
				tacticService.updateTactic(userTactic, cb);
			}

			@Override
			public void onSuccess(TacticDTO result) {
				GWT.log("TacticEditPresenter: Firing UpdateTacticEvent");
				eventBus.fireEvent(new UpdateTacticEvent(result));
			}

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Error retrieving tactic...");
			}
		}.retry(3);
	}

	@Override
	public void go(final HasWidgets container) {
		container.clear();
		container.add(display.asWidget());
	}

	public UserDTO getCurrentUser() {
		return currentUser;
	}

	public void setCurrentUser(UserDTO currentUser) {
		this.currentUser = currentUser;
	}

}
