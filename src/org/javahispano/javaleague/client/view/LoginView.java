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
	
	protected Widget widget;
	
	@UiField
	Button googleButton;
	@UiField
	Button twitterButton;
	@UiField
	Button facebookButton;
	@UiField
	Modal loginModal;
	@UiField
	Button cancelButton;
	@UiField
	Button registerUserButton;
	
	private static UserBadgeUiBinder uiBinder = GWT
			.create(UserBadgeUiBinder.class);

	interface UserBadgeUiBinder extends UiBinder<Widget, LoginView> {
	}

	public LoginView() {
		setUpDialog();
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	   // DialogBox must be overridden to let the presenter handle changes onUnload
    private void setUpDialog() {
        loginModal = new Modal() {

            @Override
            protected void onUnload() {
                LoginView.this.hide();
            }
        };
               
    }

    public final void hide() {
        loginModal.hide();
    }
    
    protected final void initWidget(final Widget widget) {
        this.widget = widget;
    }    
    
	@Override
	public Widget asWidget() {
		return widget;
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


	@Override
	public HasClickHandlers getCancelButton() {
		return cancelButton;
	}

	@Override
	public HasClickHandlers getRegisterUserButton() {
		return registerUserButton;
	}

}
