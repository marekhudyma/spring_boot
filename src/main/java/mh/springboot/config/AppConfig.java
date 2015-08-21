package mh.springboot.config;

import mh.springboot.dao.EntityService;
import mh.springboot.dao.EntityServiceInMemoryImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public EntityService entityService() {
        return new EntityServiceInMemoryImpl();
    }

}
