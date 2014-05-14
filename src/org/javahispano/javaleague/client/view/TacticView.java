/**
 * 
 */
package org.javahispano.javaleague.client.view;

import org.gwtbootstrap3.client.ui.Badge;
import org.gwtbootstrap3.client.ui.Button;
import org.gwtbootstrap3.client.ui.DescriptionData;
import org.gwtbootstrap3.client.ui.Label;
import org.gwtbootstrap3.client.ui.Paragraph;
import org.javahispano.javaleague.client.presenter.TacticPresenter;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteEvent;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author adou
 * 
 */
public class TacticView extends Composite implements TacticPresenter.Display {

	@UiField
	TextBox teamName;
	@UiField
	DescriptionData winsField;
	@UiField
	DescriptionData lostField;
	@UiField
	DescriptionData tiedField;
	@UiField
	DescriptionData goalsForField;
	@UiField
	DescriptionData goalsAgainstField;
	@UiField
	Button playMatchButton;
	@UiField
	FormPanel formPanelTactic;
	@UiField
	Label errorTeamName;
	@UiField
	Badge fileName;
	@UiField
	Label updatedTactic;
	@UiField
	Button updateTacticButton;
	@UiField
	Label errorPackagePath;
	@UiField
	Label errorInterfaceTactic;
	@UiField
	Paragraph messagePackagePath;
	@UiField
	Label waitForFriendlyMatch;
	@UiField
	Paragraph nextMatchs;
	@UiField
	Paragraph lastMatchs;
	@UiField
	Button updateWindowButton;

	private static TacticViewUiBinder uiBinder = GWT
			.create(TacticViewUiBinder.class);

	interface TacticViewUiBinder extends UiBinder<Widget, TacticView> {
	}

	public TacticView() {
		initWidget(uiBinder.createAndBindUi(this));

		formPanelTactic.setMethod(FormPanel.METHOD_POST);
		formPanelTactic.setEncoding(FormPanel.ENCODING_MULTIPART);

	}

	@UiHandler("updateTacticButton")
	void onUploadClick(ClickEvent event) {
		formPanelTactic.submit();
	}

	@UiHandler("formPanelTactic")
	void onUploadFormSubmitComplete(SubmitCompleteEvent event) {
		event.getResults();
	}

	@Override
	public Widget asWidget() {
		return this;
	}

	@Override
	public Button getPlayMatchButton() {
		return playMatchButton;
	}

	@Override
	public void setTeamName(String teamNameParam) {
		teamName.setValue(teamNameParam);
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
	public Label getErrorTeamName() {
		return errorTeamName;
	}

	@Override
	public TextBox getTeamName() {
		return teamName;
	}

	@Override
	public FormPanel getFormPanelTactic() {
		return formPanelTactic;
	}

	@Override
	public Badge getFileName() {
		return fileName;
	}

	@Override
	public Label getUpdatedTactic() {
		return updatedTactic;
	}

	@Override
	public Button getUpdateButton() {
		return updateTacticButton;
	}

	@Override
	public Label getErrorPackagePath() {
		return errorPackagePath;
	}

	@Override
	public Label getErrorInterfaceTactic() {
		return errorInterfaceTactic;
	}

	@Override
	public Paragraph getMessagePackagePath() {
		return messagePackagePath;
	}

	@Override
	public Label getWaitForFriendlyMatch() {
		return waitForFriendlyMatch;
	}

	@Override
	public Paragraph getNextMatchs() {
		return nextMatchs;
	}

	@Override
	public Paragraph getLastMatchs() {
		return lastMatchs;
	}

	@Override
	public Button getUpdateWindowButton() {
		return updateWindowButton;
	}

}
