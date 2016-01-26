package mh.springboot.repository.sampleentity;

import com.google.common.cache.GuavaCacheHelper;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import mh.springboot.SpringBootMainApplication;
import mh.springboot.model.SampleEntity;
import mh.springboot.model.SampleEntityBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.unitils.reflectionassert.ReflectionComparatorMode;

import java.util.Collection;
import java.util.Map;

import static mh.springboot.utils.TestUuid.uuid;
import static org.unitils.reflectionassert.ReflectionAssert.assertReflectionEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SpringBootMainApplication.class)
public class SampleEntityCachingDecoratorRepositoryIntegrationTest {

    @Autowired
    private SampleEntityRepository sampleEntityRepository;

    @Autowired
    private CacheManager cacheManager;

    @Autowired
    @Qualifier("SampleEntityCachingDecoratorRepository")
    private SampleEntityRepository sampleEntityCachingDecoratorRepository;

    private Cache findOne;

    private Cache findAll;

    @Before
    public final void setUp() throws Exception{
        clean();

        findOne = cacheManager.getCache(SampleEntityCachingDecoratorRepository.FIND_ONE);
        findAll = cacheManager.getCache(SampleEntityCachingDecoratorRepository.FIND_ALL);
    }

    @After
    public void tearDown() throws Exception {
        clean();
    }

    @Test
    public void testSampleEntityCaching_findOne() throws Exception {
        SampleEntity entity1 = sampleEntityCachingDecoratorRepository.save(SampleEntityBuilder.create("name1", uuid(1)));
        cleanDates(entity1);
        SampleEntity entity2 = sampleEntityCachingDecoratorRepository.save(SampleEntityBuilder.create("name2", uuid(2)));
        cleanDates(entity2);
        sampleEntityCachingDecoratorRepository.findOne(entity1.getId());
        sampleEntityCachingDecoratorRepository.findOne(entity2.getId());

        Map<Long, SampleEntity> expected = ImmutableMap.of(entity1.getId(), entity1, entity2.getId(), entity2);
        Map<Long, SampleEntity> actual = getAllItemsFromCache(findOne);

        assertReflectionEquals(expected, actual, ReflectionComparatorMode.IGNORE_DEFAULTS);
        assertReflectionEquals(0, getAllItemsFromCache(findAll).size(), ReflectionComparatorMode.IGNORE_DEFAULTS);
    }

    @Test
    public void testSampleEntityCaching_deleteId() throws Exception {
        SampleEntity entity1 = sampleEntityCachingDecoratorRepository.save(SampleEntityBuilder.create("name1", uuid(1)));
        cleanDates(entity1);
        SampleEntity entity2 = sampleEntityCachingDecoratorRepository.save(SampleEntityBuilder.create("name2", uuid(2)));
        cleanDates(entity2);
        sampleEntityCachingDecoratorRepository.findOne(entity1.getId());
        sampleEntityCachingDecoratorRepository.findOne(entity2.getId());

        sampleEntityCachingDecoratorRepository.delete(entity1.getId());

        Map<Long, SampleEntity> expected = ImmutableMap.of(entity2.getId(), entity2);
        Map<Long, SampleEntity> actual = getAllItemsFromCache(findOne);

        assertReflectionEquals(expected, actual, ReflectionComparatorMode.IGNORE_DEFAULTS);
        assertReflectionEquals(0, getAllItemsFromCache(findAll).size(), ReflectionComparatorMode.IGNORE_DEFAULTS);
    }

    @Test
    public void testSampleEntityCaching_deleteEntity() throws Exception {
        SampleEntity entity1 = sampleEntityCachingDecoratorRepository.save(SampleEntityBuilder.create("name1", uuid(1)));
        cleanDates(entity1);
        SampleEntity entity2 = sampleEntityCachingDecoratorRepository.save(SampleEntityBuilder.create("name2", uuid(2)));
        cleanDates(entity2);
        sampleEntityCachingDecoratorRepository.findOne(entity1.getId());
        sampleEntityCachingDecoratorRepository.findOne(entity2.getId());

        sampleEntityCachingDecoratorRepository.delete(entity1);

        Map<Long, SampleEntity> expected = ImmutableMap.of(entity2.getId(), entity2);
        Map<Long, SampleEntity> actual = getAllItemsFromCache(findOne);

        assertReflectionEquals(expected, actual, ReflectionComparatorMode.IGNORE_DEFAULTS);
        assertReflectionEquals(0, getAllItemsFromCache(findAll).size(), ReflectionComparatorMode.IGNORE_DEFAULTS);
    }

    @Test
    public void testSampleEntityCaching_deleteMany() throws Exception {
        SampleEntity entity1 = sampleEntityCachingDecoratorRepository.save(SampleEntityBuilder.create("name1", uuid(1)));
        cleanDates(entity1);
        SampleEntity entity2 = sampleEntityCachingDecoratorRepository.save(SampleEntityBuilder.create("name2", uuid(2)));
        cleanDates(entity2);
        SampleEntity entity3 = sampleEntityCachingDecoratorRepository.save(SampleEntityBuilder.create("name3", uuid(3)));
        cleanDates(entity3);
        sampleEntityCachingDecoratorRepository.findOne(entity1.getId());
        sampleEntityCachingDecoratorRepository.findOne(entity2.getId());
        sampleEntityCachingDecoratorRepository.findOne(entity3.getId());

        sampleEntityCachingDecoratorRepository.delete(ImmutableList.of(entity1, entity2));

        Map<Long, SampleEntity> expected = ImmutableMap.of(entity3.getId(), entity3);
        Map<Long, SampleEntity> actual = getAllItemsFromCache(findOne);

        assertReflectionEquals(expected, actual, ReflectionComparatorMode.IGNORE_DEFAULTS);
        assertReflectionEquals(0, getAllItemsFromCache(findAll).size(), ReflectionComparatorMode.IGNORE_DEFAULTS);
    }

