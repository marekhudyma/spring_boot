package com.google.common.cache;

import mh.springboot.model.SampleEntity;
import org.springframework.cache.Cache;

import java.util.Map;

/**
 * gets all items from internal cache implementation
 * class {@link com.google.common.cache.LocalCache.LocalManualCache}
 * is package protected, so it is dirty hack
 */
public class GuavaCacheHelper {

    public static Map<Long, SampleEntity> getAllItemsFromCache(Cache cache) {
        return ((com.google.common.cache.LocalCache.LocalManualCache)cache.getNativeCache()).asMap();
    }
}
