package mh.springboot.repository.user;

import com.google.common.collect.ImmutableSet;
import mh.springboot.SpringBootMainApplication;
import mh.springboot.model.security.Role;
import mh.springboot.model.security.RoleEnum;
import mh.springboot.model.security.User;
import mh.springboot.service.UserService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.unitils.reflectionassert.ReflectionComparatorMode;

import static org.unitils.reflectionassert.ReflectionAssert.assertReflectionEquals;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SpringBootMainApplication.class)
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

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
    public void testLoadUserByUsername() throws Exception {
        userService.createUser(new User(null, "login", "name", "password"), ImmutableSet.of(RoleEnum.USER));

        User actual = userRepository.loadUserByUsername("login");
        User expected = new User(null, "login", "name", "password", ImmutableSet.of(new Role("ROLE_USER")));

        assertReflectionEquals(expected, actual, ReflectionComparatorMode.IGNORE_DEFAULTS);
    }

    private void clean() {
        userRepository.deleteAll();
    }

}
