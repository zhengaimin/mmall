package com.mmall.service;

import com.github.pagehelper.PageInfo;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.Product;
import com.mmall.vo.ProductDetailVo;

/**
 * @author Lenovo
 * 日期: 2018-07-28
 * 时间: 19:59
 */
public interface IProductService {

    ServerResponse saveOrUpdata(Product product) ;

    ServerResponse<String> setSaleStatus(Integer productId, Integer status) ;

    ServerResponse<ProductDetailVo> manageProductDetail(Integer productId) ;

    ServerResponse<PageInfo> getProductList(int pageNum, int pageSize) ;

    ServerResponse<PageInfo> searchProduct(String productName, Integer productId, int pageNum, int pageSize) ;

    ServerResponse<ProductDetailVo> getProductDetail(Integer productId) ;

    ServerResponse<PageInfo> getProductByKeywordCategory(String keyword, Integer categoryId,
                                                         Integer pageNum, Integer pageSize, String orderBy) ;
}
