package org.javahispano.javaleague.client.presenter;

import org.javahispano.javaleague.client.JavaLeagueApp;
import org.javahispano.javaleague.client.view.RegisterUserView;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;

/**
 * 
 * @author adou
 * 
 */
public class MenuPresenter implements Presenter {

	public interface Display {

		HasClickHandlers getRegisterLink();

		Widget asWidget();

	}

	private final Display display;

	public MenuPresenter(Display display) {
		this.display = display;
	}

	public void bind() {

		this.display.getRegisterLink().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				doRegisterUser();
			}
		});

	}

	@Override
	public void go(HasWidgets container) {
		container.clear();
		container.add(display.asWidget());
		bind();
	}

	private void doRegisterUser() {
		JavaLeagueApp.get().getCenterPanel().clear();
		RegisterUserPresenter registerUserPresenter = new RegisterUserPresenter(
				new RegisterUserView());
		registerUserPresenter.go(JavaLeagueApp.get().getCenterPanel());
	}
}
