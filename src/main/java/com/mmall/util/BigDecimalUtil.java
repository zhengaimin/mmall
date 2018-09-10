package com.mmall.util;

import java.math.BigDecimal;

/**
 * BigDecimalUtil的工具类,用来封装BigDecimal,
 * 将浮点型数据转化为字符型
 * @author Lenovo
 * 日期: 2018-08-03
 * 时间: 9:16
 */

public class BigDecimalUtil {

    //私有构造器
    private BigDecimalUtil(){}

    /**
     * 加法
     * @param v1
     * @param v2
     * @return
     */
    public static BigDecimal add(double v1, double v2){
        BigDecimal b1 = new BigDecimal(Double.toString(v1)) ;
        BigDecimal b2 = new BigDecimal(Double.toString(v2)) ;
        return b1.add(b2);
    }

    /**
     * 减法
     * @param v1
     * @param v2
     * @return
     */
    public static BigDecimal sub(double v1, double v2){
        BigDecimal b1 = new BigDecimal(Double.toString(v1)) ;
        BigDecimal b2 = new BigDecimal(Double.toString(v2)) ;
        return b1.subtract(b2);
    }

    /**
     * 乘法
     * @param v1
     * @param v2
     * @return
     */
    public static BigDecimal mul(double v1, double v2){
        BigDecimal b1 = new BigDecimal(Double.toString(v1)) ;
        BigDecimal b2 = new BigDecimal(Double.toString(v2)) ;
        return b1.multiply(b2);
    }

    /**
     * 除法,需要考虑除不尽的情况
     * @param v1
     * @param v2
     * @return
     */
    public static BigDecimal div(double v1, double v2){
        BigDecimal b1 = new BigDecimal(Double.toString(v1)) ;
        BigDecimal b2 = new BigDecimal(Double.toString(v2)) ;
        //保留两位小数,并且四舍五入
        return b1.divide(b2,2, BigDecimal.ROUND_HALF_UP );
    }
}
