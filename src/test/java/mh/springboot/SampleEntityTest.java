package mh.springboot;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.client.RestTemplate;

import static junit.framework.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SpringBootMainApplication.class)
@WebAppConfiguration
@IntegrationTest({"server.port=0"})
public class SampleEntityTest {

    @Value("${local.server.port}")
    private int port;

    private String url;

    private RestTemplate restTemplate;

    @BeforeClass
    public static void authenticate(){
//ONE TRY
//        Authentication authentication =
//                new UsernamePasswordAuthenticationToken("user", "password",
//                AuthorityUtils.createAuthorityList("USER", "ADMIN")); //tried "ROLE_USER"
//        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @Before
    public void setUp() {
        url = String.format("http://localhost:%s/api/sampleentity", port);
        restTemplate = new RestTemplate();
//ANOTHER TRY
//        AuthenticationManager authenticationManager = context.getBean(AuthenticationManager.class);
//        Authentication authentication = authenticationManager
//                .authenticate(new UsernamePasswordAuthenticationToken("user", "password", AuthorityUtils
//                        .createAuthorityList("USER", "ADMIN"))); //tried "ROLE_USER"
//        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    //THIS METHOD SHOULD WORK !
    @Test
    //@WithMockUser(username="user",password = "password", roles={"USER","ADMIN"}) //tried "ROLE_USER"
    public void testEntity_create() throws Exception {
        SampleEntity sampleEntity = create("name", 1);
        ResponseEntity<SampleEntity> response = restTemplate.postForEntity(url, sampleEntity, SampleEntity.class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    private SampleEntity create(String name, int id) {
        SampleEntity entity = new SampleEntity();
        entity.setName(name);
        entity.setId(id);
        return entity;
    }
}

