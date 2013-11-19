/**
 * 
 */
package org.javahispano.javaleague.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;
/**
 * @author adou
 *
 */
public class MyHeaderPanel extends Composite {

	@UiField
	FlowPanel menuPanel;
	
	private static MyHeaderPanelUiBinder uiBinder = GWT
			.create(MyHeaderPanelUiBinder.class);

	interface MyHeaderPanelUiBinder extends UiBinder<Widget, MyHeaderPanel> {
	}

	public MyHeaderPanel() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	public FlowPanel getMenuPanel() {
		return menuPanel;
	}
}
