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
	

	private static JavaLeagueMainUiBinder uiBinder = GWT
			.create(JavaLeagueMainUiBinder.class);

	interface JavaLeagueMainUiBinder extends UiBinder<Widget, JavaLeagueMain> {
	}

	public JavaLeagueMain() {
		initWidget(uiBinder.createAndBindUi(this));
	}




}
