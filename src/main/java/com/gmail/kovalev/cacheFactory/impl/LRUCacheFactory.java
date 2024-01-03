package com.gmail.kovalev.cacheFactory.impl;

import com.gmail.kovalev.cache.Cache;
import com.gmail.kovalev.cache.impl.LRUCache;
import com.gmail.kovalev.cacheFactory.CacheFactory;

public class LRUCacheFactory implements CacheFactory {
    @Override
    public <K, V> Cache<K, V> createCache(int cacheSize) {
        return new LRUCache<>(cacheSize);
    }
}
