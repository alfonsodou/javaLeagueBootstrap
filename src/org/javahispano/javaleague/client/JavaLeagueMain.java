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
public class JavaLeagueMain extends Composite {
	
	@UiField
	FlowPanel myHeaderPanel;
	
	@UiField
	FlowPanel myCenterPanel;
	
	@UiField
	FlowPanel myFooterPanel;

	private static JavaLeagueMainUiBinder uiBinder = GWT
			.create(JavaLeagueMainUiBinder.class);

	interface JavaLeagueMainUiBinder extends UiBinder<Widget, JavaLeagueMain> {
	}

	public JavaLeagueMain() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	public FlowPanel getMyHeaderPanel() {
		return myHeaderPanel;
	}

	public FlowPanel getMyCenterPanel() {
		return myCenterPanel;
	}

	public FlowPanel getMyFooterPanel() {
		return myFooterPanel;
	}



}
