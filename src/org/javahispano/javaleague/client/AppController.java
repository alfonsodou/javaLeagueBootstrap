package org.javahispano.javaleague.client;

import java.util.Date;

import org.javahispano.javaleague.client.event.AddLeagueEvent;
import org.javahispano.javaleague.client.event.AddLeagueEventHandler;
import org.javahispano.javaleague.client.event.AddTacticEvent;
import org.javahispano.javaleague.client.event.AddTacticEventHandler;
import org.javahispano.javaleague.client.event.CancelUpdateTacticEvent;
import org.javahispano.javaleague.client.event.CancelUpdateTacticEventHandler;
import org.javahispano.javaleague.client.event.CreateCalendarLeagueEvent;
import org.javahispano.javaleague.client.event.CreateCalendarLeagueEventHandler;
import org.javahispano.javaleague.client.event.CreateLeagueEvent;
import org.javahispano.javaleague.client.event.CreateLeagueEventHandler;
import org.javahispano.javaleague.client.event.EditLeagueEvent;
import org.javahispano.javaleague.client.event.EditLeagueEventHandler;
import org.javahispano.javaleague.client.event.PlayMatchEvent;
import org.javahispano.javaleague.client.event.PlayMatchEventHandler;
import org.javahispano.javaleague.client.event.SearchLeagueEvent;
import org.javahispano.javaleague.client.event.SearchLeagueEventHandler;
import org.javahispano.javaleague.client.event.ShowLeagueEvent;
import org.javahispano.javaleague.client.event.ShowLeagueEventHandler;
import org.javahispano.javaleague.client.event.ShowMyLeaguesEvent;
import org.javahispano.javaleague.client.event.ShowMyLeaguesEventHandler;
import org.javahispano.javaleague.client.event.TacticEditEvent;
import org.javahispano.javaleague.client.event.TacticEditEventHandler;
import org.javahispano.javaleague.client.event.UpdateTacticEvent;
import org.javahispano.javaleague.client.event.UpdateTacticEventHandler;
import org.javahispano.javaleague.client.event.ViewMatchEvent;
import org.javahispano.javaleague.client.event.ViewMatchEventHandler;
import org.javahispano.javaleague.client.presenter.CreateCalendarLeaguePresenter;
import org.javahispano.javaleague.client.presenter.CreateLeaguePresenter;
import org.javahispano.javaleague.client.presenter.EditLeaguePresenter;
import org.javahispano.javaleague.client.presenter.MyLeaguesPresenter;
import org.javahispano.javaleague.client.presenter.Presenter;
import org.javahispano.javaleague.client.presenter.RegisterUserPresenter;
import org.javahispano.javaleague.client.presenter.SearchLeaguePresenter;
import org.javahispano.javaleague.client.presenter.ShowLeaguePresenter;
import org.javahispano.javaleague.client.presenter.ShowMatchPresenter;
import org.javahispano.javaleague.client.service.LeagueServiceAsync;
import org.javahispano.javaleague.client.service.LoginServiceAsync;
import org.javahispano.javaleague.client.service.MatchServiceAsync;
import org.javahispano.javaleague.client.service.TacticServiceAsync;
import org.javahispano.javaleague.client.service.UploadBlobstoreServiceAsync;
import org.javahispano.javaleague.client.service.UserAccountServiceAsync;
import org.javahispano.javaleague.client.service.UserFileServiceAsync;
import org.javahispano.javaleague.client.view.CreateCalendarLeagueView;
import org.javahispano.javaleague.client.view.CreateLeagueView;
import org.javahispano.javaleague.client.view.EditLeagueView;
import org.javahispano.javaleague.client.view.MyLeaguesView;
import org.javahispano.javaleague.client.view.RegisterUserView;
import org.javahispano.javaleague.client.view.SearchLeagueView;
import org.javahispano.javaleague.client.view.ShowLeagueView;
import org.javahispano.javaleague.client.view.ShowMatchView;
import org.javahispano.javaleague.shared.domain.Match;
import org.javahispano.javaleague.shared.domain.User;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.History;

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
	private final UploadBlobstoreServiceAsync blobstoreService;

	private User currentUser;
	private Long currentTacticId;
	private Long matchID;
	private Match match;
	private Long leagueId;
	private String textToSearch;

	public AppController(TacticServiceAsync rpcService,
			LoginServiceAsync loginService,
			UserFileServiceAsync userFileService,
			MatchServiceAsync matchService, LeagueServiceAsync leagueService,
			UserAccountServiceAsync userAccountService,
			UploadBlobstoreServiceAsync blobstoreService,
			SimpleEventBus eventBus) {
		this.userTacticService = rpcService;
		this.loginService = loginService;
		this.userFileService = userFileService;
		this.matchService = matchService;
		this.leagueService = leagueService;
		this.userAccountService = userAccountService;
		this.blobstoreService = blobstoreService;
		this.eventBus = eventBus;

		bind();
	}

	private void bind() {
		History.addValueChangeHandler(this);

		eventBus.addHandler(ShowMyLeaguesEvent.TYPE,
				new ShowMyLeaguesEventHandler() {
					@Override
					public void onShowMyLeagues(ShowMyLeaguesEvent event) {
						GWT.log("AppController: ShowMyLeagues Event received");
						doShowMyLeagues();
					}
				});

		eventBus.addHandler(ShowLeagueEvent.TYPE, new ShowLeagueEventHandler() {
			@Override
			public void onShowLeague(ShowLeagueEvent event) {
				GWT.log("AppController: ShowLeague Event received. Id: "
						+ event.getLeagueId());
				leagueId = event.getLeagueId();
				doShowLeague();
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

		eventBus.addHandler(AddLeagueEvent.TYPE, new AddLeagueEventHandler() {
			@Override
			public void onAddLeagueEvent(AddLeagueEvent event) {
				GWT.log("AppController: AddLeague Event received");
				doAddLeague();
			}

		});

		eventBus.addHandler(SearchLeagueEvent.TYPE,
				new SearchLeagueEventHandler() {
					@Override
					public void onSearchLeagueEvent(SearchLeagueEvent event) {
						GWT.log("AppController: SearchLeague Event received");
						doSearchLeague(event.getSearch());
					}

				});

		eventBus.addHandler(CreateCalendarLeagueEvent.TYPE,
				new CreateCalendarLeagueEventHandler() {
					@Override
					public void onCreateCalendarLeague(
							CreateCalendarLeagueEvent event) {
						GWT.log("AppController: CreateCalendarLeague Event received");
						doCreateCalendarLeague(event.getLeagueId());
					}
				});

		eventBus.addHandler(EditLeagueEvent.TYPE, new EditLeagueEventHandler() {
			@Override
			public void onEditLeague(EditLeagueEvent event) {
				GWT.log("AppController: EditLeague Event received");
				doEditLeague(event.getLeagueId());
			}

		});

	}

	private void doSearchLeague(String search) {
		textToSearch = search;
		History.newItem("searchLeague");
	}

	private void doShowMyLeagues() {
		History.newItem("showMyLeagues");
	}

	private void doShowLeague() {
		History.newItem("showLeague");
	}

	private void doShowTactics(Long id) {
		currentTacticId = id;
		History.newItem("showTactics");

	}

	private void doUpdateTactic() {
		History.newItem("updateTactic");
	}

	private void doCancelUpdateTactic() {
		History.newItem("showHome");
	}

	private void doShowMatch(Long id) {
		matchID = id;
		History.newItem("showMatch");
	}

	private void doPlayMatch(Long id) {
		currentTacticId = id;
		History.newItem("playMatch");
	}

	private void doCreateLeague() {
		History.newItem("createLeague");
	}

	private void doAddLeague() {
		History.newItem("addLeague");
	}

	private void doCreateCalendarLeague(Long id) {
		leagueId = id;
		History.newItem("createCalendarLeague");
	}

	private void doEditLeague(Long id) {
		leagueId = id;
		History.newItem("editLeague");
	}

	@Override
	public void onValueChange(ValueChangeEvent<String> event) {
		String token = event.getValue();

		if (token != null) {
			Presenter presenter = null;

			if (token.equals("showHome")) {

				JavaLeagueApp.get().setCurrentUser(null);
				JavaLeagueApp.get().showMainView();

				return;
			} else if (token.equals("showMyLeagues")) {

				presenter = new MyLeaguesPresenter(userTacticService,
						matchService, leagueService, eventBus,
						new MyLeaguesView());
				presenter.go(JavaLeagueApp.get().getCenterPanel());

				return;
			} else if (token.equals("showLeague")) {
				presenter = new ShowLeaguePresenter(leagueService,
						userTacticService, userAccountService, leagueId,
						currentUser, eventBus, new ShowLeagueView());
				presenter.go(JavaLeagueApp.get().getCenterPanel());

				return;

			} else if (token.equals("addLeague")) {

				presenter = new MyLeaguesPresenter(userTacticService,
						matchService, leagueService, eventBus,
						new MyLeaguesView());
				presenter.go(JavaLeagueApp.get().getCenterPanel());

				return;
			} else if (token.equals("showRegisterUser")) {
				presenter = new RegisterUserPresenter(userAccountService,
						eventBus, new RegisterUserView());
				JavaLeagueApp.get().getCenterPanel().clear();
				presenter.go(JavaLeagueApp.get().getCenterPanel());

				return;
			} else if (token.equals("showAuthenticateUser")) {
				presenter = new RegisterUserPresenter(userAccountService,
						eventBus, new RegisterUserView());
				JavaLeagueApp.get().getCenterPanel().clear();
				presenter.go(JavaLeagueApp.get().getCenterPanel());

				return;
			} else if (token.equals("createLeague")) {
				presenter = new CreateLeaguePresenter(leagueService, eventBus,
						new CreateLeagueView());
				JavaLeagueApp.get().getCenterPanel().clear();
				presenter.go(JavaLeagueApp.get().getCenterPanel());

				return;
			} else if (token.equals("searchLeague")) {
				presenter = new SearchLeaguePresenter(textToSearch,
						leagueService, eventBus, new SearchLeagueView());
				JavaLeagueApp.get().getCenterPanel().clear();
				presenter.go(JavaLeagueApp.get().getCenterPanel());

				return;
			} else if (token.equals("createCalendarLeague")) {
				presenter = new CreateCalendarLeaguePresenter(leagueService,
						eventBus, leagueId, new CreateCalendarLeagueView());
				JavaLeagueApp.get().getCenterPanel().clear();
				presenter.go(JavaLeagueApp.get().getCenterPanel());

			} else if (token.equals("editLeague")) {
				presenter = new EditLeaguePresenter(leagueService, eventBus,
						leagueId, new EditLeagueView());
				JavaLeagueApp.get().getCenterPanel().clear();
				presenter.go(JavaLeagueApp.get().getCenterPanel());
			} else if (token.equals("updateTactic")) {
				/*
				 * JavaLeagueApp .get() .getTacticPresenter()
				 * .go(JavaLeagueApp.get().getTacticPanel() .getTacticPanel());
				 * presenter = new ShowHomePresenter(userTacticService,
				 * matchService, eventBus, new ShowHomeView());
				 * presenter.go(JavaLeagueApp.get().getMainPanel());
				 */
			} else if (token.equals("showMatch")) {

				presenter = new ShowMatchPresenter(matchService, eventBus,
						new ShowMatchView(), Long.toString(matchID),
						Long.toString(new Date().getTime() / 1000));
				presenter.go(JavaLeagueApp.get().getCenterPanel());

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

	public User getCurrentUser() {
		return currentUser;
	}

	public void setCurrentUser(User currentUser) {
		this.currentUser = currentUser;
	}

}
