package org.infinispan.cdi.test.interceptor.service;

import javax.cache.annotation.CacheKey;
import javax.cache.annotation.CacheRemoveEntry;

import static org.infinispan.cdi.util.Contracts.assertNotNull;

/**
 * @author Kevin Pollet <kevin.pollet@serli.com> (C) 2011 SERLI
 */
public class CacheRemoveEntryService {

   @CacheRemoveEntry
   public void removeEntry(String login) {
      assertNotNull(login, "login parameter must not be null");
   }

   @CacheRemoveEntry(cacheName = "custom")
   public void removeEntryWithCacheName(String login) {
      assertNotNull(login, "login parameter must not be null");
   }

   @CacheRemoveEntry(cacheName = "custom")
   public void removeEntryWithCacheKeyParam(@CacheKey String login, String unused) {
      assertNotNull(login, "login parameter must not be null");
   }

   @CacheRemoveEntry(cacheName = "custom", afterInvocation = false)
   public void removeEntryBeforeInvocationWithException(String login) {
      assertNotNull(login, "login parameter must not be null");
   }

   @CacheRemoveEntry(cacheName = "custom", cacheKeyGenerator = CustomCacheKeyGenerator.class)
   public void removeEntryWithCacheKeyGenerator(String login) {
      assertNotNull(login, "login parameter must not be null");
   }
}
