/**
 * 
 */
package org.javahispano.javaleague.client.view;

import java.util.List;

import org.gwtbootstrap3.client.ui.CellTable;
import org.javahispano.javaleague.client.presenter.FrameWorkPresenter;
import org.javahispano.javaleague.client.resources.messages.JavaLeagueMessages;
import org.javahispano.javaleague.shared.domain.FrameWork;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ListDataProvider;

/**
 * @author adou
 * 
 */
public class FrameWorkView extends Composite implements
		FrameWorkPresenter.Display {

	private static FrameWorkViewUiBinder uiBinder = GWT
			.create(FrameWorkViewUiBinder.class);

	private JavaLeagueMessages javaLeagueMessages = GWT
			.create(JavaLeagueMessages.class);

	@UiField
	CellTable cellTableFrameWorks;

	private List<FrameWork> frameWorks;
	private ListDataProvider<FrameWork> dataGridProvider = new ListDataProvider<FrameWork>();

	interface FrameWorkViewUiBinder extends UiBinder<Widget, FrameWorkView> {
	}

	public FrameWorkView() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	/**
	 * @return the frameWorks
	 */
	public List<FrameWork> getFrameWorks() {
		return frameWorks;
	}

	/**
	 * @param frameWorks
	 *            the frameWorks to set
	 */
	public void setFrameWorks(List<FrameWork> frameWorks) {
		this.frameWorks = frameWorks;
	}

	/**
	 * @return the cellTableFrameWorks
	 */
	public CellTable getCellTableFrameWorks() {
		return cellTableFrameWorks;
	}

	/**
	 * @param cellTableFrameWorks
	 *            the cellTableFrameWorks to set
	 */
	public void setCellTableFrameWorks(CellTable cellTableFrameWorks) {
		this.cellTableFrameWorks = cellTableFrameWorks;
	}

	@Override
	public void setListFrameWorks(List<FrameWork> frameWorks) {
		this.frameWorks = frameWorks;

		TextColumn<FrameWork> col1 = new TextColumn<FrameWork>() {

			@Override
			public String getValue(FrameWork object) {
				return String.valueOf(object.getName());
			}
		};
		cellTableFrameWorks.addColumn(col1, javaLeagueMessages.nameFrameWork());
		
		TextColumn<FrameWork> col2 = new TextColumn<FrameWork>() {

			@Override
			public String getValue(FrameWork object) {
				return String.valueOf(object.getVersion());
			}
		};
		cellTableFrameWorks.addColumn(col2, javaLeagueMessages.versionFrameWork());

		TextColumn<FrameWork> col3 = new TextColumn<FrameWork>() {

			@Override
			public String getValue(FrameWork object) {
				return String.valueOf(object.getSummary());
			}
		};
		cellTableFrameWorks.addColumn(col3, javaLeagueMessages.summaryFrameWork());

		TextColumn<FrameWork> col4 = new TextColumn<FrameWork>() {

			@Override
			public String getValue(FrameWork object) {
				return String.valueOf(object.getCreation());
			}
		};
		cellTableFrameWorks.addColumn(col4, javaLeagueMessages.creationFrameWork());

		TextColumn<FrameWork> col5 = new TextColumn<FrameWork>() {

			@Override
			public String getValue(FrameWork object) {
				return String.valueOf(object.getUpdated());
			}
		};
		cellTableFrameWorks.addColumn(col5, javaLeagueMessages.updatedFrameWork());

		TextColumn<FrameWork> col6 = new TextColumn<FrameWork>() {

			@Override
			public String getValue(FrameWork object) {
				return String.valueOf(object.getUrlDownload());
			}
		};
		cellTableFrameWorks.addColumn(col6, javaLeagueMessages.urlDownloadFrameWork());

		dataGridProvider.addDataDisplay(cellTableFrameWorks);
		
		for(FrameWork f : frameWorks) {
			dataGridProvider.getList().add(f);
		}
		
		dataGridProvider.flush();
	}

}
