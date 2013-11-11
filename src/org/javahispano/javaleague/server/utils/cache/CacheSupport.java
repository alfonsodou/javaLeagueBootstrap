/** 
 *
 */
package org.javahispano.javaleague.server.utils.cache;

import java.io.Serializable;

import com.google.appengine.api.memcache.Expiration;
import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceException;
import com.google.appengine.api.memcache.MemcacheServiceFactory;


public class CacheSupport {
  
  private static MemcacheService cacheInit(String nameSpace) {
    MemcacheService memcache = MemcacheServiceFactory.getMemcacheService(nameSpace);

    return memcache;
  }

  public static Object cacheGet(String nameSpace, Object id) {
    Object r = null;
    MemcacheService memcache = cacheInit(nameSpace);
    try {
      r = memcache.get(id);
    } 
    catch (MemcacheServiceException e) {
      // nothing can be done.
    }
    return r;
  }

  public static void cacheDelete(String nameSpace, Object id) {
    MemcacheService memcache = cacheInit(nameSpace);
    try {
      memcache.delete(id);
    }
    catch (MemcacheServiceException e) {
      //...
    }
  }

  public static void cachePutExp(String nameSpace, Object id, Serializable o, int exp) {
    MemcacheService memcache = cacheInit(nameSpace);
    try {
      if (exp > 0) {
//        logger.fine("setting expiration in " + exp + " seconds for " + id);
        memcache.put(id, o, Expiration.byDeltaSeconds(exp));
      }
      else {
        memcache.put(id, o);
      }
    } 
    catch (MemcacheServiceException e) {
      // nothing can be done.
    }
  }

  public static void cachePut(String nameSpace, Object id, Serializable o) {
    MemcacheService memcache = cacheInit(nameSpace);
    try {
      memcache.put(id, o);
    }
    catch (MemcacheServiceException e) {
      // nothing can be done
    }
  }
}
