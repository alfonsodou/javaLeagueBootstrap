/**
 * 
 */
package org.javahispano.javaleague.client.view;

import java.util.List;

import org.gwtbootstrap3.client.ui.CellTable;
import org.javahispano.javaleague.client.presenter.FrameWorkPresenter;
import org.javahispano.javaleague.client.resources.messages.JavaLeagueMessages;
import org.javahispano.javaleague.shared.FrameWorkDTO;

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

	private List<FrameWorkDTO> frameWorks;
	private ListDataProvider<FrameWorkDTO> dataGridProvider = new ListDataProvider<FrameWorkDTO>();

	interface FrameWorkViewUiBinder extends UiBinder<Widget, FrameWorkView> {
	}

	public FrameWorkView() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	/**
	 * @return the frameWorks
	 */
	public List<FrameWorkDTO> getFrameWorks() {
		return frameWorks;
	}

	/**
	 * @param frameWorks
	 *            the frameWorks to set
	 */
	public void setFrameWorks(List<FrameWorkDTO> frameWorks) {
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
	public void setListFrameWorks(List<FrameWorkDTO> frameWorks) {
		this.frameWorks = frameWorks;

		TextColumn<FrameWorkDTO> col1 = new TextColumn<FrameWorkDTO>() {

			@Override
			public String getValue(FrameWorkDTO object) {
				return String.valueOf(object.getName());
			}
		};
		cellTableFrameWorks.addColumn(col1, javaLeagueMessages.nameFrameWork());
		
		dataGridProvider.addDataDisplay(cellTableFrameWorks);
		
		for(FrameWorkDTO f : frameWorks) {
			dataGridProvider.getList().add(f);
		}
		
		dataGridProvider.flush();
	}

}
