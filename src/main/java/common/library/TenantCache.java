package common.library;

import java.util.Objects;
import java.util.concurrent.Callable;

import org.springframework.cache.Cache;

public class TenantCache implements Cache {
    private Cache delegate;

    public TenantCache(Cache cache) {
        delegate = cache;
    }

    public Cache getDelegate() {
        return delegate;
    }

    @Override
    public String getName() {
        return delegate.getName();
    }

    @Override
    public Object getNativeCache() {
        return delegate.getNativeCache();
    }

    @Override
    public ValueWrapper get(Object key) {
        Object tenantKey = genTenantKey(key);
        return delegate.get(tenantKey);
    }

    @Override
    public <T> T get(Object key, Class<T> type) {
        Object tenantKey = genTenantKey(key);
        return delegate.get(tenantKey, type);
    }

    @Override
    public <T> T get(Object key, Callable<T> valueLoader) {
        Object tenantKey = genTenantKey(key);
        return delegate.get(tenantKey, valueLoader);
    }

    @Override
    public void put(Object key, Object value) {
        Object tenantKey = genTenantKey(key);
        delegate.put(tenantKey, value);
    }

    @Override
    public ValueWrapper putIfAbsent(Object key, Object value) {
        Object tenantKey = genTenantKey(key);
        return delegate.putIfAbsent(tenantKey, value);
    }

    @Override
    public void evict(Object key) {
        Object tenantKey = genTenantKey(key);
        delegate.evict(tenantKey);
    }

    @Override
    public void clear() {
        delegate.clear();
    }

    private TenantKey genTenantKey(Object key) {
        String tenant = "Tenant1";
        return new TenantKey(tenant, key);
    }

    static class TenantKey {
        /* (non-Javadoc)
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString() {
            return "[tenant=" + tenant + ":key=" + key + "]";
        }

        private final String tenant;
        private final Object key;

        /**
         * null values are ok
         */
        public TenantKey(final String tenant, Object key) {
            this.tenant = tenant;
            this.key = key;
        }

        @Override
        public boolean equals(Object o) {
            if (!(o instanceof TenantKey)) {
                return false;
            }
            TenantKey that = (TenantKey)o;
            return Objects.equals(this.tenant, that.tenant) &&
                   Objects.equals(this.key, that.key);
        }

        @Override
        public int hashCode() {
            return Objects.hash(this.tenant, this.key);
        }
    }


}
