/**
 * 
 */
package org.javahispano.javaleague.client.view;

import org.javahispano.javaleague.client.presenter.MenuPrivatePresenter;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.svenjacobs.gwtbootstrap3.client.ui.NavbarLink;

/**
 * @author adou
 * 
 */
public class MenuPrivateView extends Composite implements
		MenuPrivatePresenter.Display {

	@UiField
	NavbarLink logoutLink;
	@UiField
	NavbarLink myTeamLink;
	@UiField
	NavbarLink myLeaguesLink;

	private static MenuPrivateViewUiBinder uiBinder = GWT
			.create(MenuPrivateViewUiBinder.class);

	interface MenuPrivateViewUiBinder extends UiBinder<Widget, MenuPrivateView> {
	}

	public MenuPrivateView() {
		initWidget(uiBinder.createAndBindUi(this));

	}

	@Override
	public HasClickHandlers getLogoutLink() {
		return logoutLink;
	}

	@Override
	public Widget asWidget() {
		return this;
	}

	@Override
	public HasClickHandlers getMyTeamLink() {
		return myTeamLink;
	}

	@Override
	public HasClickHandlers getMyLeaguesLink() {
		return myLeaguesLink;
	}

}
