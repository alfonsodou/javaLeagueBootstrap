/**
 * 
 */
package org.javahispano.javaleague.client.view;

import org.gwtbootstrap3.client.ui.AnchorButton;
import org.gwtbootstrap3.client.ui.ListItem;
import org.gwtbootstrap3.client.ui.NavbarBrand;
import org.javahispano.javaleague.client.presenter.MenuPrivatePresenter;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;


/**
 * @author adou
 * 
 */
public class MenuPrivateView extends Composite implements
		MenuPrivatePresenter.Display {

	@UiField
	ListItem logoutLink;
	@UiField
	ListItem myTeamLink;
	@UiField
	ListItem myLeaguesLink;
	@UiField
	ListItem frameWorkLink;
	@UiField
	NavbarBrand navbarBrand;
	@UiField
	AnchorButton userName;

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

	@Override
	public AnchorButton getUserName() {
		return userName;
	}

	@Override
	public HasClickHandlers getFrameWorkLink() {
		return frameWorkLink;
	}

	@Override
	public HasClickHandlers getNavbarBrand() {
		return navbarBrand;
	}

}
