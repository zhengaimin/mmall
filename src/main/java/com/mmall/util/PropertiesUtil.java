package com.mmall.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * 配置文件
 * Created by geely
 */
public class PropertiesUtil {

    private static Logger logger = LoggerFactory.getLogger(PropertiesUtil.class) ;

    private static Properties props ;

    static {
        String fileName = "mmall.properties";
        props = new Properties() ;
        try {
            //得到类装载器,再通过fileName获得资源流,最终获得mmall.properties下的参数信息
            props.load(new InputStreamReader(PropertiesUtil.class.getClassLoader().getResourceAsStream(fileName),"UTF-8"));
        } catch (IOException e) {
            logger.error("配置文件读取异常",e);
        }
    }

    public static String getProperty(String key){
        String value = props.getProperty(key.trim());
        if(StringUtils.isBlank(value)){
            return null ;
        }
        return value.trim() ;
    }

    public static String getProperty(String key,String defaultValue){
        //获取盐值
        String value = props.getProperty(key.trim());
        if(StringUtils.isBlank(value)){
            return defaultValue ;
        }
        return value.trim() ;
    }

}
