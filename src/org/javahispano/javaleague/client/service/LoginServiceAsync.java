package org.javahispano.javaleague.client.service;

import org.javahispano.javaleague.shared.UserDTO;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * 
 * @author adou
 *
 */
public interface LoginServiceAsync {

  void getLoggedInUserDTO(AsyncCallback<UserDTO> callback);

  void logout(AsyncCallback<Void> callback);

}
