package mh.springboot.config;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;

@EnableCaching
//@SpringBootApplication(exclude = { SecurityAutoConfiguration.class,
//                                   WebSecurityConfiguration.class})
@SpringBootApplication
@EnableWebSecurity
@EnableWebMvcSecurity

//@Configuration
//@ComponentScan
//@EnableAutoConfiguration(exclude={WebSecurityConfiguration.class})

public class TestConfig {

}
