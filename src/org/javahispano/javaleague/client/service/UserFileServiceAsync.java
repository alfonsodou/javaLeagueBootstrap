package org.javahispano.javaleague.client.service;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface UserFileServiceAsync {

	void getBlobstoreUploadUrl(AsyncCallback<String> callback);

}
