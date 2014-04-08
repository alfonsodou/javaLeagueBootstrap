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
import org.javahispano.javaleague.client.presenter.MenuPresenter;
import org.javahispano.javaleague.client.presenter.MenuPrivatePresenter;
import org.javahispano.javaleague.client.presenter.ShowHomePresenter;
import org.javahispano.javaleague.client.presenter.TacticPresenter;
import org.javahispano.javaleague.client.service.FrameWorkService;
import org.javahispano.javaleague.client.service.FrameWorkServiceAsync;
import org.javahispano.javaleague.client.service.LeagueService;
import org.javahispano.javaleague.client.service.LeagueServiceAsync;
import org.javahispano.javaleague.client.service.LoginService;
import org.javahispano.javaleague.client.service.LoginServiceAsync;
import org.javahispano.javaleague.client.service.MatchService;
import org.javahispano.javaleague.client.service.MatchServiceAsync;
import org.javahispano.javaleague.client.service.TacticService;
import org.javahispano.javaleague.client.service.TacticServiceAsync;
import org.javahispano.javaleague.client.service.UploadBlobstoreService;
import org.javahispano.javaleague.client.service.UploadBlobstoreServiceAsync;
import org.javahispano.javaleague.client.service.UserAccountService;
import org.javahispano.javaleague.client.service.UserAccountServiceAsync;
import org.javahispano.javaleague.client.service.UserFileService;
import org.javahispano.javaleague.client.service.UserFileServiceAsync;
import org.javahispano.javaleague.client.view.MenuPrivateView;
import org.javahispano.javaleague.client.view.MenuView;
import org.javahispano.javaleague.client.view.ShowHomeView;
import org.javahispano.javaleague.client.view.TacticView;
import org.javahispano.javaleague.shared.domain.User;
import org.javahispano.javaleague.shared.messages.ChannelTextMessage;
import org.javahispano.javaleague.shared.messages.Message;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.GWT.UncaughtExceptionHandler;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.SerializationException;
import com.google.gwt.user.client.rpc.SerializationStreamFactory;
import com.google.gwt.user.client.rpc.SerializationStreamReader;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author adou
 * 
 */
public class JavaLeagueApp implements EntryPoint {
	interface JavaLeagueAppUiBinder extends UiBinder<Widget, JavaLeagueApp> {
	}

	private static JavaLeagueAppUiBinder ourUiBinder = GWT
			.create(JavaLeagueAppUiBinder.class);

	private User currentUser;

	RootLayoutPanel root;
	private static JavaLeagueApp singleton;
	private SimpleEventBus eventBus = new SimpleEventBus();

	SerializationStreamFactory pushServiceStreamFactory;

	// Presenters
	private MenuPresenter menuPresenter;
	private MenuPrivatePresenter menuPrivatePresenter;
	private TacticPresenter tacticPresenter;
	private ShowHomePresenter showHomePresenter;
	// private BusyIndicatorPresenter busyIndicator = new
	// BusyIndicatorPresenter(
	// eventBus, new BusyIndicatorView("Working hard..."));

	// RPC services
	private LoginServiceAsync loginService = GWT.create(LoginService.class);
	private final UserAccountServiceAsync userAccountService = GWT
			.create(UserAccountService.class);
	private TacticServiceAsync tacticService = GWT.create(TacticService.class);
	private UserFileServiceAsync userFileService = GWT
			.create(UserFileService.class);
	private MatchServiceAsync matchService = GWT.create(MatchService.class);
	private LeagueServiceAsync leagueService = GWT.create(LeagueService.class);
	private FrameWorkServiceAsync frameWorkService = GWT
			.create(FrameWorkService.class);
	private UploadBlobstoreServiceAsync blobstoreService = GWT
			.create(UploadBlobstoreService.class);

	// Controllers
	private MenuController menuController;
	private AppController appViewer;

	@UiField
	SimplePanel centerPanel;
	@UiField
	SimplePanel headerPanel;

	/*
	 * @UiField Paragraph footerPanelParagraph;
	 */

