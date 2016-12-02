package mh.springboot.repository.role;

import com.google.common.collect.ImmutableMap;
import mh.springboot.SpringBootMainApplication;
import mh.springboot.model.security.Role;
import mh.springboot.model.security.RoleEnum;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.unitils.reflectionassert.ReflectionComparatorMode;

import java.util.Map;

import static org.unitils.reflectionassert.ReflectionAssert.assertReflectionEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = SpringBootMainApplication.class)
public class RoleCacheDecoratorRepositoryTest {

    @Autowired
    @Qualifier("RoleCachingDecoratorRepository")
    private RoleCachingDecoratorRepository roleService;

    @Test
    public void testFindAll() throws Exception {
        Map<Long, Role> actual = roleService.findAll();
        Map<Long, Role> expected = ImmutableMap.of(
                RoleEnum.USER.getId(), new Role(RoleEnum.USER.getRoleName()),
                RoleEnum.ADMIN.getId(), new Role(RoleEnum.ADMIN.getRoleName())
        );

        assertReflectionEquals(expected, actual, ReflectionComparatorMode.IGNORE_DEFAULTS);
    }

    @Test
    public void testGetRole_user() throws Exception {
        Role actual = roleService.getRole(RoleEnum.USER);
        Role expected = new Role(RoleEnum.USER.getRoleName());

        assertReflectionEquals(expected, actual, ReflectionComparatorMode.IGNORE_DEFAULTS);
    }

    @Test
    public void testGetRole_admin() throws Exception {
        Role actual = roleService.getRole(RoleEnum.ADMIN);
        Role expected = new Role(RoleEnum.ADMIN.getRoleName());

        assertReflectionEquals(expected, actual, ReflectionComparatorMode.IGNORE_DEFAULTS);
    }

}