    @Test
    public void testSampleEntityCaching_deleteAll() throws Exception {
        SampleEntity entity1 = sampleEntityCachingDecoratorRepository.save(SampleEntityBuilder.create("name1", uuid(1)));
        cleanDates(entity1);
        SampleEntity entity2 = sampleEntityCachingDecoratorRepository.save(SampleEntityBuilder.create("name2", uuid(2)));
        cleanDates(entity2);
        sampleEntityCachingDecoratorRepository.findOne(entity1.getId());
        sampleEntityCachingDecoratorRepository.findOne(entity2.getId());

        sampleEntityCachingDecoratorRepository.deleteAll();

        Map<Long, SampleEntity> expected = ImmutableMap.of();
        Map<Long, SampleEntity> actual = getAllItemsFromCache(findOne);

        assertReflectionEquals(expected, actual, ReflectionComparatorMode.IGNORE_DEFAULTS);
        assertReflectionEquals(0, getAllItemsFromCache(findAll).size(), ReflectionComparatorMode.IGNORE_DEFAULTS);
    }

    @Test
    public void  testSampleEntityCaching_saveOne() throws Exception {
        SampleEntity entity1 = sampleEntityCachingDecoratorRepository.save(SampleEntityBuilder.create("name1", uuid(1)));
        cleanDates(entity1);
        SampleEntity entity2 = sampleEntityCachingDecoratorRepository.save(SampleEntityBuilder.create("name2", uuid(2)));
        cleanDates(entity2);
        sampleEntityCachingDecoratorRepository.findOne(entity1.getId());
        sampleEntityCachingDecoratorRepository.findOne(entity2.getId());

        sampleEntityCachingDecoratorRepository.save(entity1);

        Map<Long, SampleEntity> expected = ImmutableMap.of(entity2.getId(), entity2);
        Map<Long, SampleEntity> actual = getAllItemsFromCache(findOne);

        assertReflectionEquals(expected, actual, ReflectionComparatorMode.IGNORE_DEFAULTS);
        assertReflectionEquals(0, getAllItemsFromCache(findAll).size(), ReflectionComparatorMode.IGNORE_DEFAULTS);
    }

    @Test
    public void testSampleEntityCaching_saveMany() throws Exception {
        SampleEntity entity1 = sampleEntityCachingDecoratorRepository.save(SampleEntityBuilder.create("name1", uuid(1)));
        cleanDates(entity1);
        SampleEntity entity2 = sampleEntityCachingDecoratorRepository.save(SampleEntityBuilder.create("name2", uuid(2)));
        cleanDates(entity2);
        SampleEntity entity3 = sampleEntityCachingDecoratorRepository.save(SampleEntityBuilder.create("name3", uuid(3)));
        cleanDates(entity3);
        sampleEntityCachingDecoratorRepository.findOne(entity1.getId());
        sampleEntityCachingDecoratorRepository.findOne(entity2.getId());
        sampleEntityCachingDecoratorRepository.findOne(entity3.getId());

        sampleEntityCachingDecoratorRepository.save(ImmutableList.of(entity1, entity2));

        Map<Long, SampleEntity> expected = ImmutableMap.of(entity3.getId(), entity3);
        Map<Long, SampleEntity> actual = getAllItemsFromCache(findOne);

        assertReflectionEquals(expected, actual, ReflectionComparatorMode.IGNORE_DEFAULTS);
        assertReflectionEquals(0, getAllItemsFromCache(findAll).size(), ReflectionComparatorMode.IGNORE_DEFAULTS);
    }

    @Test
    public void testSampleEntityCaching_findAll() throws Exception {
        SampleEntity entity1 = sampleEntityCachingDecoratorRepository.save(SampleEntityBuilder.create("name1", uuid(1)));
        cleanDates(entity1);
        entity1.setUuid(null);
        SampleEntity entity2 = sampleEntityCachingDecoratorRepository.save(SampleEntityBuilder.create("name2", uuid(2)));
        cleanDates(entity2);
        entity2.setUuid(null);
        sampleEntityCachingDecoratorRepository.findOne(entity1.getId());
        sampleEntityCachingDecoratorRepository.findOne(entity2.getId());

        sampleEntityCachingDecoratorRepository.findAll();

        Collection<SampleEntity> expected = ImmutableSet.of(entity1, entity2);
        Collection<SampleEntity> actual = getAllItemsFromCache(findAll).values();

        assertReflectionEquals(expected, actual, ReflectionComparatorMode.IGNORE_DEFAULTS);
        assertReflectionEquals(0, getAllItemsFromCache(findOne).size(), ReflectionComparatorMode.IGNORE_DEFAULTS);
    }

    /**
     * method get all items from cache from internal implementation
     */
    private Map<Long, SampleEntity> getAllItemsFromCache(Cache cache) {
        return GuavaCacheHelper.getAllItemsFromCache(cache);
    }

    private void clean() {
        sampleEntityRepository.deleteAll();
        for(String cache : cacheManager.getCacheNames()) {
            cacheManager.getCache(cache).clear();
        }
    }

    private void cleanDates(SampleEntity entity) {
        entity.setCreated(null);
        entity.setLastModified(null);
    }

}
