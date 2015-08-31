package mh.springboot.config;

import mh.springboot.dao.SampleEntityCachingDecoratorService;
import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfiguration {

    @Bean
    public CacheManager cacheManager() {
        return new ConcurrentMapCacheManager(SampleEntityCachingDecoratorService.FIND_ONE,
                                             SampleEntityCachingDecoratorService.FIND_ALL);
    }

    @Bean(name = "SampleEntityCachingDecoratorService")
    public SampleEntityCachingDecoratorService sampleEntityCachingDecoratorService() {
        return new SampleEntityCachingDecoratorService();
    }

}
