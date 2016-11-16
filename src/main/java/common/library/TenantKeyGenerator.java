package common.library;

import java.lang.reflect.Method;

import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.util.StringUtils;

public class TenantKeyGenerator implements KeyGenerator {
    @Override
    public Object generate(Object o, Method method, Object... objects) {
        String tenant = "Tenant1";
        StringBuilder sb = new StringBuilder();
        if (!StringUtils.isEmpty(tenant)) {
            sb.append(tenant);
            sb.append("-");
        }
        for (Object obj : objects) {
            sb.append(obj.hashCode());
        }
        return sb.toString();
    }
}
