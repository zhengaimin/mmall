package com.mmall.dao;

import com.mmall.pojo.Order;
import com.mmall.pojo.OrderItem;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrderItemMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(OrderItem record);

    int insertSelective(OrderItem record);

    OrderItem selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(OrderItem record);

    int updateByPrimaryKey(OrderItem record);

    List<OrderItem> getByOrderNoUserId(@Param("orserNo") Long orserNo, @Param("userId") Integer userId) ;

    List<OrderItem> getByOrderNo(@Param("orserNo") Long orserNo) ;

    void batchInsert(@Param("orderItemList") List<OrderItem> orderItemList) ;


}