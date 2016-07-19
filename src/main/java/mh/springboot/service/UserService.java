package mh.springboot.service;

import com.google.common.collect.ImmutableSet;
import mh.springboot.model.security.Role;
import mh.springboot.model.security.RoleEnum;
import mh.springboot.model.security.User;
import mh.springboot.repository.role.RoleCachingDecoratorRepository;
import mh.springboot.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleCachingDecoratorRepository roleCachingDecoratorRepository;

    @Autowired
    public UserService(UserRepository userRepository, RoleCachingDecoratorRepository roleCachingDecoratorRepository) {
        this.userRepository = userRepository;
        this.roleCachingDecoratorRepository = roleCachingDecoratorRepository;
    }

    public User createUserWithRoleUser(User user) {
        return createUser(user, ImmutableSet.of(RoleEnum.USER));
    }

    public User createUser(User user, Iterable<RoleEnum> roleEnums) {
        ImmutableSet.Builder<Role> userRoles = new ImmutableSet.Builder<>();
        for(RoleEnum role : roleEnums) {
            userRoles.add(roleCachingDecoratorRepository.getRole(role));
        }
        user.setRoles(userRoles.build());
        return userRepository.save(user);
    }

    public User findByExternalId(String externalId) {
        return userRepository.findByExternalId(externalId);
    }

    public Optional<User> getLoggedUser() {
        if (SecurityContextHolder.getContext() == null
            || SecurityContextHolder.getContext().getAuthentication() == null
            || SecurityContextHolder.getContext().getAuthentication().getPrincipal() == null) {
            return Optional.empty();
        }
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof User) {
            return Optional.of((User) principal);
        }
        return Optional.empty();
    }

}
