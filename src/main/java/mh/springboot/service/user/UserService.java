package mh.springboot.service.user;

import mh.springboot.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserService extends CrudRepository<User, Long>, UserDetailsService {

    User findByLogin(String login);

    @Override
    @Query("SELECT u FROM User u WHERE u.login = LOWER(:username)")
    User loadUserByUsername(@Param("username") String username) throws UsernameNotFoundException;
}
