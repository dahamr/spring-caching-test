package hello;

import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.ehcache.EhCacheCache;
import org.springframework.context.ApplicationContext;

import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;

@SpringBootTest
public class TestCaching {

    @Autowired
    private ApplicationContext appContext;

    @Autowired
    private BookRepository bookRepository;

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testCacheIsPopulated() throws Exception {
        bookRepository.getByIsbn("isbn-1234");
        try {
            CacheManager cacheManager = (CacheManager) appContext.getBean("cacheManager");
            Cache cache = cacheManager.getCache("objekts");

            if (cache instanceof EhCacheCache) {
                System.out.println("ehcache cache");
                Ehcache ncache = (Ehcache) cache.getNativeCache();
                for (Object key: ncache.getKeys()) {
                    Element element = ncache.get(key);
                    if (element != null) {
                      System.out.println("Key " + element.getObjectKey());
                      System.out.println("KeyValue " + element.getObjectValue());
                    }
                }
                assertTrue(ncache.getKeys().size()>0);

            } else {
                //logger.info("Unknown cache type" + cache.toString());
            }
        } catch (Exception e) {
            //no nothing and move on
        }

    }

}
