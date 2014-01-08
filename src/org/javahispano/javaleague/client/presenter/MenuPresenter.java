package org.javahispano.javaleague.client.presenter;

import org.gwtbootstrap3.client.ui.TextBox;
import org.javahispano.javaleague.client.event.ShowRegisterUserEvent;
import org.javahispano.javaleague.client.helper.RPCCall;
import org.javahispano.javaleague.client.service.UserAccountServiceAsync;
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
 * 
 * @author adou
 * 
 */
public class MenuPresenter implements Presenter {

	public interface Display {

		HasClickHandlers getRegisterLink();

		HasClickHandlers getLoginButton();

		TextBox getEmailTextBox();

		TextBox getPasswordTextBox();

		Widget asWidget();

	}

	private final Display display;
	private final SimpleEventBus eventBus;
	private final UserAccountServiceAsync userAccountService;

	public MenuPresenter(SimpleEventBus eventBus,
			UserAccountServiceAsync userAccountService, Display display) {
		this.eventBus = eventBus;
		this.display = display;
		this.userAccountService = userAccountService;
	}

	public void bind() {

		this.display.getRegisterLink().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				GWT.log("MenuPresenter: Firing ShowRegisterUserEvent");
				eventBus.fireEvent(new ShowRegisterUserEvent());
			}
		});

		this.display.getLoginButton().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				GWT.log("MenuPresenter: Click LoginButton");
				doLogin();
			}
		});

	}

	private void doLogin() {
		new RPCCall<UserDTO>() {
			@Override
			protected void callService(AsyncCallback<UserDTO> cb) {
				userAccountService.login(display.getEmailTextBox().getValue(),
						display.getPasswordTextBox().getValue(), cb);
			}

			@Override
			public void onSuccess(UserDTO result) {
				GWT.log("MenuPresenter: Firing UserLoginEvent");
				eventBus.fireEvent(new ShowRegisterUserEvent());
			}

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Error user login ...");
			}
		}.retry(3);
	}

	@Override
	public void go(HasWidgets container) {
		container.clear();
		container.add(display.asWidget());
		bind();
	}

}
