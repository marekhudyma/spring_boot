package mh.springboot;

import mh.springboot.dao.SampleEntityService;
import mh.springboot.model.SampleEntity;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.TestCase.assertNull;
import static mh.springboot.TestUuid.uuid;
import static org.apache.commons.lang3.builder.EqualsBuilder.reflectionEquals;
import static org.junit.Assert.assertTrue;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SpringBootMainApplication.class)
@WebAppConfiguration
@IntegrationTest({"server.port=0"})
public class SampleEntityTest {

    @Autowired
    private SampleEntityService sampleEntityService;

    @Value("${local.server.port}")
    private int port;

    private String url;

    private RestTemplate restTemplate;

    @Before
    public void setUp() {
        url = String.format("http://localhost:%s/api/sampleentity", port);
        restTemplate = new RestTemplate();
    }

    @After
    public void tearDown() throws Exception {
        sampleEntityService.deleteAll();
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
        entity = sampleEntityService.save(entity);
        entity.setName("name2");
        ResponseEntity<?> response = restTemplate.exchange("http://localhost:{port}/api/sampleentity/{id}",
                                                           HttpMethod.PUT,
                                                           new HttpEntity<>(entity),
                                                           String.class,
                                                           port,
                                                           entity.getId());
        SampleEntity actual = sampleEntityService.findOne(entity.getId());
        SampleEntity expected = create("name2", uuid(1));
        expected.setId(entity.getId());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(reflectionEquals(expected, actual, "created", "lastModified"));
        assertNotNull(actual.getCreated());
        assertNotNull(actual.getLastModified());
    }

    @Test
    public void testEntity_delete() throws Exception {
        SampleEntity sampleEntity =create("name", uuid(1));
        sampleEntityService.save(sampleEntity);
        ResponseEntity<?> response = restTemplate.exchange("http://localhost:{port}/api/sampleentity/{id}",
                                                           HttpMethod.DELETE,
                                                           new HttpEntity<>(sampleEntity),
                                                           String.class,
                                                           port,
                                                           sampleEntity.getId());
        SampleEntity actual = sampleEntityService.findOne(sampleEntity.getId());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNull(actual);
    }

    @Test
    public void testEntity_findById() throws Exception {
        SampleEntity sampleEntity = create("name", uuid(1));
        sampleEntityService.save(sampleEntity);
        ResponseEntity<SampleEntity> response = restTemplate.getForEntity("http://localhost:{port}/api/sampleentity/{id}",
                                                                          SampleEntity.class,
                                                                          port,
                                                                          sampleEntity.getId());
        SampleEntity expected = sampleEntityService.findOne(sampleEntity.getId());
        SampleEntity actual = response.getBody();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(reflectionEquals(expected, actual, "created", "lastModified"));
        assertNotNull(actual.getCreated());
        assertNotNull(actual.getLastModified());
    }

    private SampleEntity create(String name, UUID uuid) {
        SampleEntity entity = new SampleEntity();
        entity.setName(name);
        entity.setUuid(uuid);
        return entity;
    }

}

