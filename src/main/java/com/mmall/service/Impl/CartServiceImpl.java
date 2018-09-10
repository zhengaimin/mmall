package com.mmall.service.Impl;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.mmall.common.Const;
import com.mmall.common.ResponseCode;
import com.mmall.common.ServerResponse;
import com.mmall.dao.CartMapper;
import com.mmall.dao.ProductMapper;
import com.mmall.pojo.Cart;
import com.mmall.pojo.Product;
import com.mmall.service.ICartService;
import com.mmall.util.BigDecimalUtil;
import com.mmall.util.PropertiesUtil;
import com.mmall.vo.CartProductVo;
import com.mmall.vo.CartVo;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 郑爱民
 * 日期: 2018-08-02
 * 时间: 20:57
 */
@Service
public class CartServiceImpl implements ICartService {

    @Autowired
    private CartMapper cartMapper;

    @Autowired
    private ProductMapper productMapper;

    /**
     * 将商品添加到购物车
     *
     * @param userId
     * @param productId
     * @param count
     * @return
     */
    @Override
    public ServerResponse<CartVo> add(Integer userId, Integer productId, Integer count) {
        if (productId == null || count == null) {
            //如果商品Id或者商品count为null则显示参数错误
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(), ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }

        //通过userId和productId检查购物车中是否有该商品
        Cart cart = cartMapper.selectCartByUserIdProductId(userId, productId);
        if (cart == null) {
            //商品不在购物车,需要新增一个产品的记录
            Cart cartItem = new Cart();
            //设置购物车属性
            cartItem.setQuantity(count);
            cartItem.setChecked(Const.Cart.CHECKED);
            cartItem.setProductId(productId);
            cartItem.setUserId(userId);
            //添加到数据库
            cartMapper.insert(cartItem);
        } else {
            //产品已存在,购物车里数量相加
            count = cart.getQuantity() + count;
            cart.setQuantity(count);
            //更新数据库信息
            cartMapper.updateByPrimaryKeySelective(cart);
        }
        return this.list(userId) ;
    }

