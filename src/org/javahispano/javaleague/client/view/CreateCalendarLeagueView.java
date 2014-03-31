/**
 * 
 */
package org.javahispano.javaleague.client.view;

import org.gwtbootstrap3.client.ui.Button;
import org.gwtbootstrap3.client.ui.CheckBox;
import org.gwtbootstrap3.client.ui.DescriptionData;
import org.gwtbootstrap3.client.ui.Label;
import org.gwtbootstrap3.client.ui.TextBox;
import org.gwtbootstrap3.extras.datetimepicker.client.ui.DateTimeBox;
import org.javahispano.javaleague.client.presenter.CreateCalendarLeaguePresenter;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author adou
 * 
 */
public class CreateCalendarLeagueView extends Composite implements
		CreateCalendarLeaguePresenter.Display {
	
	@UiField
	DescriptionData leagueName;
	@UiField
	DescriptionData leagueType;
	@UiField
	DescriptionData pointsForWin;
	@UiField
	DescriptionData pointsForTied;
	@UiField
	DescriptionData pointsForLost;
	@UiField
	TextBox numberRounds;
	@UiField
	DateTimeBox startFirstRound;
	@UiField
	Label errorNumberRounds;
	@UiField
	CheckBox mondayCheckBox;
	@UiField
	CheckBox tuesdayCheckBox;
	@UiField
	CheckBox wednesdayCheckBox;
	@UiField
	CheckBox thursdayCheckBox;
	@UiField
	CheckBox fridayCheckBox;
	@UiField
	CheckBox saturdayCheckBox;
	@UiField
	CheckBox sundayCheckBox;
	@UiField
	Label errorDayRound;
	@UiField
	Button createCalendarLeagueButton;
	@UiField
	Button cancelCalendarLeagueButton;
	

	private static CreateCalendarLeagueViewUiBinder uiBinder = GWT
			.create(CreateCalendarLeagueViewUiBinder.class);

	interface CreateCalendarLeagueViewUiBinder extends
			UiBinder<Widget, CreateCalendarLeagueView> {
	}

	public CreateCalendarLeagueView() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public DescriptionData getLeagueName() {
		return leagueName;
	}

	@Override
	public DescriptionData getLeagueType() {
		return leagueType;
	}

	@Override
	public DescriptionData getPointsForWin() {
		return pointsForWin;
	}

	@Override
	public DescriptionData getPointsForTied() {
		return pointsForTied;
	}

	@Override
	public DescriptionData getPointsForLost() {
		return pointsForLost;
	}

	@Override
	public TextBox getNumberRounds() {
		return numberRounds;
	}

	@Override
	public Label getErrorNumberRounds() {
		return errorNumberRounds;
	}

	@Override
	public DateTimeBox getStartFirstRound() {
		return startFirstRound;
	}

	@Override
	public CheckBox getMondayCheckBox() {
		return mondayCheckBox;
	}

	@Override
	public CheckBox getTuesdayCheckBox() {
		return tuesdayCheckBox;
	}

	@Override
	public CheckBox getWednesdayCheckBox() {
		return wednesdayCheckBox;
	}

	@Override
	public CheckBox getThursdayCheckBox() {
		return thursdayCheckBox;
	}

	@Override
	public CheckBox getFridayCheckBox() {
		return fridayCheckBox;
	}

	@Override
	public CheckBox getSaturdayCheckBox() {
		return saturdayCheckBox;
	}

	@Override
	public CheckBox getSundayCheckBox() {
		return sundayCheckBox;
	}

	@Override
	public Label getErrorDayRound() {
		return errorDayRound;
	}

	@Override
	public Button getCreateCalendarLeagueButton() {
		return createCalendarLeagueButton;
	}

	@Override
	public Button getCancelCalendarLeagueButton() {
		return cancelCalendarLeagueButton;
	}

}
