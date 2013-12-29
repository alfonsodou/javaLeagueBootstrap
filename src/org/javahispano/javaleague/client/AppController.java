package org.javahispano.javaleague.client;

import org.javahispano.javaleague.client.event.AddTacticEvent;
import org.javahispano.javaleague.client.event.AddTacticEventHandler;
import org.javahispano.javaleague.client.event.CancelUpdateTacticEvent;
import org.javahispano.javaleague.client.event.CancelUpdateTacticEventHandler;
import org.javahispano.javaleague.client.event.CreateLeagueEvent;
import org.javahispano.javaleague.client.event.CreateLeagueEventHandler;
import org.javahispano.javaleague.client.event.LogoutEvent;
import org.javahispano.javaleague.client.event.LogoutEventHandler;
import org.javahispano.javaleague.client.event.PlayMatchEvent;
import org.javahispano.javaleague.client.event.PlayMatchEventHandler;
import org.javahispano.javaleague.client.event.ShowHomeEvent;
import org.javahispano.javaleague.client.event.ShowHomeEventHandler;
import org.javahispano.javaleague.client.event.ShowMyLeaguesEvent;
import org.javahispano.javaleague.client.event.ShowMyLeaguesEventHandler;
import org.javahispano.javaleague.client.event.ShowRegisterUserEvent;
import org.javahispano.javaleague.client.event.ShowRegisterUserEventHandler;
import org.javahispano.javaleague.client.event.TacticEditEvent;
import org.javahispano.javaleague.client.event.TacticEditEventHandler;
import org.javahispano.javaleague.client.event.UpdateTacticEvent;
import org.javahispano.javaleague.client.event.UpdateTacticEventHandler;
import org.javahispano.javaleague.client.event.ViewMatchEvent;
import org.javahispano.javaleague.client.event.ViewMatchEventHandler;
import org.javahispano.javaleague.client.presenter.CreateLeaguePresenter;
import org.javahispano.javaleague.client.presenter.MyLeaguesPresenter;
import org.javahispano.javaleague.client.presenter.Presenter;
import org.javahispano.javaleague.client.presenter.RegisterUserPresenter;
import org.javahispano.javaleague.client.presenter.TacticEditPresenter;
import org.javahispano.javaleague.client.service.LeagueServiceAsync;
import org.javahispano.javaleague.client.service.LoginServiceAsync;
import org.javahispano.javaleague.client.service.MatchServiceAsync;
import org.javahispano.javaleague.client.service.TacticServiceAsync;
import org.javahispano.javaleague.client.service.UserAccountServiceAsync;
import org.javahispano.javaleague.client.service.UserFileServiceAsync;
import org.javahispano.javaleague.client.view.CreateLeagueView;
import org.javahispano.javaleague.client.view.MyLeaguesView;
import org.javahispano.javaleague.client.view.RegisterUserView;
import org.javahispano.javaleague.client.view.TacticEditView;
import org.javahispano.javaleague.shared.MatchDTO;
import org.javahispano.javaleague.shared.UserDTO;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.PopupPanel;

/**
 * 
 * @author adou
 * 
 */
public class AppController implements ValueChangeHandler<String> {
	// RPC services

	private final SimpleEventBus eventBus;
	private final TacticServiceAsync userTacticService;
	private final LoginServiceAsync loginService;
	private final UserFileServiceAsync userFileService;
	private final MatchServiceAsync matchService;
	private final LeagueServiceAsync leagueService;
	private final UserAccountServiceAsync userAccountService;

	private UserDTO currentUser;
	private String currentTacticId;
	private String matchID;
	private MatchDTO matchDTO;

	
	public AppController(TacticServiceAsync rpcService,
			LoginServiceAsync loginService,
			UserFileServiceAsync userFileService,
			MatchServiceAsync matchService, LeagueServiceAsync leagueService,
			UserAccountServiceAsync userAccountService, SimpleEventBus eventBus) {
		this.userTacticService = rpcService;
		this.loginService = loginService;
		this.userFileService = userFileService;
		this.matchService = matchService;
		this.leagueService = leagueService;
		this.userAccountService = userAccountService;
		this.eventBus = eventBus;

		bind();
	}

	private void bind() {
		History.addValueChangeHandler(this);

		eventBus.addHandler(LogoutEvent.TYPE, new LogoutEventHandler() {
			@Override
			public void onLogout(LogoutEvent event) {
				GWT.log("AppController: Logout event received");
				doLogout();
			}
		});

		eventBus.addHandler(ShowHomeEvent.TYPE, new ShowHomeEventHandler() {
			@Override
			public void onShowHome(ShowHomeEvent event) {
				GWT.log("AppController: ShowHome Event received");
				doShowHome();
			}
		});

		eventBus.addHandler(ShowMyLeaguesEvent.TYPE,
				new ShowMyLeaguesEventHandler() {
					@Override
					public void onShowMyLeagues(ShowMyLeaguesEvent event) {
						GWT.log("AppController: ShowMyLeagues Event received");
						doShowMyLeagues();
					}
				});

		eventBus.addHandler(AddTacticEvent.TYPE, new AddTacticEventHandler() {
			@Override
			public void onAddTacticEvent(AddTacticEvent event) {
				GWT.log("AppController: AddTactic Event received. Id: "
						+ event.getId());
				currentTacticId = event.getId();
			}
		});

		eventBus.addHandler(TacticEditEvent.TYPE, new TacticEditEventHandler() {
			@Override
			public void onShowTactics(TacticEditEvent event) {
				GWT.log("AppController: ShowTactics Event received. Id: "
						+ event.getId());
				doShowTactics(event.getId());
			}
		});

		eventBus.addHandler(UpdateTacticEvent.TYPE,
				new UpdateTacticEventHandler() {
					@Override
					public void onUpdateTactic(UpdateTacticEvent event) {
						GWT.log("AppController: UpdateTactic Event received.");
						doUpdateTactic();
					}
				});

		eventBus.addHandler(CancelUpdateTacticEvent.TYPE,
				new CancelUpdateTacticEventHandler() {

					@Override
					public void onCancelUpdateTactic(
							CancelUpdateTacticEvent event) {
						GWT.log("AppController: CancelUpdateTactic Event received.");
						eventBus.fireEvent(new CancelUpdateTacticEvent());
						doCancelUpdateTactic();
					}

				});

		eventBus.addHandler(ViewMatchEvent.TYPE, new ViewMatchEventHandler() {
			@Override
			public void onShowMatch(ViewMatchEvent event) {
				GWT.log("AppController: ViewMatch Event received: "
						+ event.getId());
				doShowMatch(event.getId());
			}

		});

		eventBus.addHandler(PlayMatchEvent.TYPE, new PlayMatchEventHandler() {
			@Override
			public void onPlayMatch(PlayMatchEvent event) {
				GWT.log("AppController: PlayMatch Event received: "
						+ event.getId());
				doPlayMatch(event.getId());
			}

		});

		eventBus.addHandler(CreateLeagueEvent.TYPE,
				new CreateLeagueEventHandler() {
					@Override
					public void onCreateLeague(CreateLeagueEvent event) {
						GWT.log("AppController: CreateLeague Event received");
						doCreateLeague();
					}

				});

	}

