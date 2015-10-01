package mh.springboot.service.role;

import mh.springboot.SpringBootMainApplication;
import mh.springboot.model.Role;
import mh.springboot.model.RoleEnum;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Map;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SpringBootMainApplication.class)
public class RoleCacheDecoratorServiceTest {

    @Autowired
    @Qualifier("RoleCachingDecoratorService")
    private RoleCachingDecoratorService roleService;

    @Test
    public void testFindAll() throws Exception {
        Map<Long, Role> actual = roleService.findAll();

        assertEquals(2, actual.size());

        assertEquals(Long.valueOf(1), actual.get(RoleEnum.USER.getId()).getId());
        assertEquals("USER", actual.get(RoleEnum.USER.getId()).getAuthority());

        assertEquals(Long.valueOf(2), actual.get(RoleEnum.ADMIN.getId()).getId());
        assertEquals("ADMIN", actual.get(RoleEnum.ADMIN.getId()).getAuthority());
    }
}
