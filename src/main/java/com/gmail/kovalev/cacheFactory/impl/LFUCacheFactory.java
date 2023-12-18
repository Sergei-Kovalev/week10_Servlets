package com.gmail.kovalev.cacheFactory.impl;

import com.gmail.kovalev.cache.Cache;
import com.gmail.kovalev.cache.impl.LFUCache;
import com.gmail.kovalev.cacheFactory.CacheFactory;
import com.gmail.kovalev.config.Config;

public class LFUCacheFactory implements CacheFactory {
    @Override
    public <K, V> Cache<K, V> createCache() {
        int cacheCapacity = Integer.parseInt(Config.getInstance().config.get("application").get("collectionSize"));
        return new LFUCache<>(cacheCapacity);
    }
}
