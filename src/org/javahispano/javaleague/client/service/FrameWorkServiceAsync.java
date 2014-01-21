/**
 * 
 */
package org.javahispano.javaleague.client.service;

import java.util.List;

import org.javahispano.javaleague.shared.FrameWorkDTO;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * @author adou
 *
 */
public interface FrameWorkServiceAsync {

	void getFrameWorks(AsyncCallback<List<FrameWorkDTO>> callback);

}
