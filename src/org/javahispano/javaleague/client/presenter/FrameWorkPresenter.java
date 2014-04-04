/**
 * 
 */
package org.javahispano.javaleague.client.presenter;

import org.gwtbootstrap3.client.ui.DescriptionData;
import org.gwtbootstrap3.client.ui.NavbarLink;
import org.gwtbootstrap3.client.ui.Paragraph;
import org.javahispano.javaleague.client.helper.RPCCall;
import org.javahispano.javaleague.client.resources.messages.JavaLeagueMessages;
import org.javahispano.javaleague.client.service.FrameWorkServiceAsync;
import org.javahispano.javaleague.shared.domain.FrameWork;
import org.joda.time.LocalDateTime;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author adou
 * 
 */
public class FrameWorkPresenter implements Presenter {

	public interface Display {
		Widget asWidget();

		Paragraph getFrameWorkParagraph();

		DescriptionData getNameFrameWork();

		DescriptionData getVersionFrameWork();

		DescriptionData getCreatedFrameWork();

		DescriptionData getUpdatedFrameWork();

		NavbarLink getDownloadFrameWorkNavbarLink();
	}

	private final Display display;
	private final FrameWorkServiceAsync frameWorkService;
	private final SimpleEventBus eventBus;

	private FrameWork frameWork;

	private JavaLeagueMessages javaLeagueMessages = GWT
			.create(JavaLeagueMessages.class);

	public FrameWorkPresenter(FrameWorkServiceAsync frameWorkService,
			SimpleEventBus eventBus, Display display) {
		this.frameWorkService = frameWorkService;
		this.eventBus = eventBus;
		this.display = display;
		this.frameWork = null;

		bind();
	}

	private void bind() {
		/*
		 * display.getDownloadFrameWorkButton().addClickHandler(new
		 * ClickHandler() { public void onClick(ClickEvent event) {
		 * 
		 * } });
		 */
	}

	@Override
	public void go(HasWidgets container) {
		container.clear();
		container.add(display.asWidget());

		fetchDefaultFrameWork();

	}

	private void fetchDefaultFrameWork() {
		new RPCCall<FrameWork>() {

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Error fetching frameWork: " + caught.getMessage());
			}

			@Override
			public void onSuccess(FrameWork result) {
				if (result != null) {

					DateTimeFormat fmt = DateTimeFormat
							.getFormat("dd/MM/yyyy :: HH:mm:ss");

					frameWork = result;
					display.getNameFrameWork().setText(frameWork.getName());
					display.getVersionFrameWork().setText(
							frameWork.getVersion());
					display.getCreatedFrameWork().setText(
							fmt.format(frameWork.getCreation()));
					display.getUpdatedFrameWork().setText(
							fmt.format(frameWork.getUpdated()));
					display.getDownloadFrameWorkNavbarLink().setHref(
							frameWork.getUrlDownload());
				} else {
					Window.alert("Error al obtener el frameWork!!");
				}

			}

			@Override
			protected void callService(AsyncCallback<FrameWork> cb) {
				frameWorkService.getDefaultFrameWork(cb);
			}

		}.retry(3);

	}
}
