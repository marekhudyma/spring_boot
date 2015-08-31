package mh.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class SpringBootMainApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootMainApplication.class, args);
    }
}
