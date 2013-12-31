/**
 * 
 */
package org.javahispano.javaleague.client.presenter;

import org.gwtbootstrap3.client.ui.Alert;
import org.gwtbootstrap3.client.ui.Form;
import org.gwtbootstrap3.client.ui.Label;
import org.gwtbootstrap3.client.ui.TextBox;
import org.javahispano.javaleague.client.event.RegisterUserEvent;
import org.javahispano.javaleague.client.helper.RPCCall;
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
 * @author adou
 * 
 */
public class RegisterUserPresenter implements Presenter {

	public interface Display {
		HasClickHandlers getCancelButton();

		Widget asWidget();

		HasClickHandlers getRegisterButton();

		TextBox getUserName();

		TextBox getEmail();

		TextBox getPassword();

		TextBox getRePassword();

		Label getErrorEmail();

		Label getErrorPassword();

		Label getErrorPasswordSize();

		Label getErrorUserName();

		Label getErrorRegisterEmail();

		Form getFormRegisterUser();

		Alert getAlertSendEmail();
	}

	private final Display display;
	private final UserDTO userDTO;
	private final SimpleEventBus eventBus;
	private final UserAccountServiceAsync userAccountService;

	public RegisterUserPresenter(UserAccountServiceAsync userAccountService,
			SimpleEventBus eventBus, Display display) {
		this.display = display;
		this.eventBus = eventBus;
		this.userAccountService = userAccountService;
		userDTO = new UserDTO();
		this.display.getAlertSendEmail().setVisible(false);

		hideErrorLabel();
	}

	public void bind() {
		this.display.getCancelButton().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				GWT.log("RegisterUserPresenter: Click on Cancel Button!");
				doCancel();
			}
		});

		this.display.getRegisterButton().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				GWT.log("RegisterUserPresenter: Click on Register Button!");
				doRegister();
			}
		});

	}

	@Override
	public void go(HasWidgets container) {
		container.add(display.asWidget());
		bind();
	}

	private void doCancel() {

	}

	private void doRegister() {

		boolean error = false;

		hideErrorLabel();

		if (this.display.getUserName().getValue().length() < 4) {
			this.display.getErrorUserName().setVisible(true);
			error = true;
		}

/*		if (!validateEmail(this.display.getEmail().getValue())) {
			this.display.getErrorEmail().setVisible(true);
			error = true;
		}
*/
		if (this.display.getPassword().getValue().length() < 4) {
			this.display.getErrorPasswordSize().setVisible(true);
			error = true;
		}

		if (!this.display.getPassword().getValue()
				.equals(this.display.getRePassword().getValue())) {
			this.display.getErrorPassword().setVisible(true);
			error = true;
		}

		if (!error) {
			userDTO.setEmailAddress(this.display.getEmail().getValue());
			userDTO.setName(this.display.getUserName().getValue());
			userDTO.setPassword(this.display.getPassword().getValue());
			new RPCCall<UserDTO>() {
				@Override
				protected void callService(AsyncCallback<UserDTO> cb) {
					userAccountService.register(userDTO, cb);
				}

				@Override
				public void onSuccess(UserDTO result) {
					if (result != null) {
						GWT.log("RegisterUserPresenter: Firing RegisterUserEvent");
						eventBus.fireEvent(new RegisterUserEvent(result));
						display.getFormRegisterUser().setVisible(false);
						display.getAlertSendEmail().setVisible(true);

					} else {
						display.getErrorRegisterEmail().setVisible(true);
					}
				}

				@Override
				public void onFailure(Throwable caught) {
					Window.alert("Error retrieve user...");
				}
			}.retry(3);

		}

	}

/*	private boolean validateEmail(String email) {
		boolean result = true;
		try {
			InternetAddress emailAddr = new InternetAddress(email);
			emailAddr.validate();
		} catch (AddressException ex) {
			result = false;
		}
		return result;
	}
*/
	private void hideErrorLabel() {
		this.display.getErrorUserName().setVisible(false);
		this.display.getErrorEmail().setVisible(false);
		this.display.getErrorPassword().setVisible(false);
		this.display.getErrorPasswordSize().setVisible(false);
		this.display.getErrorRegisterEmail().setVisible(false);

	}
}
