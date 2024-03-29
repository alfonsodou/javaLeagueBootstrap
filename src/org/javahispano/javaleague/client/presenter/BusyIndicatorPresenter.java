package org.javahispano.javaleague.client.presenter;

import org.javahispano.javaleague.client.event.RPCInEvent;
import org.javahispano.javaleague.client.event.RPCInEventHandler;
import org.javahispano.javaleague.client.event.RPCOutEvent;
import org.javahispano.javaleague.client.event.RPCOutEventHandler;

import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;

/**
 * 
 * @author adou
 * 
 */
public class BusyIndicatorPresenter implements Presenter {

	public interface Display {
		void show();

		void hide();

		Widget asWidget();
	}

	int outCount = 0; // # of RPC calls sent by the app. If > 0 we'll show a
	// 'busy' indicator.

	private final SimpleEventBus eventBus;
	private Display display;

	public BusyIndicatorPresenter(SimpleEventBus eventBus, Display view) {
		this.eventBus = eventBus;
		this.display = view;

		bind();
	}

	public void bind() {
		eventBus.addHandler(RPCInEvent.TYPE, new RPCInEventHandler() {
			@Override
			public void onRPCIn(RPCInEvent event) {
				outCount = outCount > 0 ? --outCount : 0;
				if (outCount <= 0) {
					display.hide();
				}
			}
		});
		eventBus.addHandler(RPCOutEvent.TYPE, new RPCOutEventHandler() {
			@Override
			public void onRPCOut(RPCOutEvent event) {
				outCount++;
				display.show();
			}
		});
	}

	public void go(HasWidgets container) {
		// nothing to do
	}
}
