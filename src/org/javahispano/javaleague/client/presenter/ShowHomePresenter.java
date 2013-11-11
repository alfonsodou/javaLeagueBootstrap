package org.javahispano.javaleague.client.presenter;

import java.util.List;
import java.util.Vector;

import org.javahispano.javaleague.client.event.ShowHomeEvent;
import org.javahispano.javaleague.client.event.ViewMatchEvent;
import org.javahispano.javaleague.client.helper.RPCCall;
import org.javahispano.javaleague.client.presenter.ShowMatchPresenter.Display;
import org.javahispano.javaleague.client.service.MatchServiceAsync;
import org.javahispano.javaleague.client.service.TacticServiceAsync;
import org.javahispano.javaleague.server.AppLib;
import org.javahispano.javaleague.shared.MatchDTO;
import org.javahispano.javaleague.shared.TacticDTO;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;

public class ShowHomePresenter implements Presenter {

	public interface Display {
		Widget asWidget();


	}
	
	private final Display display;

	public ShowHomePresenter(Display display) {
		this.display = display;

		bind();
	}

	public void bind() {
	}

	@Override
	public void go(final HasWidgets container) {
		container.clear();
		container.add(display.asWidget());
	}

}
