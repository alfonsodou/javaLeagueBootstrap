/**
 * 
 */
package org.javahispano.javaleague.client.view;

import org.gwtbootstrap3.client.ui.Button;
import org.gwtbootstrap3.client.ui.Form;
import org.gwtbootstrap3.client.ui.TextBox;
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
public class CreateLeagueView extends Composite implements CreateLeaguePresenter.Display {

	@UiField
	TextBox leagueName;

	@UiField
	Button createLeagueButton;

	@UiField
	Button cancelLeagueButton;	

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

}
