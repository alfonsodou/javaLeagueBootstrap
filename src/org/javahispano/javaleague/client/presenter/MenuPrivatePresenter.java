package org.javahispano.javaleague.client.presenter;

import org.gwtbootstrap3.client.ui.AnchorButton;
import org.javahispano.javaleague.client.event.LogoutEvent;
import org.javahispano.javaleague.client.event.ShowFrameWorkEvent;
import org.javahispano.javaleague.client.event.ShowMyLeaguesEvent;
import org.javahispano.javaleague.client.event.ShowMyTacticEvent;
import org.javahispano.javaleague.client.helper.RPCCall;
import org.javahispano.javaleague.client.service.LoginServiceAsync;
import org.javahispano.javaleague.shared.domain.User;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;

/**
 * 
 * @author adou
 * 
 */
public class MenuPrivatePresenter implements Presenter {

	public interface Display {
		HasClickHandlers getLogoutLink();

		HasClickHandlers getMyTeamLink();

		HasClickHandlers getMyLeaguesLink();
		
		HasClickHandlers getFrameWorkLink();
		
		HasClickHandlers getNavbarBrand();
		
		HasClickHandlers getWikiLink();

		AnchorButton getUserName();

		Widget asWidget();
	}

	private final LoginServiceAsync loginService;
	private final SimpleEventBus eventBus;
	private final Display display;

	private User currentUser;

	public MenuPrivatePresenter(LoginServiceAsync loginService,
			SimpleEventBus eventBus, User currentUser, Display display) {
		this.display = display;
		this.loginService = loginService;
		this.eventBus = eventBus;
		this.currentUser = currentUser;
		this.display.getUserName().setText(currentUser.getName());
	}

	public void bind() {
		this.display.getLogoutLink().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				doLogout();
			}
		});

		this.display.getMyTeamLink().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				doShowMyTeam();
			}
		});
		
		this.display.getNavbarBrand().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				doShowMyTeam();
			}
		});		

		this.display.getMyLeaguesLink().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				doShowMyLeagues();
			}
		});

		this.display.getFrameWorkLink().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				GWT.log("MenuPrivatePresenter: Firing ShowFrameWorkEvent");
				eventBus.fireEvent(new ShowFrameWorkEvent());
			}
		});
	}
	
	private void doShowMyTeam() {
		GWT.log("MenuPrivatePresenter: Firing ShowMyTacticEvent");
		eventBus.fireEvent(new ShowMyTacticEvent(currentUser.getTactic().getId()));
	}

	private void doShowMyLeagues() {
		GWT.log("MenuPrivatePresenter: Firing ShowMyLeaguesEvent");
		eventBus.fireEvent(new ShowMyLeaguesEvent());
	}

	private void doLogout() {
		new RPCCall<Void>() {
			@Override
			protected void callService(AsyncCallback<Void> cb) {
				loginService.logout(cb);
			}

			@Override
			public void onSuccess(Void result) {
				GWT.log("MenuPrivatePresenter: firing LogoutEvent");
				eventBus.fireEvent(new LogoutEvent());
			}

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("An error occurred: " + caught.toString());
			}
		}.retry(3);

	}

	@Override
	public void go(HasWidgets container) {
		container.clear();
		container.add(display.asWidget());
		bind();
	}

}
