/**
 * 
 */
package org.javahispano.javaleague.server.servlets;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.blobstore.FileInfo;
import com.google.appengine.tools.cloudstorage.GcsService;
import com.google.appengine.tools.cloudstorage.GcsServiceFactory;
import com.google.appengine.tools.cloudstorage.RetryParams;

/**
 * @author adou
 * 
 */
public class UploadBlobServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final GcsService gcsService = GcsServiceFactory
			.createGcsService(RetryParams.getDefaultInstance());
	private static final Logger log = Logger.getLogger(UploadBlobServlet.class
			.getName());
	
	
    public void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
    	BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
    	Map<String, List<FileInfo>> fileInfos = blobstoreService.getFileInfos(req);
    	List<FileInfo> uploadedFile = fileInfos.get("fileUpload");
    	log.warning("Dentro de UploadBlob!!");
    	if(uploadedFile != null) {
    	    FileInfo file = uploadedFile.get(0);
    	    String fileName = file.getFilename();
    	    log.warning("FileName: " + fileName);
    	}
    }
} 
