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
public class FooterPanel extends Composite {

	private static FooterPanelUiBinder uiBinder = GWT
			.create(FooterPanelUiBinder.class);

	interface FooterPanelUiBinder extends UiBinder<Widget, FooterPanel> {
	}

	/**
	 * Because this class has a default constructor, it can
	 * be used as a binder template. In other words, it can be used in other
	 * *.ui.xml files as follows:
	 * <ui:UiBinder xmlns:ui="urn:ui:com.google.gwt.uibinder"
	 *   xmlns:g="urn:import:**user's package**">
	 *  <g:**UserClassName**>Hello!</g:**UserClassName>
	 * </ui:UiBinder>
	 * Note that depending on the widget that is used, it may be necessary to
	 * implement HasHTML instead of HasText.
	 */
	public FooterPanel() {
		initWidget(uiBinder.createAndBindUi(this));
	}


	public FooterPanel(String firstName) {
		initWidget(uiBinder.createAndBindUi(this));


	}


}
