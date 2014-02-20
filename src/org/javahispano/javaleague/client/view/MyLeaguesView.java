/**
 * 
 */
package org.javahispano.javaleague.client.view;

import org.gwtbootstrap3.client.ui.Button;
import org.gwtbootstrap3.client.ui.CellTable;
import org.javahispano.javaleague.client.presenter.MyLeaguesPresenter;

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
public class MyLeaguesView extends Composite implements MyLeaguesPresenter.Display {

	private static MyLeaguesViewUiBinder uiBinder = GWT
			.create(MyLeaguesViewUiBinder.class);

	interface MyLeaguesViewUiBinder extends UiBinder<Widget, MyLeaguesView> {
	}
	
	@UiField
	Button createLeagueButton;
	@UiField
	CellTable cellTableLeagues;

	public MyLeaguesView() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public HasClickHandlers getCreateLeagueButton() {
		return createLeagueButton;
	}

	@Override
	public CellTable getCellTableLeagues() {
		return cellTableLeagues;
	}

}
