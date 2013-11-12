package org.javahispano.javaleague.server.domain;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.channels.Channels;
import java.util.Date;

import javax.jdo.listener.StoreCallback;
import javax.persistence.Id;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.javahispano.javaleague.server.AppLib;
import org.javahispano.javaleague.server.utils.cache.CacheSupport;
import org.javahispano.javaleague.server.utils.cache.Cacheable;
import org.javahispano.javaleague.shared.TacticDTO;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.files.AppEngineFile;
import com.google.appengine.api.files.FileService;
import com.google.appengine.api.files.FileServiceFactory;
import com.google.appengine.api.files.FileWriteChannel;

/**
 * 
 * @author adou
 * 
 */
//@PersistenceCapable(identityType = IdentityType.APPLICATION, detachable = "true")
public class TacticUser implements StoreCallback, Serializable, Cacheable {

	/**
	 * 
	 */
	private static final int CACHE_EXPIR = 600; // in seconds

	/**
	 * 
	 */
	@Id
	private Long id;

	/**
	 * Pointer back to userinfo object with which this tactic is associated.
	 */
	/*
	 * @Persistent(mappedBy = "tactic") private UserAccount userPrefs;
	 */

	/**
	 * Team's Name
	 */
//	@Persistent
	private String teamName;

	/**
	 * Type of tactic.
	 */
	/*
	 * @Persistent private TacticDetail userTacticDetail;
	 */

	/**
	 * Is tactic validated ?.
	 */
	//@Persistent
	private boolean valid;

	/**
	 * Date/Time first upload.
	 */
	//@Persistent
	private Date creation;

	/**
	 * Date/Time last updated.
	 */
	//@Persistent
	private Date updated;

	//@Persistent
	private int friendlyMatch;

	//@Persistent
	private int goalsFor;

	//@Persistent
	private int goalsAgainst;

	//@Persistent
	private int matchWins;

	//@Persistent
	private int matchLost;

	//@Persistent
	private int matchTied;
	
	/**
	 * Blob key for zip
	 */
	//@Persistent
	private BlobKey zipClasses;

	public TacticUser() {
		this.creation = new Date();
		this.updated = new Date();
		this.valid = true;
		// this.tacticsClass = null;
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
		// this.tacticsClass = null;
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

	/*
	 * public UserAccount getUserAccount() { return userPrefs; }
	 * 
	 * public void setUserAccount(UserAccount userAccount) { this.userPrefs =
	 * userAccount; }
	 */

	/*
	 * public TacticDetail getUserTacticDetail() { return userTacticDetail; }
	 * 
	 * public void setUserTacticDetail(TacticDetail userTacticDetail) {
	 * this.userTacticDetail = userTacticDetail; }
	 */

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
	 * @param zipClasses the zipClasses to set
	 */
	public void setZipClasses(BlobKey zipClasses) {
		this.zipClasses = zipClasses;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	public TacticDTO toDTO() {
		TacticDTO userTacticDTO = new TacticDTO();
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
			
			
			/*this.setZipClasses(SaveFile(
					"http://localhost:8888/tactic/samples/TacticaEjemplo.jar", 
					"TacticaEjemplo.jar"));*/
					
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

	@Override
	public void jdoPreStore() {

	}

	@Override
	public void addToCache() {
		// force load of lazily-loaded fields
		// getUserAccount();
		// getUserTacticDetail();
		

		CacheSupport.cachePutExp(this.getClass().getName(), id, this,
				CACHE_EXPIR);
	}

	@Override
	public void removeFromCache() {
		CacheSupport.cacheDelete(this.getClass().getName(), id);
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