package org.javahispano.javaleague.client.service;

import org.javahispano.javaleague.shared.domain.User;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * 
 * @author adou
 *
 */
public interface LoginServiceAsync {

  void getLoggedInUser(AsyncCallback<User> callback);

  void logout(AsyncCallback<Void> callback);

}
