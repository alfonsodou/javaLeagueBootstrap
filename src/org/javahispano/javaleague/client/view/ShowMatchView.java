/**
 * 
 */
package org.javahispano.javaleague.client.view;

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

	private static ShowMatchViewUiBinder uiBinder = GWT
			.create(ShowMatchViewUiBinder.class);

	interface ShowMatchViewUiBinder extends UiBinder<Widget, ShowMatchView> {
	}

	/**
	 * Because this class has a default constructor, it can be used as a binder
	 * template. In other words, it can be used in other *.ui.xml files as
	 * follows: <ui:UiBinder xmlns:ui="urn:ui:com.google.gwt.uibinder"
	 * xmlns:g="urn:import:**user's package**">
	 * <g:**UserClassName**>Hello!</g:**UserClassName> </ui:UiBinder> Note that
	 * depending on the widget that is used, it may be necessary to implement
	 * HasHTML instead of HasText.
	 */
	public ShowMatchView() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	public ShowMatchView(String firstName) {
		initWidget(uiBinder.createAndBindUi(this));

		// Can access @UiField after calling createAndBindUi

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
}
