package org.javahispano.javaleague.server;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;

import org.javahispano.javaleague.server.utils.cache.CacheMgmtLifecycleListener;
import org.javahispano.javaleague.server.utils.cache.CacheMgmtTxnLifecycleListener;

/**
 * 
 * @author adou
 * 
 */
public final class PMF {
	private static final java.lang.Class[] classes = new java.lang.Class[] {};
	private static final PersistenceManagerFactory pmfInstance = JDOHelper
			.getPersistenceManagerFactory("transactions-optional");

	private PMF() {
	}

	public static PersistenceManagerFactory get() {
		return pmfInstance;
	}

	public static PersistenceManager getNonTxnPm() {
		PersistenceManager pm = pmfInstance.getPersistenceManager();
		pm.addInstanceLifecycleListener(new CacheMgmtLifecycleListener(),
				classes);
		return pm;
	}

	public static PersistenceManager getTxnPm() {
		PersistenceManager pm = pmfInstance.getPersistenceManager();
		pm.addInstanceLifecycleListener(new CacheMgmtTxnLifecycleListener(),
				classes);
		return pm;
	}
}
