package com.jclz.fruit.dao;

import com.jclz.fruit.entity.OrderRefund;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface OrderRefundMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(OrderRefund record);

    int insertSelective(OrderRefund record);

    OrderRefund selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(OrderRefund record);

    int updateByPrimaryKey(OrderRefund record);

    OrderRefund selectByOrderNo(String orderNo);

    OrderRefund selectByOutRefundNo(String outRefundNo);

    List<OrderRefund> orderRefundList(@Param("fruitName") String fruitName,
                                      @Param("orderNo")String orderNo,
                                      @Param("start")Integer start,
                                      @Param("size")Integer size);


    Integer orderRefundListTotal(@Param("fruitName") String fruitName,
                                 @Param("orderNo")String orderNo,
                                 @Param("start")Integer start,
                                 @Param("size")Integer size);

    List<OrderRefund> orderRefundIsHandle(@Param("fruitName") String fruitName,
                                          @Param("orderNo")String orderNo,
                                          @Param("start")Integer start,
                                          @Param("size")Integer size);

    Integer orderRefundIsHandleTotal(@Param("fruitName") String fruitName,
                                     @Param("orderNo")String orderNo,
                                     @Param("start")Integer start,
                                     @Param("size")Integer size);

    List<OrderRefund> orderRefundHandle(@Param("fruitName") String fruitName,
                                        @Param("orderNo")String orderNo,
                                        @Param("start")Integer start,
                                        @Param("size")Integer size);

    Integer orderRefundHandleTotal(@Param("fruitName") String fruitName,
                                   @Param("orderNo")String orderNo,
                                   @Param("start")Integer start,
                                   @Param("size")Integer size);
}