/**
 * 
 */
package org.javahispano.javaleague.client.view;

import org.gwtbootstrap3.client.ui.Button;
import org.gwtbootstrap3.client.ui.Heading;
import org.gwtbootstrap3.client.ui.Pagination;
import org.gwtbootstrap3.client.ui.Paragraph;
import org.gwtbootstrap3.client.ui.TabPane;
import org.javahispano.javaleague.client.presenter.ShowLeaguePresenter;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author adou
 * 
 */
public class ShowLeagueView extends Composite implements
		ShowLeaguePresenter.Display {

	private static ShowLeagueUiBinder uiBinder = GWT
			.create(ShowLeagueUiBinder.class);

	interface ShowLeagueUiBinder extends UiBinder<Widget, ShowLeagueView> {
	}

	@UiField
	Paragraph descriptionLeague;
	@UiField
	Button joinLeagueButton;
	@UiField
	Button dropLeagueButton;
	@UiField
	Button editLeagueButton;
	@UiField
	Button createCalendarLeagueButton;
	@UiField
	Heading nameLeague;
	@UiField
	Paragraph paragraphRoundDate;
	@UiField
	Paragraph paragraphRoundClasification;
	@UiField
	TabPane tabPaneDate;
	@UiField
	TabPane tabPaneClasification;
	@UiField
	Pagination paginationRounds;
	@UiField
	TabPane tabPaneInformation;

	public ShowLeagueView() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public Button getJoinLeagueButton() {
		return joinLeagueButton;
	}

	@Override
	public Button getDropLeagueButton() {
		return dropLeagueButton;
	}

	@Override
	public Button getEditLeagueButton() {
		return editLeagueButton;
	}

	@Override
	public Paragraph getDescriptionLeague() {
		return descriptionLeague;
	}

	@Override
	public Heading getNameLeague() {
		return nameLeague;
	}

	@Override
	public Button getCreateCalendarLeagueButton() {
		return createCalendarLeagueButton;
	}

	@Override
	public TabPane getTabPaneDate() {
		return tabPaneDate;
	}

	@Override
	public Paragraph getParagraphRoundDate() {
		return paragraphRoundDate;
	}

	@Override
	public Paragraph getParagraphRoundClasification() {
		return paragraphRoundClasification;
	}

	@Override
	public TabPane getTabPaneClasification() {
		return tabPaneClasification;
	}

	@Override
	public Pagination getPaginationRounds() {
		return paginationRounds;
	}

	@Override
	public TabPane getTabPaneInformation() {
		return tabPaneInformation;
	}

}
