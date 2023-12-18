package com.gmail.kovalev.exception;

public class CacheNotImplementedException extends RuntimeException {
    public CacheNotImplementedException(String cacheType) {
        super("Sorry cache with type " + cacheType + " not implemented yet");
    }
}
