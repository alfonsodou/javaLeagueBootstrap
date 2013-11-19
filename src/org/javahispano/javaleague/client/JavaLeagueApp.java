/**
 * 
 */
package org.javahispano.javaleague.client;

import org.javahispano.javaleague.client.channel.Channel;
import org.javahispano.javaleague.client.channel.ChannelFactory;
import org.javahispano.javaleague.client.channel.SocketListener;
import org.javahispano.javaleague.client.event.ContentAvailableEvent;
import org.javahispano.javaleague.client.event.LoginEvent;
import org.javahispano.javaleague.client.helper.RPCCall;
import org.javahispano.javaleague.client.presenter.BusyIndicatorPresenter;
import org.javahispano.javaleague.client.presenter.LoginPresenter;
import org.javahispano.javaleague.client.presenter.MenuPresenter;
import org.javahispano.javaleague.client.presenter.MenuPrivatePresenter;
import org.javahispano.javaleague.client.presenter.ShowHomePresenter;
import org.javahispano.javaleague.client.presenter.TacticPresenter;
import org.javahispano.javaleague.client.presenter.UserBadgePresenter;
import org.javahispano.javaleague.client.service.LeagueService;
import org.javahispano.javaleague.client.service.LeagueServiceAsync;
import org.javahispano.javaleague.client.service.LoginService;
import org.javahispano.javaleague.client.service.LoginServiceAsync;
import org.javahispano.javaleague.client.service.MatchService;
import org.javahispano.javaleague.client.service.MatchServiceAsync;
import org.javahispano.javaleague.client.service.TacticService;
import org.javahispano.javaleague.client.service.TacticServiceAsync;
import org.javahispano.javaleague.client.service.UserAccountService;
import org.javahispano.javaleague.client.service.UserAccountServiceAsync;
import org.javahispano.javaleague.client.service.UserFileService;
import org.javahispano.javaleague.client.service.UserFileServiceAsync;
import org.javahispano.javaleague.client.view.BusyIndicatorView;
import org.javahispano.javaleague.client.view.LoginView;
import org.javahispano.javaleague.client.view.MenuPrivateView;
import org.javahispano.javaleague.client.view.MenuView;
import org.javahispano.javaleague.client.view.ShowHomeView;
import org.javahispano.javaleague.client.view.TacticView;
import org.javahispano.javaleague.shared.UserDTO;
import org.javahispano.javaleague.shared.messages.ChannelTextMessage;
import org.javahispano.javaleague.shared.messages.Message;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.RunAsyncCallback;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.SerializationException;
import com.google.gwt.user.client.rpc.SerializationStreamFactory;
import com.google.gwt.user.client.rpc.SerializationStreamReader;
import com.google.gwt.user.client.ui.RootLayoutPanel;

/**
 * @author adou
 * 
 */
public class JavaLeagueApp implements EntryPoint {

	private UserDTO currentUser;
	private final UserAccountServiceAsync userService = GWT
			.create(UserAccountService.class);

	RootLayoutPanel root;
	private static JavaLeagueApp singleton;
	private SimpleEventBus eventBus = new SimpleEventBus();
	BusyIndicatorPresenter busyIndicator = new BusyIndicatorPresenter(eventBus,
			new BusyIndicatorView("Working hard..."));

	SerializationStreamFactory pushServiceStreamFactory;

	// Presenters
	private UserBadgePresenter userBadgePresenter;
	private MenuPresenter menuPresenter;
	private MenuPrivatePresenter menuPrivatePresenter;
	private TacticPresenter tacticPresenter;
	private ShowHomePresenter showHomePresenter;

	// RPC services
	private LoginServiceAsync loginService = GWT.create(LoginService.class);

	@UiField
	MyHeaderPanel myHeaderPanel;

	@UiField
	MyFooterPanel myFooterPanel;

	@UiField
	MyCenterPanel myCenterPanel;

	JavaLeagueMain javaLeagueMain;

	/**
	 * Gets the singleton application instance.
	 */
	public static JavaLeagueApp get() {
		return singleton;
	}

	/**
	 * @return the myHeaderPanel
	 */
	public MyHeaderPanel getMyHeaderPanel() {
		return myHeaderPanel;
	}

	/**
	 * @param myHeaderPanel
	 *            the myHeaderPanel to set
	 */
	public void setMyHeaderPanel(MyHeaderPanel myHeaderPanel) {
		this.myHeaderPanel = myHeaderPanel;
	}

	/**
	 * @return the myFooterPanel
	 */
	public MyFooterPanel getMyFooterPanel() {
		return myFooterPanel;
	}

	/**
	 * @param myFooterPanel
	 *            the myFooterPanel to set
	 */
	public void setMyFooterPanel(MyFooterPanel myFooterPanel) {
		this.myFooterPanel = myFooterPanel;
	}

	/**
	 * @return the myCenterPanel
	 */
	public MyCenterPanel getMyCenterPanel() {
		return myCenterPanel;
	}

	/**
	 * @param myCenterPanel
	 *            the myCenterPanel to set
	 */
	public void setMyCenterPanel(MyCenterPanel myCenterPanel) {
		this.myCenterPanel = myCenterPanel;
	}

