package mh.springboot.repository.user;

import mh.springboot.model.security.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserRepository extends CrudRepository<User, Long> , UserDetailsService {

    User findByLogin(String login);

    /**
     * read user by login
     * @param username login of user
     * @return User
     * @throws UsernameNotFoundException
     */
    @Override
    @Query("SELECT u FROM User u WHERE u.login = LOWER(:username)")
    User loadUserByUsername(@Param("username") String username) throws UsernameNotFoundException;

    /**
     * read user by externalID (OAUTH2)
     * @param externalId
     * @return User
     * @throws UsernameNotFoundException
     */
    User findByExternalId(String externalId) throws UsernameNotFoundException;
}
