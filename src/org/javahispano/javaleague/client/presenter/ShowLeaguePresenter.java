/**
 * 
 */
package org.javahispano.javaleague.client.presenter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;

import org.gwtbootstrap3.client.ui.Anchor;
import org.gwtbootstrap3.client.ui.Button;
import org.gwtbootstrap3.client.ui.Column;
import org.gwtbootstrap3.client.ui.Heading;
import org.gwtbootstrap3.client.ui.ListItem;
import org.gwtbootstrap3.client.ui.Pagination;
import org.gwtbootstrap3.client.ui.Paragraph;
import org.gwtbootstrap3.client.ui.Row;
import org.gwtbootstrap3.client.ui.Small;
import org.gwtbootstrap3.client.ui.TabPane;
import org.gwtbootstrap3.client.ui.constants.Alignment;
import org.gwtbootstrap3.client.ui.constants.ColumnSize;
import org.gwtbootstrap3.client.ui.constants.IconType;
import org.gwtbootstrap3.extras.bootbox.client.Bootbox;
import org.gwtbootstrap3.extras.bootbox.client.callback.ConfirmCallback;
import org.javahispano.javaleague.client.event.CreateCalendarLeagueEvent;
import org.javahispano.javaleague.client.event.ShowMyLeaguesEvent;
import org.javahispano.javaleague.client.helper.MyClickHandlerMatch;
import org.javahispano.javaleague.client.helper.RPCCall;
import org.javahispano.javaleague.client.resources.messages.JavaLeagueMessages;
import org.javahispano.javaleague.client.service.LeagueServiceAsync;
import org.javahispano.javaleague.shared.domain.CalendarDate;
import org.javahispano.javaleague.shared.domain.League;
import org.javahispano.javaleague.shared.domain.Match;
import org.javahispano.javaleague.shared.domain.User;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;
import com.googlecode.objectify.Ref;

/**
 * @author adou
 * 
 */
public class ShowLeaguePresenter implements Presenter {
	public interface Display {
		Widget asWidget();

		Button getJoinLeagueButton();

		Button getDropLeagueButton();

		Button getEditLeagueButton();

		Button getCreateCalendarLeagueButton();

		Paragraph getDescriptionLeague();

		Paragraph getParagraphRoundDate();

		Paragraph getParagraphRoundClasification();

		Heading getNameLeague();

		TabPane getTabPaneDate();

		TabPane getTabPaneClasification();

		Pagination getPaginationRounds();

	}

	private final SimpleEventBus eventBus;
	private final Display display;
	private final LeagueServiceAsync leagueService;

	private League league;
	private User user;
	private Long matchId;
	private int round;

	private JavaLeagueMessages javaLeagueMessages = GWT
			.create(JavaLeagueMessages.class);

	public ShowLeaguePresenter(LeagueServiceAsync leagueService, League league,
			User user, SimpleEventBus eventBus, Display display) {
		this.leagueService = leagueService;
		this.league = league;
		this.user = user;
		this.eventBus = eventBus;
		this.display = display;
		this.round = 1;

		ShowLeague();
	}

