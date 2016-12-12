package mh.springboot.config;


import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

/**
 * I added this class, because in the test I exclude {@link WebSecurityConfiguration} and I have exception:
 * (it started to occurre after adding "spring-boot-starter-actuator" to pom.xml)
 *
 * java.lang.IllegalStateException: Failed to load ApplicationContext [...]
 * Caused by: org.springframework.beans.factory.UnsatisfiedDependencyException: Error creating bean with name
 * 'org.springframework.boot.actuate.autoconfigure.ManagementWebSecurityAutoConfiguration$ManagementWebSecurityConfigurerAdapter':
 * Unsatisfied dependency expressed through method 'setObjectPostProcessor' parameter 0; nested exception is
 * org.springframework.beans.factory.NoSuchBeanDefinitionException: No qualifying bean of type
 * 'org.springframework.security.config.annotation.ObjectPostProcessor<java.lang.Object>' available:
 * expected at least 1 bean which qualifies as autowire candidate. Dependency annotations: {}
 */
@EnableWebSecurity
public class WebSecurityConfigurationForTests {

}
