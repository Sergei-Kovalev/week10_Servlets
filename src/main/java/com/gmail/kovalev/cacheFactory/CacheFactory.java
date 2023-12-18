package com.gmail.kovalev.cacheFactory;

import com.gmail.kovalev.cache.Cache;
import com.gmail.kovalev.cacheFactory.impl.LFUCacheFactory;
import com.gmail.kovalev.cacheFactory.impl.LRUCacheFactory;
import com.gmail.kovalev.config.Config;
import com.gmail.kovalev.exception.CacheNotImplementedException;

public interface CacheFactory {
    <K, V> Cache<K, V> createCache();

    static <K, V> Cache<K, V> createCacheByName() {
        String cacheType = Config.getInstance().config.get("application").get("cache");
        if (cacheType.equalsIgnoreCase("LFU")) {
            return new LFUCacheFactory().createCache();
        } else if (cacheType.equalsIgnoreCase("LRU")) {
            return new LRUCacheFactory().createCache();
        } else {
            throw new CacheNotImplementedException(cacheType);
        }
    }
}
