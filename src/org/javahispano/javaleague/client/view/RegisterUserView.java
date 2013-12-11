/**
 * 
 */
package org.javahispano.javaleague.client.view;


import org.javahispano.javaleague.client.presenter.RegisterUserPresenter;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.svenjacobs.gwtbootstrap3.client.ui.Button;
import com.svenjacobs.gwtbootstrap3.client.ui.Modal;

/**
 * @author adou
 * 
 */
public class RegisterUserView extends Composite implements RegisterUserPresenter.Display {
	
	protected Widget widget;
	
	@UiField
	Modal registerUserModal;
	@UiField
	Button cancelButton;	

	private static RegisterUserViewUiBinder uiBinder = GWT
			.create(RegisterUserViewUiBinder.class);

	interface RegisterUserViewUiBinder extends
			UiBinder<Widget, RegisterUserView> {
	}

	public RegisterUserView() {
		setUpDialog();
		initWidget(uiBinder.createAndBindUi(this));
	}
	
    public final void hide() {
        registerUserModal.hide();
    }
    
    protected final void initWidget(final Widget widget) {
        this.widget = widget;
    }   
    
	// DialogBox must be overridden to let the presenter handle changes onUnload
	private void setUpDialog() {
		registerUserModal = new Modal() {

			@Override
			protected void onUnload() {
				RegisterUserView.this.hide();
			}
		};

	}
	
	@Override
	public Widget asWidget() {
		return widget;
	}
	
	@Override
	public Modal getRegisterUserModal() {
		return registerUserModal;
	}


	@Override
	public HasClickHandlers getCancelButton() {
		return cancelButton;
	}	
}
