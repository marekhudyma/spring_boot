package mh.springboot.repository.sampleentity;

import mh.springboot.model.SampleEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

/**
 * This is just decorator for generated bean
 * The only reason is that you cannot invalidate many key-value caches
 * using @CacheEvict in save(Iterable) and delete(Iterable)
 * https://stackoverflow.com/questions/32276418/spring-boot-cacheevict-saveiterables-entities
 */
@Component("SampleEntityCachingDecoratorRepository")
public class SampleEntityCachingDecoratorRepository implements SampleEntityRepository {

    public static final String FIND_ONE = "findOne";
    public static final String FIND_ALL = "findAll";

    private CacheManager cacheManager;
    private SampleEntityRepository sampleEntityRepository;

    @Autowired
    public SampleEntityCachingDecoratorRepository(CacheManager cacheManager,
                                                  SampleEntityRepository sampleEntityRepository) {
        this.cacheManager = cacheManager;
        this.sampleEntityRepository = sampleEntityRepository;
    }

    @Caching(evict = {@CacheEvict(value = FIND_ONE,  key = "#entity.id"),
                      @CacheEvict(value = FIND_ALL, allEntries = true)})
    public <S extends SampleEntity> S save(S entity) {
        return sampleEntityRepository.save(entity);
    }

    /**
     * it was not possible to invalidate many items from cache by @CacheEvict
     */
    public <S extends SampleEntity> Iterable<S> save(Iterable<S> entities) {
        for(SampleEntity sampleEntity : entities) {
            cacheManager.getCache(FIND_ONE).evict(sampleEntity.getId());
        }
        cacheManager.getCache(FIND_ALL).clear();

        return sampleEntityRepository.save(entities);
    }

    @Cacheable(key = "#a0", value = FIND_ONE)
    public SampleEntity findOne(Long id) {
        return sampleEntityRepository.findOne(id);
    }

    public boolean exists(Long id) {
        return sampleEntityRepository.exists(id);
    }

    @Cacheable(value = FIND_ALL)
    public Iterable<SampleEntity> findAll() {
        return sampleEntityRepository.findAll();
    }

    public Iterable<SampleEntity> findAll(Iterable<Long> ids) {
        return sampleEntityRepository.findAll(ids);
    }

    public long count() {
        return sampleEntityRepository.count();
    }

    @Caching(evict = {@CacheEvict(value = FIND_ONE, key = "#a0"),
                      @CacheEvict(value = FIND_ALL, allEntries = true)})
    public void delete(Long id) {
        sampleEntityRepository.delete(id);
    }

    @Caching(evict = {@CacheEvict(value = FIND_ONE, key = "#entity.id"),
                      @CacheEvict(value = FIND_ALL, allEntries = true)})
    public void delete(SampleEntity entity) {
        sampleEntityRepository.delete(entity);
    }

    /**
     * it was not possible to invalidate many items from cache by @CacheEvict
     */
    public void delete(Iterable<? extends SampleEntity> entities) {
        for(SampleEntity sampleEntity : entities) {
            cacheManager.getCache(FIND_ONE).evict(sampleEntity.getId());
        }
        cacheManager.getCache(FIND_ALL).clear();

        sampleEntityRepository.delete(entities);
    }

    @Caching(evict = {
            @CacheEvict(value = FIND_ONE, allEntries = true),
            @CacheEvict(value = FIND_ALL, allEntries = true)})
    public void deleteAll() {
        sampleEntityRepository.deleteAll();
    }

    // custom methods

    public List<SampleEntity> findByUuidIn(List<UUID> uuids) {
        return sampleEntityRepository.findByUuidIn(uuids);
    }

    public List<SampleEntity> findByNameEndsWith(String name) {
        return sampleEntityRepository.findByNameEndsWith(name);
    }

    public Page<SampleEntity> findAll(Pageable pageable) {
        return sampleEntityRepository.findAll(pageable);
    }
}
