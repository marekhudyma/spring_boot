package mh.springboot.service.user;

import mh.springboot.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserService extends CrudRepository<User, Long> { //, UserDetailsService { TODO HUDYMA - REMOVE SECURITY

    User findByLogin(String login);

    //TODO HUDYMA - REMOVE SECURITY
//    @Override
//    @Query("SELECT u FROM User u WHERE u.login = LOWER(:username)")
//    User loadUserByUsername(@Param("username") String username) throws UsernameNotFoundException;
}
