package com.mmall.vo;

import java.math.BigDecimal;

/**
 * @author 郑爱民
 * 日期: 2018-08-02
 * 时间: 21:18
 */
public class CartProductVo {
    //结合产品和购物车的抽象对象

    //购物车Id
    private Integer id ;
    //用户id
    private Integer userId ;
    //商品Id
    private Integer productId ;
    //购物车商品数量
    private Integer quantity ;
    //商品名称
    private String productName ;
    //商品副标题
    private String productSubtitle ;
    //商品主图
    private String productMainImage ;
    //商品价格
    private BigDecimal productPrice ;
    //商品状态
    private Integer productStatus ;
    //商品的总价格
    private BigDecimal productTotalPrice ;
    //商品库存
    private Integer productStock ;
    //商品是否勾选
    private Integer productChecked ;
    //商品的限制的数量,加入购物车的数量不能超过商品总库存
    private String limitQuantity ;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductSubtitle() {
        return productSubtitle;
    }

    public void setProductSubtitle(String productSubtitle) {
        this.productSubtitle = productSubtitle;
    }

    public String getProductMainImage() {
        return productMainImage;
    }

    public void setProductMainImage(String productMainImage) {
        this.productMainImage = productMainImage;
    }

    public BigDecimal getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(BigDecimal productPrice) {
        this.productPrice = productPrice;
    }

    public Integer getProductStatus() {
        return productStatus;
    }

    public void setProductStatus(Integer productStatus) {
        this.productStatus = productStatus;
    }

    public BigDecimal getProductTotalPrice() {
        return productTotalPrice;
    }

    public void setProductTotalPrice(BigDecimal productTotalPrice) {
        this.productTotalPrice = productTotalPrice;
    }

    public Integer getProductStock() {
        return productStock;
    }

    public void setProductStock(Integer productStock) {
        this.productStock = productStock;
    }

    public Integer getProductChecked() {
        return productChecked;
    }

    public void setProductChecked(Integer productChecked) {
        this.productChecked = productChecked;
    }

    public String getLimitQuantity() {
        return limitQuantity;
    }

    public void setLimitQuantity(String limitQuantity) {
        this.limitQuantity = limitQuantity;
    }
}
