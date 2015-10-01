package mh.springboot.service.role;

import mh.springboot.model.Role;
import org.springframework.data.repository.CrudRepository;

public interface RoleService extends CrudRepository<Role, Long> {
}
