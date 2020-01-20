package com.jclz.fruit.dao;

import com.jclz.fruit.entity.OrderInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface OrderInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(OrderInfo record);

    int insertSelective(OrderInfo record);

    OrderInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(OrderInfo record);

    int updateByPrimaryKey(OrderInfo record);

    OrderInfo selectByOrderNo(String orderNo);

    int updateOrderStatus(OrderInfo o);

    List<OrderInfo> orderInfoList(@Param("fruitName") String fruitName,
                                  @Param("orderNo")String orderNo,
                                  @Param("orderStatus")Integer orderStatus,
                                  @Param("start")Integer start,
                                  @Param("size")Integer size);

    int orderInfoListTotal(@Param("fruitName") String fruitName,
                           @Param("orderNo")String orderNo,
                           @Param("orderStatus")Integer orderStatus,
                           @Param("start")Integer start,
                           @Param("size")Integer size);

    List<OrderInfo> signedList(@Param("fruitName") String fruitName,
                               @Param("orderNo")String orderNo,
                               @Param("start")Integer start,
                               @Param("size")Integer size);

    int signedListTotal(@Param("fruitName") String fruitName,
                        @Param("orderNo")String orderNo,
                        @Param("start")Integer start,
                        @Param("size")Integer size);

    List<OrderInfo> billList(@Param("fruitName") String fruitName,
                             @Param("orderNo")String orderNo,
                             @Param("start")Integer start,
                             @Param("size")Integer size);

    int billListTotal(@Param("fruitName") String fruitName,
                          @Param("orderNo")String orderNo,
                          @Param("start")Integer start,
                          @Param("size")Integer size);
}