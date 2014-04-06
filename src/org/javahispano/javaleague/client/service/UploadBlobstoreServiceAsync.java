/**
 * 
 */
package org.javahispano.javaleague.client.service;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * @author adou
 *
 */
public interface UploadBlobstoreServiceAsync {

	/**
	 * 
	 * @see org.javahispano.javaleague.client.service.UploadBlobstoreService#getUploadGCSURL()
	 */
	void getUploadGCSURL(AsyncCallback<String> callback);

	/**
	 * 
	 * @see org.javahispano.javaleague.client.service.UploadBlobstoreService#getUploadURL()
	 */
	void getUploadURL(AsyncCallback<String> callback);

}
