/**
 * 
 */
package org.javahispano.javaleague.client.view;

import org.gwtbootstrap3.client.ui.Alert;
import org.gwtbootstrap3.client.ui.Button;
import org.gwtbootstrap3.client.ui.CellTable;
import org.gwtbootstrap3.client.ui.TextBox;
import org.javahispano.javaleague.client.presenter.SearchLeaguePresenter;
import org.javahispano.javaleague.shared.domain.League;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author adou
 *
 */
public class SearchLeagueView extends Composite implements SearchLeaguePresenter.Display {

	private static SearchLeagueViewUiBinder uiBinder = GWT
			.create(SearchLeagueViewUiBinder.class);

	interface SearchLeagueViewUiBinder extends
			UiBinder<Widget, SearchLeagueView> {
	}
	
	@UiField
	Button createLeagueButton;
	@UiField
	Button searchLeagueButton;
	@UiField
	CellTable cellTableLeagues;
	@UiField
	TextBox searchLeagueTextBox;
	@UiField
	Alert emptyAlert;

	public SearchLeagueView() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public HasClickHandlers getCreateLeagueButton() {
		return createLeagueButton;
	}

	@Override
	public HasClickHandlers getSearchLeagueButton() {
		return searchLeagueButton;
	}

	@Override
	public CellTable<League> getCellTableLeagues() {
		return cellTableLeagues;
	}

	@Override
	public TextBox getSearchLeagueTextBox() {
		return searchLeagueTextBox;
	}

	@Override
	public Alert getEmptyAlert() {
		return emptyAlert;
	}

}
