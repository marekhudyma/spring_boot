package mh.springboot.service.role;

import com.google.common.collect.Lists;
import mh.springboot.SpringBootMainApplication;
import mh.springboot.model.Role;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SpringBootMainApplication.class)
public class RoleTest {

    @Autowired
    private RoleService roleService;

    @Test
    public void testFindAll() throws Exception {
        ArrayList<Role> actual = Lists.newArrayList(roleService.findAll());

        assertEquals(2, actual.size());
    }
}
