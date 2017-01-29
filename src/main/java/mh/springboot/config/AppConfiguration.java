package mh.springboot.config;

import com.google.common.cache.CacheBuilder;
import mh.springboot.repository.role.RoleCachingDecoratorRepository;
import mh.springboot.repository.role.RoleRepository;
import mh.springboot.repository.sampleentity.SampleEntityCachingDecoratorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.web.servlet.ErrorPage;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.guava.GuavaCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

@Configuration
@EnableCaching
public class AppConfiguration {

    @Autowired
    private RoleRepository roleRepository;

    @Bean
    public CacheManager cacheManager() {
        SimpleCacheManager cacheManager = new SimpleCacheManager();

        GuavaCache cacheOne = new GuavaCache(SampleEntityCachingDecoratorRepository.FIND_ONE,
                                           CacheBuilder.newBuilder()
                                                       .expireAfterAccess(30, TimeUnit.MINUTES)
                                                       .build());
        GuavaCache cacheAll = new GuavaCache(SampleEntityCachingDecoratorRepository.FIND_ALL,
                                           CacheBuilder.newBuilder()
                                                       .expireAfterAccess(30, TimeUnit.MINUTES)
                                                       .build());
        cacheManager.setCaches(Arrays.asList(cacheOne, cacheAll));
        return cacheManager;
    }

    @Bean(name = "RoleCachingDecoratorRepository")
    public RoleCachingDecoratorRepository roleCachingDecoratorService() {
        return new RoleCachingDecoratorRepository(roleRepository);
    }

    //add custom error pages
    @Bean
    public EmbeddedServletContainerCustomizer containerCustomizer() {
        return new EmbeddedServletContainerCustomizer() {
            @Override
            public void customize(ConfigurableEmbeddedServletContainer container) {

                //ErrorPage error401Page = new ErrorPage(HttpStatus.UNAUTHORIZED, "/401.html");
                ErrorPage error404Page = new ErrorPage(HttpStatus.NOT_FOUND, "/404");
                //ErrorPage error500Page = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/500.html");
                ErrorPage error403Page = new ErrorPage(HttpStatus.FORBIDDEN, "/403");

                container.addErrorPages(error403Page, error404Page);
            }
        };
    }

}
