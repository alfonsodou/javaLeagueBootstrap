/**
 * 
 */
package org.javahispano.javaleague.client.presenter;

import org.javahispano.javaleague.client.event.CreateLeagueEvent;
import org.javahispano.javaleague.client.service.MatchServiceAsync;
import org.javahispano.javaleague.client.service.TacticServiceAsync;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author adou
 *
 */
public class MyLeaguesPresenter implements Presenter {

	public interface Display {
		Widget asWidget();
		
		HasClickHandlers getCreateLeagueButton();
	}
	
	private final SimpleEventBus eventBus;
	private final Display display;
	private final TacticServiceAsync tacticService;
	private final MatchServiceAsync matchService;	
	
	public MyLeaguesPresenter(TacticServiceAsync tacticService, MatchServiceAsync matchService,
			SimpleEventBus eventBus, Display display) {
		this.display = display;
		this.eventBus = eventBus;
		this.tacticService = tacticService;
		this.matchService = matchService;

		bind();
	}	
	
	@Override
	public void go(HasWidgets container) {
		container.clear();
		container.add(display.asWidget());
		
	}
	
	private void bind() {
		this.display.getCreateLeagueButton().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				eventBus.fireEvent(new CreateLeagueEvent());
			}
		});
		
	}

}
