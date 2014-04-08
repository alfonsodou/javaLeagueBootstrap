/**
 * 
 */
package org.javahispano.javaleague.shared.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.logging.Logger;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

/**
 * @author adou
 * 
 */
@Entity
public class FrameWork implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final Logger log = Logger.getLogger(FrameWork.class
			.getName());

	@Id
	private Long id;

	private String name;

	private String description;

	private String summary;

	private String version;

	private int state;

	private Date creation;

	private Date updated;

	private String frameWork;

	private int active;

	private int defaultFrameWork;

	private String urlDownload;

	public FrameWork() {
		super();
		this.creation = new Date();
		this.updated = new Date();
		this.active = 1;
		this.defaultFrameWork = 1;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public Date getCreation() {
		return creation;
	}

	public void setCreation(Date creation) {
		this.creation = creation;
	}

	public Date getUpdated() {
		return updated;
	}

	public void setUpdated(Date updated) {
		this.updated = updated;
	}

	public int getActive() {
		return active;
	}

	public void setActive(int active) {
		this.active = active;
	}

	/**
	 * @return the state
	 */
	public int getState() {
		return state;
	}

	/**
	 * @param state
	 *            the state to set
	 */
	public void setState(int state) {
		this.state = state;
	}

	/**
	 * @return the frameWork
	 */
	public String getFrameWork() {
		return frameWork;
	}

	/**
	 * @param frameWork
	 *            the frameWork to set
	 */
	public void setFrameWork(String frameWork) {
		this.frameWork = frameWork;
	}

	/**
	 * @return the defaultFrameWork
	 */
	public int getDefaultFrameWork() {
		return defaultFrameWork;
	}

	/**
	 * @param defaultFrameWork
	 *            the defaultFrameWork to set
	 */
	public void setDefaultFrameWork(int defaultFrameWork) {
		this.defaultFrameWork = defaultFrameWork;
	}

	/**
	 * @return the urlDownload
	 */
	public String getUrlDownload() {
		return urlDownload;
	}

	/**
	 * @param urlDownload
	 *            the urlDownload to set
	 */
	public void setUrlDownload(String urlDownload) {
		this.urlDownload = urlDownload;
	}

	/**
	 * @return the version
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * @param version
	 *            the version to set
	 */
	public void setVersion(String version) {
		this.version = version;
	}

}
