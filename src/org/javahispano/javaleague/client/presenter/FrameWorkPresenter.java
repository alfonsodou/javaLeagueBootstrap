/**
 * 
 */
package org.javahispano.javaleague.client.presenter;

import java.util.List;

import org.javahispano.javaleague.client.helper.RPCCall;
import org.javahispano.javaleague.client.service.FrameWorkServiceAsync;
import org.javahispano.javaleague.shared.FrameWorkDTO;

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

		void setListFrameWorks(List<FrameWorkDTO> frameWorks);
	}

	private final Display display;
	private final FrameWorkServiceAsync frameWorkService;
	private final SimpleEventBus eventBus;
	
	private List<FrameWorkDTO> frameWorks;

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
		fetchFrameWorksDTO();

		container.clear();
		container.add(display.asWidget());
	}

	private void fetchFrameWorksDTO() {
		new RPCCall<List<FrameWorkDTO>>() {

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Error fetching frameWork: "
						+ caught.getMessage());
			}

			@Override
			public void onSuccess(List<FrameWorkDTO> result) {
				if (result.size() > 0) {
					frameWorks = result;
					
					display.setListFrameWorks(frameWorks);
				}

			}

			@Override
			protected void callService(AsyncCallback<List<FrameWorkDTO>> cb) {
				frameWorkService.getFrameWorks(cb);
			}

		}.retry(3);

	}
}
