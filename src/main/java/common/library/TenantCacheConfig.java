package common.library;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TenantCacheConfig extends CachingConfigurerSupport {

    @Autowired(required=false)
    TenantKeyGenerator tenantKeyGenerator;

    @Override
    @Bean
    public KeyGenerator keyGenerator() {
        return tenantKeyGenerator;
    }
}