	/**
	 * Gets the singleton application instance.
	 */
	public static JavaLeagueApp get() {
		return singleton;
	}

	public SimplePanel getCenterPanel() {
		return centerPanel;
	}

	public SimplePanel getHeaderPanel() {
		return headerPanel;
	}

	@Override
	public void onModuleLoad() {

		GWT.setUncaughtExceptionHandler(new UncaughtExceptionHandler() {

			@Override
			public void onUncaughtException(Throwable e) {
				Window.alert("uncaught: " + e.getMessage());
				String s = buildStackTrace(e, "RuntimeException:\n");
				Window.alert(s);
				e.printStackTrace();

			}
		});

		singleton = this;

		RootPanel.get().add(ourUiBinder.createAndBindUi(this));

		menuController = new MenuController(userAccountService,
				frameWorkService, tacticService, userFileService, matchService,
				blobstoreService, eventBus);
		menuController.go();

		/*
		 * footerPanelParagraph .setHTML("<b>2014</b> - " +
		 * "<a href='http://www.javahispano.org'>" +
		 * "<img src='images/logoJavaHispano.png' /></a>");
		 */

		showMainView();

		if (currentUser == null) {
			showMainView();
		} else {
			goAfterLogin(currentUser);
		}
	}

	public void getLoggedInUser() {
		new RPCCall<User>() {
			@Override
			protected void callService(AsyncCallback<User> cb) {
				loginService.getLoggedInUser(cb);
			}

			@Override
			public void onSuccess(User loggedInUser) {
				if (loggedInUser == null) {
					showMainView();
				} else {
					// user is logged in
					setCurrentUser(loggedInUser);
					goAfterLogin(loggedInUser);
					eventBus.fireEvent(new LoginEvent(currentUser));
				}
			}

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Error: " + caught.getMessage());
			}
		}.retry(3);

	}

	public SimpleEventBus getEventBus() {
		return eventBus;
	}

	public void setEventBus(SimpleEventBus eventBus) {
		this.eventBus = eventBus;
	}

	public void showMainView() {

		headerPanel.clear();
		menuPresenter = new MenuPresenter(eventBus, userAccountService,
				new MenuView());
		menuPresenter.go(headerPanel);

		centerPanel.clear();
		showHomePresenter = new ShowHomePresenter(new ShowHomeView());
		showHomePresenter.go(centerPanel);

	}

	public User getCurrentUser() {
		return currentUser;
	}

	public void setCurrentUser(User currentUser) {
		this.currentUser = currentUser;
	}

	public void goAfterLogin(User user) {

		setCurrentUser(user);

		appViewer = new AppController(tacticService, loginService,
				userFileService, matchService, leagueService,
				userAccountService, blobstoreService, eventBus);
		appViewer.setCurrentUser(user);
		// appViewer.go();

		headerPanel.clear();
		menuPrivatePresenter = new MenuPrivatePresenter(loginService, eventBus,
				currentUser, new MenuPrivateView());
		menuPrivatePresenter.go(headerPanel);

		centerPanel.clear();
		tacticPresenter = new TacticPresenter(tacticService, matchService,
				userFileService, blobstoreService, eventBus, new TacticView());
		tacticPresenter.go(centerPanel);

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

	private String buildStackTrace(Throwable t, String log) {
		// return "disabled";
		if (t != null) {
			log += t.getClass().toString();
			log += t.getMessage();
			//
			StackTraceElement[] stackTrace = t.getStackTrace();
			if (stackTrace != null) {
				StringBuffer trace = new StringBuffer();

				for (int i = 0; i < stackTrace.length; i++) {
					trace.append(stackTrace[i].getClassName() + "."
							+ stackTrace[i].getMethodName() + "("
							+ stackTrace[i].getFileName() + ":"
							+ stackTrace[i].getLineNumber());
				}

				log += trace.toString();
			}
			//
			Throwable cause = t.getCause();
			if (cause != null && cause != t) {

				log += buildStackTrace(cause, "CausedBy:\n");

			}
		}
		return log;
	}

}
