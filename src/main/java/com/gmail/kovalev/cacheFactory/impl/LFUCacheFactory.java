package com.gmail.kovalev.cacheFactory.impl;

import com.gmail.kovalev.cache.Cache;
import com.gmail.kovalev.cache.impl.LFUCache;
import com.gmail.kovalev.cacheFactory.CacheFactory;

public class LFUCacheFactory implements CacheFactory {
    @Override
    public <K, V> Cache<K, V> createCache(int cacheSize) {
        return new LFUCache<>(cacheSize);
    }
}
