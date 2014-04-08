/**
 * 
 */
package org.javahispano.javaleague.client;

import org.javahispano.javaleague.client.event.LoginEvent;
import org.javahispano.javaleague.client.event.LoginEventHandler;
import org.javahispano.javaleague.client.event.LogoutEvent;
import org.javahispano.javaleague.client.event.LogoutEventHandler;
import org.javahispano.javaleague.client.event.ShowFrameWorkEvent;
import org.javahispano.javaleague.client.event.ShowFrameWorkEventHandler;
import org.javahispano.javaleague.client.event.ShowHomeEvent;
import org.javahispano.javaleague.client.event.ShowHomeEventHandler;
import org.javahispano.javaleague.client.event.ShowLoginEvent;
import org.javahispano.javaleague.client.event.ShowLoginEventHandler;
import org.javahispano.javaleague.client.event.ShowMyTacticEvent;
import org.javahispano.javaleague.client.event.ShowMyTacticEventHandler;
import org.javahispano.javaleague.client.event.ShowRegisterUserEvent;
import org.javahispano.javaleague.client.event.ShowRegisterUserEventHandler;
import org.javahispano.javaleague.client.presenter.FrameWorkPresenter;
import org.javahispano.javaleague.client.presenter.LoginPresenter;
import org.javahispano.javaleague.client.presenter.Presenter;
import org.javahispano.javaleague.client.presenter.RegisterUserPresenter;
import org.javahispano.javaleague.client.presenter.ShowHomePresenter;
import org.javahispano.javaleague.client.presenter.TacticPresenter;
import org.javahispano.javaleague.client.service.FrameWorkServiceAsync;
import org.javahispano.javaleague.client.service.MatchServiceAsync;
import org.javahispano.javaleague.client.service.TacticServiceAsync;
import org.javahispano.javaleague.client.service.UploadBlobstoreServiceAsync;
import org.javahispano.javaleague.client.service.UserAccountServiceAsync;
import org.javahispano.javaleague.client.service.UserFileServiceAsync;
import org.javahispano.javaleague.client.view.FrameWorkView;
import org.javahispano.javaleague.client.view.LoginView;
import org.javahispano.javaleague.client.view.RegisterUserView;
import org.javahispano.javaleague.client.view.ShowHomeView;
import org.javahispano.javaleague.client.view.TacticView;
import org.javahispano.javaleague.shared.domain.User;

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
	private final FrameWorkServiceAsync frameWorkService;
	private final TacticServiceAsync tacticService;
	private final UserFileServiceAsync userFileService;
	private final MatchServiceAsync matchService;
	private final UploadBlobstoreServiceAsync blobstoreService;

	private User user;

	public MenuController(UserAccountServiceAsync userAccountService,
			FrameWorkServiceAsync frameWorkService,
			TacticServiceAsync tacticService,
			UserFileServiceAsync userFileService,
			MatchServiceAsync matchService,
			UploadBlobstoreServiceAsync blobstoreService, SimpleEventBus eventBus) {
		this.eventBus = eventBus;
		this.userAccountService = userAccountService;
		this.frameWorkService = frameWorkService;
		this.tacticService = tacticService;
		this.userFileService = userFileService;
		this.matchService = matchService;
		this.blobstoreService = blobstoreService;

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

		eventBus.addHandler(ShowFrameWorkEvent.TYPE,
				new ShowFrameWorkEventHandler() {
					@Override
					public void onShowFrameWork(ShowFrameWorkEvent event) {
						GWT.log("MenuController: ShowFrameWork Event received");
						doShowFrameWork();
					}

				});

		eventBus.addHandler(ShowHomeEvent.TYPE, new ShowHomeEventHandler() {
			@Override
			public void onShowHome(ShowHomeEvent event) {
				GWT.log("MenuController: ShowHome Event received");
				doShowHome();
			}
		});

		eventBus.addHandler(ShowLoginEvent.TYPE, new ShowLoginEventHandler() {
			@Override
			public void onShowLogin(ShowLoginEvent event) {
				GWT.log("MenuController: ShowLogin Event received");
				doShowLogin();
			}
		});

		eventBus.addHandler(ShowMyTacticEvent.TYPE,
				new ShowMyTacticEventHandler() {
					@Override
					public void onShowMyTactic(ShowMyTacticEvent event) {
						GWT.log("MenuController: ShowMyTactic Event received");
						doShowMyTactic();
					}
				});

		eventBus.addHandler(LoginEvent.TYPE, new LoginEventHandler() {
			@Override
			public void onLogin(LoginEvent event) {
				GWT.log("MenuController: Login Event received");
				doLogin(event.getUser());
			}
		});

		eventBus.addHandler(LogoutEvent.TYPE, new LogoutEventHandler() {
			@Override
			public void onLogout(LogoutEvent event) {
				GWT.log("AppController: Logout event received");
				doLogout();
			}
		});
	}

	private void doLogout() {
		History.newItem("showHome");
	}

	private void doShowFrameWork() {
		History.newItem("showFrameWork");
	}

	private void doShowMyTactic() {
		History.newItem("showMyTactic");
	}

	private void doShowRegisterUser() {
		History.newItem("showRegisterUser");
	}

	private void doShowHome() {
		History.newItem("showHome");
	}

	private void doShowLogin() {
		History.newItem("showLogin");
	}

	private void doLogin(User user) {
		this.user = user;
		History.newItem("loginUser");
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
			} else if (token.equals("showHome")) {
				presenter = new ShowHomePresenter(new ShowHomeView());
				JavaLeagueApp.get().getCenterPanel().clear();
				presenter.go(JavaLeagueApp.get().getCenterPanel());

				return;
			} else if (token.equals("showLogin")) {
				presenter = new LoginPresenter(userAccountService, eventBus,
						new LoginView());
				JavaLeagueApp.get().getCenterPanel().clear();
				presenter.go(JavaLeagueApp.get().getCenterPanel());

				return;
			} else if (token.equals("showMyTactic")) {
				presenter = new TacticPresenter(tacticService, matchService,
						userFileService, blobstoreService, eventBus,
						new TacticView());
				JavaLeagueApp.get().getCenterPanel().clear();
				presenter.go(JavaLeagueApp.get().getCenterPanel());

				return;
			} else if (token.equals("loginUser")) {

				JavaLeagueApp.get().goAfterLogin(user);

				return;
			} else if (token.equals("showFrameWork")) {
				presenter = new FrameWorkPresenter(frameWorkService, eventBus,
						new FrameWorkView());
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
