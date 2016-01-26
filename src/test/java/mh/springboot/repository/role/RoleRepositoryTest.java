package mh.springboot.repository.role;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import mh.springboot.SpringBootMainApplication;
import mh.springboot.model.security.Role;
import mh.springboot.model.security.RoleEnum;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.unitils.reflectionassert.ReflectionComparatorMode;

import java.util.List;

import static org.unitils.reflectionassert.ReflectionAssert.assertReflectionEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SpringBootMainApplication.class)
public class RoleRepositoryTest {

    @Autowired
    private RoleRepository roleRepository;

    @Test
    public void testFindAll() throws Exception {
        List<Role> actual = Lists.newArrayList(roleRepository.findAll());
        List<Role> expected = ImmutableList.of(
            new Role(RoleEnum.USER.getRoleName()),
            new Role(RoleEnum.ADMIN.getRoleName())
        );

        assertReflectionEquals(expected, actual, ReflectionComparatorMode.IGNORE_DEFAULTS);
    }
}
