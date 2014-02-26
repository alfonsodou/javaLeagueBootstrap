/**
 * 
 */
package org.javahispano.javaleague.client.view;

import org.gwtbootstrap3.client.ui.Button;
import org.gwtbootstrap3.client.ui.Heading;
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
	Heading nameLeague;

	public ShowLeagueView() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public HasClickHandlers getJoinLeagueButton() {
		return joinLeagueButton;
	}

	@Override
	public HasClickHandlers getDropLeagueButton() {
		return dropLeagueButton;
	}

	@Override
	public HasClickHandlers getEditLeagueButton() {
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

}
