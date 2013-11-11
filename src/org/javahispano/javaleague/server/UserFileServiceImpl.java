/**
 * 
 */
package org.javahispano.javaleague.server;

import org.javahispano.javaleague.client.service.UserFileService;

import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * @author adou
 * 
 */
public class UserFileServiceImpl extends RemoteServiceServlet implements
		UserFileService {

	@Override
	public String getBlobstoreUploadUrl() {
		BlobstoreService blobstoreService = BlobstoreServiceFactory
				.getBlobstoreService();
		return blobstoreService.createUploadUrl("/upload");
	}

}
