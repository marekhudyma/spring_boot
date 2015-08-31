package mh.springboot.dao.sampleentity;

import com.google.common.cache.GuavaCacheHelper;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import mh.springboot.SpringBootMainApplication;
import mh.springboot.dao.SampleEntityCachingDecoratorService;
import mh.springboot.dao.SampleEntityService;
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

import java.util.Map;

import static mh.springboot.utils.TestUuid.uuid;
import static org.apache.commons.lang3.builder.EqualsBuilder.reflectionEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SpringBootMainApplication.class)
public class SampleEntityCacheDecoratorServiceTest {

    @Autowired
    @Qualifier("SampleEntityCachingDecoratorService")
    private SampleEntityService sampleEntityService;

    @Autowired
    private CacheManager cacheManager;

    private Cache findOne;

    private Cache findAll;

    @Before
    public final void setUp() throws Exception{
        findOne = cacheManager.getCache(SampleEntityCachingDecoratorService.FIND_ONE);
        findAll = cacheManager.getCache(SampleEntityCachingDecoratorService.FIND_ALL);
        clean();
    }

    @After
    public void tearDown() throws Exception {
        clean();
    }

    @Test
    public void testSampleEntityCaching_findOne() throws Exception {
        SampleEntity entity1 = sampleEntityService.save(SampleEntityBuilder.create("name1", uuid(1)));
        sampleEntityService.findOne(entity1.getId());
        SampleEntity entity2 = sampleEntityService.save(SampleEntityBuilder.create("name2", uuid(1)));
        sampleEntityService.findOne(entity2.getId());

        Long id1 = entity1.getId();
        Long id2 = entity2.getId();
        Map<Long, SampleEntity> expected = ImmutableMap.of(id1, entity1, id2, entity2);
        Map<Long, SampleEntity> actual = getAllItemsFromCache(findOne);

        assertEquals(2, actual.size());
        assertTrue(reflectionEquals(expected.get(id1), actual.get(id1), "created", "lastModified"));
        assertTrue(reflectionEquals(expected.get(id2), actual.get(id2), "created", "lastModified"));

        assertEquals(0, getAllItemsFromCache(findAll).size());
    }

    @Test
    public void testSampleEntityCaching_deleteId() throws Exception {
        SampleEntity entity1 = sampleEntityService.save(SampleEntityBuilder.create("name1", uuid(1)));
        sampleEntityService.findOne(entity1.getId());
        SampleEntity entity2 = sampleEntityService.save(SampleEntityBuilder.create("name2", uuid(1)));
        sampleEntityService.findOne(entity2.getId());

        Long id1 = entity1.getId();
        Long id2 = entity2.getId();

        sampleEntityService.delete(id1);

        Map<Long, SampleEntity> expected = ImmutableMap.of(id2, entity2);
        Map<Long, SampleEntity> actual = getAllItemsFromCache(findOne);

        assertEquals(1, actual.size());
        assertTrue(reflectionEquals(expected.get(id2), actual.get(id2), "created", "lastModified"));

        assertEquals(0, getAllItemsFromCache(findAll).size());
    }

    @Test
    public void testSampleEntityCaching_deleteEntity() throws Exception {
        SampleEntity entity1 = sampleEntityService.save(SampleEntityBuilder.create("name1", uuid(1)));
        sampleEntityService.findOne(entity1.getId());
        SampleEntity entity2 = sampleEntityService.save(SampleEntityBuilder.create("name2", uuid(1)));
        sampleEntityService.findOne(entity2.getId());

        Long id2 = entity2.getId();

        sampleEntityService.delete(entity1);

        Map<Long, SampleEntity> expected = ImmutableMap.of(id2, entity2);
        Map<Long, SampleEntity> actual = getAllItemsFromCache(findOne);

        assertEquals(1, actual.size());
        assertTrue(reflectionEquals(expected.get(id2), actual.get(id2), "created", "lastModified"));

        assertEquals(0, getAllItemsFromCache(findAll).size());
    }

    @Test
    public void testSampleEntityCaching_deleteMany() throws Exception {
        SampleEntity entity1 = sampleEntityService.save(SampleEntityBuilder.create("name1", uuid(1)));
        sampleEntityService.findOne(entity1.getId());
        SampleEntity entity2 = sampleEntityService.save(SampleEntityBuilder.create("name2", uuid(1)));
        sampleEntityService.findOne(entity2.getId());
        SampleEntity entity3 = sampleEntityService.save(SampleEntityBuilder.create("name2", uuid(1)));
        sampleEntityService.findOne(entity3.getId());

        Long id3 = entity3.getId();

        sampleEntityService.delete(ImmutableList.of(entity1, entity2));

        Map<Long, SampleEntity> expected = ImmutableMap.of(id3, entity3);
        Map<Long, SampleEntity> actual = getAllItemsFromCache(findOne);

        assertEquals(1, actual.size());
        assertTrue(reflectionEquals(expected.get(id3), actual.get(id3), "created", "lastModified"));

        assertEquals(0, getAllItemsFromCache(findAll).size());
    }

