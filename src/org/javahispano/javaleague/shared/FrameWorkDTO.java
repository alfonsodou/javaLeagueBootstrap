/**
 * 
 */
package org.javahispano.javaleague.shared;

import java.io.Serializable;

/**
 * @author adou
 * 
 */
public class FrameWorkDTO implements Serializable {
	private long id;
	private String name;

	private String description;

	private String summary;

	private String version;

	private int state;

	private String creation;

	private String updated;

	private Boolean active;

	private Boolean defaultFrameWork;

	private String urlDownload;

	public FrameWorkDTO() {

	}

	public FrameWorkDTO(long id, String name, String description,
			String summary, String version, int state, String creation,
			String updated, Boolean active, Boolean defaultFrameWork,
			String urlDownload) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.summary = summary;
		this.version = version;
		this.state = state;
		this.creation = creation;
		this.updated = updated;
		this.active = active;
		this.defaultFrameWork = defaultFrameWork;
		this.urlDownload = urlDownload;
	}

	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the summary
	 */
	public String getSummary() {
		return summary;
	}

	/**
	 * @param summary the summary to set
	 */
	public void setSummary(String summary) {
		this.summary = summary;
	}

	/**
	 * @return the version
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * @param version the version to set
	 */
	public void setVersion(String version) {
		this.version = version;
	}

	/**
	 * @return the state
	 */
	public int getState() {
		return state;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(int state) {
		this.state = state;
	}

	/**
	 * @return the creation
	 */
	public String getCreation() {
		return creation;
	}

	/**
	 * @param creation the creation to set
	 */
	public void setCreation(String creation) {
		this.creation = creation;
	}

	/**
	 * @return the updated
	 */
	public String getUpdated() {
		return updated;
	}

	/**
	 * @param updated the updated to set
	 */
	public void setUpdated(String updated) {
		this.updated = updated;
	}

	/**
	 * @return the active
	 */
	public Boolean getActive() {
		return active;
	}

	/**
	 * @param active the active to set
	 */
	public void setActive(Boolean active) {
		this.active = active;
	}

	/**
	 * @return the defaultFrameWork
	 */
	public Boolean getDefaultFrameWork() {
		return defaultFrameWork;
	}

	/**
	 * @param defaultFrameWork the defaultFrameWork to set
	 */
	public void setDefaultFrameWork(Boolean defaultFrameWork) {
		this.defaultFrameWork = defaultFrameWork;
	}

	/**
	 * @return the urlDownload
	 */
	public String getUrlDownload() {
		return urlDownload;
	}

	/**
	 * @param urlDownload the urlDownload to set
	 */
	public void setUrlDownload(String urlDownload) {
		this.urlDownload = urlDownload;
	}
	
	
}
