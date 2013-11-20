/**
 * 
 */
package org.javahispano.javaleague.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author adou
 *
 */
public class MyFooterPanel extends Composite {

	private static MyFooterPanelUiBinder uiBinder = GWT
			.create(MyFooterPanelUiBinder.class);

	interface MyFooterPanelUiBinder extends UiBinder<Widget, MyFooterPanel> {
	}

	public MyFooterPanel() {
		initWidget(uiBinder.createAndBindUi(this));
	}

}
