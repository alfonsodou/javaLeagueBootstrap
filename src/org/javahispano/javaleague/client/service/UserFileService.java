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
@RemoteServiceRelativePath("userFileService")
public interface UserFileService extends RemoteService {
	public String getBlobstoreUploadUrl();
}