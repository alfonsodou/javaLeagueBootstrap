/**
 * 
 */
package org.javahispano.javaleague.client.view;

import org.javahispano.javaleague.client.presenter.ShowHomePresenter;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author adou
 * 
 */
public class ShowHomeView extends Composite implements
		ShowHomePresenter.Display {

	private static ShowHomeViewUiBinder uiBinder = GWT
			.create(ShowHomeViewUiBinder.class);

	interface ShowHomeViewUiBinder extends UiBinder<Widget, ShowHomeView> {
	}

	public ShowHomeView() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	public ShowHomeView(String firstName) {
		initWidget(uiBinder.createAndBindUi(this));


	}

	@Override
	public Widget asWidget() {
		return this;
	}



}
