/**
 * 
 */
package org.javahispano.javaleague.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author adou
 * 
 */
public class TacticPanel extends Composite {

	private static TacticPanelUiBinder uiBinder = GWT
			.create(TacticPanelUiBinder.class);

	@UiField
	VerticalPanel tacticPanel;

	interface TacticPanelUiBinder extends UiBinder<Widget, TacticPanel> {
	}

	public TacticPanel() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	public VerticalPanel getTacticPanel() {
		return tacticPanel;
	}

}
