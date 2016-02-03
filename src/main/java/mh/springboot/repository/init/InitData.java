package mh.springboot.repository.init;

import com.google.common.collect.ImmutableSet;
import mh.springboot.model.security.RoleEnum;
import mh.springboot.model.security.User;
import mh.springboot.repository.user.UserRepository;
import mh.springboot.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class InitData implements ApplicationListener<ContextRefreshedEvent> {

    private final UserRepository userRepository;
    private final UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(InitData.class);

    @Autowired
    public InitData(UserRepository userRepository, UserService userService) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if(userRepository.count() == 0) {
            userService.createUser(new User("admin", "a", "marekAdmin"), ImmutableSet.of(RoleEnum.ADMIN, RoleEnum.USER));
            userService.createUser(new User("user", "u", "marekUser"), ImmutableSet.of(RoleEnum.USER));
            logger.info("Default users created");
        }
    }
}
