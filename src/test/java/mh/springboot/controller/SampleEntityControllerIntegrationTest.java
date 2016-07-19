package mh.springboot.controller;

import com.google.common.collect.ImmutableList;
import mh.springboot.SpringBootMainApplication;
import mh.springboot.model.SampleEntity;
import mh.springboot.repository.sampleentity.SampleEntityRepository;
import mh.springboot.utils.PageAdapter;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.cache.CacheManager;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.unitils.reflectionassert.ReflectionComparatorMode;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.TestCase.assertNull;
import static mh.springboot.utils.TestUuid.uuid;
import static org.junit.Assert.assertTrue;
import static org.unitils.reflectionassert.ReflectionAssert.assertReflectionEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SpringBootMainApplication.class)
@WebAppConfiguration
@IntegrationTest({"server.port=0"})
@ActiveProfiles("test")
public class SampleEntityControllerIntegrationTest {

    @Autowired
    private SampleEntityRepository sampleEntityRepository;

    @Autowired
    private CacheManager cacheManager;

    @Value("${local.server.port}")
    private int port;

    private String url;

    private TestRestTemplate restTemplate;

    static class SampleEntityPageAdapter extends PageAdapter<SampleEntity> {};

    @Before
    public void setUp() {
        url = String.format("http://localhost:%s/api/sampleentity", port);
        restTemplate = new TestRestTemplate();
        clean();
    }

    @After
    public void tearDown() throws Exception {
        clean();
    }

    @Test
    public void testEntity_create() throws Exception {
        SampleEntity sampleEntity = create("name", uuid(1));
        ResponseEntity<SampleEntity> response = restTemplate.postForEntity(url, sampleEntity, SampleEntity.class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody().getId());
    }

    @Test
    public void testEntity_update() throws Exception {
        SampleEntity entity = create("name", uuid(1));
        entity = sampleEntityRepository.save(entity);
        entity.setName("name2");
        ResponseEntity<?> response = restTemplate.exchange("http://localhost:{port}/api/sampleentity/{id}",
                                                           HttpMethod.PUT,
                                                           new HttpEntity<>(entity),
                                                           String.class,
                                                           port,
                                                           entity.getId());
        SampleEntity actual = sampleEntityRepository.findOne(entity.getId());
        SampleEntity expected = create("name2", uuid(1));
        expected.setId(entity.getId());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertReflectionEquals(expected, actual, ReflectionComparatorMode.IGNORE_DEFAULTS);
    }

    @Test
    public void testEntity_delete() throws Exception {
        SampleEntity sampleEntity = create("name", uuid(1));
        sampleEntityRepository.save(sampleEntity);
        ResponseEntity<?> response = restTemplate.exchange("http://localhost:{port}/api/sampleentity/{id}",
                                                           HttpMethod.DELETE,
                                                           new HttpEntity<>(sampleEntity),
                                                           String.class,
                                                           port,
                                                           sampleEntity.getId());
        SampleEntity actual = sampleEntityRepository.findOne(sampleEntity.getId());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNull(actual);
    }

    @Test
    public void testEntityfindById_correct() throws Exception {
        SampleEntity sampleEntity = create("name", uuid(1));
        sampleEntityRepository.save(sampleEntity);
        ResponseEntity<SampleEntity> response = restTemplate.getForEntity(
                "http://localhost:{port}/api/sampleentity/{id}",
                SampleEntity.class,
                port,
                sampleEntity.getId());
        SampleEntity expected = sampleEntityRepository.findOne(sampleEntity.getId());
        SampleEntity actual = response.getBody();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertReflectionEquals(expected, actual, ReflectionComparatorMode.IGNORE_DEFAULTS);
    }

    @Test
    public void testEntityfindById_badRequest() throws Exception {
        ResponseEntity<?> response = restTemplate.getForEntity("http://localhost:{port}/api/sampleentity/-1",
                                                               String.class,
                                                               port);
        String actual = response.getBody().toString();
        String expected = "{\"httpCode\":400,\"errors\":[{\"code\":\"BAD_REQUEST\",\"message\":\"invalid id\"}]}";

        assertEquals(400, response.getStatusCode().value());
        JSONAssert.assertEquals(expected, actual, false);
    }

