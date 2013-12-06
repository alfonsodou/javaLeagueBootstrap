/**
 * 
 */
package org.javahispano.javaleague.client.presenter;

import com.github.gwtbootstrap.client.ui.Modal;
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
		
		Modal getRegisterUserModal();

		Widget asWidget();
	}
	
	private final Display display;
	
	public RegisterUserPresenter(SimpleEventBus eventBus, Display display) {
		this.display = display;
	}

	public void bind() {
		this.display.getCancelButton().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				GWT.log("Click on Cancel Button!");
				doCancel();
			}
		});		
	}
	
	@Override
	public void go(HasWidgets container) {
		container.add(display.asWidget());
		bind();		
	}

	private void doCancel() {
		this.display.getRegisterUserModal().hide();
	}
}
