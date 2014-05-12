/**
 * 
 */
package org.javahispano.javaleague.client.presenter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;

import org.gwtbootstrap3.client.ui.Anchor;
import org.gwtbootstrap3.client.ui.Badge;
import org.gwtbootstrap3.client.ui.Button;
import org.gwtbootstrap3.client.ui.Column;
import org.gwtbootstrap3.client.ui.Italics;
import org.gwtbootstrap3.client.ui.Label;
import org.gwtbootstrap3.client.ui.Paragraph;
import org.gwtbootstrap3.client.ui.Row;
import org.gwtbootstrap3.client.ui.Small;
import org.gwtbootstrap3.client.ui.constants.Alignment;
import org.gwtbootstrap3.client.ui.constants.ColumnSize;
import org.gwtbootstrap3.client.ui.constants.IconType;
import org.javahispano.javaleague.client.event.PlayMatchEvent;
import org.javahispano.javaleague.client.event.UpdateTacticEvent;
import org.javahispano.javaleague.client.helper.MyClickHandlerLeague;
import org.javahispano.javaleague.client.helper.RPCCall;
import org.javahispano.javaleague.client.resources.messages.JavaLeagueMessages;
import org.javahispano.javaleague.client.service.MatchServiceAsync;
import org.javahispano.javaleague.client.service.TacticServiceAsync;
import org.javahispano.javaleague.client.service.UploadBlobstoreServiceAsync;
import org.javahispano.javaleague.client.service.UserFileServiceAsync;
import org.javahispano.javaleague.shared.AppLib;
import org.javahispano.javaleague.shared.domain.Match;
import org.javahispano.javaleague.shared.domain.TacticUser;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.DateTimeFormat.PredefinedFormat;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteEvent;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteHandler;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.XMLParser;

/**
 * @author adou
 * 
 */
public class TacticPresenter implements Presenter {

	public interface Display {
		Widget asWidget();

		void setTeamName(String teamName);

		void setMatchWins(String matchWins);

		void setMatchLost(String matchLost);

		void setMatchTied(String matchTied);

		void setGoalsFor(String goalsFor);

		void setGoalsAgainst(String goalsAgainst);

		Button getPlayMatchButton();

		Label getErrorTeamName();

		TextBox getTeamName();

		FormPanel getFormPanelTactic();

		Badge getFileName();

		Label getUpdatedTactic();

		Button getUpdateButton();

		Label getErrorPackagePath();

		Label getErrorInterfaceTactic();

		Paragraph getMessagePackagePath();

		Label getWaitForFriendlyMatch();

		Paragraph getNextMatchs();

		Paragraph getLastMatchs();
	}

	private TacticUser tactic;
	private final SimpleEventBus eventBus;
	private final Display display;
	private final TacticServiceAsync tacticService;
	private final MatchServiceAsync matchService;
	private final UserFileServiceAsync userFileService;
	private final UploadBlobstoreServiceAsync blobstoreService;

	private int round;
	private Date now;

	private JavaLeagueMessages javaLeagueMessages = GWT
			.create(JavaLeagueMessages.class);

	public TacticPresenter(TacticServiceAsync tacticService,
			MatchServiceAsync matchService,
			UserFileServiceAsync userFileService,
			UploadBlobstoreServiceAsync blobstoreService,
			SimpleEventBus eventBus, Display display) {
		this.display = display;
		this.eventBus = eventBus;
		this.tacticService = tacticService;
		this.matchService = matchService;
		this.userFileService = userFileService;
		this.blobstoreService = blobstoreService;

		hideErrorLabel();

		this.display.getUpdateButton().setEnabled(false);
		this.display.getWaitForFriendlyMatch().setVisible(false);

		bind();
	}

