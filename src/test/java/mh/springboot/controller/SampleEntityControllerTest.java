package mh.springboot.controller;

import mh.springboot.SpringBootMainApplication;
import mh.springboot.model.SampleEntity;
import mh.springboot.repository.sampleentity.SampleEntityRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static mh.springboot.utils.TestUuid.uuid;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = SpringBootMainApplication.class)
@WebAppConfiguration
public class SampleEntityControllerTest {

    @Autowired
    private SampleEntityRepository sampleEntityRepository;

    @Autowired
    private WebApplicationContext webContext;

    private MockMvc mockMvc;

    @Before
    public void setupMockMvc() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webContext).build();
    }

    @After
    public void tearDown() throws Exception {
        sampleEntityRepository.deleteAll();
    }

    @Test
    public void testFindById() throws Exception {
        SampleEntity sampleEntity = SampleEntityControllerIntegrationTest.create("name", uuid(1));
        sampleEntityRepository.save(sampleEntity);

        mockMvc.perform(MockMvcRequestBuilders.get(String.format("/api/sampleentity/%s", sampleEntity.getId())))
               .andExpect(MockMvcResultMatchers.status().isOk())
               .andExpect(MockMvcResultMatchers.content().json("{\"uuid\":\"00000000-0000-0000-0000-000000000001\",\"name\":\"name\"}"));

    }

}
