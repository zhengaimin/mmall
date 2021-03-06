package com.mmall.common;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * @author Lenovo
 * 日期: 2018-07-26
 * 时间: 9:30
 */
public class TokenCache {

    //日志
    private static Logger logger = LoggerFactory.getLogger(TokenCache.class);

    public static final String TOKEN_PREFIX = "token_" ;

    //本地缓存 LUR算法
    private static LoadingCache<String, String> localCache =
            CacheBuilder.newBuilder().initialCapacity(1000).maximumSize(10000).expireAfterAccess(12, TimeUnit.HOURS).
                    build(new CacheLoader<String, String>() {
                //默认的数据加载实现,当调用get取值的时候,如果key没有对应的值,就调用这个方法进行加载
                @Override
                public String load(String key) throws Exception {
                    //当key为null,调用key.equals() 时 会报空指针异常,多有返回字符串null
                    return "null";
                }
            });

    public static void setKey(String key, String value) {
        localCache.put(key, value);
        ;
    }

    public static String getKey(String key) {
        String value = null;
        try {
            value = localCache.get(key);
            if ("null".equals(value)) {
                return null;
            }
            return value ;
        } catch (Exception e) {
            logger.error("localCache get error", e);
        }
        return null ;
    }
}
