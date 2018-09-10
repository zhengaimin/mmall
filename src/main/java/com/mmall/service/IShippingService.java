package com.mmall.service;

import com.github.pagehelper.PageInfo;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.Shipping;

/**
 * @author Lenovo
 * 日期: 2018-08-04
 * 时间: 14:17
 */
public interface IShippingService {

    ServerResponse add(Integer userId, Shipping shipping) ;

    ServerResponse<String> del(Integer userId, Integer shippingId) ;

    ServerResponse update(Integer userId, Shipping shipping) ;

    ServerResponse<Shipping> select(Integer userId, Integer shippingId) ;

    ServerResponse<PageInfo> list(Integer userId, int pageNum, int pageSize) ;
}
