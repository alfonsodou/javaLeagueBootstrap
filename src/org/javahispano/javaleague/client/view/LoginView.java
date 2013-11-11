package org.javahispano.javaleague.client.view;

import org.javahispano.javaleague.client.presenter.LoginPresenter;

import com.github.gwtbootstrap.client.ui.Button;
import com.github.gwtbootstrap.client.ui.Modal;
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
	@UiField
	Button googleButton;
	@UiField
	Button twitterButton;
	@UiField
	Button facebookButton;
	@UiField
	Modal loginModal;
/*
	@UiField
	SubmitButton loginButton;
	@UiField
	Button cancelButton;
*/
	
	private static UserBadgeUiBinder uiBinder = GWT
			.create(UserBadgeUiBinder.class);

	interface UserBadgeUiBinder extends UiBinder<Widget, LoginView> {
	}

	public LoginView() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public Widget asWidget() {
		return this;
	}

	@Override
	public HasClickHandlers getFacebookButton() {
		return facebookButton;
	}

	@Override
	public HasClickHandlers getGoogleButton() {
		return googleButton;
	}

	@Override
	public HasClickHandlers getTwitterButton() {
		return twitterButton;
	}
	
	@Override
	public Modal getLoginModal() {
		return loginModal;
	}

	/*
	@Override
	public HasClickHandlers getLoginButton() {
		return loginButton;
	}

	@Override
	public HasClickHandlers getCancelButton() {
		return cancelButton;
	}
*/
}
