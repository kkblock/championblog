package com.champion.blog.utils;

import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.xml.XmlConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

public class EhcacheUtils {

    private static final Logger log = LoggerFactory.getLogger(EhcacheUtils.class);

    private static CacheManager cacheManager;

    public EhcacheUtils() {
        if (Objects.isNull(cacheManager)) {
            synchronized (this) {
                log.info("[Ehcache配置初始化<开始>]");
                // 配置默认缓存属性
                cacheManager = CacheManagerBuilder.newCacheManager(new XmlConfiguration(getClass().getResource("/ehcache.xml")));
                cacheManager.init();
                log.info("[Ehcache配置初始化<完成>]");
            }
        }
    }

    public CacheManager getInstance(){
        return cacheManager;
    }

    public void close(){
        if (Objects.nonNull(cacheManager)) {
            cacheManager.close();
        }
    }
    public static EhcacheUtils build(){
        return new EhcacheUtils();
    }

    public Cache<String,String> getminiCache(){
        return cacheManager.getCache("defaultCache", String.class, String.class);
    }
}
