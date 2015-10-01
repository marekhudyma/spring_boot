package mh.springboot.service.role;

import com.google.common.collect.ImmutableMap;
import mh.springboot.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;

import java.util.Map;

public class RoleCachingDecoratorService {

    @Autowired
    private RoleService roleService;

    public static final String FIND_ALL = "findAll";

    @Cacheable(value = FIND_ALL)
    public Map<Long, Role> findAll() {
        ImmutableMap.Builder<Long, Role> builder = ImmutableMap.builder();
        for(Role role : roleService.findAll()) {
            builder.put(role.getId(), role);
        }
        return builder.build();
    }

}
