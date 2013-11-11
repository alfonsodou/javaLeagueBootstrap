package org.javahispano.javaleague.server.utils.cache;

import javax.jdo.listener.DeleteLifecycleListener;
import javax.jdo.listener.InstanceLifecycleEvent;
import javax.jdo.listener.LoadLifecycleListener;

/**
 * 
 * @author adou
 *
 */
public class CacheMgmtTxnLifecycleListener implements 
  DeleteLifecycleListener, LoadLifecycleListener {

  public void preDelete(InstanceLifecycleEvent event)    {
    removeFromCache(event);
  }

  public void postDelete(InstanceLifecycleEvent event)  {
    // must be defined
  }

  public void postLoad(InstanceLifecycleEvent event)  {
    removeFromCache(event);
  }

  private void removeFromCache(InstanceLifecycleEvent event) {
    Object o = event.getSource();
    if (o instanceof Cacheable) {
      Cacheable f = (Cacheable) o;
      f.removeFromCache();
    }
  }

}