package mh.springboot.service.sampleentity;

import mh.springboot.model.SampleEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

/**
 * This is just decorator for generated bean
 * The only reason is that you cannot invalidate many key-value caches
 * using @CacheEvict in save(Iterable) and delete(Iterable)
 * https://stackoverflow.com/questions/32276418/spring-boot-cacheevict-saveiterables-entities
 */
public class SampleEntityCachingDecoratorService implements SampleEntityService {

    public static final String FIND_ONE = "findOne";
    public static final String FIND_ALL = "findAll";

    @Autowired
    private SampleEntityService sampleEntityService;

    @Autowired
    private CacheManager cacheManager;

    @Caching(evict = {@CacheEvict(value = FIND_ONE,  key = "#a0.id"), //TODO HUDYMA entity.id
                      @CacheEvict(value = FIND_ALL, allEntries = true)})
    public <S extends SampleEntity> S save(S entity) {
        return sampleEntityService.save(entity);
    }

    /**
     * it was not possible to invalidate many items from cache by @CacheEvict
     */
    public <S extends SampleEntity> Iterable<S> save(Iterable<S> entities) {
        for(SampleEntity sampleEntity : entities) {
            cacheManager.getCache(FIND_ONE).evict(sampleEntity.getId());
        }
        cacheManager.getCache(FIND_ALL).clear();

        return sampleEntityService.save(entities);
    }

    @Cacheable(key = "#a0", value = FIND_ONE)
    public SampleEntity findOne(Long id) {
        return sampleEntityService.findOne(id);
    }

    public boolean exists(Long id) {
        return sampleEntityService.exists(id);
    }

    @Cacheable(value = FIND_ALL)
    public Iterable<SampleEntity> findAll() {
        return sampleEntityService.findAll();
    }

    public Iterable<SampleEntity> findAll(Iterable<Long> ids) {
        return sampleEntityService.findAll(ids);
    }

    public long count() {
        return sampleEntityService.count();
    }

    @Caching(evict = {@CacheEvict(value = FIND_ONE, key = "#a0"),
                      @CacheEvict(value = FIND_ALL, allEntries = true)})
    public void delete(Long id) {
        sampleEntityService.delete(id);
    }

    @Caching(evict = {@CacheEvict(value = FIND_ONE, key = "#p0.id"),
                      @CacheEvict(value = FIND_ALL, allEntries = true)})
    public void delete(SampleEntity entity) {
        sampleEntityService.delete(entity);
    }

    /**
     * it was not possible to invalidate many items from cache by @CacheEvict
     */
    public void delete(Iterable<? extends SampleEntity> entities) {
        for(SampleEntity sampleEntity : entities) {
            cacheManager.getCache(FIND_ONE).evict(sampleEntity.getId());
        }
        cacheManager.getCache(FIND_ALL).clear();

        sampleEntityService.delete(entities);
    }

    @Caching(evict = {
            @CacheEvict(value = FIND_ONE, allEntries = true),
            @CacheEvict(value = FIND_ALL, allEntries = true)})
    public void deleteAll() {
        sampleEntityService.deleteAll();
    }

    // custom methods

    public List<SampleEntity> findByUuidIn(List<UUID> uuids) {
        return sampleEntityService.findByUuidIn(uuids);
    }

    public List<SampleEntity> findByNameEndsWith(String name) {
        return sampleEntityService.findByNameEndsWith(name);
    }

    public Page<SampleEntity> findAll(Pageable pageable) {
        return sampleEntityService.findAll(pageable);
    }
}
