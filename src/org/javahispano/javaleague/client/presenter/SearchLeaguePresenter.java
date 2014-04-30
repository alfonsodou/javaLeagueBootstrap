/**
 * 
 */
package org.javahispano.javaleague.client.presenter;

import java.util.List;

import org.gwtbootstrap3.client.ui.Alert;
import org.gwtbootstrap3.client.ui.CellTable;
import org.gwtbootstrap3.client.ui.TextBox;
import org.javahispano.javaleague.client.event.ShowLeagueEvent;
import org.javahispano.javaleague.client.helper.RPCCall;
import org.javahispano.javaleague.client.resources.messages.JavaLeagueMessages;
import org.javahispano.javaleague.client.service.LeagueServiceAsync;
import org.javahispano.javaleague.shared.domain.League;

import com.google.gwt.core.shared.GWT;
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
import com.googlecode.objectify.Ref;



/**
 * @author adou
 * 
 */
public class SearchLeaguePresenter implements Presenter {

	public interface Display {
		Widget asWidget();
		
		HasClickHandlers getCreateLeagueButton();

		HasClickHandlers getSearchLeagueButton();

		CellTable<League> getCellTableLeagues();

		TextBox getSearchLeagueTextBox();
		
		Alert getEmptyAlert();
	}

	private final Display display;
	private final LeagueServiceAsync leagueService;
	private final SimpleEventBus eventBus;
	private final String textToSearch;
	
	private List<Ref<League>> leagues;

	private JavaLeagueMessages javaLeagueMessages = GWT
			.create(JavaLeagueMessages.class);

	private ListDataProvider<League> dataGridProvider = new ListDataProvider<League>();	
	
	public SearchLeaguePresenter(String textToSearch, LeagueServiceAsync leagueService,
			SimpleEventBus eventBus, Display display) {
		this.textToSearch = textToSearch;
		this.leagueService = leagueService;
		this.eventBus = eventBus;
		this.display = display;
		
		display.getEmptyAlert().setVisible(false);

		bind();
	}

	private void bind() {

	}

	@Override
	public void go(HasWidgets container) {
		fetchLeagues();

		container.clear();
		container.add(display.asWidget());
	}

	private void fetchLeagues() {
		new RPCCall<List<Ref<League>>>() {

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Error fetching leagues: "
						+ caught.getMessage());
			}

			@Override
			public void onSuccess(List<Ref<League>> result) {
				if ((result != null) && (result.size() > 0)) {
					leagues = result;
					
					display.getEmptyAlert().setVisible(false);
					
					doShowLeagues();
				} else {
					display.getEmptyAlert().setVisible(true);
				}

			}

			@Override
			protected void callService(AsyncCallback<List<Ref<League>>> cb) {
				leagueService.getLeagues(textToSearch, cb);
				
			}

		}.retry(3);

	}
	
	private void doShowLeagues() {
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
				return String.valueOf(object.getNameManager());
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

		for (Ref<League> l : leagues) {
			dataGridProvider.getList().add(l.get());
		}

		// Add a selection model to handle user selection.
		final SingleSelectionModel<League> selectionModel = new SingleSelectionModel<League>();
		display.getCellTableLeagues().setSelectionModel(selectionModel);
		selectionModel
				.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
					public void onSelectionChange(SelectionChangeEvent event) {
						League selected = selectionModel.getSelectedObject();
						if (selected != null) {
							GWT.log("SearchLeaguesPresenter: Firing ShowLeagueEvent. LeagueName: "
									+ selected.getName());
							eventBus.fireEvent(new ShowLeagueEvent(selected.getId()));
						}
					}
				});

		dataGridProvider.flush();
	}
	
}
