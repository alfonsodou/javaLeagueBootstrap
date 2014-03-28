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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DescriptionData getPointsForWin() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DescriptionData getPointsForTied() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DescriptionData getPointsForLost() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TextBox getNumberRounds() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Label getErrorNumberRounds() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DateTimeBox getDateFirstRound() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CheckBox getMondayCheckBox() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CheckBox getTuesdayCheckBox() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CheckBox getWednesdayCheckBox() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CheckBox getThursdayCheckBox() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CheckBox getFridayCheckBox() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CheckBox getSaturdayCheckBox() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CheckBox getSundayCheckBox() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Label getErrorDaysRound() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Button getCreateCalendarLeagueButton() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Button getCancelCalendarLeagueButton() {
		// TODO Auto-generated method stub
		return null;
	}

}
