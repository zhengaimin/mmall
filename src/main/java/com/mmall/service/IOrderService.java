package com.mmall.service;

import com.github.pagehelper.PageInfo;
import com.mmall.common.ServerResponse;
import com.mmall.vo.OrderVo;

import java.util.Map;

/**
 * Created by geely
 */
public interface IOrderService {

    ServerResponse pay(Long orderNo, Integer userId, String path) ;

    ServerResponse aliCallback(Map<String,String> params) ;

    ServerResponse<Boolean> queryOrderPayStatus(Integer userId, Long orderNo) ;

    ServerResponse createOrder(Integer userId, Integer shippingId) ;

    ServerResponse<String> cancel(Integer userId, Long orderNo) ;

    ServerResponse getOrderCartProduct(Integer userId) ;

    ServerResponse<OrderVo> getOrderDetail(Integer userId, Long orderNo) ;

    ServerResponse<PageInfo> getOrderList(Integer userId,int pageNum, int pageSize);

    //backend
    ServerResponse<PageInfo> manageList(int pageNum, int pageSize) ;

    ServerResponse<OrderVo> manageDetail(Long orderNO) ;

    ServerResponse<PageInfo> manageSearch(Long orderNO, int pageNum, int pageSize) ;

    ServerResponse<String> manageSendGoods(Long orderNo) ;
}
