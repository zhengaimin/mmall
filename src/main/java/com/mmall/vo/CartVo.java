package com.mmall.vo;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author Lenovo
 * 日期: 2018-08-02
 * 时间: 21:34
 */
public class CartVo {

    //购物车中有多个商品
    private List<CartProductVo> cartProductVoList ;
    private BigDecimal cartTotalPrice ;
    private Boolean allChecked ;//是否全部勾选
    private String imageHost ;

    public List<CartProductVo> getCartProductVoList() {
        return cartProductVoList;
    }

    public void setCartProductVoList(List<CartProductVo> cartProductVoList) {
        this.cartProductVoList = cartProductVoList;
    }

    public BigDecimal getCartTotalPrice() {
        return cartTotalPrice;
    }

    public void setCartTotalPrice(BigDecimal cartTotalPrice) {
        this.cartTotalPrice = cartTotalPrice;
    }

    public Boolean getAllChecked() {
        return allChecked;
    }

    public void setAllChecked(Boolean allChecked) {
        this.allChecked = allChecked;
    }

    public String getImageHost() {
        return imageHost;
    }

    public void setImageHost(String imageHost) {
        this.imageHost = imageHost;
    }
}
