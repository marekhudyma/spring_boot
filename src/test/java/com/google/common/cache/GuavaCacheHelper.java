package com.google.common.cache;

import com.google.common.collect.ImmutableMap;
import mh.springboot.model.SampleEntity;
import org.springframework.cache.Cache;

import java.util.Collection;
import java.util.Map;

/**
 * gets all items from internal cache implementation
 * class {@link com.google.common.cache.LocalCache.LocalManualCache}
 * is package protected, so it is dirty hack
 */
public class GuavaCacheHelper {

    public static Map<Long, SampleEntity> getAllItemsFromCache(Cache cache) {
        ImmutableMap.Builder<Long, SampleEntity> returnMap = ImmutableMap.builder();
        Map<Long, SampleEntity> items =
                ((com.google.common.cache.LocalCache.LocalManualCache)cache.getNativeCache()).asMap();
        for(Map.Entry<?, ?> entry : items.entrySet()) {
            if(entry.getValue() instanceof Collection) {
                Collection<SampleEntity> collection = (Collection)entry.getValue();
                for(SampleEntity sampleEntity : collection) {
                    returnMap.put(sampleEntity.getId(), sampleEntity);
                }
            } else {
                returnMap.put((Long) entry.getKey(), (SampleEntity) entry.getValue());
            }
        }
        return returnMap.build();
    }
}