	public void bind() {

		display.getUpdateButton().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				GWT.log("TacticPresenter: Firing UpdateTacticEvent");
				eventBus.fireEvent(new UpdateTacticEvent(tactic));

				doUpdateTactic();
			}
		});

		display.getPlayMatchButton().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				GWT.log("TacticPresenter: Firing PlayMatchEvent");
				eventBus.fireEvent(new PlayMatchEvent(tactic.getId()));

				doPlayMatch();
			}
		});

		display.getFormPanelTactic().addSubmitCompleteHandler(
				new SubmitCompleteHandler() {
					@Override
					public void onSubmitComplete(SubmitCompleteEvent event) {

						if (!event.getResults().isEmpty()) {
							Document document = XMLParser.parse(event
									.getResults());
							Element tacticElement = document
									.getDocumentElement();
							XMLParser.removeWhitespace(tacticElement);

							int validate = Integer
									.parseInt(getElementTextValue(
											tacticElement, "error"));

							switch (validate) {
							case 0:
								showTactic(tacticElement);
								break;
							case 1:
								display.getErrorPackagePath().setVisible(true);
								break;
							case 2:
								display.getErrorInterfaceTactic().setVisible(
										true);
								break;
							}
						} else {
							display.getFileName().setText(
									javaLeagueMessages.emptyUserTactic());
						}
					}
				});
	}

	private void showTactic(Element tacticElement) {
		DateTimeFormat fmt = DateTimeFormat
				.getFormat(PredefinedFormat.DATE_TIME_MEDIUM);
		Date now = new Date();

		display.setTeamName(getElementTextValue(tacticElement, "teamname"));

		display.getUpdatedTactic().setText(fmt.format(now));

		if (!getElementTextValue(tacticElement, "filename").equals(
				AppLib.NO_FILE)) {
			display.getFileName().setText(
					getElementTextValue(tacticElement, "filename") + " :: "
							+ getElementTextValue(tacticElement, "bytes")
							+ " bytes");
			if (tactic.getFriendlyMatch() != AppLib.FRIENDLY_MATCH_SCHEDULED) {
				display.getPlayMatchButton().setEnabled(true);
			}
		} else {
			display.getFileName().setText(javaLeagueMessages.emptyUserTactic());
		}

	}

	private void doUpdateTactic() {
		boolean error = false;

		hideErrorLabel();

		if (display.getTeamName().getValue().isEmpty()) {
			display.getErrorTeamName().setVisible(true);
			error = true;
		}

		if (!error) {
			tactic.setTeamName(display.getTeamName().getValue());
			display.getFormPanelTactic().submit();
		}
	}

	private void doPlayMatch() {
		new RPCCall() {

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Error dispatching match for Tactic "
						+ tactic.getId() + ": " + caught.getMessage());
			}

			@Override
			public void onSuccess(Object result) {
				GWT.log("Dispathing Match for Tactic: " + tactic.getId());

				display.getPlayMatchButton().setEnabled(false);
				display.getWaitForFriendlyMatch().setVisible(true);
			}

			@Override
			protected void callService(AsyncCallback cb) {
				matchService.dispatchMatch(tactic.getId(), cb);
			}

		}.retry(3);

	}

	@Override
	public void go(final HasWidgets container) {
		container.clear();
		container.add(display.asWidget());
		fetchTacticSummary();
	}

	private void fetchMatchs() {
		new RPCCall<List<Match>>() {

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Error fetching matchs. TacticId: "
						+ tactic.getId() + " :: " + caught.getMessage());
			}

			@Override
			public void onSuccess(List<Match> result) {
				if ((result != null) && (result.size() > 0)) {
					for (Match m : result) {
						Row row = new Row();

						row.add(addType(m.getLeagueId()));
						row.add(addTeam(m.getLocal().getId(), m.getNameLocal(),
								m.getNameLocalManager()));
						row.add(addResult(m.getLocalGoals(),
								m.getVisitingTeamGoals(),
								m.getLocalPossesion(), m.getState(), m.getId(),
								m.getVisualization()));
						row.add(addTeam(m.getVisiting().getId(),
								m.getNameForeign(), m.getNameVisitingManager()));
						row.add(addLinks(m.getId(), m.getLocal().getUserId(), m
								.getVisiting().getUserId(), m.getState()));

						if ((m.getState() == AppLib.MATCH_OK)
								&& (!now.before(addMinutesToDate(
										m.getVisualization(),
										-AppLib.MINUTES_BEFORE_LIVE_MATCH)))) {
							display.getLastMatchs().add(row);
						} else {
							display.getNextMatchs().add(row);
						}
					}
				}
			}

			@Override
			protected void callService(AsyncCallback<List<Match>> cb) {
				matchService.getMatchList(tactic.getId(), cb);
			}

		}.retry(3);
	}

	private void fetchTacticSummary() {

		new RPCCall<TacticUser>() {
			@Override
			protected void callService(AsyncCallback<TacticUser> cb) {
				tacticService.getTacticUserLogin(cb);
			}

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Error fetching tactic summary: "
						+ caught.getMessage());
			}

			@Override
			public void onSuccess(TacticUser result) {
				if (result != null) {
					tactic = result;

					DateTimeFormat fmt = DateTimeFormat
							.getFormat(PredefinedFormat.DATE_TIME_MEDIUM);
					display.getUpdateButton().setEnabled(true);

					display.getUpdatedTactic().setText(
							fmt.format(result.getUpdated()));
					display.setTeamName(result.getTeamName());
					display.setGoalsAgainst(Integer.toString(result
							.getGoalsAgainst()));
					display.setGoalsFor(Integer.toString(result.getGoalsFor()));
					display.setMatchLost(Integer.toString(result.getMatchLost()));
					display.setMatchTied(Integer.toString(result.getMatchTied()));
					display.setMatchWins(Integer.toString(result.getMatchWins()));

					if (!result.getFileName().equals(AppLib.NO_FILE)) {
						display.getFileName().setText(
								result.getFileName() + " :: "
										+ result.getBytes() + " bytes");
					} else {
						display.getFileName().setText(
								javaLeagueMessages.emptyUserTactic());
						display.getPlayMatchButton().setEnabled(false);
					}

					if (tactic.getFriendlyMatch() == AppLib.FRIENDLY_MATCH_SCHEDULED) {
						display.getPlayMatchButton().setEnabled(false);
						display.getWaitForFriendlyMatch().setVisible(true);
					}

					display.getMessagePackagePath().setText(
							javaLeagueMessages.packagePath(AppLib.PATH_PACKAGE
									+ tactic.getId().toString()));
				} else {
					display.getUpdateButton().setEnabled(false);
					display.getPlayMatchButton().setEnabled(false);
				}
				fetchDate();
			}
		}.retry(3);

	}

	private void hideErrorLabel() {
		display.getErrorTeamName().setVisible(false);
		display.getErrorInterfaceTactic().setVisible(false);
		display.getErrorPackagePath().setVisible(false);
	}

	private String getElementTextValue(Element parent, String elementTag) {
		// If the xml is not coming from a known good source, this method would
		// have to include safety checks.
		return parent.getElementsByTagName(elementTag).item(0).getFirstChild()
				.getNodeValue();
	}

	private Column addType(Long leagueId) {
		Column column = new Column();

		column.setSize(ColumnSize.MD_1);
		if (leagueId != 0) {
			Anchor anchor = new Anchor();
			anchor.setText(javaLeagueMessages.league());
			MyClickHandlerLeague myClickHandler = new MyClickHandlerLeague(
					leagueId, eventBus);
			anchor.addClickHandler(myClickHandler);
			column.add(anchor);
		} else {
			Italics italics = new Italics();
			italics.setText(javaLeagueMessages.friendly());
			column.add(italics);
		}

		return column;
	}

	private Column addResult(int localGoals, int visitingTeamGoals,
			double localPossesion, int state, Long id, Date d) {
		Column column = new Column();
		column.setSize(ColumnSize.MD_2);
		Paragraph p = new Paragraph();
		p.setAlignment(Alignment.CENTER);
		Paragraph result = new Paragraph();
		Small possesion = new Small();
		DateTimeFormat date = DateTimeFormat
				.getFormat(PredefinedFormat.DATE_TIME_MEDIUM);

		Anchor anchor = new Anchor();

		switch (state) {
		case AppLib.MATCH_ERROR:
			anchor.setText(javaLeagueMessages.matchError());
			anchor.setHref(AppLib.baseURL + "/serveError?id="
					+ Long.toString(id));
			break;
		case AppLib.MATCH_SCHEDULED:
			anchor.setText(date.format(d));
			break;
		case AppLib.MATCH_QUEUE:
			anchor.setText(date.format(d));
			break;
		case AppLib.MATCH_OK:
			if (now.before(addMinutesToDate(d,
					-AppLib.MINUTES_BEFORE_LIVE_MATCH))) {
				anchor.setText(date.format(d));
			} else {
				if (now.after(addMinutesToDate(d,
						-AppLib.MINUTES_BEFORE_LIVE_MATCH))
						&& now.before(addMinutesToDate(d,
								AppLib.MINUTES_AFTER_LIVE_MATCH))) {
					anchor.setText(javaLeagueMessages.live());
				} else {
					anchor.setText(localGoals + " - " + visitingTeamGoals);
					possesion.setText(round(localPossesion * 100d, 2) + " - "
							+ round((1d - localPossesion) * 100d, 2));
				}
				/*
				 * De momento el enlace descarga el partido Falta arreglar el
				 * visor para ver el partido en directo
				 */
				anchor.setHref(AppLib.baseURL + "/serve?id="
						+ Long.toString(id));
				/*
				 * MyClickHandlerMatch myClickHandler = new MyClickHandlerMatch(
				 * id, eventBus); anchor.addClickHandler(myClickHandler);
				 */
			}
			break;
		}

		result.add(anchor);

		p.add(result);
		p.add(possesion);

		column.add(p);

		return column;
	}

	private Column addTeam(Long id, String name, String nameManager) {
		Column column = new Column();
		column.setSize(ColumnSize.MD_4);
		Paragraph p = new Paragraph();
		p.setAlignment(Alignment.CENTER);
		Paragraph teamName = new Paragraph();
		Small managerName = new Small();

		teamName.setText(name);
		managerName.setText(nameManager);

		p.add(teamName);
		p.add(managerName);

		column.add(p);

		return column;
	}

	private Column addLinks(Long id, Long localId, Long visitingId, int state) {
		Column column = new Column();
		column.setSize(ColumnSize.MD_1);

		if (state == AppLib.MATCH_OK) {
			Paragraph p = new Paragraph();
			p.setAlignment(Alignment.CENTER);
			Anchor match = new Anchor();
			match.setIcon(IconType.DOWNLOAD);
			match.setHref(AppLib.baseURL + "/serve?id=" + Long.toString(id));
			p.add(match);

			if (tactic.getUserId().equals(localId)) {
				Anchor csv = new Anchor();
				csv.setIcon(IconType.CALENDAR);
				csv.setHref(AppLib.baseURL + "/timeTacticMatch?id="
						+ Long.toString(id) + "&tactic=local");

				p.add(csv);

			} else if (tactic.getUserId().equals(visitingId)) {
				Anchor csv = new Anchor();
				csv.setIcon(IconType.CALENDAR);
				csv.setHref(AppLib.baseURL + "/timeTacticMatch?id="
						+ Long.toString(id) + "&tactic=vis");

				p.add(csv);
			}

			column.add(p);
		}

		return column;
	}

	/**
	 * Agrega o quita minutos a una fecha dada. Para quitar minutos hay que
	 * sumarle valores negativos.
	 * 
	 * @param date
	 * @param minutes
	 * @return
	 */
	private Date addMinutesToDate(Date date, int minutes) {
		return new Date(date.getTime() + (minutes * 60 * 1000));
	}

	private void fetchDate() {
		new RPCCall<Date>() {

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Error fetching dateNow. tacticId: "
						+ tactic.getId() + " :: " + caught.getMessage());
			}

			@Override
			public void onSuccess(Date result) {
				now = result;
				fetchMatchs();
			}

			@Override
			protected void callService(AsyncCallback<Date> cb) {
				tacticService.getDateNow(cb);
			}

		}.retry(3);
	}

	private static double round(double value, int places) {
		if (places < 0)
			throw new IllegalArgumentException();

		BigDecimal bd = new BigDecimal(value);
		bd = bd.setScale(places, RoundingMode.HALF_UP);
		return bd.doubleValue();
	}

}