	private void doLogout() {
		History.newItem("login");
	}

	private void doShowHome() {
		History.newItem("showHome");
	}

	private void doShowMyLeagues() {
		History.newItem("showMyLeagues");
	}

	private void doShowTactics(String id) {
		currentTacticId = id;
		History.newItem("showTactics");

	}

	private void doUpdateTactic() {
		History.newItem("updateTactic");
	}

	private void doCancelUpdateTactic() {
		History.newItem("showHome");
	}

	private void doShowMatch(String id) {
		matchID = id;
		History.newItem("showMatch");
	}

	private void doPlayMatch(String id) {
		currentTacticId = id;
		History.newItem("playMatch");
	}

	private void doCreateLeague() {
		History.newItem("createLeague");
	}

	@Override
	public void onValueChange(ValueChangeEvent<String> event) {
		String token = event.getValue();

		if (token != null) {
			Presenter presenter = null;

			if (token.equals("login")) {

				JavaLeagueApp.get().setCurrentUser(null);
				JavaLeagueApp.get().showMainView();

				return;
			} else if (token.equals("showHome")) {
				/*
				 * presenter = new ShowHomePresenter(userTacticService,
				 * matchService, eventBus, new ShowHomeView());
				 * presenter.go(JavaLeagueApp.get().getMainPanel());
				 */

				return;
			} else if (token.equals("showMyLeagues")) {

				presenter = new MyLeaguesPresenter(userTacticService,
						matchService, eventBus, new MyLeaguesView());
				presenter.go(JavaLeagueApp.get().getCenterPanel());

				return;
			} else if (token.equals("showRegisterUser")) {
				presenter = new RegisterUserPresenter(userAccountService,
						eventBus, new RegisterUserView());
				JavaLeagueApp.get().getCenterPanel().clear();
				presenter.go(JavaLeagueApp.get().getCenterPanel());

				return;
			} else if (token.equals("showTactics")) {
				TacticEditView tacticEditView = new TacticEditView();

				final PopupPanel editTacticPopup = new PopupPanel(true);
				editTacticPopup.setAnimationEnabled(true);
				editTacticPopup.setWidget(tacticEditView);
				editTacticPopup.setGlassEnabled(true);
				editTacticPopup.setAutoHideEnabled(true);
				editTacticPopup.center();

				presenter = new TacticEditPresenter(userTacticService,
						userFileService, eventBus, tacticEditView,
						currentTacticId);

				presenter.go(editTacticPopup);
				/*
				 * presenter = new TacticEditPresenter(userTacticService,
				 * userFileService, eventBus, new TacticEditView(),
				 * currentTacticId);
				 * presenter.go(JavaLeagueApp.get().getMainPanel());
				 */

				return;
			} else if (token.equals("createLeague")) {
				CreateLeagueView createLeagueView = new CreateLeagueView();

				final PopupPanel createLeaguePopup = new PopupPanel(true);
				createLeaguePopup.setAnimationEnabled(true);
				createLeaguePopup.setWidget(createLeagueView);
				createLeaguePopup.setGlassEnabled(true);
				createLeaguePopup.setAutoHideEnabled(true);
				createLeaguePopup.center();

				presenter = new CreateLeaguePresenter(leagueService, eventBus,
						createLeagueView);

				presenter.go(createLeaguePopup);

				return;
			} else if (token.equals("updateTactic")) {
				/*
				 * JavaLeagueApp .get() .getTacticPresenter()
				 * .go(JavaLeagueApp.get().getTacticPanel() .getTacticPanel());
				 * presenter = new ShowHomePresenter(userTacticService,
				 * matchService, eventBus, new ShowHomeView());
				 * presenter.go(JavaLeagueApp.get().getMainPanel());
				 */
			} else if (token.equals("showMatch")) {
				/*
				 * presenter = new ShowMatchPresenter(matchService, eventBus,
				 * new ShowMatchView(), matchID, Long.toString(new
				 * Date().getTime() / 1000));
				 * presenter.go(JavaLeagueApp.get().getMainPanel());
				 */

			} else if (token.equals("playMatch")) {

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

	public UserDTO getCurrentUser() {
		return currentUser;
	}

	public void setCurrentUser(UserDTO currentUser) {
		this.currentUser = currentUser;
	}

}
