package org.javahispano.javaleague.server.domain;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.channels.Channels;
import java.util.Date;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.javahispano.javaleague.server.AppLib;
import org.javahispano.javaleague.shared.TacticDTO;

import com.google.appengine.api.blobstore.BlobInfo;
import com.google.appengine.api.blobstore.BlobInfoFactory;
import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.files.AppEngineFile;
import com.google.appengine.api.files.FileService;
import com.google.appengine.api.files.FileServiceFactory;
import com.google.appengine.api.files.FileWriteChannel;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

/**
 * 
 * @author adou
 * 
 */

@Entity
public class TacticUser {


	/**
	 * 
	 */
	@Id
	private Long id;

	/**
	 * Team's Name
	 */
	private String teamName;

	/**
	 * Is tactic validated ?.
	 */
	private boolean valid;

	/**
	 * Date/Time first upload.
	 */
	private Date creation;

	/**
	 * Date/Time last updated.
	 */
	private Date updated;

	private int friendlyMatch;

	private int goalsFor;

	private int goalsAgainst;

	private int matchWins;

	private int matchLost;

	private int matchTied;

	/**
	 * Blob key for zip
	 */
	private BlobKey zipClasses;

	public TacticUser() {
		this.creation = new Date();
		this.updated = new Date();
		this.valid = true;
		this.teamName = "javaLeague";
		this.friendlyMatch = AppLib.FRIENDLY_MATCH_NO;
		this.goalsAgainst = 0;
		this.goalsFor = 0;
		this.matchLost = 0;
		this.matchTied = 0;
		this.matchWins = 0;
		this.zipClasses = null;
	}

	public TacticUser(TacticDTO userTacticDTO) {
		this.teamName = userTacticDTO.getTeamName();
		this.creation = new Date();
		this.updated = new Date();
		this.valid = true;
		this.friendlyMatch = AppLib.FRIENDLY_MATCH_NO;
		this.goalsAgainst = 0;
		this.goalsFor = 0;
		this.matchLost = 0;
		this.matchTied = 0;
		this.matchWins = 0;

	}

	/**
	 * Get id for this object.
	 * 
	 * @return id
	 */
	public Long getId() {
		return id;
	}

