/**
 * 
 */
package org.javahispano.javaleague.client.view;

import org.gwtbootstrap3.client.ui.Button;
import org.gwtbootstrap3.client.ui.CheckBox;
import org.gwtbootstrap3.client.ui.FormLabel;
import org.gwtbootstrap3.client.ui.Label;
import org.gwtbootstrap3.client.ui.TextBox;
import org.gwtbootstrap3.extras.datetimepicker.client.ui.DateTimeBox;
import org.gwtbootstrap3.extras.summernote.client.ui.Summernote;
import org.javahispano.javaleague.client.presenter.CreateLeaguePresenter;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author adou
 * 
 */
public class CreateLeagueView extends Composite implements
		CreateLeaguePresenter.Display {

	@UiField
	TextBox leagueName;
	@UiField
	Summernote leagueDescription;
	@UiField
	CheckBox leaguePublic;
	@UiField
	CheckBox leaguePrivate;
	@UiField
	TextBox passwordLeague;
	@UiField
	DateTimeBox startSignIn;
	@UiField
	DateTimeBox endSignIn;
	@UiField
	Label errorName;
	@UiField
	Label errorPassword;
	@UiField
	Label errorDate;
	@UiField
	Button createLeagueButton;
	@UiField
	Button cancelLeagueButton;
	@UiField
	FormLabel passwordLeagueLabel;
	@UiField
	Label errorType;
	@UiField
	TextBox pointsForWin;
	@UiField
	TextBox pointsForTied;
	@UiField
	TextBox pointsForLost;
	@UiField
	Label errorPointsForWin;
	@UiField
	Label errorPointsForTied;
	@UiField
	Label errorPointsForLost;

	private static CreateLeagueViewUiBinder uiBinder = GWT
			.create(CreateLeagueViewUiBinder.class);

	interface CreateLeagueViewUiBinder extends
			UiBinder<Widget, CreateLeagueView> {
	}

	public CreateLeagueView() {
		initWidget(uiBinder.createAndBindUi(this));

	}

	@Override
	public HasClickHandlers getCancelButton() {
		return cancelLeagueButton;
	}

	@Override
	public HasClickHandlers getCreateLeagueButton() {
		return createLeagueButton;
	}

	@Override
	public HasValue<String> getLeagueName() {
		return leagueName;
	}

	@Override
	protected void onLoad() {
		super.onLoad();
		leagueName.setFocus(true);
	}

	@Override
	public Summernote getLeagueDescription() {
		return leagueDescription;
	}

	@Override
	public CheckBox getLeaguePublic() {
		return leaguePublic;
	}

	@Override
	public CheckBox getLeaguePrivate() {
		return leaguePrivate;
	}

	@Override
	public TextBox getPasswordLeague() {
		return passwordLeague;
	}

	@Override
	public DateTimeBox getStartSignIn() {
		return startSignIn;
	}

	@Override
	public DateTimeBox getEndSignIn() {
		return endSignIn;
	}

	@Override
	public Label getErrorName() {
		return errorName;
	}

	@Override
	public Label getErrorPassword() {
		return errorPassword;
	}

	@Override
	public Label getErrorDate() {
		return errorDate;
	}

	@Override
	public FormLabel getPasswordLeagueLabel() {
		return passwordLeagueLabel;
	}

	@Override
	public Label getErrorType() {
		return errorType;
	}

	@Override
	public TextBox getPointsForWin() {
		return pointsForWin;
	}

	@Override
	public TextBox getPointsForTied() {
		return pointsForTied;
	}

	@Override
	public TextBox getPointsForLost() {
		return pointsForLost;
	}

	@Override
	public Label getErrorPointsForWin() {
		return errorPointsForWin;
	}

	@Override
	public Label getErrorPointsForTied() {
		return errorPointsForTied;
	}

	@Override
	public Label getErrorPointsForLost() {
		return errorPointsForLost;
	}

}
