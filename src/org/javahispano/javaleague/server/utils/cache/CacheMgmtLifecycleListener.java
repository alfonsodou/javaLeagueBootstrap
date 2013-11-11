package org.javahispano.javaleague.server.utils.cache;

import javax.jdo.listener.DeleteLifecycleListener;
import javax.jdo.listener.InstanceLifecycleEvent;
import javax.jdo.listener.LoadLifecycleListener;
import javax.jdo.listener.StoreLifecycleListener;

/**
 * 
 * @author adou
 *
 */
public class CacheMgmtLifecycleListener implements 
  DeleteLifecycleListener, LoadLifecycleListener, 
  StoreLifecycleListener {

  public void preDelete(InstanceLifecycleEvent event)    {
    Object o = event.getSource();
    if (o instanceof Cacheable) {
      Cacheable f = (Cacheable) o;
      f.removeFromCache();
    }
  }

  public void postDelete(InstanceLifecycleEvent event)    {
  }

  public void postLoad(InstanceLifecycleEvent event)   {
    addToCache(event);
  }

  public void preStore(InstanceLifecycleEvent event)    {
  }

  public void postStore(InstanceLifecycleEvent event)    {
    addToCache(event);
  }
  
  private void addToCache(InstanceLifecycleEvent event) {
    Object o = event.getSource();
    if (o instanceof Cacheable) {
      Cacheable f = (Cacheable) o;
      f.addToCache();
    }
  }

}