    /**
     * 更新购物车信息
     *
     * @param userId
     * @param productId
     * @param count
     * @return
     */
    @Override
    public ServerResponse<CartVo> update(Integer userId, Integer productId, Integer count) {
        if (productId == null || count == null) {
            //如果商品Id或者商品count为null则显示参数错误
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(), ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        Cart cart = cartMapper.selectCartByUserIdProductId(userId, productId);
        if (cart != null) {
            cart.setQuantity(count);
        }
        cartMapper.updateByPrimaryKeySelective(cart);
        return this.list(userId) ;
    }

    /**
     * 删除购物车中的商品
     * @param userId
     * @param productIds
     * @return
     */
    @Override
    public ServerResponse<CartVo> deleteProduct(Integer userId, String productIds) {
        //guava 用","分割productIds添加到List集合中
        List<String> productList = Splitter.on(",").splitToList(productIds);
        if (CollectionUtils.isEmpty(productList)) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(), ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        cartMapper.deleteByUserIdProductIds(userId,productList) ;
        return this.list(userId) ;
    }

    /**
     * 查找购物车商品
     * @param userId
     * @return
     */
    @Override
    public ServerResponse<CartVo> list(Integer userId){
        CartVo cartVoLimit = this.getCartVoLimit(userId);
        return ServerResponse.createBySuccess(cartVoLimit);
    }

    /**
     * 选择或者反选所有
     * @param userId
     * @return
     */
    @Override
    public ServerResponse<CartVo> selectOrUnSelect(Integer userId, Integer checked,Integer productId){
        cartMapper.checkedOrUncheckedProduct(userId,checked,productId) ;
        return this.list(userId) ;
    }

    /**
     * 查询当前用户的购物车里面的产品数量
     * @param userId
     * @return
     */
    @Override
    public ServerResponse<Integer> getCartProductCount(Integer userId){
        if (userId == null) {
            return ServerResponse.createBySuccess(0) ;
        }
        return ServerResponse.createBySuccess(cartMapper.selectCartProductCount(userId));
    }




    /**
     * 通过用户Id找到购物车的商品,然后更新购物车并遍历商品
     *
     * @param userId
     * @return
     */
    private CartVo getCartVoLimit(Integer userId) {
        //CartVo类提供给前端
        CartVo cartVo = new CartVo();
        //通过用户Id查找购物车的商品信息
        List<Cart> cartList = cartMapper.selectCartByUserId(userId);

        List<CartProductVo> cartProductVoList = Lists.newArrayList();
        //设置购物车中总价为0
        BigDecimal cartTotalPrice = new BigDecimal("0");

        if (CollectionUtils.isNotEmpty(cartList)) {
            //循环,将Cart和Product类的属性整合到CartProductVo中
            for (Cart cartItem : cartList) {
                CartProductVo cartProductVo = new CartProductVo();
                //设置属性
                cartProductVo.setId(cartItem.getId());
                cartProductVo.setUserId(cartItem.getUserId());
                cartProductVo.setProductId(cartItem.getProductId());

                //通过商品Id查找商品信息
                Product product = productMapper.selectByPrimaryKey(cartItem.getProductId());
                if (product != null) {
                    cartProductVo.setProductMainImage(product.getMainImage());
                    cartProductVo.setProductName(product.getName());
                    cartProductVo.setProductSubtitle(product.getSubtitle());
                    cartProductVo.setProductStatus(product.getStatus());
                    cartProductVo.setProductPrice(product.getPrice());
                    cartProductVo.setProductStock(product.getStock());
                    //判断库存
                    int buyLimitCount = 0;
                    if (product.getStock() >= cartItem.getQuantity()) {
                        //商品库存大于购物车商品数量
                        buyLimitCount = cartItem.getQuantity() ;
                        cartProductVo.setLimitQuantity(Const.Cart.LIMIT_NUM_SUCCESS);
                    } else {
                        //商品库存小于购物车商品数量
                        buyLimitCount = product.getStock();
                        cartProductVo.setLimitQuantity(Const.Cart.LIMIT_NUM_FAIL);
                        //购物车更新有效库存
                        Cart cartForQuantity = new Cart();
                        cartForQuantity.setId(cartItem.getId());
                        cartForQuantity.setQuantity(buyLimitCount);
                        //更新购物车商品数量
                        cartMapper.updateByPrimaryKeySelective(cartForQuantity);
                    }
                    cartProductVo.setQuantity(buyLimitCount);
                    //计算总价
                    cartProductVo.setProductTotalPrice(BigDecimalUtil.mul(product.getPrice().doubleValue(), cartProductVo.getQuantity()));
                    //设置勾选状态 1--勾选  2--未勾选
                    cartProductVo.setProductChecked(cartItem.getChecked());
                }

                if (cartItem.getChecked() == Const.Cart.CHECKED) {
                    //商品已勾选,增加到整个购物车的总价中
                    cartTotalPrice = BigDecimalUtil.add(cartTotalPrice.doubleValue(), cartProductVo.getProductTotalPrice().doubleValue());
                }
                cartProductVoList.add(cartProductVo);
            }
        }
        //填充cartVo类
        cartVo.setCartProductVoList(cartProductVoList);
        cartVo.setCartTotalPrice(cartTotalPrice);
        //设置是否全选
        cartVo.setAllChecked(this.getAllCheckedStatus(userId));
        cartVo.setImageHost(PropertiesUtil.getProperty("ftp.server.http.prefix"));
        return cartVo;
    }


    /**
     * 得到是否全选的方法,封装
     *
     * @param userId
     * @return
     */
    private boolean getAllCheckedStatus(Integer userId) {
        if (userId == null) {
            return false;
        }
        return cartMapper.selectCartProductCheckedStatusByUserId(userId) == 0;
    }


}
