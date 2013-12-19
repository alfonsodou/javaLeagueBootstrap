/**
 * 
 */
package org.javahispano.javaleague.client.presenter;

import org.gwtbootstrap3.client.ui.Form;
import org.javahispano.javaleague.client.helper.RPCCall;
import org.javahispano.javaleague.client.service.LeagueServiceAsync;
import org.javahispano.javaleague.shared.LeagueDTO;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;


/**
 * @author adou
 * 
 */
public class CreateLeaguePresenter implements Presenter {

	public interface Display {
		Widget asWidget();

		HasClickHandlers getCancelButton();

		HasClickHandlers getCreateLeagueButton();

		HasValue<String> getLeagueName();

		Form getFormPanel();
	}

	private LeagueDTO leagueDTO;
	
	private final LeagueServiceAsync leagueService;
	private final SimpleEventBus eventBus;
	private final Display display;

	public CreateLeaguePresenter(LeagueServiceAsync leagueService,
			SimpleEventBus eventBus, Display display) {
		this.leagueService = leagueService;
		this.eventBus = eventBus;
		this.display = display;
		
		leagueDTO = new LeagueDTO();
		
		bind();

		/*
		CreateLeaguePresenter.this.display.getFormPanel()
		.addSubmitCompleteHandler(new Form.SubmitCompleteHandler() {

			@Override
			public void onSubmitComplete(
					com.svenjacobs.gwtbootstrap3.client.ui.Form.SubmitCompleteEvent event) {
				CreateLeaguePresenter.this.display.getFormPanel().reset();
			}
		});
		*/
	}

	@Override
	public void go(HasWidgets container) {
		container.clear();
		container.add(display.asWidget());
	}

	private void bind() {
		this.display.getCreateLeagueButton().addClickHandler(
				new ClickHandler() {
					public void onClick(ClickEvent event) {
						doSave();
					}
				});
	}

	private void doSave() {
		leagueDTO.setLeagueName(display.getLeagueName().getValue().trim());
		
		new RPCCall<Void>() {
			@Override
			protected void callService(AsyncCallback<Void> cb) {
				leagueService.createLeague(leagueDTO, cb);
			}

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Error creating league...");
			}

			@Override
			public void onSuccess(Void result) {
				// TODO Auto-generated method stub
				
			}
		}.retry(3);
	}
}
