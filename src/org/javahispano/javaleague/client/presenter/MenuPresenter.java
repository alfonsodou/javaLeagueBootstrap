package org.javahispano.javaleague.client.presenter;

import org.javahispano.javaleague.client.event.ShowFrameWorkEvent;
import org.javahispano.javaleague.client.event.ShowLoginEvent;
import org.javahispano.javaleague.client.event.ShowRegisterUserEvent;
import org.javahispano.javaleague.client.resources.messages.JavaLeagueMessages;
import org.javahispano.javaleague.client.service.UserAccountServiceAsync;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.SimpleEventBus;
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

		HasClickHandlers getLoginLink();
		
		HasClickHandlers getFrameWorkLink();

		Widget asWidget();

	}

	private final Display display;
	private final SimpleEventBus eventBus;
	private final UserAccountServiceAsync userAccountService;

	private JavaLeagueMessages javaLeagueMessages = GWT
			.create(JavaLeagueMessages.class);

	public MenuPresenter(SimpleEventBus eventBus,
			UserAccountServiceAsync userAccountService, Display display) {
		this.eventBus = eventBus;
		this.display = display;
		this.userAccountService = userAccountService;
	}

	public void bind() {

		this.display.getRegisterLink().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				GWT.log("MenuPresenter: Firing ShowRegisterUserEvent");
				eventBus.fireEvent(new ShowRegisterUserEvent());
			}
		});

		this.display.getLoginLink().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				GWT.log("MenuPresenter: Firing ShowLoginEvent");
				eventBus.fireEvent(new ShowLoginEvent());
			}
		});
		
		this.display.getFrameWorkLink().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				GWT.log("MenuPresenter: Firing ShowFrameWorkEvent");
				eventBus.fireEvent(new ShowFrameWorkEvent());
			}
		});
	}

	@Override
	public void go(HasWidgets container) {
		container.clear();
		container.add(display.asWidget());
		bind();
	}

}
