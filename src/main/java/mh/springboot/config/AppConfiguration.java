package mh.springboot.config;

import com.google.common.cache.CacheBuilder;
import mh.springboot.service.role.RoleCachingDecoratorService;
import mh.springboot.service.sampleentity.SampleEntityCachingDecoratorService;
import org.springframework.cache.CacheManager;
import org.springframework.cache.guava.GuavaCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

@Configuration
public class AppConfiguration {

    @Bean
    public CacheManager cacheManager() {
        SimpleCacheManager cacheManager = new SimpleCacheManager();

        GuavaCache cacheOne = new GuavaCache(SampleEntityCachingDecoratorService.FIND_ONE,
                                           CacheBuilder.newBuilder()
                                                       .expireAfterAccess(30, TimeUnit.MINUTES)
                                                       .build());
        GuavaCache cacheAll = new GuavaCache(SampleEntityCachingDecoratorService.FIND_ALL,
                                           CacheBuilder.newBuilder()
                                                       .expireAfterAccess(30, TimeUnit.MINUTES)
                                                       .build());
        cacheManager.setCaches(Arrays.asList(cacheOne, cacheAll));
        return cacheManager;
    }

    @Bean(name = "RoleCachingDecoratorService")
    public RoleCachingDecoratorService roleCachingDecoratorService() {
        return new RoleCachingDecoratorService();
    }

    @Bean(name = "SampleEntityCachingDecoratorService")
    public SampleEntityCachingDecoratorService sampleEntityCachingDecoratorService() {
        return new SampleEntityCachingDecoratorService();
    }

//    @Bean
//    public EmbeddedServletContainerCustomizer containerCustomizer() {
//        return container -> container.addErrorPages(new ErrorPage(HttpStatus.NOT_FOUND, "/404.html"));
//    }

}
