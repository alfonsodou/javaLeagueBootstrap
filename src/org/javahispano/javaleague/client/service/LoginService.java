package org.javahispano.javaleague.client.service;

import org.javahispano.javaleague.shared.domain.User;
import org.javahispano.javaleague.shared.exception.NotLoggedInException;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * 
 * @author adou
 *
 */
@RemoteServiceRelativePath("loginService")
public interface LoginService extends RemoteService {

  User getLoggedInUser();
  void logout() throws NotLoggedInException;
}
