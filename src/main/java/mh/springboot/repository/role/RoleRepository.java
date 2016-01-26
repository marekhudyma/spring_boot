package mh.springboot.repository.role;

import mh.springboot.model.security.Role;
import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends CrudRepository<Role, Long> {
}
