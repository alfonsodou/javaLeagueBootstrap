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
@RemoteServiceRelativePath("blobstoreService")
public interface BlobstoreService extends RemoteService {
	String getUploadURL();
	String getUploadGCSURL();
}
