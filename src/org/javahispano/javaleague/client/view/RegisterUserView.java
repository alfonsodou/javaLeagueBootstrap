/**
 * 
 */
package org.javahispano.javaleague.client.view;

import org.gwtbootstrap3.client.ui.Alert;
import org.gwtbootstrap3.client.ui.Button;
import org.gwtbootstrap3.client.ui.Form;
import org.gwtbootstrap3.client.ui.Label;
import org.gwtbootstrap3.client.ui.SubmitButton;
import org.gwtbootstrap3.client.ui.TextBox;
import org.javahispano.javaleague.client.presenter.RegisterUserPresenter;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author adou
 * 
 */
public class RegisterUserView extends Composite implements
		RegisterUserPresenter.Display {

	protected Widget widget;


	@UiField
	Button cancelButton;
	@UiField
	SubmitButton registerButton;
	@UiField
	TextBox userName;
	@UiField
	TextBox email;
	@UiField
	TextBox password;
	@UiField
	TextBox rePassword;
	@UiField
	Label errorUserName;
	@UiField
	Label errorEmail;
	@UiField
	Label errorPassword;
	@UiField
	Label errorPasswordSize;
	@UiField
	Label errorRegisterEmail;
	@UiField
	Form formRegisterUser;
	@UiField
	Alert alertSendEmail;

	
	private static RegisterUserViewUiBinder uiBinder = GWT
			.create(RegisterUserViewUiBinder.class);

	interface RegisterUserViewUiBinder extends
			UiBinder<Widget, RegisterUserView> {
	}

	public RegisterUserView() {
		initWidget(uiBinder.createAndBindUi(this));
	}


	protected final void initWidget(final Widget widget) {
		this.widget = widget;
	}


	@Override
	public Widget asWidget() {
		return widget;
	}

	@Override
	public HasClickHandlers getCancelButton() {
		return cancelButton;
	}

	@Override
	public HasClickHandlers getRegisterButton() {
		return registerButton;
	}

	@Override
	public TextBox getUserName() {
		return userName;
	}

	@Override
	public TextBox getEmail() {
		return email;
	}

	@Override
	public TextBox getPassword() {
		return password;
	}

	@Override
	public TextBox getRePassword() {
		return rePassword;
	}

	@Override
	public Label getErrorUserName() {
		return errorUserName;
	}


	@Override
	public Label getErrorEmail() {
		return errorEmail;
	}


	@Override
	public Label getErrorPassword() {
		return errorPassword;
	}
	
	@Override
	public Label getErrorPasswordSize() {
		return errorPasswordSize;
	}


	@Override
	public Label getErrorRegisterEmail() {
		return errorRegisterEmail;
	}	
	
	@Override
	public Form getFormRegisterUser() {
		return formRegisterUser;
	}
	
	@Override
	public Alert getAlertSendEmail() {
		return alertSendEmail;
	}
}
