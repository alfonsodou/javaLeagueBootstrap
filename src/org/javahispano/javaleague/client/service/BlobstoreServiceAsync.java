/**
 * 
 */
package org.javahispano.javaleague.client.service;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * @author adou
 *
 */
public interface BlobstoreServiceAsync {

	/**
	 * 
	 * @see org.javahispano.javaleague.client.service.BlobstoreService#getUploadGCSURL()
	 */
	void getUploadGCSURL(AsyncCallback<String> callback);

	/**
	 * 
	 * @see org.javahispano.javaleague.client.service.BlobstoreService#getUploadURL()
	 */
	void getUploadURL(AsyncCallback<String> callback);

}
