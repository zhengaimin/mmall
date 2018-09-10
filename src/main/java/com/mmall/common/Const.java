package com.mmall.common;

import com.google.common.collect.Sets;

import java.util.Set;

/**
 * @author 郑爱民
 * 日期: 2018-07-25
 * 时间: 19:49
 */
public class Const {

    //当前用户 做为session的关键字
    public static final String CURRENT_USER = "currentUer" ;

    public static final String EMAIL = "email" ;

    public static final String USERNAME= "username" ;

    public interface Role{
        int ROLE_CUSTOMER = 0 ;     //普通用户
        int ROLE_ADMIN = 1 ;        //管理员
    }

    public interface Cart{
        int CHECKED = 1 ;       //购物车选中状态
        int UN_CHECKED = 0 ;    //购物车未选中

        String LIMIT_NUM_FAIL = "LIMIT_NUM_FAIL" ;  // 库存限制失败
        String LIMIT_NUM_SUCCESS = "LIMIT_NUM_SUCCESS" ;    //库存限制成功

    }

    public interface ProductListOrderBy{
        Set<String> PRICE_ASC_DESC = Sets.newHashSet("price_desc","price_asc") ;
    }


    public enum ProductStatusEnum{
        ON_SALE(1,"在线");

        private String value ;
        private int code ;

        ProductStatusEnum(int code, String value ) {
            this.value = value;
            this.code = code;
        }

        public String getValue() {
            return value;
        }

        public int getCode() {
            return code;
        }
    }

    public enum OrderStatusEnum{
        CANCELED(0,"已取消"),
        NO_PAY(10,"未支付"),
        PAID(20,"已付款"),
        SHIPPED(40,"已发货"),
        ORDER_SUCCESS(50,"订单交易完成"),
        ORDER_CLOSE(60,"订单交易关闭")
        ;
        private String value ;
        private int code ;

        OrderStatusEnum(int code, String value) {
            this.value = value;
            this.code = code;
        }

        public String getValue() {
            return value;
        }

        public int getCode() {
            return code;
        }

        //将code以文字的形式展示
        public static OrderStatusEnum codeOf(int code){
            for (OrderStatusEnum orderStatusEnum : OrderStatusEnum.values()){
                if (orderStatusEnum.getCode() == code){
                    return orderStatusEnum ;
                }
            }
            throw new RuntimeException("没有找到对应的枚举") ;
        }
    }

    public interface AlipayCallback{
        String TRADE_STATUS_WAIT_BUYER_PAY = "WAIT_BUYER_PAY" ;  //等待卖家支付
        String TRADE_STATUS_TRADE_SUCCESS = "TRADE_SUCCESS" ;    //交易成功
        String RESPONSE_SUCCESS = "success" ;       //响应成功
        String RESPONSE_FAILED = "failed" ;         //响应失败
    }

    //支付平台
    public enum PayPlatformEnum{
        ALIPAY(1,"支付宝") ;
        ;
        private String value ;
        private int code ;

        PayPlatformEnum(int code, String value) {
            this.value = value;
            this.code = code;
        }

        public String getValue() {
            return value;
        }

        public int getCode() {
            return code;
        }
    }


    public enum PaymentTypeEnum{
        ONLINE_PAY(1,"在线支付")
        ;
        private String value ;
        private int code ;

        PaymentTypeEnum(int code, String value) {
            this.value = value;
            this.code = code;
        }

        public String getValue() {
            return value;
        }

        public int getCode() {
            return code;
        }

        //将code以文字的形式展示
        public static PaymentTypeEnum codeOf(int code){
            for (PaymentTypeEnum paymentTypeEnum : PaymentTypeEnum.values()){
                if (paymentTypeEnum.getCode() == code){
                    return paymentTypeEnum ;
                }
            }
            throw new RuntimeException("没有找到对应的枚举") ;
        }
    }




}
