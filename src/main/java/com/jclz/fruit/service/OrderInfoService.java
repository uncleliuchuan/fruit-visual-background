package com.jclz.fruit.service;

import com.jclz.fruit.entity.OrderInfo;
import com.jclz.fruit.entity.Page;

import java.util.Map;

public interface OrderInfoService {
    Page<OrderInfo> orderInfoList(String fruitName, String orderNo, Integer orderStatus,Integer current, Integer size);

    Page<OrderInfo> signedList(String fruitName, String orderNo, Integer current, Integer size);

    Map<String, Object> deliverGoods(String orderNo) throws Exception;

    Page<OrderInfo> billList(String fruitName, String orderNo, Integer current, Integer size);
}
