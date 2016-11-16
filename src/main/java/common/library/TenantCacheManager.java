package common.library;

import java.util.Collection;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

public class TenantCacheManager implements CacheManager {

    private CacheManager delegate;

    public TenantCacheManager(CacheManager delegate) {
        this.delegate = delegate;
    }

    @Override
    public Cache getCache(String name) {
        Cache c = delegate.getCache(name);
        TenantCache tc = new TenantCache(c);
        return tc;
    }

    @Override
    public Collection<String> getCacheNames() {
        return this.delegate.getCacheNames();
    }

}
