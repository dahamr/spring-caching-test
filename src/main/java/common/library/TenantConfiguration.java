package common.library;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(name = "simulate.sharedlib", matchIfMissing=false)
public class TenantConfiguration {

  @Bean
  @ConditionalOnProperty(name = "simulate.keygenerator", matchIfMissing=false)
  public TenantKeyGenerator tenantKeyGenerator() {
      return new TenantKeyGenerator();
  }

  @Bean
  @ConditionalOnProperty(name = "simulate.beanpostprocessor", matchIfMissing=false)
  public CacheManagerBeanPostProcessor tenantCacheBeanPostProcessor() {
      return new CacheManagerBeanPostProcessor();
  }
}