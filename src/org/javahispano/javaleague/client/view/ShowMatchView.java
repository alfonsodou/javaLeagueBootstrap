/**
 * 
 */
package org.javahispano.javaleague.client.view;

import org.gwtbootstrap3.client.ui.Heading;
import org.javahispano.javaleague.client.presenter.ShowMatchPresenter;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.ParamElement;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author adou
 * 
 */
public class ShowMatchView extends Composite implements
		ShowMatchPresenter.Display {

	@UiField
	ParamElement matchID;
	@UiField
	ParamElement date;
	@UiField
	ParamElement dateMatch;
	@UiField
	Heading matchHeading;

	private static ShowMatchViewUiBinder uiBinder = GWT
			.create(ShowMatchViewUiBinder.class);

	interface ShowMatchViewUiBinder extends UiBinder<Widget, ShowMatchView> {
	}

	public ShowMatchView() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public Widget asWidget() {
		return this;
	}

	@Override
	public void setMatchID(String id) {
		this.matchID.setValue(id);
	}

	@Override
	public void setDate(String date) {
		this.date.setValue(date);
	}

	@Override
	public void setDateMatch(String dateMatch) {
		this.dateMatch.setValue(dateMatch);
	}

	@Override
	public Heading getMatchHeading() {
		return matchHeading;
	}
}
