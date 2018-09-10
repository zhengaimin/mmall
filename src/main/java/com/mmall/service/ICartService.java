package com.mmall.service;

import com.mmall.common.ServerResponse;
import com.mmall.vo.CartVo;

/**
 * @author Lenovo
 * 日期: 2018-08-02
 * 时间: 20:56
 */
public interface ICartService {

    ServerResponse<CartVo> add(Integer userId, Integer productId, Integer count);

    ServerResponse<CartVo> update(Integer userId, Integer productId, Integer count);

    ServerResponse<CartVo> deleteProduct(Integer userId, String productIds) ;

    ServerResponse<CartVo> list(Integer userId) ;

    ServerResponse<CartVo> selectOrUnSelect(Integer userId, Integer checked,Integer productId) ;

    ServerResponse<Integer> getCartProductCount(Integer userId) ;
}