	private void ShowLeague() {
		Date now = new Date(); // La fecha hay que solicitarla al servidor !!!!

		if ((league.getEndSignIn().before(now))
				|| (user.isJoinLeague(league.getId()))) {
			display.getJoinLeagueButton().setEnabled(false);
		} else {
			display.getJoinLeagueButton().setEnabled(true);
		}
		display.getDescriptionLeague().setHTML(league.getDescription());
		display.getNameLeague().setText(league.getName());

		ListItem previousLink = display.getPaginationRounds().addPreviousLink();
		previousLink.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if (round > 1) {
					round--;
					doDisplayRound(round);
				}
			}

		});
		ListItem nextLink = display.getPaginationRounds().addNextLink();
		nextLink.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if (round < league.getMatchs().size() - 1) {
					round++;
					doDisplayRound(round);
				}
			}

		});

		if (league.getMatchs() != null) {
			doDisplayRound(round);
		}

	}

	private void doDisplayRound(int index) {
		display.getTabPaneDate().clear();
		display.getParagraphRoundDate().setText(
				javaLeagueMessages.round() + " " + Integer.toString(index)
						+ " / " + Integer.toString(league.getMatchs().size() - 1));
		Ref<CalendarDate> cd = league.getMatchs().get(index);

		for (Ref<Match> m : cd.get().getMatchs()) {

			Row row = new Row();

			row.add(addTeam(m.get().getLocal().getId(), m.get().getNameLocal(),
					m.get().getNameLocal()));
			row.add(addResult(m.get().getLocalGoals(), m.get()
					.getVisitingTeamGoals(), m.get().getLocalPossesion(), m
					.get().getState(), m.get().getId()));
			row.add(addTeam(m.get().getVisiting().getId(), m.get()
					.getNameForeign(), m.get().getNameForeign()));
			row.add(addLinks(m.get().getId()));
			
			display.getTabPaneDate().add(row);
		}
	}

	private Column addResult(int localGoals, int visitingTeamGoals,
			double localPossesion, int state, Long id) {
		Column column = new Column();
		column.setSize(ColumnSize.MD_3);
		Paragraph p = new Paragraph();
		p.setAlignment(Alignment.CENTER);
		Paragraph result = new Paragraph();
		Small possesion = new Small();

		matchId = id;
		if (state == 1) {
			Anchor anchor = new Anchor();
			anchor.setText(localGoals + " - " + visitingTeamGoals);
			MyClickHandlerMatch myClickHandler = new MyClickHandlerMatch(id,
					eventBus);
			anchor.addClickHandler(myClickHandler);
			result.add(anchor);
			possesion.setText(round(localPossesion * 100d, 2) + " - "
					+ round((1d - localPossesion) * 100d, 2));
		} else {
			result.setText("N / A");
		}

		p.add(result);
		p.add(possesion);

		column.add(p);

		return column;
	}

	private Column addTeam(Long id, String name, String nameManager) {
		Column column = new Column();
		column.setSize(ColumnSize.MD_4);
		Paragraph p = new Paragraph();
		p.setAlignment(Alignment.CENTER);
		Paragraph teamName = new Paragraph();
		Small managerName = new Small();

		teamName.setText(name);
		managerName.setText(nameManager);

		p.add(teamName);
		p.add(managerName);

		column.add(p);

		return column;
	}
	
	private Column addLinks(Long id) {
		Column column = new Column();
		column.setSize(ColumnSize.MD_1);
		Paragraph p = new Paragraph();
		p.setAlignment(Alignment.CENTER);
		Anchor anchor = new Anchor();
		anchor.setIcon(IconType.DOWNLOAD);
		anchor.setHref("http://javaleague.appspot.com/serve?id=" + Long.toString(id));
		p.add(anchor);
		column.add(p);
		
		return column;
	}

	private static double round(double value, int places) {
		if (places < 0)
			throw new IllegalArgumentException();

		BigDecimal bd = new BigDecimal(value);
		bd = bd.setScale(places, RoundingMode.HALF_UP);
		return bd.doubleValue();
	}

	@Override
	public void go(HasWidgets container) {
		container.clear();
		container.add(display.asWidget());

		bind();
	}

	private void bind() {
		display.getDropLeagueButton().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				Bootbox.confirm(javaLeagueMessages.confirm(),
						new ConfirmCallback() {
							@Override
							public void callback(boolean result) {
								if (result) {
									doDropLeague();
								}
							}
						});
			}
		});

		display.getJoinLeagueButton().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				doJoinLeague();
			}
		});

		display.getCreateCalendarLeagueButton().addClickHandler(
				new ClickHandler() {
					public void onClick(ClickEvent event) {
						// doCreateCalendarLeague();
						GWT.log("ShowLeaguePresenter: firing CreateCalenderLeagueEvent. LeagueId: "
								+ league.getId());
						eventBus.fireEvent(new CreateCalendarLeagueEvent(league));
					}
				});
	}

	private void doCreateCalendarLeague() {

		new RPCCall<League>() {

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Error createCalendarLeague: "
						+ caught.getMessage());
			}

			@Override
			public void onSuccess(League result) {
				league = result;
				GWT.log("ShowLeaguePresenter: firing CreateCalenderLeagueEvent. LeagueId: "
						+ league.getId());
				eventBus.fireEvent(new CreateCalendarLeagueEvent(league));
			}

			@Override
			protected void callService(AsyncCallback<League> cb) {
				leagueService.createCalendarLeague(league, cb);
			}

		}.retry(3);
	}

	private void doJoinLeague() {
		new RPCCall<Void>() {

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Error join league: " + caught.getMessage());
			}

			@Override
			public void onSuccess(Void result) {
				GWT.log("ShowLeaguePresenter: firing ShowMyLeaguesEvent");
				eventBus.fireEvent(new ShowMyLeaguesEvent());
			}

			@Override
			protected void callService(AsyncCallback<Void> cb) {
				leagueService.joinLeague(league.getId(), cb);
			}

		}.retry(3);
	}

	private void doDropLeague() {
		new RPCCall<Void>() {

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Error drop league: " + caught.getMessage());
			}

			@Override
			public void onSuccess(Void result) {
				GWT.log("ShowLeaguePresenter: firing ShowMyLeaguesEvent");
				eventBus.fireEvent(new ShowMyLeaguesEvent());
			}

			@Override
			protected void callService(AsyncCallback<Void> cb) {
				leagueService.dropLeague(league.getId(), cb);
			}

		}.retry(3);
	}
}
