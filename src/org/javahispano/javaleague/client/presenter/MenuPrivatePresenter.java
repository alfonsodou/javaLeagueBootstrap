package org.javahispano.javaleague.client.presenter;

import org.javahispano.javaleague.client.event.ShowMyLeaguesEvent;
import org.javahispano.javaleague.client.helper.RPCCall;
import org.javahispano.javaleague.client.service.LoginServiceAsync;
import org.javahispano.javaleague.shared.AuthTypes;
import org.javahispano.javaleague.shared.UserDTO;

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

		Widget asWidget();

	}

	private final LoginServiceAsync loginService;
	private final SimpleEventBus eventBus;
	private final Display display;

	private UserDTO currentUser;

	public MenuPrivatePresenter(LoginServiceAsync loginService,
			SimpleEventBus eventBus, UserDTO currentUser, Display display) {
		this.display = display;
		this.loginService = loginService;
		this.eventBus = eventBus;
		this.currentUser = currentUser;
	}

	public void bind() {
		this.display.getLogoutLink().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				doLogout();
			}
		});

		this.display.getMyTeamLink().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				
			}
		});
		
		this.display.getMyLeaguesLink().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				doShowMyLeagues();
			}
		});
	}

	private void doShowMyLeagues() {
		eventBus.fireEvent(new ShowMyLeaguesEvent(currentUser));
	}
	
	private void doLogout() {
		new RPCCall<Void>() {
			@Override
			protected void callService(AsyncCallback<Void> cb) {
				if (facebookUser()) {
					Window.Location.assign("/facebooklogout.jsp");
				} else {
					loginService.logout(cb);
				}
			}

			@Override
			public void onSuccess(Void result) {
				// logout event already fired by RPCCall
			}

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("An error occurred: " + caught.toString());
			}
		}.retry(3);

	}

	private boolean facebookUser() {
		return currentUser.getUniqueId()
				.endsWith(AuthTypes.FACEBOOK.toString());
	}

	@Override
	public void go(HasWidgets container) {
		container.clear();
		container.add(display.asWidget());
		bind();
	}

}
