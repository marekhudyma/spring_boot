package mh.springboot.repository.role;

import com.google.common.collect.ImmutableMap;
import mh.springboot.model.security.Role;
import mh.springboot.model.security.RoleEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;

import java.util.Map;

public class RoleCachingDecoratorRepository {

    public static final String FIND_ALL = "findAll";
    private final RoleRepository roleRepository;

    @Autowired
    public RoleCachingDecoratorRepository(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Cacheable(value = FIND_ALL)
    public Map<Long, Role> findAll() {
        ImmutableMap.Builder<Long, Role> builder = ImmutableMap.builder();
        for(Role role : roleRepository.findAll()) {
            builder.put(role.getId(), role);
        }
        return builder.build();
    }

    public Role getRole(RoleEnum roleEnum) {
        Role role = findAll().get(roleEnum.getId());
        if(role == null) {
            throw new IllegalStateException("The role %s was ");
        }
        return role;
    }

}
