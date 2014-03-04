/**
 * 
 */
package org.javahispano.javaleague.client.presenter;

import java.util.List;

import org.javahispano.javaleague.client.helper.RPCCall;
import org.javahispano.javaleague.client.service.FrameWorkServiceAsync;
import org.javahispano.javaleague.shared.domain.FrameWork;

import com.google.gwt.event.shared.SimpleEventBus;
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

		void setListFrameWorks(List<FrameWork> frameWorks);
	}

	private final Display display;
	private final FrameWorkServiceAsync frameWorkService;
	private final SimpleEventBus eventBus;
	
	private List<FrameWork> frameWorks;

	public FrameWorkPresenter(FrameWorkServiceAsync frameWorkService,
			SimpleEventBus eventBus, Display display) {
		this.frameWorkService = frameWorkService;
		this.eventBus = eventBus;
		this.display = display;

		bind();
	}

	private void bind() {

	}

	@Override
	public void go(HasWidgets container) {
		fetchFrameWorks();

		container.clear();
		container.add(display.asWidget());
	}

	private void fetchFrameWorks() {
		new RPCCall<List<FrameWork>>() {

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Error fetching frameWork: "
						+ caught.getMessage());
			}

			@Override
			public void onSuccess(List<FrameWork> result) {
				if (result.size() > 0) {
					frameWorks = result;
					
					display.setListFrameWorks(frameWorks);
				}

			}

			@Override
			protected void callService(AsyncCallback<List<FrameWork>> cb) {
				frameWorkService.getFrameWorks(cb);
			}

		}.retry(3);

	}
}
