/**
 * 
 */
package org.javahispano.javaleague.client.presenter;

import org.gwtbootstrap3.client.ui.Heading;
import org.gwtbootstrap3.client.ui.Paragraph;
import org.javahispano.javaleague.client.resources.messages.JavaLeagueMessages;
import org.javahispano.javaleague.client.service.LeagueServiceAsync;
import org.javahispano.javaleague.shared.LeagueDTO;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author adou
 * 
 */
public class ShowLeaguePresenter implements Presenter {
	public interface Display {
		Widget asWidget();

		HasClickHandlers getJoinLeagueButton();

		HasClickHandlers getDropLeagueButton();

		HasClickHandlers getEditLeagueButton();

		Paragraph getDescriptionLeague();
		
		Heading getNameLeague();

	}

	private final SimpleEventBus eventBus;
	private final Display display;
	private final LeagueServiceAsync leagueService;

	private LeagueDTO leagueDTO;

	private JavaLeagueMessages javaLeagueMessages = GWT
			.create(JavaLeagueMessages.class);

	public ShowLeaguePresenter(LeagueServiceAsync leagueService,
			LeagueDTO leagueDTO, SimpleEventBus eventBus, Display display) {
		this.leagueService = leagueService;
		this.leagueDTO = leagueDTO;
		this.eventBus = eventBus;
		this.display = display;
		
		ShowLeague();
	}

	private void ShowLeague() {
		display.getDescriptionLeague().setHTML(leagueDTO.getDescription());
		display.getNameLeague().setText(leagueDTO.getName());
	}
	
	@Override
	public void go(HasWidgets container) {
		container.clear();
		container.add(display.asWidget());

		bind();
	}

	private void bind() {

	}

}