	public boolean isValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}

	public Date getCreation() {
		return creation;
	}

	public void setCreation(Date creation) {
		this.creation = creation;
	}

	public Date getUpdated() {
		return updated;
	}

	public void setUpdated(Date updated) {
		this.updated = updated;
	}

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	/**
	 * @return the friendlyMatch
	 */
	public int getFriendlyMatch() {
		return friendlyMatch;
	}

	/**
	 * @param friendlyMatch
	 *            the friendlyMatch to set
	 */
	public void setFriendlyMatch(int friendlyMatch) {
		this.friendlyMatch = friendlyMatch;
	}

	public void addGoalsFor(int goals) {
		this.goalsFor += goals;
	}

	public void addGoalsAgainst(int goals) {
		this.goalsAgainst += goals;
	}

	public void addMatchWins() {
		this.matchWins++;
	}

	public void addMatchLost() {
		this.matchLost++;
	}

	public void addMatchTied() {
		this.matchTied++;
	}

	/**
	 * @return the goalsFor
	 */
	public int getGoalsFor() {
		return goalsFor;
	}

	/**
	 * @param goalsFor
	 *            the goalsFor to set
	 */
	public void setGoalsFor(int goalsFor) {
		this.goalsFor = goalsFor;
	}

	/**
	 * @return the goalsAgainst
	 */
	public int getGoalsAgainst() {
		return goalsAgainst;
	}

	/**
	 * @param goalsAgainst
	 *            the goalsAgainst to set
	 */
	public void setGoalsAgainst(int goalsAgainst) {
		this.goalsAgainst = goalsAgainst;
	}

	/**
	 * @return the matchWins
	 */
	public int getMatchWins() {
		return matchWins;
	}

	/**
	 * @param matchWins
	 *            the matchWins to set
	 */
	public void setMatchWins(int matchWins) {
		this.matchWins = matchWins;
	}

	/**
	 * @return the matchLost
	 */
	public int getMatchLost() {
		return matchLost;
	}

	/**
	 * @param matchLost
	 *            the matchLost to set
	 */
	public void setMatchLost(int matchLost) {
		this.matchLost = matchLost;
	}

	/**
	 * @return the matchTied
	 */
	public int getMatchTied() {
		return matchTied;
	}

	/**
	 * @param matchTied
	 *            the matchTied to set
	 */
	public void setMatchTied(int matchTied) {
		this.matchTied = matchTied;
	}

	/**
	 * @return the zipClasses
	 */
	public BlobKey getZipClasses() {
		return zipClasses;
	}

	/**
	 * @param zipClasses
	 *            the zipClasses to set
	 */
	public void setZipClasses(BlobKey zipClasses) {
		this.zipClasses = zipClasses;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	public TacticDTO toDTO() {
		TacticDTO userTacticDTO = new TacticDTO();

		if (this.getZipClasses() != null) {
			BlobInfoFactory infoFactory = new BlobInfoFactory();
			BlobInfo blobInfo = infoFactory.loadBlobInfo(this.getZipClasses());
			userTacticDTO.setFileName(blobInfo.getFilename());
			userTacticDTO.setBytes(blobInfo.getSize());
		}
		userTacticDTO.setId(this.getId().toString());
		userTacticDTO.setCreation(this.getCreation().toString());
		userTacticDTO.setUpdated(this.getUpdated().toString());
		userTacticDTO.setTeamName(this.getTeamName());
		userTacticDTO.setFriendlyMatch(this.getFriendlyMatch());
		userTacticDTO.setGoalsAgainst(this.getGoalsAgainst());
		userTacticDTO.setGoalsFor(this.getGoalsFor());
		userTacticDTO.setMatchLost(this.getMatchLost());
		userTacticDTO.setMatchTied(this.getMatchTied());
		userTacticDTO.setMatchWins(this.getMatchWins());

		return userTacticDTO;
	}

	public void addSampleTacticClass() {
		try {

			/*
			 * this.setZipClasses(SaveFile(
			 * "http://localhost:8888/tactic/samples/TacticaEjemplo.jar",
			 * "TacticaEjemplo.jar"));
			 */

			this.setZipClasses(SaveFile(
					"http://javaleague.appspot.com/tactic/samples/TacticaEjemplo.jar",
					"TacticaEjemplo.jar"));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void updatedFromDTO(TacticDTO tacticDTO) {
		this.teamName = tacticDTO.getTeamName();
	}

	private static BlobKey SaveFile(String link, String fileName)
			throws Exception {
		BlobKey result = null;
		URL url = new URL(link);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		try {
			connection.setInstanceFollowRedirects(false);
			connection.setRequestMethod("GET");
			connection.setConnectTimeout(10000);

			FileService fileService = FileServiceFactory.getFileService();

			Integer code = connection.getResponseCode();
			if (code == HttpURLConnection.HTTP_OK) {
				String contentType = connection.getHeaderField("Content-type");
				InputStream is = connection.getInputStream();

				AppEngineFile file = fileService.createNewBlobFile(contentType,
						fileName);
				boolean lock = true;
				FileWriteChannel writeChannel = fileService.openWriteChannel(
						file, lock);
				OutputStream os = Channels.newOutputStream(writeChannel);

				byte[] buf = new byte[4096];
				ByteArrayOutputStream bas = new ByteArrayOutputStream();
				int n;
				while ((n = is.read(buf)) >= 0)
					bas.write(buf, 0, n);
				os.write(bas.toByteArray());
				os.close();
				writeChannel.closeFinally();

				return fileService.getBlobKey(file);
			}
		} finally {
			connection.disconnect();
		}
		return result;
	}

}
