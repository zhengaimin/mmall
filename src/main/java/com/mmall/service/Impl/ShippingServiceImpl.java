package com.mmall.service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import com.mmall.common.ServerResponse;
import com.mmall.dao.ShippingMapper;
import com.mmall.pojo.Shipping;
import com.mmall.service.IShippingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Lenovo
 * 日期: 2018-08-04
 * 时间: 14:18
 */
@Service
public class ShippingServiceImpl implements IShippingService {

    @Autowired
    private ShippingMapper shippingMapper ;

    /**
     * 添加收货地址
     * @param userId
     * @param shipping
     * @return
     */
    @Override
    public ServerResponse add(Integer userId, Shipping shipping){
        //填写收货地址用户不会填写用户Id,需要自己读取
        shipping.setUserId(userId);
        int rowCount = shippingMapper.insert(shipping);
        if (rowCount > 0 ){
            Map result = Maps.newHashMap();
            result.put("shippingId", shipping.getId()) ;
            return ServerResponse.createBySuccess("新建地址成功", result) ;
        }
        return ServerResponse.createByErrorMessage("新建地址失败") ;
    }

    /**
     * 删除地址
     * @param userId
     * @param shippingId
     * @return
     */
    @Override
    public ServerResponse<String> del(Integer userId, Integer shippingId){
        //直接进行删除会有安全漏洞,需要重写一个SQl
        // int resultCount = shippingMapper.deleteByPrimaryKey(shippingId);
        int resultCount = shippingMapper.deleteByShippingIdUserId(userId,shippingId) ;
        if (resultCount > 0 ){
            return ServerResponse.createBySuccess("删除地址成功") ;
        }
        return ServerResponse.createByErrorMessage("删除地址失败") ;
    }

    /**
     * 更新地址
     * @param userId
     * @param shipping
     * @return
     */
    @Override
    public ServerResponse update(Integer userId, Shipping shipping){
        //防止传入假Id,越权访问
        shipping.setUserId(userId);
        int rowCount = shippingMapper.updateByShipping(shipping);
        if (rowCount > 0 ){
            return ServerResponse.createBySuccess("更新地址成功") ;
        }
        return ServerResponse.createByErrorMessage("更新地址失败") ;
    }

    /**
     * 查询地址
     * @param userId
     * @param shippingId
     * @return
     */
    @Override
    public ServerResponse<Shipping> select(Integer userId, Integer shippingId){
        Shipping shipping = shippingMapper.selectByshippingIdUserId(userId, shippingId);
        if (shipping == null){
            return ServerResponse.createByErrorMessage("无法查询到该地址") ;
        }
        return ServerResponse.createBySuccess("查询地址成功",shipping) ;
    }

    /**
     * 遍历地址,分页
     * @param userId
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public ServerResponse<PageInfo> list(Integer userId, int pageNum, int pageSize){
        PageHelper.startPage(pageNum,pageSize) ;
        List<Shipping> shippingList = shippingMapper.selectBuuserId(userId);
        PageInfo pageInfo = new PageInfo(shippingList) ;
        return ServerResponse.createBySuccess(pageInfo) ;
    }
}
