/**
 * 
 */
package org.javahispano.javaleague.client.view;

import java.util.Vector;

import org.javahispano.javaleague.client.presenter.ShowHomePresenter;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTMLTable;
import com.google.gwt.user.client.ui.Label;
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
