package com.mmall.test;

import org.junit.Test;

import java.math.BigDecimal;

/**
 * @author Lenovo
 * 日期: 2018-08-03
 * 时间: 8:57
 */

public class BigDecimalTest {

    //测试BigDecimal
    @Test
    public void testBigDecimal(){
        System.out.println("0.05+0.01 = " + (0.05 + 0.01));
        System.out.println("1.0-0.42 = " + (1.0 - 0.42));
        System.out.println("4.015 * 100 = " + (4.015 * 100));
        System.out.println("123.3/100 = " + (123.3 / 100));

        BigDecimal b1 = new BigDecimal("0.05") ;
        BigDecimal b2 = new BigDecimal("0.01") ;
        System.out.println((0.05 + 0.01));
        System.out.println(b1.add(b2));
    }

}
