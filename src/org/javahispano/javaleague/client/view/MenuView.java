/**
 * 
 */
package org.javahispano.javaleague.client.view;

import org.gwtbootstrap3.client.ui.ListItem;
import org.gwtbootstrap3.client.ui.NavbarBrand;
import org.javahispano.javaleague.client.presenter.MenuPresenter;

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
public class MenuView extends Composite implements MenuPresenter.Display {

	private static MenuViewUiBinder uiBinder = GWT
			.create(MenuViewUiBinder.class);

	interface MenuViewUiBinder extends UiBinder<Widget, MenuView> {
	}

	public MenuView() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	
	@UiField
	ListItem registerLink;
	@UiField
	ListItem loginLink;
	@UiField
	ListItem frameWorkLink;
	@UiField
	ListItem wikiLink;
	@UiField
	NavbarBrand navbarBrand;
	
	public MenuView(String firstName) {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public Widget asWidget() {
		return this;
	}

	@Override
	public HasClickHandlers getRegisterLink() {
		return registerLink;
	}

	@Override
	public HasClickHandlers getLoginLink() {
		return loginLink;
	}

	@Override
	public HasClickHandlers getFrameWorkLink() {
		return frameWorkLink;
	}

	@Override
	public HasClickHandlers getNavbarBrand() {
		return navbarBrand;
	}

	@Override
	public HasClickHandlers getWikiLink() {
		return wikiLink;
	}


}
