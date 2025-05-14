package pro.sky.starbankrecommendations.controller.dynamic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InfoCachesController {
    @Autowired
    private CacheManager cacheManager;

    @PostMapping("/management/clear-caches")
    public void clearCaches() {
        cacheManager.getCacheNames().forEach(cacheName -> cacheManager.getCache(cacheName).clear());
    }

}
