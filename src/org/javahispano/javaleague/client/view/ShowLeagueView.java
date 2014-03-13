/**
 * 
 */
package org.javahispano.javaleague.client.view;

import org.gwtbootstrap3.client.ui.Button;
import org.gwtbootstrap3.client.ui.Heading;
import org.gwtbootstrap3.client.ui.ListGroup;
import org.gwtbootstrap3.client.ui.Paragraph;
import org.javahispano.javaleague.client.presenter.ShowLeaguePresenter;

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
	Paragraph paragraphDate;
	@UiField
	ListGroup homeTeams;
	@UiField
	ListGroup visitingTeams;
	@UiField
	ListGroup resultMatch;

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
	public Paragraph getParagraphDate() {
		return paragraphDate;
	}

	@Override
	public ListGroup getHomeTeams() {
		return homeTeams;
	}

	@Override
	public ListGroup getVisitingTeams() {
		return visitingTeams;
	}

	@Override
	public ListGroup getResultMatch() {
		return resultMatch;
	}

}