    @Test
    public void testEntityfindById_notFound() throws Exception {
        ResponseEntity<?> response = restTemplate.getForEntity("http://localhost:{port}/api/sampleentity/9223372036854775807",
                                      String.class,
                                      port);
        String actual = response.getBody().toString();
        String expected = "{\"httpCode\":404,\"errors\":[{\"code\":\"NOT_FOUND\",\"message\":\"entity does not exist\"}]}";

        assertEquals(404, response.getStatusCode().value());
        JSONAssert.assertEquals(expected, actual, false);
    }

    @Test
    public void testEntityfindById_invalid() throws Exception {
        ResponseEntity<?> response = restTemplate.getForEntity("http://localhost:{port}/api/sampleentity/abc",
                                      String.class,
                                      port);
        String actual = response.getBody().toString();
        String expected = "{\"httpCode\":400,\"errors\":[{\"code\":\"TYPE_MISMATCH\",\"message\":\"Failed to convert value of type [java.lang.String] to required type [java.lang.Long]; nested exception is java.lang.NumberFormatException: For input string: \\\"abc\\\"\"}]}";

        assertEquals(400, response.getStatusCode().value());
        JSONAssert.assertEquals(expected, actual, false);
    }

    @Test
    public void testEntity_findByUuidIn() throws Exception {
        sampleEntityRepository.save(create("name1", uuid(1)));
        sampleEntityRepository.save(create("name2", uuid(2)));
        sampleEntityRepository.save(create("name3", uuid(3)));

        List<SampleEntity> results = sampleEntityRepository.findByUuidIn(ImmutableList.of(uuid(1), uuid(3)));
        assertEquals(2, results.size());
    }

    @Test
    public void testEntity_findByNameEndsWith() throws Exception {
        sampleEntityRepository.save(create("name1", uuid(1)));
        Long id = sampleEntityRepository.save(create("nameABC", uuid(2))).getId();
        sampleEntityRepository.save(create("name3", uuid(3)));

        List<SampleEntity> actual = sampleEntityRepository.findByNameEndsWith("ABC");
        SampleEntity expected = create("nameABC", uuid(2));
        expected.setId(id);
        assertEquals(1, actual.size());
        assertReflectionEquals(expected, actual.get(0), ReflectionComparatorMode.IGNORE_DEFAULTS);
    }

    @Test
    public void testEntity_getAll() throws Exception {
        Set<Long> expected = new HashSet<>();
        for(int i=0; i<105; i++) {
            SampleEntity entity = sampleEntityRepository.save(create(String.format("name%d", i), uuid(i)));
            expected.add(entity.getId());
        }
        ResponseEntity<SampleEntity[]> response = restTemplate.getForEntity(
                "http://localhost:{port}/api/sampleentity/",
                SampleEntity[].class,
                port);
        Set<Long> actual = new HashSet<>();
        for(SampleEntity entity : response.getBody()) {
            actual.add(entity.getId());
        }

        assertEquals(expected, actual);
    }

    @Test
    public void testEntity_getAllWithPagination() throws Exception {
        Set<Long> expected = new HashSet<>();
        for(int i=0; i<105; i++) {
            SampleEntity entity = sampleEntityRepository.save(create(String.format("name%d", i), uuid(i)));
            expected.add(entity.getId());
        }
        Set<Long> actual = new HashSet<>();
        for(int i=0; i<11; i++) {
            ResponseEntity<SampleEntityPageAdapter> response =
                    restTemplate.exchange(
                            "http://localhost:{port}/api/sampleentity/{page}/{size}",
                            HttpMethod.GET,
                            null,
                            SampleEntityPageAdapter.class,
                            port, i, 10);
            for(SampleEntity entity : response.getBody().getContent()) {
                actual.add(entity.getId());
            }
        }

        assertEquals(expected, actual);
    }

    @Ignore //TODO HUDYMA fix it
    @Test
    public void testEntityfindById_notExistingPage() throws Exception {
        ResponseEntity<?> response =  restTemplate.getForEntity("http://localhost:{port}/not_existing_page.html",
                                      String.class,
                                      port);
        String actual = response.getBody().toString();

        assertEquals(404, response.getStatusCode().value());
        assertTrue(actual.toLowerCase().contains("page not found"));
    }

    private SampleEntity create(String name, UUID uuid) {
        SampleEntity entity = new SampleEntity();
        entity.setName(name);
        entity.setUuid(uuid);
        return entity;
    }

    private void clean() {
        sampleEntityRepository.deleteAll();
        for(String cache : cacheManager.getCacheNames()) {
            cacheManager.getCache(cache).clear();
        }
    }

}

