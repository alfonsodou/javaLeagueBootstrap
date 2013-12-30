package org.javahispano.javaleague.client.presenter;

import org.javahispano.javaleague.client.event.ShowRegisterUserEvent;

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

		Widget asWidget();

	}

	private final Display display;
	private final SimpleEventBus eventBus;

	public MenuPresenter(SimpleEventBus eventBus, Display display) {
		this.eventBus = eventBus;
		this.display = display;
	}

	public void bind() {

		this.display.getRegisterLink().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				GWT.log("MenuPresenter: Firing ShowRegisterUserEvent");
				eventBus.fireEvent(new ShowRegisterUserEvent());
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
