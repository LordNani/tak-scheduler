package com.simpletak.takscheduler.api.config.cache;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class SimpleCacheManager implements CacheManager, InitializingBean {
    private volatile Set<String> cacheNames = Collections.emptySet();
    private final ConcurrentMap<String, Cache> cacheMap = new ConcurrentHashMap<>(16);
    private final Collection<? extends Cache> caches = Arrays.asList(
            new ConcurrentMapCache("eventGroups"));

    @Override
    public void afterPropertiesSet() throws Exception {
        Collection<? extends Cache> caches = this.caches;

        synchronized (cacheMap) {
            cacheNames.clear();
            cacheMap.clear();
            Set<String> hashSet = new LinkedHashSet<>(caches.size());
            caches.forEach(cache -> {
                String name = cache.getName();
                cacheMap.put(name, cache);
                hashSet.add(name);
            });
            cacheNames = Collections.unmodifiableSet(hashSet);
        }
    }

    @Override
    public Cache getCache(String s) {
        return cacheMap.get(s);
    }

    @Override
    public Collection<String> getCacheNames() {
        return cacheNames;
    }
}
