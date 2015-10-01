package mh.springboot.service;

import mh.springboot.SpringBootMainApplication;
import mh.springboot.service.user.UserService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SpringBootMainApplication.class)
public class UserTest {

    @Autowired
    private UserService userService;

    @Before
    public void setUp() {
        clean();
    }

    @After
    public void tearDown() throws Exception {
        clean();
    }

    @Test
    public void fake() {

    }

//    @Test
//    public void testEntity_create() throws Exception {
//        User user = create("name", uuid(1));
//        ResponseEntity<SampleEntity> response = restTemplate.postForEntity(url, sampleEntity, SampleEntity.class);
//
//        assertEquals(HttpStatus.CREATED, response.getStatusCode());
//        assertNotNull(response.getBody().getId());
//    }
//
//    @Test
//    public void testEntity_update() throws Exception {
//        SampleEntity entity = create("name", uuid(1));
//        entity = sampleEntityService.save(entity);
//        entity.setName("name2");
//        ResponseEntity<?> response = restTemplate.exchange("http://localhost:{port}/api/sampleentity/{id}",
//                                                           HttpMethod.PUT,
//                                                           new HttpEntity<>(entity),
//                                                           String.class,
//                                                           port,
//                                                           entity.getId());
//        SampleEntity actual = sampleEntityService.findOne(entity.getId());
//        SampleEntity expected = create("name2", uuid(1));
//        expected.setId(entity.getId());
//
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertTrue(reflectionEquals(expected, actual, "created", "lastModified"));
//        assertNotNull(actual.getCreated());
//        assertNotNull(actual.getLastModified());
//    }
//
//    @Test
//    public void testEntity_delete() throws Exception {
//        SampleEntity sampleEntity = create("name", uuid(1));
//        sampleEntityService.save(sampleEntity);
//        ResponseEntity<?> response = restTemplate.exchange("http://localhost:{port}/api/sampleentity/{id}",
//                                                           HttpMethod.DELETE,
//                                                           new HttpEntity<>(sampleEntity),
//                                                           String.class,
//                                                           port,
//                                                           sampleEntity.getId());
//        SampleEntity actual = sampleEntityService.findOne(sampleEntity.getId());
//
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertNull(actual);
//    }
//
//    @Test
//    public void testEntityfindById_correct() throws Exception {
//        SampleEntity sampleEntity = create("name", uuid(1));
//        sampleEntityService.save(sampleEntity);
//        ResponseEntity<SampleEntity> response = restTemplate.getForEntity(
//                "http://localhost:{port}/api/sampleentity/{id}",
//                SampleEntity.class,
//                port,
//                sampleEntity.getId());
//        SampleEntity expected = sampleEntityService.findOne(sampleEntity.getId());
//        SampleEntity actual = response.getBody();
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertTrue(reflectionEquals(expected, actual, "created", "lastModified"));
//        assertNotNull(actual.getCreated());
//        assertNotNull(actual.getLastModified());
//    }
//
//    @Test
//    public void testEntityfindById_badRequest() throws Exception {
//        try {
//            restTemplate.getForEntity("http://localhost:{port}/api/sampleentity/-1",
//                                      String.class,
//                                      port);
//            fail();
//        }
//        catch (HttpClientErrorException ex) {
//            assertEquals(400, ex.getStatusCode().value());
//            String actual = ex.getResponseBodyAsString();
//            String expected = "{\"httpCode\":400,\"errors\":[{\"code\":\"INVALID_ID\",\"message\":\"invalid id\"}]}";
//            JSONAssert.assertEquals(expected, actual, false);
//        }
//    }
//
//    @Test
//    public void testEntityfindById_notFound() throws Exception {
//        try {
//            restTemplate.getForEntity("http://localhost:{port}/api/sampleentity/9223372036854775807",
//                                      String.class,
//                                      port);
//            fail();
//        }
//        catch (HttpClientErrorException ex) {
//            assertEquals(404, ex.getStatusCode().value());
//            String actual = ex.getResponseBodyAsString();
//            String expected = "{\"httpCode\":404,\"errors\":[{\"code\":\"NOT_FOUND\",\"message\":\"entity does not exist\"}]}";
//            JSONAssert.assertEquals(expected, actual, false);
//        }
//    }
//
//    @Test
//    public void testEntityfindById_invalid() throws Exception {
//        try {
//            restTemplate.getForEntity("http://localhost:{port}/api/sampleentity/abc",
//                                      String.class,
//                                      port);
//            fail();
//        }
//        catch (HttpClientErrorException ex) {
//            assertEquals(400, ex.getStatusCode().value());
//            String actual = ex.getResponseBodyAsString();
//            String expected = "{\"httpCode\":400,\"errors\":[{\"code\":\"TYPE_MISMATCH\",\"message\":\"Failed to convert value of type 'java.lang.String' to required type 'java.lang.Long'; nested exception is java.lang.NumberFormatException: For input string: \\\"abc\\\"\"}]}";
//            JSONAssert.assertEquals(expected, actual, false);
//        }
//    }
//
//    @Test
//    public void testEntity_findByUuidIn() throws Exception {
//        sampleEntityService.save(create("name1", uuid(1)));
//        sampleEntityService.save(create("name2", uuid(2)));
//        sampleEntityService.save(create("name3", uuid(3)));
//
//        List<SampleEntity> results = sampleEntityService.findByUuidIn(ImmutableList.of(uuid(1), uuid(3)));
//        assertEquals(2, results.size());
//    }
//
//    @Test
//    public void testEntity_findByNameEndsWith() throws Exception {
//        sampleEntityService.save(create("name1", uuid(1)));
//        Long id = sampleEntityService.save(create("nameABC", uuid(2))).getId();
//        sampleEntityService.save(create("name3", uuid(3)));
//
//        List<SampleEntity> actual = sampleEntityService.findByNameEndsWith("ABC");
//        SampleEntity expected = create("nameABC", uuid(2));
//        expected.setId(id);
//        assertEquals(1, actual.size());
//        assertTrue(reflectionEquals(expected, actual.get(0), "created", "lastModified"));
//    }
//
//    @Test
//    public void testEntity_getAll() throws Exception {
//        Set<Long> expected = new HashSet<>();
//        for(int i=0; i<105; i++) {
//            SampleEntity entity = sampleEntityService.save(create(String.format("name%d", i), uuid(i)));
//            expected.add(entity.getId());
//        }
//        ResponseEntity<SampleEntity[]> response = restTemplate.getForEntity(
//                "http://localhost:{port}/api/sampleentity/",
//                SampleEntity[].class,
//                port);
//        Set<Long> actual = new HashSet<>();
//        for(SampleEntity entity : response.getBody()) {
//            actual.add(entity.getId());
//        }
//
//        assertEquals(expected, actual);
//    }
//
//    @Test
//    public void testEntity_getAllWithPagination() throws Exception {
//        Set<Long> expected = new HashSet<>();
//        for(int i=0; i<105; i++) {
//            SampleEntity entity = sampleEntityService.save(create(String.format("name%d", i), uuid(i)));
//            expected.add(entity.getId());
//        }
//        Set<Long> actual = new HashSet<>();
//        for(int i=0; i<11; i++) {
//            ResponseEntity<SampleEntityPageAdapter> response =
//                    restTemplate.exchange(
//                            "http://localhost:{port}/api/sampleentity/{page}/{size}",
//                            HttpMethod.GET,
//                            null,
//                            SampleEntityPageAdapter.class,
//                            port, i, 10);
//            for(SampleEntity entity : response.getBody().getContent()) {
//                actual.add(entity.getId());
//            }
//        }
//
//        assertEquals(expected, actual);
//    }
//
//    @Test
//    public void testEntityfindById_notExistingPage() throws Exception {
//        try {
//            restTemplate.getForEntity("http://localhost:{port}/not_existing_page.html",
//                                      String.class,
//                                      port);
//            fail();
//        }
//        catch (HttpClientErrorException ex) {
//            assertEquals(404, ex.getStatusCode().value());
//            String actual = ex.getResponseBodyAsString();
//            assertTrue(actual.toLowerCase().contains("page not found"));
//        }
//    }
//
//
//
//    private User create(String name, UUID uuid) {
//        User entity = new User();
//        entity.setLogin("login");
//        entity.setName("name");
//        entity.setPassword("password");
//
//        entity.setName(name);
//
//        return entity;
//    }

    private void clean() {
        userService.deleteAll();
    }

}

