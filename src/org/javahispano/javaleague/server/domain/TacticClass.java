package org.javahispano.javaleague.server.domain;

import java.io.Serializable;

import javax.jdo.annotations.Extension;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import javax.jdo.listener.StoreCallback;

import org.javahispano.javaleague.server.utils.cache.CacheSupport;
import org.javahispano.javaleague.server.utils.cache.Cacheable;
import org.javahispano.javaleague.shared.TacticClassDTO;

import com.google.appengine.api.blobstore.BlobKey;

/**
 * 
 * @author adou
 * 
 */
@PersistenceCapable(identityType = IdentityType.APPLICATION, detachable = "true")
public class TacticClass implements StoreCallback, Serializable, Cacheable {

	/**
	 * 
	 */
	private static final int CACHE_EXPIR = 600; // in seconds

	/**
	 * 
	 */
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	@Extension(vendorName = "datanucleus", key = "gae.encoded-pk", value = "true")
	private String id;

	/**
	 * Pointer back to usertactic object with which this class is associated.
	 */
	@Persistent
	private TacticUser userTactic;

	/**
	 * Package name.
	 */
	@Persistent
	private String packageName;

	/**
	 * File name.
	 */
	@Persistent
	private String className;

	/**
	 * Blob key for datastore.
	 */
	@Persistent
	private BlobKey blob;

	/**
	 * This class implements interface Tactic ?
	 */
	@Persistent
	private boolean isTacticClass;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public TacticUser getUserTactic() {
		return userTactic;
	}

	public void setUserTactic(TacticUser userTactic) {
		this.userTactic = userTactic;
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public BlobKey getBlob() {
		return blob;
	}

	public void setBlob(BlobKey blob) {
		this.blob = blob;
	}

	public boolean isTacticClass() {
		return isTacticClass;
	}

	public void setTacticClass(boolean isTacticClass) {
		this.isTacticClass = isTacticClass;
	}

	public TacticClassDTO toDTO() {
		TacticClassDTO tacticClassDTO = new TacticClassDTO();
		tacticClassDTO.setId(this.getId());
		tacticClassDTO.setPackageName(this.getPackageName());
		tacticClassDTO.setClassName(this.getClassName());
		tacticClassDTO.setTacticClass(this.isTacticClass());

		return tacticClassDTO;
	}

	@Override
	public void addToCache() {
		getUserTactic();

		CacheSupport.cachePutExp(this.getClass().getName(), id, this,
				CACHE_EXPIR);
	}

	@Override
	public void removeFromCache() {
		CacheSupport.cacheDelete(this.getClass().getName(), id);
	}

	@Override
	public void jdoPreStore() {
		// TODO Auto-generated method stub

	}

}
