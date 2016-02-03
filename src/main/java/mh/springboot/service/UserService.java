package mh.springboot.service;

import com.google.common.collect.ImmutableSet;
import mh.springboot.model.security.Role;
import mh.springboot.model.security.RoleEnum;
import mh.springboot.model.security.User;
import mh.springboot.repository.role.RoleCachingDecoratorRepository;
import mh.springboot.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleCachingDecoratorRepository roleCachingDecoratorRepository;

    @Autowired
    public UserService(UserRepository userRepository, RoleCachingDecoratorRepository roleCachingDecoratorRepository) {
        this.userRepository = userRepository;
        this.roleCachingDecoratorRepository = roleCachingDecoratorRepository;
    }

    public User createUser(User user, Iterable<RoleEnum> roleEnums) {
        ImmutableSet.Builder<Role> userRoles = new ImmutableSet.Builder<>();
        for(RoleEnum role : roleEnums) {
            userRoles.add(roleCachingDecoratorRepository.getRole(role));
        }
        user.setRoles(userRoles.build());
        return userRepository.save(user);
    }

//TODO ???
//    public User loadUserByUsername(String username) {
//        return userRepository.loadUserByUsername(username);
//    }
}
