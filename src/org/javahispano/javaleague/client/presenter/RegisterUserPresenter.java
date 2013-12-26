/**
 * 
 */
package org.javahispano.javaleague.client.presenter;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import org.gwtbootstrap3.client.ui.Label;
import org.gwtbootstrap3.client.ui.TextBox;
import org.javahispano.javaleague.shared.UserDTO;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
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

		Label getErrorLabel();
	}

	private final Display display;
	private final UserDTO userDTO;

	public RegisterUserPresenter(Display display) {
		this.display = display;
		userDTO = new UserDTO();
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

		userDTO.setName(this.display.getUserName().getValue());
		userDTO.setPassword(this.display.getPassword().getValue());
		Validator validator = Validation.buildDefaultValidatorFactory()
				.getValidator();
		Set<ConstraintViolation<UserDTO>> violations = validator
				.validate(userDTO);
		StringBuffer errorMessage = new StringBuffer();
		if (!violations.isEmpty()) {
			for (ConstraintViolation<UserDTO> constraintViolation : violations) {
				if (errorMessage.length() == 0) {
					errorMessage.append('\n');
				}
				errorMessage.append(constraintViolation.getMessage());
			}
			this.display.getErrorLabel().setText(errorMessage.toString());
		} else if (!this.display.getPassword().getValue()
				.equals(this.display.getRePassword().getValue())) {
			errorMessage.append("Las conraseñas no coinciden");
			this.display.getErrorLabel().setText(errorMessage.toString());
		} else { // Validacion en el servidor
			
		}

	}
}
