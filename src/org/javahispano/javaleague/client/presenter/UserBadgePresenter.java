package org.javahispano.javaleague.client.presenter;

import org.javahispano.javaleague.client.event.LoginEvent;
import org.javahispano.javaleague.client.event.LoginEventHandler;
import org.javahispano.javaleague.client.helper.RPCCall;
import org.javahispano.javaleague.client.service.LoginServiceAsync;
import org.javahispano.javaleague.shared.domain.User;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;

/**
 * 
 * @author adou
 * 
 */
public class UserBadgePresenter implements Presenter {
	public interface Display {
		HasClickHandlers getLogoutLink();

		HasText getUsernameLabel();

		Widget asWidget();
	}

	private final LoginServiceAsync rpcService;
	private final SimpleEventBus eventBus;
	private final Display display;

	private User currentUser;

	public UserBadgePresenter(LoginServiceAsync rpcService,
			SimpleEventBus eventBus, Display display) {
		this.rpcService = rpcService;
		this.eventBus = eventBus;
		this.display = display;
	}

	public void bind() {
		this.display.getLogoutLink().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				doLogout();
			}
		});

		// Listen to login events
		eventBus.addHandler(LoginEvent.TYPE, new LoginEventHandler() {
			@Override
			public void onLogin(LoginEvent event) {
				currentUser = event.getUser();
				doLogin();
			}
		});
	}

	private void doLogout() {
		new RPCCall<Void>() {
			@Override
			protected void callService(AsyncCallback<Void> cb) {
				rpcService.logout(cb);
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

	private void doLogin() {
		if (currentUser != null) {
			UserBadgePresenter.this.display.getUsernameLabel().setText(
					currentUser.getName());
		} else
			UserBadgePresenter.this.display.getUsernameLabel().setText(
					"Login first");
	}

	public void go(final HasWidgets container) {
		container.clear();
		container.add(display.asWidget());
		bind();
	}

}