    @Test
    public void testSampleEntityCaching_deleteAll() throws Exception {
        SampleEntity entity1 = sampleEntityService.save(SampleEntityBuilder.create("name1", uuid(1)));
        sampleEntityService.findOne(entity1.getId());
        SampleEntity entity2 = sampleEntityService.save(SampleEntityBuilder.create("name2", uuid(1)));
        sampleEntityService.findOne(entity2.getId());

        sampleEntityService.deleteAll();

        Map<Long, SampleEntity> actual = getAllItemsFromCache(findOne);

        assertEquals(0, actual.size());
        assertEquals(0, getAllItemsFromCache(findAll).size());
    }

    @Test
    public void  testSampleEntityCaching_saveOne() throws Exception {
        SampleEntity entity1 = sampleEntityService.save(SampleEntityBuilder.create("name1", uuid(1)));
        sampleEntityService.findOne(entity1.getId());
        SampleEntity entity2 = sampleEntityService.save(SampleEntityBuilder.create("name2", uuid(1)));
        sampleEntityService.findOne(entity2.getId());

        Long id2 = entity2.getId();

        sampleEntityService.save(entity1);

        Map<Long, SampleEntity> expected = ImmutableMap.of(id2, entity2);
        Map<Long, SampleEntity> actual = getAllItemsFromCache(findOne);

        assertEquals(1, actual.size());
        assertTrue(reflectionEquals(expected.get(id2), actual.get(id2), "created", "lastModified"));

        assertEquals(0, getAllItemsFromCache(findAll).size());
    }

    @Test
    public void testSampleEntityCaching_saveMany() throws Exception {
        SampleEntity entity1 = sampleEntityService.save(SampleEntityBuilder.create("name1", uuid(1)));
        sampleEntityService.findOne(entity1.getId());
        SampleEntity entity2 = sampleEntityService.save(SampleEntityBuilder.create("name2", uuid(1)));
        sampleEntityService.findOne(entity2.getId());
        SampleEntity entity3 = sampleEntityService.save(SampleEntityBuilder.create("name2", uuid(1)));
        sampleEntityService.findOne(entity3.getId());

        Long id3 = entity3.getId();

        sampleEntityService.save(ImmutableList.of(entity1, entity2));

        Map<Long, SampleEntity> expected = ImmutableMap.of(id3, entity3);
        Map<Long, SampleEntity> actual = getAllItemsFromCache(findOne);

        assertEquals(1, actual.size());
        assertTrue(reflectionEquals(expected.get(id3), actual.get(id3), "created", "lastModified"));

        assertEquals(0, getAllItemsFromCache(findAll).size());
    }

    @Test
    public void testSampleEntityCaching_findAll() throws Exception {
        SampleEntity entity1 = sampleEntityService.save(SampleEntityBuilder.create("name1", uuid(1)));
        sampleEntityService.findOne(entity1.getId());
        SampleEntity entity2 = sampleEntityService.save(SampleEntityBuilder.create("name2", uuid(1)));
        sampleEntityService.findOne(entity2.getId());

        Long id1 = entity1.getId();
        Long id2 = entity2.getId();

        Iterable<SampleEntity> allEntities = sampleEntityService.findAll();
        Map<Long, SampleEntity> expected = Maps.uniqueIndex(allEntities, SampleEntity::getId);
        Map<Long, SampleEntity> actual = getAllItemsFromCache(findOne);

        assertEquals(2, actual.size());
        assertTrue(reflectionEquals(expected.get(id1), actual.get(id1), "created", "lastModified"));
        assertTrue(reflectionEquals(expected.get(id2), actual.get(id2), "created", "lastModified"));
    }

    /**
     * method get all items from cache from internal implementation
     */
    private Map<Long, SampleEntity> getAllItemsFromCache(Cache cache) {
        return GuavaCacheHelper.getAllItemsFromCache(cache);
    }

    private void clean() {
        sampleEntityService.deleteAll();
        for(String cache : cacheManager.getCacheNames()) {
            cacheManager.getCache(cache).clear();
        }
    }
}
