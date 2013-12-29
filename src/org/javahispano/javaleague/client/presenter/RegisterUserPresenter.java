/**
 * 
 */
package org.javahispano.javaleague.client.presenter;

import org.gwtbootstrap3.client.ui.Label;
import org.gwtbootstrap3.client.ui.TextBox;
import org.javahispano.javaleague.client.service.UserAccountServiceAsync;
import org.javahispano.javaleague.shared.UserDTO;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.SimpleEventBus;
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
		hideErrorLabel();
	}

	public void bind() {
		this.display.getCancelButton().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				GWT.log("Click on Cancel Button!");
				doCancel();
			}
		});

		this.display.getRegisterButton().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				GWT.log("Click on Register Button!");
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

		/*
		 * userDTO.setName(this.display.getUserName().getValue());
		 * userDTO.setPassword(this.display.getPassword().getValue()); Validator
		 * validator = Validation.buildDefaultValidatorFactory()
		 * .getValidator(); Set<ConstraintViolation<UserDTO>> violations =
		 * validator .validate(userDTO); StringBuffer errorMessage = new
		 * StringBuffer(); if (!violations.isEmpty()) { for
		 * (ConstraintViolation<UserDTO> constraintViolation : violations) { if
		 * (errorMessage.length() == 0) { errorMessage.append('\n'); }
		 * errorMessage.append(constraintViolation.getMessage()); break; }
		 * this.display.getErrorLabel().setText(errorMessage.toString()); } else
		 * { // Validacion en el servidor
		 * 
		 * }
		 */

		boolean error = false;

		hideErrorLabel();

		if (this.display.getUserName().getValue().length() < 4) {
			this.display.getErrorUserName().setVisible(true);
			error = true;
		}

		if (!validateEmail(this.display.getEmail().getValue())) {
			this.display.getErrorEmail().setVisible(true);
			error = true;
		}

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

		}

	}

	private boolean validateEmail(String email) {
		return email
				.matches("^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.\\-[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
	}

	private void hideErrorLabel() {
		this.display.getErrorUserName().setVisible(false);
		this.display.getErrorEmail().setVisible(false);
		this.display.getErrorPassword().setVisible(false);
		this.display.getErrorPasswordSize().setVisible(false);

	}
}
