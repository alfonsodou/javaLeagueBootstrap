/**
 * 
 */
package org.javahispano.javaleague.client.presenter;

import java.util.List;

import org.gwtbootstrap3.client.ui.CellTable;
import org.javahispano.javaleague.client.event.CreateLeagueEvent;
import org.javahispano.javaleague.client.event.ShowLeagueEvent;
import org.javahispano.javaleague.client.helper.RPCCall;
import org.javahispano.javaleague.client.resources.messages.JavaLeagueMessages;
import org.javahispano.javaleague.client.service.LeagueServiceAsync;
import org.javahispano.javaleague.client.service.MatchServiceAsync;
import org.javahispano.javaleague.client.service.TacticServiceAsync;
import org.javahispano.javaleague.shared.domain.League;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;

/**
 * @author adou
 * 
 */
public class MyLeaguesPresenter implements Presenter {

	public interface Display {
		Widget asWidget();

		HasClickHandlers getCreateLeagueButton();

		CellTable<League> getCellTableLeagues();

	}

	private final SimpleEventBus eventBus;
	private final Display display;
	private final TacticServiceAsync tacticService;
	private final MatchServiceAsync matchService;
	private final LeagueServiceAsync leagueService;

	private List<League> leagues;

	private JavaLeagueMessages javaLeagueMessages = GWT
			.create(JavaLeagueMessages.class);

	private ListDataProvider<League> dataGridProvider = new ListDataProvider<League>();

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
		new RPCCall<List<League>>() {

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Error fetching leagues: " + caught.getMessage());
			}

			@Override
			public void onSuccess(List<League> result) {
				if (result.size() > 0) {
					leagues = result;

					doShowMyLeagues();
				}

			}

			@Override
			protected void callService(AsyncCallback<List<League>> cb) {
				leagueService.getMyLeagues(cb);
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

	}

	private void doShowMyLeagues() {
		TextColumn<League> col1 = new TextColumn<League>() {

			@Override
			public String getValue(League object) {
				return String.valueOf(object.getName());
			}
		};
		display.getCellTableLeagues().addColumn(col1,
				javaLeagueMessages.nameLeague());

		TextColumn<League> col2 = new TextColumn<League>() {

			@Override
			public String getValue(League object) {
				return String.valueOf(object.getName());
				//return String.valueOf(object.getNameManager());
			}
		};
		display.getCellTableLeagues().addColumn(col2,
				javaLeagueMessages.manager());

		TextColumn<League> col3 = new TextColumn<League>() {

			@Override
			public String getValue(League object) {
				return String.valueOf(object.getStartSignIn().toString());
			}
		};
		display.getCellTableLeagues().addColumn(col3,
				javaLeagueMessages.startSignIn());

		TextColumn<League> col4 = new TextColumn<League>() {

			@Override
			public String getValue(League object) {
				return String.valueOf(object.getEndSignIn().toString());
			}
		};
		display.getCellTableLeagues().addColumn(col4,
				javaLeagueMessages.endSignIn());

		dataGridProvider.addDataDisplay(display.getCellTableLeagues());

		for (League l : leagues) {
			dataGridProvider.getList().add(l);
		}

		// Add a selection model to handle user selection.
		final SingleSelectionModel<League> selectionModel = new SingleSelectionModel<League>();
		display.getCellTableLeagues().setSelectionModel(selectionModel);
		selectionModel
				.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
					public void onSelectionChange(SelectionChangeEvent event) {
						League selected = selectionModel.getSelectedObject();
						if (selected != null) {
							GWT.log("MyLeaguesPresenter: Firing ShowLeagueEvent. LeagueName: "
									+ selected.getName());
							eventBus.fireEvent(new ShowLeagueEvent(selected));
						}
					}
				});

		dataGridProvider.flush();
	}
}
