/**
 * 
 */
package org.javahispano.javaleague.client.view;

import org.javahispano.javaleague.client.presenter.TacticEditPresenter;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.Widget;
import com.svenjacobs.gwtbootstrap3.client.ui.Button;
import com.svenjacobs.gwtbootstrap3.client.ui.Form;
import com.svenjacobs.gwtbootstrap3.client.ui.SubmitButton;
import com.svenjacobs.gwtbootstrap3.client.ui.TextBox;

/**
 * @author adou
 * 
 */
public class TacticEditView extends Composite implements
		TacticEditPresenter.Display {

	@UiField
	TextBox teamName;

	@UiField
	Form form;

	@UiField
	FileUpload fileUpload;

	@UiField
	SubmitButton updateButton;

	@UiField
	Button cancelButton;

	@UiField
	TextBox tacticId;

	private static ShowTacticsViewUiBinder uiBinder = GWT
			.create(ShowTacticsViewUiBinder.class);

	interface ShowTacticsViewUiBinder extends UiBinder<Widget, TacticEditView> {
	}

	public TacticEditView() {
		initWidget(uiBinder.createAndBindUi(this));

		fileUpload.setName("fileUpload");
		tacticId.setName("tacticId");
		teamName.setName("teamName");

		form.setEncoding(FormPanel.ENCODING_MULTIPART);
		form.setMethod(FormPanel.METHOD_POST);

	}

	@Override
	protected void onLoad() {
		super.onLoad();
		teamName.setFocus(true);
	}

	@Override
	public Widget asWidget() {
		return this;
	}

	@Override
	public HasClickHandlers getCancelButton() {
		return cancelButton;
	}

	@Override
	public HasClickHandlers getUpdateButton() {
		return updateButton;
	}

	@Override
	public HasValue<String> getTeamName() {
		return teamName;
	}

	@Override
	public void setTacticId(String Id) {
		tacticId.setText(Id);
	}

	@Override
	public HasValue<String> getTacticId() {
		return tacticId;
	}

	@Override
	public Form getFormPanel() {
		return form;
	}

	@Override
	public FileUpload getFileUpload() {
		return fileUpload;
	}

}
