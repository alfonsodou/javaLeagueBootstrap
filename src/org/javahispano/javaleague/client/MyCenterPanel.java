/**
 * 
 */
package org.javahispano.javaleague.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author adou
 *
 */
public class MyCenterPanel extends Composite {
	
	@UiField
	HTMLPanel mainPanel;

	private static MyCenterPanelUiBinder uiBinder = GWT
			.create(MyCenterPanelUiBinder.class);

	interface MyCenterPanelUiBinder extends UiBinder<Widget, MyCenterPanel> {
	}

	public MyCenterPanel() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	public HTMLPanel getMainPanel() {
		return mainPanel;
	}
}
