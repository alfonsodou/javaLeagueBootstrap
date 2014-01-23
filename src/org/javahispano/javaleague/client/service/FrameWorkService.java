/**
 * 
 */
package org.javahispano.javaleague.client.service;

import java.util.List;

import org.javahispano.javaleague.shared.FrameWorkDTO;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * @author adou
 *
 */

@RemoteServiceRelativePath("frameWorkService")
public interface FrameWorkService extends RemoteService {
	List<FrameWorkDTO> getFrameWorks();
}