/**
 * 
 */
package org.javahispano.javaleague.client.service;

import java.util.List;

import org.javahispano.javaleague.shared.domain.FrameWork;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * @author adou
 *
 */
public interface FrameWorkServiceAsync {

	void getFrameWorks(AsyncCallback<List<FrameWork>> callback);

	void getDefaultFrameWork(AsyncCallback<FrameWork> callback);

}
