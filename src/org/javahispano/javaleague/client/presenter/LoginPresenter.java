package org.javahispano.javaleague.client.presenter;

import com.github.gwtbootstrap.client.ui.Modal;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;

/**
 * 
 * @author adou
 * 
 */
public class LoginPresenter implements Presenter {

	public interface Display {
		HasClickHandlers getGoogleButton();

		HasClickHandlers getTwitterButton();

		HasClickHandlers getFacebookButton();

		//HasClickHandlers getLoginButton();
		
		//HasClickHandlers getCancelButton();
		
		Modal getLoginModal();

		Widget asWidget();
	}

	private final Display display;

	public LoginPresenter(SimpleEventBus eventBus, Display display) {
		this.display = display;
	}

	public void bind() {

		this.display.getGoogleButton().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				doLoginGoogle();
			}
		});

		this.display.getTwitterButton().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				doLoginTwitter();
			}
		});

		this.display.getFacebookButton().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				doLoginFacebook();
			}
		});
/*
		this.display.getLoginButton().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				GWT.log("Click on Login Button!");
			}
		});

		this.display.getCancelButton().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				GWT.log("Click on Cancel Button!");
			}
		});
	*/	
	}

	public void go(final HasWidgets container) {
		//container.clear();
		container.add(display.asWidget());
		bind();
	}

	private void doLoginFacebook() {
		Window.Location.assign("/loginfacebook");
	}

	private void doLoginGoogle() {
		Window.Location.assign("/logingoogle");
	}

	private void doLoginTwitter() {
		Window.Location.assign("/logintwitter");
	}

}
