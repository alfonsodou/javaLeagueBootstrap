/**
 * 
 */
package org.javahispano.javaleague.client.view;

import org.gwtbootstrap3.client.ui.Button;
import org.gwtbootstrap3.client.ui.DescriptionData;
import org.gwtbootstrap3.client.ui.NavbarLink;
import org.gwtbootstrap3.client.ui.Paragraph;
import org.javahispano.javaleague.client.presenter.FrameWorkPresenter;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author adou
 * 
 */
public class FrameWorkView extends Composite implements
		FrameWorkPresenter.Display {

	private static FrameWorkViewUiBinder uiBinder = GWT
			.create(FrameWorkViewUiBinder.class);

	@UiField
	Paragraph frameWorkParagraph;
	@UiField
	DescriptionData nameFrameWork;
	@UiField
	DescriptionData versionFrameWork;
	@UiField
	DescriptionData createdFrameWork;
	@UiField
	DescriptionData updatedFrameWork;
	@UiField
	NavbarLink downloadFrameWorkNavbarLink;

	interface FrameWorkViewUiBinder extends UiBinder<Widget, FrameWorkView> {
	}

	public FrameWorkView() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public Paragraph getFrameWorkParagraph() {
		return frameWorkParagraph;
	}

	@Override
	public DescriptionData getNameFrameWork() {
		return nameFrameWork;
	}

	@Override
	public DescriptionData getVersionFrameWork() {
		return versionFrameWork;
	}

	@Override
	public DescriptionData getCreatedFrameWork() {
		return createdFrameWork;
	}

	@Override
	public DescriptionData getUpdatedFrameWork() {
		return updatedFrameWork;
	}

	@Override
	public NavbarLink getDownloadFrameWorkNavbarLink() {
		return downloadFrameWorkNavbarLink;
	}


}
