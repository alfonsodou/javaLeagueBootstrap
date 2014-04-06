/**
 * 
 */
package org.javahispano.javaleague.client.service;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * @author adou
 *
 */
@RemoteServiceRelativePath("uploadBlobstoreService")
public interface UploadBlobstoreService extends RemoteService {
	String getUploadURL();
	String getUploadGCSURL();
}
