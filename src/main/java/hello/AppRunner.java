package hello;

import java.util.Iterator;
import java.util.concurrent.ConcurrentMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.ehcache.EhCacheCache;
import org.springframework.cache.guava.GuavaCache;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;

@Component
public class AppRunner implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(AppRunner.class);

    private final BookRepository bookRepository;

    @Autowired
    private ApplicationContext appContext;

    public AppRunner(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        logger.info(".... Fetching books");
        logger.info("isbn-1234 -->" + bookRepository.getByIsbn("isbn-1234"));
        logger.info("isbn-1234 -->" + bookRepository.getByIsbn("isbn-1234"));
        logger.info("isbn-1234 -->" + bookRepository.getByIsbn("isbn-1234"));
        dumpCache("books");
    }

    public void dumpCache(String name) {
        // Dump the cache contents to verify the objekt is there
        try {
            CacheManager cacheManager = (CacheManager) appContext.getBean("cacheManager");
            Cache cache = cacheManager.getCache(name);

            if (cache instanceof GuavaCache) {
                logger.info(".... dumping google cache");
                com.google.common.cache.Cache<Object,Object> gcache = ((GuavaCache)cache).getNativeCache();
                ConcurrentMap<Object, Object> gmap = gcache.asMap();
                Iterator<Object> i = gmap.keySet().iterator();
                while (i.hasNext()) {
                    String key = (String)i.next();
                    logger.info("Key: {}, Value: {}", key, gmap.get(key));
                }
            } else if (cache instanceof EhCacheCache) {
                logger.info(".... dumping ehcache cache");
                Ehcache ncache = (Ehcache) cache.getNativeCache();
                for (Object key: ncache.getKeys()) {
                    Element element = ncache.get(key);
                    if (element != null) {
                        logger.info("Key: {}, Value: {}", element.getObjectKey(), element.getObjectValue());
                    }
                }
            } else {
                logger.info(">>>>> Unknown cache type" + cache.toString());
            }
        } catch (Exception e) {
            logger.info("Something failed", e);
        }

    }

}