	@Override
	public void onModuleLoad() {

		singleton = this;

		// Globalresources.RESOURCE.styles().ensureInjected();
		javaLeagueMain = new JavaLeagueMain();

		RootLayoutPanel.get().add(javaLeagueMain);
		myHeaderPanel = javaLeagueMain.getMyHeaderPanel();
		myCenterPanel = javaLeagueMain.getMyCenterPanel();
		
		// createUI();

		/*
		 * //Inject the contents of the CSS file
		 * Globalresources.RESOURCE.styles().ensureInjected(); DockLayoutPanel
		 * outer = uiBinder.createAndBindUi(this);
		 * 
		 * root = RootLayoutPanel.get(); root.add(outer);
		 */

		getLoggedInUser();
	}

	public void getLoggedInUser() {
		new RPCCall<UserDTO>() {
			@Override
			protected void callService(AsyncCallback<UserDTO> cb) {
				loginService.getLoggedInUserDTO(cb);
			}

			@Override
			public void onSuccess(UserDTO loggedInUserDTO) {
				if (loggedInUserDTO == null) {
					// nobody is logged in
					// showLoginView();
					createUI();
				} else {
					// user is logged in
					setCurrentUser(loggedInUserDTO);
					createPrivateUI();
				}
			}

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Error: " + caught.getMessage());
			}
		}.retry(3);

	}

	public void showLoginView() {
		LoginView loginView = new LoginView();

		LoginPresenter loginPresenter = new LoginPresenter(eventBus, loginView);
		loginView.getLoginModal().show();
		
		loginPresenter.go(myCenterPanel.getMainPanel());
	}

	public SimpleEventBus getEventBus() {
		return eventBus;
	}

	public void setEventBus(SimpleEventBus eventBus) {
		this.eventBus = eventBus;
	}

	private void createUI() {

		GWT.runAsync(new RunAsyncCallback() {
			@Override
			public void onFailure(Throwable reason) {
				Window.alert("Code download error: " + reason.getMessage());
			}

			@Override
			public void onSuccess() {
				/*
				 * goAfterLogin(); eventBus.fireEvent(new
				 * LoginEvent(currentUser));
				 */
				showMainView();
			}
		});
	}

	private void createPrivateUI() {

		GWT.runAsync(new RunAsyncCallback() {
			@Override
			public void onFailure(Throwable reason) {
				Window.alert("Code download error: " + reason.getMessage());
			}

			@Override
			public void onSuccess() {

				goAfterLogin();
				eventBus.fireEvent(new LoginEvent(currentUser));

				// showPrivateMainView();
			}
		});
	}

	private void showMainView() {
		menuPresenter = new MenuPresenter(new MenuView());
		menuPresenter.go(myHeaderPanel.getMenuPanel());

		showHomePresenter = new ShowHomePresenter(new ShowHomeView());
		showHomePresenter.go(myCenterPanel.getMainPanel());
	}

	private void showPrivateMainView() {
	}

	public UserDTO getCurrentUser() {
		return currentUser;
	}

	public void setCurrentUser(UserDTO currentUser) {
		this.currentUser = currentUser;
	}

	private void goAfterLogin() {

		/*
		 * DockLayoutPanel outer = uiBinder.createAndBindUi(this); root =
		 * RootLayoutPanel.get(); root.clear(); root.add(outer);
		 */
		TacticServiceAsync tacticService = GWT.create(TacticService.class);
		UserFileServiceAsync userFileService = GWT
				.create(UserFileService.class);
		MatchServiceAsync matchService = GWT.create(MatchService.class);
		LeagueServiceAsync leagueService = GWT.create(LeagueService.class);
		AppController appViewer = new AppController(tacticService,
				loginService, userFileService, matchService, leagueService,
				eventBus);
		appViewer.go();

		/*
		 * userBadgePresenter = new UserBadgePresenter(loginService, eventBus,
		 * new UserBadgeView());
		 * userBadgePresenter.go(myHeaderPanel.getUserBadgePanel());
		 */

		menuPrivatePresenter = new MenuPrivatePresenter(loginService, eventBus,
				currentUser, new MenuPrivateView());
		menuPrivatePresenter.go(myHeaderPanel.getMenuPanel());

		tacticPresenter = new TacticPresenter(tacticService, matchService,
				eventBus, new TacticView());
		tacticPresenter.go(myCenterPanel.getMainPanel());

		listenToChannel();
	}

	private void listenToChannel() {
		String channelId = currentUser.getChannelId();
		if (channelId == null)
			return; // Use of Channel API not enabled

		GWT.log("Creating client channel id: " + currentUser.getChannelId());
		Channel channel = ChannelFactory.createChannel(currentUser
				.getChannelId());
		channel.open(new SocketListener() {
			public void onOpen() {
				GWT.log("Channel onOpen()");
			}

			public void onMessage(String encodedData) {
				try {
					SerializationStreamReader reader = pushServiceStreamFactory
							.createStreamReader(encodedData);
					Message message = (Message) reader.readObject();
					handleMessage(message);
				} catch (SerializationException e) {
					throw new RuntimeException("Unable to deserialize "
							+ encodedData, e);
				}
			}
		});
	}

	/**
	 * Handle messages pushed from the server.
	 */
	public void handleMessage(Message msg) {
		switch (msg.getType()) {

		case NEW_CONTENT_AVAILABLE:
			GWT.log("Pushed msg received: NEW_CONTENT_AVAILABLE");
			eventBus.fireEvent(new ContentAvailableEvent());
			break;

		case TEXT_MESSAGE:
			String ttext = ((ChannelTextMessage) msg).get();
			GWT.log("Pushed msg received: TEXT_MESSAGE: " + ttext);
			break;

		default:
			Window.alert("Unknown message type: " + msg.getType());
		}
	}

}
