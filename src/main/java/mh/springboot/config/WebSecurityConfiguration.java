package mh.springboot.config;

import mh.springboot.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
@Import(SecurityAutoConfiguration.class)
@Profile("!test") //it would be skipped for profile "test"
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserRepository userRepository;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userRepository);
    }

//to use in-memory-db
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication().withUser("user").password("password").roles("USER");
//    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .csrf().disable() //TODO CHANGE IT !!!
            .authorizeRequests()
            .antMatchers("/api/sampleentity").authenticated()

            .antMatchers("/page_user.html").hasRole("USER")
            .antMatchers("/page_admin.html").hasRole("ADMIN")
            .and().formLogin().loginPage("/custom_login.html").permitAll()
            .and().logout().permitAll().logoutUrl("/logout")
            .logoutSuccessUrl("/");
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

//TODO add hash of password
//    @Override
//    public void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth
//                .userDetailsService(userDetailsService)
//                .passwordEncoder(new BCryptPasswordEncoder());
//    }
}
