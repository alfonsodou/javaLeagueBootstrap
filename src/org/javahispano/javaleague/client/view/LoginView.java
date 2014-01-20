package org.javahispano.javaleague.client.view;

import org.gwtbootstrap3.client.ui.Button;
import org.gwtbootstrap3.client.ui.Input;
import org.gwtbootstrap3.client.ui.SubmitButton;
import org.gwtbootstrap3.client.ui.TextBox;
import org.javahispano.javaleague.client.presenter.LoginPresenter;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;


/**
 * 
 * @author adou
 * 
 */
public class LoginView extends Composite implements LoginPresenter.Display {
	
	protected Widget widget;

	@UiField
	SubmitButton loginButton;
	@UiField
	Button cancelButton;
	@UiField
	Button registerUserButton;
	@UiField
	TextBox emailTextBox;
	@UiField
	Input passwordTextBox;
	
	private static UserBadgeUiBinder uiBinder = GWT
			.create(UserBadgeUiBinder.class);

	interface UserBadgeUiBinder extends UiBinder<Widget, LoginView> {
	}

	public LoginView() {
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
	public HasClickHandlers getRegisterUserButton() {
		return registerUserButton;
	}


	@Override
	public HasClickHandlers getLoginButton() {
		return loginButton;
	}


	@Override
	public TextBox getEmailTextBox() {
		return emailTextBox;
	}


	@Override
	public Input getPasswordTextBox() {
		return passwordTextBox;
	}

}
