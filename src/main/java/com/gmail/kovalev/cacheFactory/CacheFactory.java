package com.gmail.kovalev.cacheFactory;

import com.gmail.kovalev.cache.Cache;
import com.gmail.kovalev.cacheFactory.impl.LFUCacheFactory;
import com.gmail.kovalev.cacheFactory.impl.LRUCacheFactory;
import com.gmail.kovalev.exception.CacheNotImplementedException;

public interface CacheFactory {
    <K, V> Cache<K, V> createCache(int cacheSize);

    static <K, V> Cache<K, V> createCacheByName(String cacheType, int cacheSize) {
        if (cacheType.equalsIgnoreCase("LFU")) {
            return new LFUCacheFactory().createCache(cacheSize);
        } else if (cacheType.equalsIgnoreCase("LRU")) {
            return new LRUCacheFactory().createCache(cacheSize);
        } else {
            throw new CacheNotImplementedException(cacheType);
        }
    }
}
