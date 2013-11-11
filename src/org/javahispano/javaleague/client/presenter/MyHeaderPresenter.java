/**
 * 
 */
package org.javahispano.javaleague.client.presenter;

import org.javahispano.javaleague.client.JavaLeagueApp;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author adou
 * 
 */
public class MyHeaderPresenter implements Presenter {

	public interface Display {
		HasClickHandlers getLoginLink();

		HasClickHandlers getRegisterLink();

		Widget asWidget();

	}

	private final Display display;

	public MyHeaderPresenter(Display display) {
		this.display = display;
	}

	public void bind() {
		this.display.getLoginLink().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				JavaLeagueApp.get().showLoginView();
			}
		});

		this.display.getRegisterLink().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
			}
		});

	}

	@Override
	public void go(HasWidgets container) {
		bind();

	}

}
