package common.library;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.cache.CacheManager;

public class CacheManagerBeanPostProcessor implements BeanPostProcessor {
    private static final String CACHE_MANAGER_BEAN_NAME = "cachemanager";
    public CacheManagerBeanPostProcessor() {
        System.out.println("constructor");
    }
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (beanName.toLowerCase().equals(CACHE_MANAGER_BEAN_NAME)) {
            CacheManager cacheManager = new TenantCacheManager((CacheManager) bean);
            return cacheManager;
        }
        return bean;
    }

}
