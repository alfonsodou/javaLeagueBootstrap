/**
 * 
 */
package org.javahispano.javaleague.server;

import java.util.logging.Logger;

import org.javahispano.javaleague.client.service.UploadBlobstoreService;
import org.javahispano.javaleague.shared.AppLib;

import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.blobstore.UploadOptions;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * @author adou
 * 
 */
public class UploadBlobstoreServiceImpl extends RemoteServiceServlet implements
		UploadBlobstoreService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger log = Logger
			.getLogger(UploadBlobstoreServiceImpl.class.getName());

	@Override
	public String getUploadURL() {
		BlobstoreService blobstoreService = BlobstoreServiceFactory
				.getBlobstoreService();
		String url = blobstoreService.createUploadUrl("/upload");
		log.warning("URL: " + url);
		return url;
	}

	@Override
	public String getUploadGCSURL() {
		BlobstoreService blobstoreService = BlobstoreServiceFactory
				.getBlobstoreService();
		String url = blobstoreService.createUploadUrl("/upload",
				UploadOptions.Builder
						.withGoogleStorageBucketName(AppLib.BUCKET_GCS));
		log.warning("URL: " + url);
		return url;
	}

}
