/**
 * 
 */
package org.javahispano.javaleague.client;

import org.javahispano.javaleague.client.event.ShowRegisterUserEvent;
import org.javahispano.javaleague.client.event.ShowRegisterUserEventHandler;
import org.javahispano.javaleague.client.presenter.Presenter;
import org.javahispano.javaleague.client.presenter.RegisterUserPresenter;
import org.javahispano.javaleague.client.service.UserAccountServiceAsync;
import org.javahispano.javaleague.client.view.RegisterUserView;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.History;

/**
 * @author adou
 * 
 */
public class MenuController implements ValueChangeHandler<String> {
	private final SimpleEventBus eventBus;
	private final UserAccountServiceAsync userAccountService;

	public MenuController(UserAccountServiceAsync userAccountService,
			SimpleEventBus eventBus) {
		this.eventBus = eventBus;
		this.userAccountService = userAccountService;
		
		bind();
	}

	private void bind() {
		History.addValueChangeHandler(this);

		eventBus.addHandler(ShowRegisterUserEvent.TYPE,
				new ShowRegisterUserEventHandler() {
					@Override
					public void onShowRegisterUser(ShowRegisterUserEvent event) {
						GWT.log("MenuController: ShowRegisterUser Event received");
						doShowRegisterUser();
					}

				});

	}

	private void doShowRegisterUser() {
		History.newItem("showRegisterUser");
	}

	@Override
	public void onValueChange(ValueChangeEvent<String> event) {
		String token = event.getValue();

		if (token != null) {
			Presenter presenter = null;

			if (token.equals("showRegisterUser")) {
				presenter = new RegisterUserPresenter(userAccountService,
						eventBus, new RegisterUserView());
				JavaLeagueApp.get().getCenterPanel().clear();
				presenter.go(JavaLeagueApp.get().getCenterPanel());

				return;
			}
		}

	}
	
	public void go() {
		if ("".equals(History.getToken())) {
			History.newItem("showHome");
		} else {
			History.fireCurrentHistoryState();
		}
	}	

}
