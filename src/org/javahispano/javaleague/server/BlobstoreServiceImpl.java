/**
 * 
 */
package org.javahispano.javaleague.server;

import java.util.logging.Logger;

import org.javahispano.javaleague.client.service.BlobstoreService;
import org.javahispano.javaleague.server.servlets.UploadBlobServlet;
import org.javahispano.javaleague.server.utils.BlobstoreUtil;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * @author adou
 *
 */
public class BlobstoreServiceImpl extends RemoteServiceServlet implements BlobstoreService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger log = Logger.getLogger(BlobstoreServiceImpl.class
			.getName());

	@Override
	public String getUploadURL() {
		return BlobstoreUtil.getUrl();
	}

	@Override
	public String getUploadGCSURL() {
		String url = BlobstoreUtil.getUrlGCS();
		log.warning("URL: " + url);
		return url;
	}

}
