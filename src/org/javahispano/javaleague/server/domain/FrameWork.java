/**
 * 
 */
package org.javahispano.javaleague.server.domain;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.channels.Channels;
import java.util.Date;
import java.util.logging.Logger;

import javax.jdo.listener.StoreCallback;
import javax.persistence.Id;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.javahispano.javaleague.server.utils.cache.CacheSupport;
import org.javahispano.javaleague.server.utils.cache.Cacheable;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.files.AppEngineFile;
import com.google.appengine.api.files.FileService;
import com.google.appengine.api.files.FileServiceFactory;
import com.google.appengine.api.files.FileWriteChannel;

/**
 * @author adou
 * 
 */
public class FrameWork implements StoreCallback, Serializable, Cacheable {

	private static final int CACHE_EXPIR = 600; // in seconds
	private static final Logger log = Logger.getLogger(FrameWork.class
			.getName());

	@Id
	private Long id;

	private String name;

	private String description;

	private String summary;

	private Date creation;

	private Date updated;

	private BlobKey framework;
	
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

	public BlobKey getFramework() {
		return framework;
	}

	public void setFramework(BlobKey framework) {
		this.framework = framework;
	}

	
	
	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public Boolean getDefaultFramework() {
		return defaultFrameWork;
	}

	public void setDefaultFramework(Boolean defaultFramework) {
		this.defaultFrameWork = defaultFramework;
	}

	public void addSampleFrameWork() {
		try {

			
			 this.setFramework(SaveFile(
			  "http://javaleague.appspot.com/framework/framework.jar",
			  "framework.jar"));

			 /*this.setFramework(SaveFile(
					  "http://localhost:8888/framework/framework.jar",
					  "framework.jar"));*/


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

	@Override
	public void addToCache() {
		CacheSupport.cachePutExp(this.getClass().getName(), id, this,
				CACHE_EXPIR);

	}

	@Override
	public void removeFromCache() {
		CacheSupport.cacheDelete(this.getClass().getName(), id);

	}

	@Override
	public void jdoPreStore() {

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
