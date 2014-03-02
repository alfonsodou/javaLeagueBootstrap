/**
 * 
 */
package org.javahispano.javaleague.server.domain;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.channels.Channels;
import java.util.Date;
import java.util.logging.Logger;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.javahispano.javaleague.shared.FrameWorkDTO;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.files.AppEngineFile;
import com.google.appengine.api.files.FileService;
import com.google.appengine.api.files.FileServiceFactory;
import com.google.appengine.api.files.FileWriteChannel;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

/**
 * @author adou
 * 
 */
@Entity
public class FrameWork {

	private static final int CACHE_EXPIR = 600; // in seconds
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

	private BlobKey frameWork;

	private Boolean active;

	private Boolean defaultFrameWork;

	private String urlDownload;

	public FrameWork() {
		this.creation = new Date();
		this.updated = new Date();
		this.active = true;
		this.defaultFrameWork = true;
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

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
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
	public BlobKey getFrameWork() {
		return frameWork;
	}

	/**
	 * @param frameWork
	 *            the frameWork to set
	 */
	public void setFrameWork(BlobKey frameWork) {
		this.frameWork = frameWork;
	}

	public void addSampleFrameWork() {
		try {

			this.setFrameWork(SaveFile(
					"http://javaleague.appspot.com/framework/framework.jar",
					"framework.jar"));

			/*
			 * this.setFramework(SaveFile(
			 * "http://localhost:8888/framework/framework.jar",
			 * "framework.jar"));
			 */

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @return the defaultFrameWork
	 */
	public Boolean getDefaultFrameWork() {
		return defaultFrameWork;
	}

	/**
	 * @param defaultFrameWork
	 *            the defaultFrameWork to set
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

	public FrameWorkDTO toDTO() {
		FrameWorkDTO frameWorkDTO = new FrameWorkDTO(this.id, this.name,
				this.description, this.summary, this.version, this.state,
				this.creation.toString(), this.updated.toString(), this.active,
				this.defaultFrameWork, this.urlDownload);

		return frameWorkDTO;
	}

	private static BlobKey SaveFile(String link, String fileName)
			throws Exception {
		BlobKey result = null;
		URL url = new URL(link);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		try {
			connection.setInstanceFollowRedirects(false);
			connection.setRequestMethod("GET");
			connection.setConnectTimeout(10000);

			FileService fileService = FileServiceFactory.getFileService();

			Integer code = connection.getResponseCode();
			if (code == HttpURLConnection.HTTP_OK) {
				String contentType = connection.getHeaderField("Content-type");
				InputStream is = connection.getInputStream();

				AppEngineFile file = fileService.createNewBlobFile(contentType,
						fileName);
				boolean lock = true;
				FileWriteChannel writeChannel = fileService.openWriteChannel(
						file, lock);
				OutputStream os = Channels.newOutputStream(writeChannel);

				byte[] buf = new byte[4096];
				ByteArrayOutputStream bas = new ByteArrayOutputStream();
				int n;
				while ((n = is.read(buf)) >= 0)
					bas.write(buf, 0, n);
				os.write(bas.toByteArray());
				os.close();
				writeChannel.closeFinally();

				return fileService.getBlobKey(file);
			}
		} finally {
			connection.disconnect();
		}
		return result;
	}

}
