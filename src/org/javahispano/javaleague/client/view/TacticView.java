/**
 * 
 */
package org.javahispano.javaleague.client.view;

import org.gwtbootstrap3.client.ui.Button;
import org.gwtbootstrap3.client.ui.Label;
import org.javahispano.javaleague.client.presenter.TacticPresenter;

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
public class TacticView extends Composite implements TacticPresenter.Display {

	@UiField
	Label teamNameField;
	@UiField
	Button updateTacticButton;
	@UiField
	Button addTacticButton;
	@UiField
	Label winsField;
	@UiField
	Label lostField;
	@UiField
	Label tiedField;
	@UiField
	Label goalsForField;
	@UiField
	Label goalsAgainstField;
	@UiField
	Button playMatchButton;

	private static TacticViewUiBinder uiBinder = GWT
			.create(TacticViewUiBinder.class);

	interface TacticViewUiBinder extends UiBinder<Widget, TacticView> {
	}

	public TacticView() {
		initWidget(uiBinder.createAndBindUi(this));

	}

	@Override
	public Widget asWidget() {
		return this;
	}

	@Override
	public HasClickHandlers getUpdateButton() {
		return updateTacticButton;
	}

	@Override
	public HasClickHandlers getPlayMatchButton() {
		return playMatchButton;
	}

	@Override
	public void setTeamName(String teamName) {
		teamNameField.setText(teamName);

	}

	@Override
	public void setMatchWins(String matchWins) {
		winsField.setText(matchWins);
	}

	@Override
	public void setMatchLost(String matchLost) {
		lostField.setText(matchLost);
	}

	@Override
	public void setMatchTied(String matchTied) {
		tiedField.setText(matchTied);
	}

	@Override
	public void setGoalsFor(String goalsFor) {
		goalsForField.setText(goalsFor);
	}

	@Override
	public void setGoalsAgainst(String goalsAgainst) {
		goalsAgainstField.setText(goalsAgainst);
	}

	@Override
	public void setVisibleUpdateButton(boolean visible) {
		updateTacticButton.setVisible(visible);
	}

	@Override
	public void setVisibleAddButton(boolean visible) {
		addTacticButton.setVisible(visible);
	}

}
