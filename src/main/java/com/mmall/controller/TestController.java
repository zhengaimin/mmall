package com.mmall.controller;

import com.mmall.common.TokenCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.MessageFormat;
import java.util.concurrent.ExecutionException;

/**
 * @author Lenovo
 * 日期: 2018-08-06
 * 时间: 16:36
 */
@RequestMapping("/test")
public class TestController {

    private Logger logger = LoggerFactory.getLogger(TestController.class) ;

    @RequestMapping("/set_cache.do")
    @ResponseBody
    public String setCache(String key, String value){
        TokenCache.setKey(key, value);
        return MessageFormat.format("set key:{0},value:{1}.ok",key, value) ;
    }

    @RequestMapping("/get_cache.do")
    @ResponseBody
    public String getCache(String key) throws ExecutionException{
        return TokenCache.getKey(key) ;
    }

    @RequestMapping("/test.do")
    @ResponseBody
    public  String test(String str){
        logger.info("testinfo");
        logger.warn("testwarn");
        logger.error("testerror");
        return "testValue;" + str ;
    }
}
