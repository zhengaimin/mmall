package com.mmall.util;


import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.Date;

/**
 * 时间转化工具类
 *
 * @author Lenovo
 * 日期: 2018-07-30
 * 时间: 9:56
 */
public class DateTimeUtil {

    //joda-time

    public static final String STANDARD_FORMAT = "yyyy-MM-dd HH:mm:ss";

    /**
     * 基于strToDate的重载 日期格式定死
     * @param dateTimeStr
     * @return
     */
    public static Date strToDate(String dateTimeStr) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern(STANDARD_FORMAT);
        //将时间转化为对应格式的DateTime类
        DateTime dateTime = dateTimeFormatter.parseDateTime(dateTimeStr);
        //返回日期类
        return dateTime.toDate();
    }

    /**
     * 基于 dateToStr的重载 日期格式定死
     * @param date
     * @return
     */
    public static String dateToStr(Date date) {
        if (date == null) {
            return StringUtils.EMPTY;
        }
        //将Date -> DateTime
        DateTime dateTime = new DateTime(date);
        //返回对应格式的字符串
        return dateTime.toString(STANDARD_FORMAT);
    }

    /**
     * str  -> DateTime -> Date 将字符串转化为date类
     * @param dateTimeStr
     * @param formatStr
     * @return
     */
    public static Date strToDate(String dateTimeStr, String formatStr) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern(formatStr);
        //将时间转化为对应格式的DateTime类
        DateTime dateTime = dateTimeFormatter.parseDateTime(dateTimeStr);
        //返回日期类
        return dateTime.toDate();
    }

    /**
     * Date -> DateTime -> str 将日期类转化为字符串
     * @param date
     * @param formatStr
     * @return
     */
    public static String dateToStr(Date date, String formatStr) {
        if (date == null) {
            return StringUtils.EMPTY;
        }
        //将Date -> DateTime
        DateTime dateTime = new DateTime(date);
        //返回对应格式的字符串
        return dateTime.toString(formatStr);
    }

    public static void main(String[] args) {
        System.out.println(new Date());
        System.out.println(DateTimeUtil.dateToStr(new Date(), "yyyy-MM-dd HH:mm:ss"));
        System.out.println(DateTimeUtil.strToDate("1111-11-11 11:11:11", "yyyy-MM-dd HH:mm:ss"));
    }

}
