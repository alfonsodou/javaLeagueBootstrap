package org.javahispano.javaleague.client.view;

import org.gwtbootstrap3.client.ui.Icon;
import org.gwtbootstrap3.client.ui.Panel;
import org.gwtbootstrap3.client.ui.constants.IconSize;
import org.gwtbootstrap3.client.ui.constants.IconType;
import org.javahispano.javaleague.client.presenter.BusyIndicatorPresenter;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * Displays a loading message, indicating the app is working while the user is
 * waiting.
 * 
 */
public class BusyIndicatorView extends PopupPanel implements
		BusyIndicatorPresenter.Display {
	private Label message = new Label("Working...");
	private Panel panel = new Panel();
	private Icon icon = new Icon();
	
	private static BusyIndicatorUiBinder uiBinder = GWT
			.create(BusyIndicatorUiBinder.class);

	interface BusyIndicatorUiBinder extends UiBinder<Widget, BusyIndicatorView> {
	}	

	public BusyIndicatorView() {
		center();
		setAnimationEnabled(false);
		
		icon.setType(IconType.GEAR);
		icon.setSize(IconSize.TIMES5);
		icon.setSpin(true);
		
		panel.add(icon);
		add(panel);
		
		//add(message);
		
		//initWidget(uiBinder.createAndBindUi(this));
	}

	public BusyIndicatorView(String msg) {
		this();
		message.setText(msg);
	}

	@Override
	public Widget asWidget() {
		return this;
	}


}
