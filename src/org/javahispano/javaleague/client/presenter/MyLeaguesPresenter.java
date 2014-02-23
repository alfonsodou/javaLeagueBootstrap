/**
 * 
 */
package org.javahispano.javaleague.client.presenter;

import java.util.List;

import org.gwtbootstrap3.client.ui.CellTable;
import org.javahispano.javaleague.client.event.CreateLeagueEvent;
import org.javahispano.javaleague.client.helper.RPCCall;
import org.javahispano.javaleague.client.resources.messages.JavaLeagueMessages;
import org.javahispano.javaleague.client.service.LeagueServiceAsync;
import org.javahispano.javaleague.client.service.MatchServiceAsync;
import org.javahispano.javaleague.client.service.TacticServiceAsync;
import org.javahispano.javaleague.shared.LeagueDTO;

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

		CellTable<LeagueDTO> getCellTableLeagues();

	}

	private final SimpleEventBus eventBus;
	private final Display display;
	private final TacticServiceAsync tacticService;
	private final MatchServiceAsync matchService;
	private final LeagueServiceAsync leagueService;

	private List<LeagueDTO> leaguesDTO;

	private JavaLeagueMessages javaLeagueMessages = GWT
			.create(JavaLeagueMessages.class);

	private ListDataProvider<LeagueDTO> dataGridProvider = new ListDataProvider<LeagueDTO>();

	public MyLeaguesPresenter(TacticServiceAsync tacticService,
			MatchServiceAsync matchService, LeagueServiceAsync leagueService,
			SimpleEventBus eventBus, Display display) {
		this.display = display;
		this.eventBus = eventBus;
		this.tacticService = tacticService;
		this.leagueService = leagueService;
		this.matchService = matchService;

		fetchLeaguesDTO();

	}

	@Override
	public void go(HasWidgets container) {
		container.clear();
		container.add(display.asWidget());

		bind();
	}

	private void fetchLeaguesDTO() {
		new RPCCall<List<LeagueDTO>>() {

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Error fetching leagues: " + caught.getMessage());
			}

			@Override
			public void onSuccess(List<LeagueDTO> result) {
				if (result.size() > 0) {
					leaguesDTO = result;

					doShowMyLeagues();
				}

			}

			@Override
			protected void callService(AsyncCallback<List<LeagueDTO>> cb) {
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
		TextColumn<LeagueDTO> col1 = new TextColumn<LeagueDTO>() {

			@Override
			public String getValue(LeagueDTO object) {
				return String.valueOf(object.getName());
			}
		};
		display.getCellTableLeagues().addColumn(col1,
				javaLeagueMessages.nameLeague());

		TextColumn<LeagueDTO> col2 = new TextColumn<LeagueDTO>() {

			@Override
			public String getValue(LeagueDTO object) {
				return String.valueOf(object.getNameManager());
			}
		};
		display.getCellTableLeagues().addColumn(col2,
				javaLeagueMessages.manager());

		TextColumn<LeagueDTO> col3 = new TextColumn<LeagueDTO>() {

			@Override
			public String getValue(LeagueDTO object) {
				return String.valueOf(object.getStartSignIn().toString());
			}
		};
		display.getCellTableLeagues().addColumn(col3,
				javaLeagueMessages.startSignIn());

		TextColumn<LeagueDTO> col4 = new TextColumn<LeagueDTO>() {

			@Override
			public String getValue(LeagueDTO object) {
				return String.valueOf(object.getEndSignIn().toString());
			}
		};
		display.getCellTableLeagues().addColumn(col4,
				javaLeagueMessages.endSignIn());

		dataGridProvider.addDataDisplay(display.getCellTableLeagues());

		for (LeagueDTO l : leaguesDTO) {
			dataGridProvider.getList().add(l);
		}

	    // Add a selection model to handle user selection.
	    final SingleSelectionModel<LeagueDTO> selectionModel = new SingleSelectionModel<LeagueDTO>();
	    display.getCellTableLeagues().setSelectionModel(selectionModel);
	    selectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
	      public void onSelectionChange(SelectionChangeEvent event) {
	        LeagueDTO selected = selectionModel.getSelectedObject();
	        if (selected != null) {
	          Window.alert("You selected: " + selected.getName());
	        }
	      }
	    });	
	    
		dataGridProvider.flush();
	}
}
