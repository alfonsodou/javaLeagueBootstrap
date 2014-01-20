package org.javahispano.javaleague.client.presenter;

import org.gwtbootstrap3.client.ui.Input;
import org.gwtbootstrap3.client.ui.TextBox;
import org.javahispano.javaleague.client.event.LoginEvent;
import org.javahispano.javaleague.client.event.ShowHomeEvent;
import org.javahispano.javaleague.client.event.ShowRegisterUserEvent;
import org.javahispano.javaleague.client.helper.RPCCall;
import org.javahispano.javaleague.client.resources.messages.JavaLeagueMessages;
import org.javahispano.javaleague.client.service.UserAccountServiceAsync;
import org.javahispano.javaleague.shared.UserDTO;

import com.google.gwt.core.client.GWT;
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
public class LoginPresenter implements Presenter {

	public interface Display {
		HasClickHandlers getLoginButton();

		HasClickHandlers getCancelButton();

		HasClickHandlers getRegisterUserButton();

		TextBox getEmailTextBox();

		Input getPasswordTextBox();

		Widget asWidget();
	}

	private final Display display;
	private final UserAccountServiceAsync userAccountService;
	private final SimpleEventBus eventBus;

	private JavaLeagueMessages javaLeagueMessages = GWT
			.create(JavaLeagueMessages.class);

	public LoginPresenter(UserAccountServiceAsync userAccountService,
			SimpleEventBus eventBus, Display display) {
		this.userAccountService = userAccountService;
		this.eventBus = eventBus;
		this.display = display;
	}

	public void bind() {

		this.display.getLoginButton().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				GWT.log("Click on Login Button!");
				doLogin();
			}
		});

		this.display.getCancelButton().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				GWT.log("Click on Cancel Button!");
				doCancel();
			}
		});

		this.display.getRegisterUserButton().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				GWT.log("Click on RegisterUser Button!");
				doShowRegisterUser();
			}
		});


	}

	public void go(final HasWidgets container) {
		container.add(display.asWidget());
		bind();
	}

	private void doCancel() {
		GWT.log("LoginPresenter: Firing ShowHomeEvent");
		eventBus.fireEvent(new ShowHomeEvent());
	}

	private void doShowRegisterUser() {
		GWT.log("LoginPresenter: Firing ShowRegisterUserEvent");
		eventBus.fireEvent(new ShowRegisterUserEvent());
	}

	private void doLogin() {
		new RPCCall<UserDTO>() {
			@Override
			protected void callService(AsyncCallback<UserDTO> cb) {
				userAccountService.login(display.getEmailTextBox().getValue(),
						display.getPasswordTextBox().getFormValue(), cb);
			}

			@Override
			public void onSuccess(UserDTO result) {
				if (result != null) {
					GWT.log("LoginPresenter: Firing LoginEvent");
					eventBus.fireEvent(new LoginEvent(result));
				} else {
					Window.alert(javaLeagueMessages.errorEmailPassword());
				}
			}

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Error user login ...");
			}
		}.retry(3);
	}

}
