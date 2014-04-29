/**
 * 
 */
package org.javahispano.javaleague.client.presenter;

import java.util.List;

import org.gwtbootstrap3.client.ui.CellTable;
import org.gwtbootstrap3.client.ui.TextBox;
import org.javahispano.javaleague.client.event.CreateLeagueEvent;
import org.javahispano.javaleague.client.event.SearchLeagueEvent;
import org.javahispano.javaleague.client.event.ShowLeagueEvent;
import org.javahispano.javaleague.client.helper.RPCCall;
import org.javahispano.javaleague.client.resources.messages.JavaLeagueMessages;
import org.javahispano.javaleague.client.service.LeagueServiceAsync;
import org.javahispano.javaleague.client.service.MatchServiceAsync;
import org.javahispano.javaleague.client.service.TacticServiceAsync;
import org.javahispano.javaleague.shared.domain.League;
import org.javahispano.javaleague.shared.domain.LeagueSummary;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.DateTimeFormat.PredefinedFormat;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;
import com.googlecode.objectify.Ref;

/**
 * @author adou
 * 
 */
public class MyLeaguesPresenter implements Presenter {

	public interface Display {
		Widget asWidget();

		HasClickHandlers getCreateLeagueButton();

		HasClickHandlers getSearchLeagueButton();

		CellTable<LeagueSummary> getCellTableLeagues();

		TextBox getSearchLeagueTextBox();

	}

	private final SimpleEventBus eventBus;
	private final Display display;
	private final TacticServiceAsync tacticService;
	private final MatchServiceAsync matchService;
	private final LeagueServiceAsync leagueService;

	private List<Ref<LeagueSummary>> leaguesSummary;

	private JavaLeagueMessages javaLeagueMessages = GWT
			.create(JavaLeagueMessages.class);

	private ListDataProvider<LeagueSummary> dataGridProvider = new ListDataProvider<LeagueSummary>();

	public MyLeaguesPresenter(TacticServiceAsync tacticService,
			MatchServiceAsync matchService, LeagueServiceAsync leagueService,
			SimpleEventBus eventBus, Display display) {
		this.display = display;
		this.eventBus = eventBus;
		this.tacticService = tacticService;
		this.leagueService = leagueService;
		this.matchService = matchService;

		fetchLeagues();

	}

	@Override
	public void go(HasWidgets container) {
		container.clear();
		container.add(display.asWidget());

		bind();
	}

	private void fetchLeagues() {
		new RPCCall<List<Ref<LeagueSummary>>>() {

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Error fetching leagues: " + caught.getMessage());
			}

			@Override
			public void onSuccess(List<Ref<LeagueSummary>> result) {
				if (result.size() > 0) {
					leaguesSummary = result;

					doShowMyLeagues();
				}

			}

			@Override
			protected void callService(AsyncCallback<List<Ref<LeagueSummary>>> cb) {
				leagueService.getLeaguesSummary(cb);
			}

		}.retry(3);
	}

	private void bind() {
		this.display.getCreateLeagueButton().addClickHandler(
				new ClickHandler() {
					public void onClick(ClickEvent event) {
						GWT.log("MyLeaguesPresenter: firing CreateLeagueEvent");
						eventBus.fireEvent(new CreateLeagueEvent());
					}
				});

		this.display.getSearchLeagueButton().addClickHandler(
				new ClickHandler() {
					public void onClick(ClickEvent event) {
						GWT.log("MyLeaguesPresenter: firing SearchLeagueEvent");
						eventBus.fireEvent(new SearchLeagueEvent(display
								.getSearchLeagueTextBox().getValue()));
					}
				});

	}

	private void doShowMyLeagues() {

		TextColumn<LeagueSummary> col1 = new TextColumn<LeagueSummary>() {

			@Override
			public String getValue(LeagueSummary object) {
				return String.valueOf(object.getName());
			}
		};
		display.getCellTableLeagues().addColumn(col1,
				javaLeagueMessages.nameLeague());

		TextColumn<LeagueSummary> col2 = new TextColumn<LeagueSummary>() {

			@Override
			public String getValue(LeagueSummary object) {
				return String.valueOf(object.getNameManager());
			}
		};
		display.getCellTableLeagues().addColumn(col2,
				javaLeagueMessages.manager());

		TextColumn<LeagueSummary> col3 = new TextColumn<LeagueSummary>() {

			@Override
			public String getValue(LeagueSummary object) {
				DateTimeFormat date = DateTimeFormat
						.getFormat(PredefinedFormat.DATE_TIME_MEDIUM);
				return String.valueOf(date.format(object.getStartSignIn()));
			}
		};
		display.getCellTableLeagues().addColumn(col3,
				javaLeagueMessages.startSignIn());

		TextColumn<LeagueSummary> col4 = new TextColumn<LeagueSummary>() {

			@Override
			public String getValue(LeagueSummary object) {
				DateTimeFormat date = DateTimeFormat
						.getFormat(PredefinedFormat.DATE_TIME_MEDIUM);
				return String.valueOf(date.format(object.getEndSignIn()));
			}
		};
		display.getCellTableLeagues().addColumn(col4,
				javaLeagueMessages.endSignIn());

		dataGridProvider.addDataDisplay(display.getCellTableLeagues());

		for (Ref<LeagueSummary> l : leaguesSummary) {
			dataGridProvider.getList().add(l.get());
		}

		// Add a selection model to handle user selection.
		final SingleSelectionModel<LeagueSummary> selectionModel = new SingleSelectionModel<LeagueSummary>();
		display.getCellTableLeagues().setSelectionModel(selectionModel);
		selectionModel
				.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
					public void onSelectionChange(SelectionChangeEvent event) {
						LeagueSummary selected = selectionModel.getSelectedObject();
						if (selected != null) {
							GWT.log("MyLeaguesPresenter: Firing ShowLeagueEvent. LeagueName: "
									+ selected.getName());
							eventBus.fireEvent(new ShowLeagueEvent(null));
							//eventBus.fireEvent(new ShowLeagueEvent(selected));
						}
					}
				});

		dataGridProvider.flush();
	}
}
