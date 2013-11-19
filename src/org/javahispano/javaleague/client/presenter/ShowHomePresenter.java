package org.javahispano.javaleague.client.presenter;

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
