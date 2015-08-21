package mh.springboot;

import mh.springboot.dao.EntityService;
import mh.springboot.model.Entity;
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

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.TestCase.assertNull;
import static org.unitils.reflectionassert.ReflectionAssert.assertReflectionEquals;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SpringBootMainApplication.class)
@WebAppConfiguration
@IntegrationTest({"server.port=0"})
public class EntityTest {

    @Autowired
    private EntityService entityService;

    @Value("${local.server.port}")
    private int port;

    private String url;

    private RestTemplate restTemplate;

    @Before
    public void setUp() {
        url = String.format("http://localhost:%s/api/entity", port);
        restTemplate = new RestTemplate();
        entityService.deleteAll();
    }

    @Test
    public void testEntity_create() throws Exception {
        Entity entity = new Entity.Builder().withName("name").build();
        ResponseEntity<Entity> response = restTemplate.postForEntity(url, entity, Entity.class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody().getId());
    }

    @Test
    public void testEntity_update() throws Exception {
        Entity entity = new Entity.Builder().withName("name").build();
        entity = entityService.create(entity);
        entity.setName("name2");
        ResponseEntity<?> response = restTemplate.exchange("http://localhost:{port}/api/entity/{id}",
                                                           HttpMethod.PUT,
                                                           new HttpEntity<>(entity),
                                                           String.class,
                                                           port,
                                                           entity.getId());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertReflectionEquals(entity, entityService.findById(entity.getId()));
    }

    @Test
    public void testEntity_delete() throws Exception {
        Entity entity = new Entity.Builder().withName("name").build();
        entityService.create(entity);
        ResponseEntity<?> response = restTemplate.exchange("http://localhost:{port}/api/entity/{id}",
                                                           HttpMethod.DELETE,
                                                           new HttpEntity<>(entity),
                                                           String.class,
                                                           port,
                                                           entity.getId());
        Entity actual = entityService.findById(entity.getId());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNull(actual);
    }

    @Test
    public void testEntity_findById() throws Exception {
        Entity entity = new Entity.Builder().withName("name").build();
        entityService.create(entity);
        ResponseEntity<Entity> response = restTemplate.getForEntity("http://localhost:{port}/api/entity/{id}",
                                                                    Entity.class,
                                                                    port,
                                                                    entity.getId());
        Entity actual = entityService.findById(entity.getId());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertReflectionEquals(actual, response.getBody());
    }

}

