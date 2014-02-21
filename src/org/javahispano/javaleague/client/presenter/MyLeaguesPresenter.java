/**
 * 
 */
package org.javahispano.javaleague.client.presenter;

import java.util.List;

import org.gwtbootstrap3.client.ui.CellTable;
import org.gwtbootstrap3.client.ui.DataGrid;
import org.javahispano.javaleague.client.event.CreateLeagueEvent;
import org.javahispano.javaleague.client.helper.RPCCall;
import org.javahispano.javaleague.client.resources.messages.JavaLeagueMessages;
import org.javahispano.javaleague.client.service.LeagueServiceAsync;
import org.javahispano.javaleague.client.service.MatchServiceAsync;
import org.javahispano.javaleague.client.service.TacticServiceAsync;
import org.javahispano.javaleague.shared.LeagueDTO;

import com.google.gwt.cell.client.AbstractEditableCell;
import com.google.gwt.cell.client.ButtonCell;
import com.google.gwt.cell.client.Cell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ListDataProvider;

/**
 * @author adou
 * 
 */
public class MyLeaguesPresenter implements Presenter {

	public interface Display {
		Widget asWidget();

		HasClickHandlers getCreateLeagueButton();

		CellTable<LeagueDTO> getCellTableLeagues();

		DataGrid getDataGridLeagues();
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

	/**
	 * Get a cell value from a record.
	 * 
	 * @param <C>
	 *            the cell type
	 */
	private static interface GetValue<C> {
		C getValue(LeagueDTO league);
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

		dataGridProvider.flush();

		// ButtonCell.
		addColumn(new ButtonCell(), "Button", new GetValue<String>() {
			@Override
			public String getValue(LeagueDTO leagueDTO) {
				return "Click " + leagueDTO.getName();
			}
		}, new FieldUpdater<LeagueDTO, String>() {
			@Override
			public void update(int index, LeagueDTO object, String value) {
				Window.alert("You clicked " + object.getName());
			}
		});

		dataGridProvider.addDataDisplay(display.getDataGridLeagues());

		for (LeagueDTO l : leaguesDTO) {
			dataGridProvider.getList().add(l);
		}

		dataGridProvider.flush();

	}

	/**
	 * Add a column with a header.
	 * 
	 * @param <C>
	 *            the cell type
	 * @param cell
	 *            the cell used to render the column
	 * @param headerText
	 *            the header string
	 * @param getter
	 *            the value getter for the cell
	 */
	private <C> Column<LeagueDTO, C> addColumn(Cell<C> cell, String headerText,
			final GetValue<C> getter, FieldUpdater<LeagueDTO, C> fieldUpdater) {
		Column<LeagueDTO, C> column = new Column<LeagueDTO, C>(cell) {
			@Override
			public C getValue(LeagueDTO object) {
				return getter.getValue(object);
			}
		};
		column.setFieldUpdater(fieldUpdater);

		display.getDataGridLeagues().addColumn(column, headerText);

		return column;
	}
}
